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
		CommandLineParser parser = new CommandLineParser();
		final ConsoleClient client = new ConsoleClient();
		
		while ((line = console.readLine()) != null && (!(line.equalsIgnoreCase("quit")))) {
			try {
				parser.parseAndExecute(client, line);
			} catch (ClientException e) {
				log.info("Client exception", e);
				console.writer().print(e.toString());
				console.writer().print("\n");
				console.flush();
			}
		}
		
		log.debug("Quitting. Bye bye.");
	}
	

}
