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

import eu.spoonman.smasher.scorebot.ServerInfoScorebot;
import eu.spoonman.smasher.serverinfo.PlayerInfo;

/**
 * @author Tomasz Kalkosiński
 * Default console formatter.
 *
 */
public class ConsoleFormatter {
	
	private final ConsoleColors colors;
	
	/**
	 * @param colors
	 */
	public ConsoleFormatter(ConsoleColors colors) {
		this.colors = colors;
	}
	
	public String formatMatchLine(ServerInfoScorebot scorebot) {
		String format = "Game %s. %s%s %s%d%s vs %s%d%s";
		return String.format(format, "A1");
	}

	public String formatShort(PlayerInfo playerInfo) {
		return String.format("%s (%d)", playerInfo.getName(), playerInfo.getScore());
		
	}
	
	public String formatLong(PlayerInfo playerInfo) {
		return String.format("%s (%d) P%d", playerInfo.getName(), playerInfo.getScore(), playerInfo.getPing());
	}	
}
