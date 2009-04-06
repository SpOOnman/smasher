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
import java.util.List;

import eu.spoonman.smasher.common.Pair;
import eu.spoonman.smasher.output.OutputStyle;
import eu.spoonman.smasher.scorebot.ServerInfoScorebot;
import eu.spoonman.smasher.serverinfo.PlayerInfo;

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

	private final String PLAYER_NAME_CHANGE = "Player %s changed name to %s";

	private final ConsoleColors colors;

	private Boolean formatMainLine;
	private List<String> mainLines;
	private List<String> jointLines;
	private List<String> exclusiveLines;

	/**
	 * @param colors
	 */
	public ConsoleFormatter(ConsoleColors colors) {
		this.colors = colors;

		formatMainLine = false;

		mainLines = new ArrayList<String>();
		jointLines = new ArrayList<String>();
		exclusiveLines = new ArrayList<String>();
	}

	private void clear() {
		formatMainLine = false;

		mainLines.clear();
		jointLines.clear();
		exclusiveLines.clear();
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
			synchr
			
			
			
			break;

		default:
			break;
		}
		switch(style) {
		
		}
		if (style == OutputStyle.DONT_SHOW)
			return;

		lazyFormatString.format();
	}

	public void formatPlayerNameChange(final Pair<PlayerInfo, PlayerInfo> pair, OutputStyle style) {
		format(style, new LazyFormat() {

			@Override
			public String format() {
				return String.format(PLAYER_NAME_CHANGE, pair.getFirst().getName(), pair.getSecond()
						.getName());
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
