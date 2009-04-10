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

	private final ConsoleColors colors;
	private final OutputConfiguration outputConfiguration;

	private Boolean formatMainLine;
	private List<String> mainLines;
	private List<String> jointLines;
	private List<String> exclusiveLines;
	
	private static PrintStream output;

	/**
	 * @param colors
	 */
	public ConsoleFormatter(ConsoleColors colors, OutputConfiguration outputConfiguration) {
		this.colors = colors;
		this.outputConfiguration = outputConfiguration;

		formatMainLine = false;

		mainLines = new ArrayList<String>();
		jointLines = new ArrayList<String>();
		exclusiveLines = new ArrayList<String>();
	}
	
	public void flush() {
		synchronized (output) {
			output.println();
		}
	}

	private void clear() {
		formatMainLine = false;

		mainLines.clear();
		jointLines.clear();
		exclusiveLines.clear();
	}
	
	public List<String> formatOutput() {
		List<String> output = new ArrayList<String>();
		
		synchronized (formatMainLine) {
			synchronized (exclusiveLines) {
				
			}
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
			
		case EXCLUSIVE_NEW_LINE:
			synchronized (exclusiveLines) {
				exclusiveLines.add(lazyFormatString.format());
			}
			
		case JOINT_NEW_LINE:
			synchronized (jointLines) {
				jointLines.add(lazyFormatString.format());
			}

		case MAIN_LINE:
			synchronized (mainLines) {
				mainLines.add(lazyFormatString.format());
			}

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

	public String formatMatchLine(ServerInfoScorebot scorebot) {
		String format = "Game %s. %s%s %s%d%s vs %s%d%s";
		return String.format(format, "A1");
	}

	public String formatShort(PlayerInfo playerInfo) {
		return String.format("%s (%d)", playerInfo.getName(), playerInfo.getScore());

	}

	public String formatLong(PlayerInfo playerInfo) {
		return String
				.format("%s (%d) P%d", playerInfo.getName(), playerInfo.getScore(), playerInfo.getPing());
	}
}
