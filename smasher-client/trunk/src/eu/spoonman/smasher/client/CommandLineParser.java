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

import java.net.InetAddress;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class CommandLineParser {

	enum Commands {
		START, STOP, SHOW
	}

	public void parseAndExecute(String line) {
		String[] split = line.split(" ");

		Commands command = null;
		InetAddress address = null;
		String scorebotId = null;
		String[] args = new String[2];

		for (String word : args) {

			//Search for first known command;
			if (command == null) {
				command = searchForCommand(word);
			}
			
			//Search for

		}

	}

	private Commands searchForCommand(String word) {
		try {
			Commands valueOf = Enum.valueOf(Commands.class, word.toUpperCase());

			return valueOf;

		} catch (IllegalArgumentException e) {
			return null;
		}

	}

}
