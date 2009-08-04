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

import org.apache.log4j.Logger;

import eu.spoonman.smasher.scorebot.ScorebotManager;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class ConsoleMain {
	
	private static final Logger log = Logger.getLogger(ConsoleMain.class);
	
	private static java.io.Console console;
	
	private static String WELCOME_MESSAGE = "Welcome to QScorebot";
	private static String QUIT = "quit";
	
	public static void main(String[] args) {
		
		log.debug("Starting QScorebot.");
		
		console = System.console();
		
		if (console == null) {
			log.error("Cannot allocate console.");
			throw new RuntimeException("Cannot allocate console");
		}
		
		log.debug("Console found. Starting reading.");
		
		String line = null;
		Client client = ClientBuilder.getInstance().getConsoleClient();
		
		while ((line = console.readLine()) != null && (!(line.equalsIgnoreCase("quit")))) {
			try {
				CommandLineParser.getInstance().parseAndExecute(client, line);
			} catch (ClientException e) {
				log.info("Client exception", e);
				console.writer().print(e.getMessage());
				console.writer().print("\n");
				console.flush();
			}
		}
		
		ScorebotManager.getInstance().shutdown();
		
		log.debug("Quitting. Bye bye.");
	}
	

}
