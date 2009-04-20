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

	/*
	<!-- This is summary information while scorebot is running -->
    <!-- Keys:

            %1% scorebot id
            %2% map symbol
            %3% map name
            %4% time

            %6% server ip
            %7% server name
            %8% gametype name
            %9% mod name

            %10% red team name color stripped
            %11% red team score
            %12% red team avg ping
            %13% red team players
            %14% red team net from last print (zero = blank)

            %15% blue team name color stripped
            %16% blue team score
            %17% blue team avg ping
            %18% blue team players
            %19% blue team net from last print (zero = blank)

            %20% spectator players
            %21% team score difference (zero = blank)

            %22% events inline

            ^W   winning team color (^N if there is a tie)
            ^L   loosing team color (^N if there is a tie)

    -->
    */
	
	private final String MAIN_LINE_TDM = "^O%1%^N. ^R^O%10%^N  (%|14$+2|) ^O%11%^N  vs  ^O%16%^N (%|19$+2|)  ^B^O%15%^N (%4%, map: ^O%2%) ^O^W*^N%|21$+2|^O^W*^N %22%";
	private final String MAIN_LINE_TDM2 = "%s%s%s. %s%s%s%s  (%+2d) %s%d%s  vs  %s%d%s (%+2d)  %s%s%s%s (%s, map: %s%s) %s%s*%s%+2d%s%s*%s %s";

	private final ConsoleColors colors;
	private OutputConfiguration outputConfiguration;

	private Boolean formatMainLine;
	private List<String> mainLines;
	private List<String> jointLines;
	private List<String> exclusiveLines;
	
	private static PrintStream output;
	private Client client;

	/**
	 * @param colors
	 */
	public ConsoleFormatter(ConsoleColors colors, OutputConfiguration outputConfiguration) {
		this.colors = colors;
		this.outputConfiguration = outputConfiguration;
		
		String.format(MAIN_LINE_TDM2,
				colors.getBold(), scorebot.getId(), colors.getReset(),
				colors.getRed(), colors.getBold(), scorebot.getReadTeamName(), colors.getReset(),
				);

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

	public String formatMatchLine(Scorebot scorebot) {
		String format = "Game %s. %s%s %s%d%s vs %s%d%s";
		return String.format(format, scorebot.getId());
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
