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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.Seconds;

import eu.spoonman.smasher.common.Pair;
import eu.spoonman.smasher.output.OutputConfiguration;
import eu.spoonman.smasher.output.OutputStyle;
import eu.spoonman.smasher.scorebot.Scorebot;
import eu.spoonman.smasher.scorebot.ServerInfoScorebot;
import eu.spoonman.smasher.serverinfo.GameInfo;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ProgressInfo;
import eu.spoonman.smasher.serverinfo.ProgressInfoFlags;
import eu.spoonman.smasher.serverinfo.RoundInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfoStatus;
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
	 * Logger for this class
	 */
	private static final Logger log = Logger.getLogger(ConsoleFormatter.class);

	/**
	 * Interface needed for lazy evaluation.
	 * 
	 * @author Tomasz Kalkosiński
	 */
	private interface LazyFormat {
		public String format();
	}
	
	private final String SCOREBOT_START = "%s%s%s. Starting %s%s%s scorebot on %s.";
	private final String SCOREBOT_STOP = "%s%s%s. Scorebot stopped. %s";

	private final String PLAYER_CONNECTED_EVENT = "Player %s connected";
	private final String PLAYER_DISCONNECTED_EVENT = "Player %s disconnected";
	private final String PLAYER_NAME_CHANGE = "Player %s changed name to %s";
	private final String PLAYER_SCORE_CHANGE = "Player %s scores to %d";
	
	private final String TEAM_NAME_CHANGE = "Team %s renames to %s";
	private final String SUBSCRIPTION_TEAM_NAME_CHANGE = "Teams are now known as %s%s%s%s and %s%s%s%s.";
	private final String TEAM_SCORE_CHANGE = "Team %s scores to %d";
	private final String TEAM_PLAYERS = "Team %s%s%s%s is: %s.";
	private final String SPEC_PLAYERS = "%s%s%s%s: %s.";
	private final String TEAM_PLAYER = "%s%s%s (%d), "; //clan/null, space/null, name, score
	
	private final String TIME_PERIOD_INFO = "%d:%02d%s";
	private final String ROUND_INFO = "%d/%d%s";
	
	private final int TDM_INTERVAL = 30; //seconds
	private final int INTERVAL = TDM_INTERVAL * 2;
	
	private final static String MAIN_LINE_TDM = "%s%s%s. %s%s%s%s  (%s) %s%d%s  vs  %s%d%s (%s)  %s%s%s%s (%s, map: %s) %s%s*%s%s%s%s*%s %s";
	private final static String MAIN_LINE_CTF = "%s%s%s. %s%s%s%s  %s%d%s  vs  %s%d%s  %s%s%s%s (%s, map: %s) %s%s*%s%s%s%s*%s %s";

	private final Colors colors;
	private OutputConfiguration outputConfiguration;

	private Boolean formatMainLine;
	private Boolean formatPlayers;
	private List<String> beforeMainLines;
	private List<String> mainInlines;
	private List<String> secondMainInlines;
	private List<String> afterMainLines;
	
	private Subscription subscription;
	private int lastPrintedRedScore;
	private int lastPrintedBlueScore;
	
	private String redTeamName;
	private String blueTeamName;
	
	private String timeMark = "NULL";

	/**
	 * @param colors
	 */
	public ConsoleFormatter(Colors colors, OutputConfiguration outputConfiguration) {
		this.colors = colors;
		this.outputConfiguration = outputConfiguration;
		
		formatMainLine = false;
		formatPlayers = false;

		beforeMainLines = new ArrayList<String>();
		mainInlines = new ArrayList<String>();
		secondMainInlines = new ArrayList<String>();
		afterMainLines = new ArrayList<String>();
	}
	
	public void ensureMainLine() {
		synchronized (formatMainLine) {
			formatMainLine = true;
		}
	}
	
	public synchronized void flush() {
		subscription.getClient().print(formatOutput());
		
		clear();
	}

	private synchronized void clear() {
		formatMainLine = false;
		formatPlayers = false;

		beforeMainLines.clear();
		mainInlines.clear();
		secondMainInlines.clear();
		afterMainLines.clear();
	}
	
	private synchronized List<String> formatOutput() {
		List<String> output = new ArrayList<String>();
		
		StringBuilder s = new StringBuilder();
		
		output.addAll(beforeMainLines);
		
		if (formatMainLine) {
			output.add(formatMatchLine(subscription.getScorebot()));
		}
		
		if (secondMainInlines.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (String string : secondMainInlines) {
				sb.append(string);
				sb.append(" ");
			}
			output.add(sb.toString());
		}
		
		output.addAll(afterMainLines);
		
		if (formatPlayers)
			output.addAll(formatPlayers(subscription.getScorebot()));

		// Remove blank lines
		for (Iterator<String> iterator = output.iterator(); iterator.hasNext();) {
			if (iterator.next() == null)
				iterator.remove();
		}
						
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
			synchronized (afterMainLines) {
				afterMainLines.add(lazyFormatString.format());
			}
			break;
			
		case JOINT_NEW_LINE:
			synchronized (secondMainInlines) {
				secondMainInlines.add(lazyFormatString.format());
			}
			break;

		case MAIN_LINE:
			synchronized (mainInlines) {
				mainInlines.add(lazyFormatString.format());
			}
			break;

		default:
			throw new RuntimeException("Enum value " + style + " not found.");
		}
	}
	
	public void formatScorebotStart(Scorebot scorebot) {
		synchronized (beforeMainLines) {
			beforeMainLines.add(String.format(SCOREBOT_START,
					colors.getBold(), scorebot.getId(), colors.getReset(),
					colors.getBold(), scorebot.getGame().getFullName(), colors.getReset(),
					scorebot.getIdentification().toString().replaceFirst("^/", "")));
		}
	}
	
	public void formatScorebotStop(Scorebot scorebot) {
		synchronized (afterMainLines) {
			afterMainLines.add(String.format(SCOREBOT_STOP,
					colors.getBold(), scorebot.getId(), colors.getReset(), scorebot.getCurrentServerInfo() != null ? scorebot.getCurrentServerInfo().getMessage() : ""
					));
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
		
		assert(pair.getSecond() != null);
		
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
			Seconds secondsFirst = Seconds.standardSecondsIn(((TimePeriodInfo)pair.getFirst()).getPeriod());
			Seconds secondsSecond = Seconds.standardSecondsIn(((TimePeriodInfo)pair.getSecond()).getPeriod());
			
			int modifier = secondsFirst.getSeconds() < secondsSecond.getSeconds() ? -1 : 1;
			modifier = -1;
			
			mark = Integer.toString((int) (Math.floor( (secondsSecond.getSeconds() + modifier) / TDM_INTERVAL)));
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
	
	public void formatDifferenceStopEvent(Pair<Scorebot, Scorebot> pair) {
		TeamInfo redTeam = pair.getSecond().getCurrentServerInfo().getTeamInfos().get(TeamKey.RED_TEAM);
		TeamInfo blueTeam = pair.getSecond().getCurrentServerInfo().getTeamInfos().get(TeamKey.BLUE_TEAM);
		
		if (redTeam == null || blueTeam == null) {
			log.error("Cannot format score differences - no red or blue team");
			return;
		}
		
		if (Math.signum(lastPrintedBlueScore - lastPrintedRedScore) != Math.signum(blueTeam.getScore() - redTeam.getScore()))
			ensureMainLine();
	}
	
	public synchronized String formatMatchLine(Scorebot scorebot) {
		
		if (!(scorebot instanceof ServerInfoScorebot))
			throw new RuntimeException("Cannot draw line with scorebot different than ServerInfoScorebot");
		
		ServerInfo serverInfo = ensureServerInfo(scorebot);
		
		TeamInfo redTeam = serverInfo.getTeamInfos().get(TeamKey.RED_TEAM);
		TeamInfo blueTeam = serverInfo.getTeamInfos().get(TeamKey.BLUE_TEAM);
		ProgressInfo progressInfo = serverInfo.getProgressInfo();
		GameInfo gameInfo = serverInfo.getGameInfo();
		
		if (redTeam == null || blueTeam == null)
			throw new RuntimeException("There is no red either blue team!");
		
		String winningColor = colors.getReset();
		
		if (redTeam.getScore() > blueTeam.getScore())
			winningColor = colors.getRed();
		else if (redTeam.getScore() < blueTeam.getScore())
			winningColor = colors.getBlue();
		
		StringBuilder mainLines = new StringBuilder();
		
		for (String line : this.mainInlines) {
			mainLines.append(line);
			mainLines.append(" ");
		}
		
		String returnString = formatMainLineInternal(scorebot, redTeam, blueTeam, progressInfo, gameInfo,
				winningColor, mainLines);
		
		lastPrintedRedScore = redTeam.getScore();
		lastPrintedBlueScore = blueTeam.getScore();
		
		return returnString;
	}

	private ServerInfo ensureServerInfo(Scorebot scorebot) {
		ServerInfoScorebot serverInfoScorebot = (ServerInfoScorebot)scorebot;
		ServerInfo serverInfo = null;
		
		if (serverInfoScorebot.getCurrentServerInfo() != null && 
				serverInfoScorebot.getCurrentServerInfo().getStatus() == ServerInfoStatus.OK)
			serverInfo = serverInfoScorebot.getCurrentServerInfo();
		
		// When currentServerInfo is not ok and previous is ok - use it
		else if (serverInfoScorebot.getPreviousServerInfo() != null &&
				serverInfoScorebot.getPreviousServerInfo().getStatus() == ServerInfoStatus.OK)
			serverInfo = serverInfoScorebot.getPreviousServerInfo();
		
		return serverInfo;
	}

	private String formatMainLineTDM(Scorebot scorebot, TeamInfo redTeam, TeamInfo blueTeam,
			ProgressInfo progressInfo, GameInfo gameInfo, String winningColor, StringBuilder mainLines) {
		
		String returnString = String.format(MAIN_LINE_TDM,
				colors.getBold(), scorebot.getId(), colors.getReset(),
				colors.getRed(), colors.getBold(), formatTeamName(redTeam.getName(), true) , colors.getReset(),
				formatNet(redTeam.getScore() - lastPrintedRedScore),
				colors.getBold(), redTeam.getScore(), colors.getReset(),
				colors.getBold(), blueTeam.getScore(), colors.getReset(),
				formatNet(blueTeam.getScore() - lastPrintedBlueScore),
				colors.getBlue(), colors.getBold(), formatTeamName(blueTeam.getName(), false), colors.getReset(),
				formatProgressInfo(progressInfo),
				gameInfo.getMap(),
				colors.getBold(), winningColor, colors.getReset(), formatNet(Math.abs(redTeam.getScore() - blueTeam.getScore())), colors.getBold(), winningColor, colors.getReset(),
				mainLines.toString()
				);
		return returnString;
	}
	
	private String formatMainLineCTF(Scorebot scorebot, TeamInfo redTeam, TeamInfo blueTeam,
			ProgressInfo progressInfo, GameInfo gameInfo, String winningColor, StringBuilder mainLines) {
		
		String returnString = String.format(MAIN_LINE_CTF,
				colors.getBold(), scorebot.getId(), colors.getReset(),
				colors.getRed(), colors.getBold(), formatTeamName(redTeam.getName(), true) , colors.getReset(),
				colors.getBold(), redTeam.getScore(), colors.getReset(),
				colors.getBold(), blueTeam.getScore(), colors.getReset(),
				colors.getBlue(), colors.getBold(), formatTeamName(blueTeam.getName(), false), colors.getReset(),
				formatProgressInfo(progressInfo),
				gameInfo.getMap(),
				colors.getBold(), winningColor, colors.getReset(), formatNet(Math.abs(redTeam.getScore() - blueTeam.getScore())), colors.getBold(), winningColor, colors.getReset(),
				mainLines.toString()
		);
		return returnString;
	}
	
	private String formatMainLineInternal(Scorebot scorebot, TeamInfo redTeam, TeamInfo blueTeam,
			ProgressInfo progressInfo, GameInfo gameInfo, String winningColor, StringBuilder mainLines) {
		if (gameInfo.getGameType() == null)
			return MAIN_LINE_TDM;
		
		switch (gameInfo.getGameType()) {
			case CAPTURE_THE_FLAG:
				return formatMainLineCTF(scorebot, redTeam, blueTeam, progressInfo, gameInfo, winningColor, mainLines);
				
			default:
				return formatMainLineTDM(scorebot, redTeam, blueTeam, progressInfo, gameInfo, winningColor, mainLines);
		}
	}
	
	private List<String> formatPlayers(Scorebot scorebot) {
		ServerInfo serverInfo = ensureServerInfo(scorebot);
		List<String> strings = new ArrayList<String>();
		
		TeamInfo redTeam = serverInfo.getTeamInfos().get(TeamKey.RED_TEAM);
		if (redTeam != null)
			strings.add(String.format(TEAM_PLAYERS, colors.getRed(), colors.getBold(), formatTeamName(redTeam.getName(), true) , colors.getReset(), formatPlayersTeam(redTeam, serverInfo.getPlayerInfos())));
		
		TeamInfo blueTeam = serverInfo.getTeamInfos().get(TeamKey.BLUE_TEAM);
		if (blueTeam != null)
			strings.add(String.format(TEAM_PLAYERS, colors.getBlue(), colors.getBold(), formatTeamName(blueTeam.getName(), false) , colors.getReset(), formatPlayersTeam(blueTeam, serverInfo.getPlayerInfos())));
		
		TeamInfo specTeam = serverInfo.getTeamInfos().get(TeamKey.SPECTATORS_TEAM);
		if (specTeam != null)
			strings.add(String.format(SPEC_PLAYERS, colors.getBlue(), colors.getBold(), specTeam.getName() , colors.getReset(), formatPlayersTeam(specTeam, serverInfo.getPlayerInfos())));
		
		return strings;
	}
	
	private String formatPlayersTeam(TeamInfo teamInfo, List<PlayerInfo> players) {
		StringBuilder sb = new StringBuilder();
		for (PlayerInfo player : players)
			if (player.getTeamKey() == teamInfo.getKey()) {
				sb.append(String.format(TEAM_PLAYER, null, null, player.getName(), player.getScore()));
			}
		
		return sb.toString();
	}
	
	private String formatProgressInfo(ProgressInfo progressInfo) {
		
		assert(progressInfo != null);
		
		if (progressInfo instanceof TimePeriodInfo) {
			TimePeriodInfo time = (TimePeriodInfo)progressInfo;
			
			String flags = formatProgressInfoFlags(time);
			
			return String.format(TIME_PERIOD_INFO, time.getPeriod().getMinutes(), time.getPeriod().getSeconds(), flags);
		}
		
		if (progressInfo instanceof RoundInfo) {
			RoundInfo rounds = (RoundInfo)progressInfo;
			String flags = formatProgressInfoFlags(rounds);
			
			return String.format(ROUND_INFO, rounds.getRoundNumber(), rounds.getRoundLimit(), flags);
		}
		
		return progressInfo.getRawText();
	}

	private String formatProgressInfoFlags(ProgressInfo time) {
		StringBuilder flags = new StringBuilder();
		for (ProgressInfoFlags f : time.getProgressInfoFlags()) {
			if (f == ProgressInfoFlags.IN_PLAY)
				continue;
			
			flags.append(" ");
			flags.append(f.toString());
		}
		
		return flags.toString();
	}
	
	private String formatTeamName(String name, boolean redTeam) {
		if (redTeam)
			return redTeamName != null ? redTeamName : name;
		
		return blueTeamName != null ? blueTeamName : name;
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

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}
	
	synchronized void setOutputConfiguration(OutputConfiguration outputConfiguration) {
		this.outputConfiguration = outputConfiguration;
	}

	public synchronized void setTeamNames(String redTeam, String blueTeam) {
		redTeamName = redTeam;
		blueTeamName = blueTeam;
		
		afterMainLines.add(String.format(SUBSCRIPTION_TEAM_NAME_CHANGE,
				colors.getRed(), colors.getBold(), redTeamName , colors.getReset(),
				colors.getBlue(), colors.getBold(), blueTeamName , colors.getReset()));
	}

	public void showPlayers() {
	}
	
}
