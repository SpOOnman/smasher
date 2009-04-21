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

import eu.spoonman.smasher.common.Pair;
import eu.spoonman.smasher.output.OutputConfiguration;
import eu.spoonman.smasher.output.OutputStyle;
import eu.spoonman.smasher.scorebot.Scorebot;
import eu.spoonman.smasher.scorebot.ServerInfoScorebot;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.TeamInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;

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

	private final String MAIN_LINE_TDM = "%s%s%s. %s%s%s%s  (%+2d) %s%d%s  vs  %s%d%s (%+2d)  %s%s%s%s (%s, map: %s%s) %s%s*%s%+2d%s%s*%s %s";

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
	
	//private final String MAIN_LINE_TDM2 = "%s%s%s. %s%s%s%s  (%+2d) %s%d%s  vs  %s%d%s (%+2d)  %s%s%s%s (%s, map: %s) %s%s*%s%+2d%s%s*%s %s";

	public String formatMatchLine(Scorebot scorebot) {
		
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
		
		String inlines = "inlines";
		
		String returnString = String.format(MAIN_LINE_TDM,
				colors.getBold(), scorebot.getId(), colors.getReset(),
				colors.getRed(), colors.getBold(), redTeam.getName(), colors.getReset(),
				redTeam.getScore() - lastPrintedRedScore,
				colors.getBold(), redTeam.getScore(), colors.getReset(),
				colors.getBold(), blueTeam.getScore(), colors.getReset(),
				blueTeam.getScore() - lastPrintedBlueScore,
				colors.getBlue(), colors.getBold(), blueTeam.getName(), colors.getReset(),
				serverInfoScorebot.getCurrentServerInfo().getProgressInfo().toString(),
				serverInfoScorebot.getCurrentServerInfo().getGameInfo().getMap(),
				colors.getBold(), winningColor, colors.getReset(), Math.abs(redTeam.getScore() - blueTeam.getScore()), colors.getBold(), winningColor, colors.getReset(),
				inlines
				);
		
		lastPrintedRedScore = redTeam.getScore();
		lastPrintedBlueScore = blueTeam.getScore();
		
		return returnString;
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
