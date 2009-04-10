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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class CommandLineParser {

	enum Commands {
		START, STOP, SHOW
	}

	public void parseAndExecute(String line) throws ClientException {
		String[] split = line.split(" ");

		Commands command = null;
		InetAddress address = null;
		String scorebotId = null;
		List<String> args = new ArrayList<String>();

		for (String word : args) {

			// Search for first known command;
			if (command == null) {
				command = searchForCommand(word);
				if (command != null)
					continue;
			}

			// Search for IP
			if (address == null) {
				address = searchForIP(word);
				if (address != null)
					continue;
			}
			
			if (scorebotId == null) {
				scorebotId = searchForScorebotId(word);
				if (scorebotId != null)
					continue;
			}
			
			args.add(word);
		}
		
		execute(command, address, scorebotId, args);

	}
	
	protected void execute(Commands command, InetAddress address, String scorebotId, List<String> args) throws ClientException {
		
		switch (command) {
		case START:
			if (address == null)
				throw new ClientException("IP address is needed.");
			
			break;
			
		case SHOW:
			if (scorebotId == null)
				throw new ClientException("Scorebot ID is needed.");
			break;
			
		case STOP:
			if (scorebotId == null)
				throw new ClientException("Scorebot ID is needed.");

		default:
			throw new ClientException("Unknown command " + command);
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
	
	private InetAddress searchForIP(String word) {
		try {
			InetAddress address = InetAddress.getByName(word);
			
			return address;

		} catch (UnknownHostException e) {
			return null;
		}
	}
	
	private String searchForScorebotId(String word) {
		return null;
	}

}