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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import sun.security.jca.GetInstance;

import eu.spoonman.smasher.scorebot.Scorebot;
import eu.spoonman.smasher.scorebot.ScorebotManager;
import eu.spoonman.smasher.serverinfo.Games;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class CommandLineParser {
	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger.getLogger(CommandLineParser.class);
	
	private static Map<String, Games> gameMapping = new HashMap<String, Games>();

	enum Commands {
		START, STOP, SHOW
	}
	
	private static CommandLineParser parser;
	
	private CommandLineParser() {
		gameMapping.put("q3", Games.QUAKE3ARENA);
		gameMapping.put("q3a", Games.QUAKE3ARENA);
		gameMapping.put("quake3a", Games.QUAKE3ARENA);
		gameMapping.put("q2", Games.QUAKE2);
		gameMapping.put("quake2", Games.QUAKE2);
		gameMapping.put("ql", Games.QUAKELIVE);
		gameMapping.put("quakelive", Games.QUAKELIVE);
		gameMapping.put("qz", Games.QUAKELIVE);
	}
	
	public static synchronized CommandLineParser getInstance() {
		if (parser == null)
			parser = new CommandLineParser();
		
		return parser;
	}
	
	/**
	 * I know that no properties is a poor design, but it's only Data Value Object.
	 */
	class CommandData {
		Commands command = null;
		Games game = null;
		InetAddress address = null;
		int port = 0;
		Scorebot scorebot = null;
		List<String> args = new ArrayList<String>();
		
	}

	public void parseAndExecute(Client client, String line) throws ClientException {
		String[] split = line.split(" ");
		
		CommandData data = new CommandData();

		for (String word : split) {

			// Search for first known command;
			if (data.command == null) {
				data.command = searchForCommand(word);
				if (data.command != null)
					continue;
			}

			// Search for IP
			if (data.address == null) {
				data.address = searchForIP(word);
				if (data.address != null) {
					data.port = searchForPort(word);
					continue;
				}
			}
			// Search for game
			if (data.game == null) {
				data.game = searchForGame(word);
				if (data.game != null)
					continue;
			}
			
			if (data.scorebot == null) {
				data.scorebot = searchForScorebot(word);
				if (data.scorebot != null)
					continue;
			}
			
			data.args.add(word);
		}
		
		execute(client, data);

	}
	
	protected void execute(Client client, CommandData data) throws ClientException {
		
		if (data.command == null)
			throw new ClientException("Unknown command");
		
		switch (data.command) {
		case START:
			if (data.address == null)
				throw new ClientException("I don't have IP.");
			
			if (data.game == null)
				throw new ClientException("I don't know what game to use.");
			
			Scorebot scorebot = ScorebotManager.getInstance().createOrGetScorebot(data.game, data.address, data.port, data.args);
			SubscriptionManager.getInstance().subscribe(client, scorebot);
			
			break;
			
		case SHOW:
			if (data.scorebot == null)
				throw new ClientException("Scorebot ID is needed.");
			break;
			
		case STOP:
			if (data.scorebot == null)
				throw new ClientException("Scorebot ID is needed.");
			
			SubscriptionManager.getInstance().unsubscribe(client, data.scorebot);
			ScorebotManager.getInstance().stopScorebot(data.scorebot);
			
			break;

		default:
			throw new ClientException("Unknown command " + data.command);
		}
	}

	private Commands searchForCommand(String word) {
		try {
			Commands valueOf = Enum.valueOf(Commands.class, word.toUpperCase());
			
			log.debug("Parsed command: " + valueOf);

			return valueOf;

		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	private InetAddress searchForIP(String word) {
		try {
			String addr = word.split(":")[0];
			if (!addr.contains("."))
				return null;
			InetAddress address = InetAddress.getByName(addr);
			
			log.debug("Parsed IP: " + address.toString());
			
			return address;

		} catch (UnknownHostException e) {
			return null;
		}
	}
	
	private Games searchForGame(String word) {
		return CommandLineParser.gameMapping.get(word);
	}
	
	private int searchForPort(String word) {
		try {
			String[] splitted = word.split(":");
			if (splitted.length > 1) {
				int port = Integer.parseInt(splitted[1]);
				
				log.debug("Parsed port: " + port);
				
				return port;
			}
		} catch (NumberFormatException e) {
		}
		
		return 0;
	}
	
	private Scorebot searchForScorebot(String word) {
		return ScorebotManager.getInstance().getScorebotById(word);
	}

}
