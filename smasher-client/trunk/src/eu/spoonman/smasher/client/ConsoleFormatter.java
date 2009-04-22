/*
 * This file is part of Smasher.
 * Copyright 2008, 2009 Tomasz 'SpOOnman' Kalkosiński <spoonman@op.pl>
 * 
 * Smasher is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Smasher is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Smasher.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.spoonman.smasher.client;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.Seconds;

import eu.spoonman.smasher.common.Pair;
import eu.spoonman.smasher.output.OutputConfiguration;
import eu.spoonman.smasher.output.OutputStyle;
import eu.spoonman.smasher.scorebot.Scorebot;
import eu.spoonman.smasher.scorebot.ServerInfoScorebot;
import eu.spoonman.smasher.serverinfo.GameInfo;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ProgressInfo;
import eu.spoonman.smasher.serverinfo.RoundInfo;
import eu.spoonman.smasher.serverinfo.TeamInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;
import eu.spoonman.smasher.serverinfo.TimePeriodInfo;

/**
 * Default console formatter.
 * 
 * @author Tomasz Kalkosiński
 */
public class ConsoleFormatter {

	/**
	 * Interface needed for lazy evaluation.
	 * 
	 * @author Tomasz Kalkosiński
	 */
	private interface LazyFormat {
		public String format();
	}

	private final String PLAYER_CONNECTED_EVENT = "Player %s connected";
	private final String PLAYER_DISCONNECTED_EVENT = "Player %s disconnected";
	private final String PLAYER_NAME_CHANGE = "Player %s changed name to %s";
	private final String PLAYER_SCORE_CHANGE = "Player %s scores to %d";
	
	private final String TEAM_NAME_CHANGE = "Team %s renames to %s";
	private final String TEAM_SCORE_CHANGE = "Team %s scores to %d";
	
	private final String TIME_PERIOD_INFO = "%d:%02d";
	private final String ROUND_INFO = "%d/%d";
	
	private final int TDM_INTERVAL = 30; //seconds
	private final int INTERVAL = TDM_INTERVAL * 2;
	
	private final static String MAIN_LINE_TDM = "%s%s%s. %s%s%s%s  (%s) %s%d%s  vs  %s%d%s (%s)  %s%s%s%s (%s, map: %s) %s%s*%s%s%s%s*%s %s";

	private final ConsoleColors colors;
	private OutputConfiguration outputConfiguration;

	private Boolean formatMainLine;
	private List<String> mainLines;
	private List<String> jointLines;
	private List<String> exclusiveLines;
	
	private static PrintStream output;
	private Client client;
	private int lastPrintedRedScore;
	private int lastPrintedBlueScore;
	
	private String timeMark = "NULL";

	/**
	 * @param colors
	 */
	public ConsoleFormatter(ConsoleColors colors, OutputConfiguration outputConfiguration) {
		this.colors = colors;
		this.outputConfiguration = outputConfiguration;
		
		output = System.out;
		formatMainLine = false;

		mainLines = new ArrayList<String>();
		jointLines = new ArrayList<String>();
		exclusiveLines = new ArrayList<String>();
	}
	
	public synchronized void flush() {
		for(String line : formatOutput()) {
			output.println(line);
		}
		
		clear();
	}

	private synchronized void clear() {
		formatMainLine = false;

		mainLines.clear();
		jointLines.clear();
		exclusiveLines.clear();
	}
	
	private synchronized List<String> formatOutput() {
		List<String> output = new ArrayList<String>();
		
		if (formatMainLine) {
			output.add(formatMatchLine(client.getScorebot()));
		}
		
		output.addAll(exclusiveLines);
						
		return output;
	}

	public void format(OutputStyle style, LazyFormat lazyFormatString) {
		switch (style) {
		
		case DONT_SHOW:
			return;
			
		case TRIGGER_MAIN_LINE:
			synchronized (formatMainLine) {
				formatMainLine = true;
			}
			break;
			
		case EXCLUSIVE_NEW_LINE:
			synchronized (exclusiveLines) {
				exclusiveLines.add(lazyFormatString.format());
			}
			break;
			
		case JOINT_NEW_LINE:
			synchronized (jointLines) {
				jointLines.add(lazyFormatString.format());
			}
			break;

		case MAIN_LINE:
			synchronized (mainLines) {
				mainLines.add(lazyFormatString.format());
			}
			break;

		default:
			throw new RuntimeException("Enum value " + style + " not found.");
		}
	}
	
	public void formatGameInfoChange(final Pair<GameInfo, GameInfo> pair) {
		if (pair.getFirst() == null && pair.getSecond() != null) {
			format(OutputStyle.TRIGGER_MAIN_LINE, null);
			return;
		}
		
		if (pair.getFirst().getClass() != pair.getSecond().getClass()) {
			format(OutputStyle.TRIGGER_MAIN_LINE, null);
			return;
		}
	}
	
	public void formatProgressInfoChange(final Pair<ProgressInfo, ProgressInfo> pair) {
		if (pair.getFirst() == null && pair.getSecond() != null) {
			format(OutputStyle.TRIGGER_MAIN_LINE, null);
			return;
		}
		
		if (pair.getFirst().getClass() != pair.getSecond().getClass()) {
			format(OutputStyle.TRIGGER_MAIN_LINE, null);
			return;
		}

		//Show every change
		String mark = pair.getSecond().getRawText();
		
		//Or every 30 minutes
		if (pair.getFirst() instanceof TimePeriodInfo) { //both are
			Seconds seconds = Seconds.standardSecondsIn(((TimePeriodInfo)pair.getSecond()).getPeriod());
			mark = Integer.toString((int) (Math.floor(seconds.getSeconds() / TDM_INTERVAL)));
		}
		
		//Or every round
		if (pair.getFirst() instanceof RoundInfo) { //both are
			mark = Integer.toString(((RoundInfo) pair.getSecond()).getRoundNumber());
		}
			
		if (!(mark.equals(timeMark))) {
			timeMark = mark;
			format(OutputStyle.TRIGGER_MAIN_LINE, null);
		}
	}

	public void formatPlayerConnectedEvent(final Pair<PlayerInfo, PlayerInfo> pair) {
		format(outputConfiguration.getShowEveryPlayerConnectEvent(), new LazyFormat() {
			
			@Override
			public String format() {
				return String.format(PLAYER_CONNECTED_EVENT, pair.getSecond().getName());
			}
		});
	}
	
	public void formatPlayerDisconnectedEvent(final Pair<PlayerInfo, PlayerInfo> pair) {
		format(outputConfiguration.getShowEveryPlayerDisconnectEvent(), new LazyFormat() {
			
			@Override
			public String format() {
				return String.format(PLAYER_DISCONNECTED_EVENT, pair.getFirst().getName());
			}
		});
	}
	
	public void formatPlayerNameChange(final Pair<PlayerInfo, PlayerInfo> pair) {
		format(outputConfiguration.getShowEveryPlayerNameChange(), new LazyFormat() {

			@Override
			public String format() {
				return String.format(PLAYER_NAME_CHANGE, pair.getFirst().getName(), pair.getSecond()
						.getName());
			}
		});
	}
	
	public void formatPlayerScoreChange(final Pair<PlayerInfo, PlayerInfo> pair) {
		format(outputConfiguration.getShowEveryPlayerScoreChange(), new LazyFormat() {
			
			@Override
			public String format() {
				return String.format(PLAYER_SCORE_CHANGE, pair.getSecond().getName(), pair.getSecond()
						.getScore());
			}
		});
	}
	
	public void formatTeamNameChange(final Pair<TeamInfo, TeamInfo> pair) {
		format(outputConfiguration.getShowEveryPlayerNameChange(), new LazyFormat() {
			
			@Override
			public String format() {
				return String.format(TEAM_NAME_CHANGE, pair.getFirst().getName(), pair.getSecond()
						.getName());
			}
		});
	}
	
	public void formatTeamScoreChange(final Pair<TeamInfo, TeamInfo> pair) {
		format(outputConfiguration.getShowEveryTeamScoreChange(), new LazyFormat() {
			
			@Override
			public String format() {
				return String.format(TEAM_SCORE_CHANGE, pair.getSecond().getName(), pair.getSecond()
						.getScore());
			}
		});
	}
	
	public synchronized String formatMatchLine(Scorebot scorebot) {
		
		if (!(scorebot instanceof ServerInfoScorebot))
			throw new RuntimeException("Cannot draw line with scorebot different than ServerInfoScorebot");
		
		ServerInfoScorebot serverInfoScorebot = (ServerInfoScorebot)scorebot;
		TeamInfo redTeam = serverInfoScorebot.getCurrentServerInfo().getTeamInfos().get(TeamKey.RED_TEAM);
		TeamInfo blueTeam = serverInfoScorebot.getCurrentServerInfo().getTeamInfos().get(TeamKey.BLUE_TEAM);
		
		if (redTeam == null || blueTeam == null)
			throw new RuntimeException("There is no red either blue team!");
		
		String winningColor = colors.getReset();
		
		if (redTeam.getScore() > blueTeam.getScore())
			winningColor = colors.getRed();
		else if (redTeam.getScore() < blueTeam.getScore())
			winningColor = colors.getBlue();
		
		StringBuilder mainLines = new StringBuilder();
		
		for (String line : this.mainLines) {
			mainLines.append(line);
			mainLines.append(" ");
		}
		
		String returnString = String.format(MAIN_LINE_TDM,
				colors.getBold(), scorebot.getId(), colors.getReset(),
				colors.getRed(), colors.getBold(), redTeam.getName(), colors.getReset(),
				formatNet(redTeam.getScore() - lastPrintedRedScore),
				colors.getBold(), redTeam.getScore(), colors.getReset(),
				colors.getBold(), blueTeam.getScore(), colors.getReset(),
				formatNet(blueTeam.getScore() - lastPrintedBlueScore),
				colors.getBlue(), colors.getBold(), blueTeam.getName(), colors.getReset(),
				formatProgressInfo(serverInfoScorebot.getCurrentServerInfo().getProgressInfo()),
				serverInfoScorebot.getCurrentServerInfo().getGameInfo().getMap(),
				colors.getBold(), winningColor, colors.getReset(), formatNet(Math.abs(redTeam.getScore() - blueTeam.getScore())), colors.getBold(), winningColor, colors.getReset(),
				mainLines.toString()
				);
		
		lastPrintedRedScore = redTeam.getScore();
		lastPrintedBlueScore = blueTeam.getScore();
		
		return returnString;
	}
	
	private String formatProgressInfo(ProgressInfo progressInfo) {
		
		assert(progressInfo != null);
		
		if (progressInfo instanceof TimePeriodInfo) {
			TimePeriodInfo time = (TimePeriodInfo)progressInfo;
			
			return String.format(TIME_PERIOD_INFO, time.getPeriod().getMinutes(), time.getPeriod().getSeconds());
		}
		
		if (progressInfo instanceof RoundInfo) {
			RoundInfo rounds = (RoundInfo)progressInfo;
			
			return String.format(ROUND_INFO, rounds.getRoundNumber(), rounds.getRoundLimit());
		}
		
		return progressInfo.getRawText();
	}
	
	private String formatNet(int net) {
		if (net == 0)
			return "  ";
		
		return String.format("%+d", net);
		
	}
	
	public String formatShort(PlayerInfo playerInfo) {
		return String.format("%s (%d)", playerInfo.getName(), playerInfo.getScore());

	}

	public String formatLong(PlayerInfo playerInfo) {
		return String
				.format("%s (%d) P%d", playerInfo.getName(), playerInfo.getScore(), playerInfo.getPing());
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	void setOutputConfiguration(OutputConfiguration outputConfiguration) {
		synchronized (this.outputConfiguration) {
			this.outputConfiguration = outputConfiguration;
		}
	}
	
	
}
