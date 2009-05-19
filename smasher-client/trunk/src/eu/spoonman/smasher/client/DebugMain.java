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
public class DebugMain {
	
	private static String commands = ""; 
	
	private static final Logger log = Logger.getLogger(DebugMain.class);
	
	public static void main(String[] args) throws InterruptedException {
		
		log.debug("Starting debug QScorebot.");
		
		String line = null;
		Client client = ClientBuilder.getInstance().getConsoleClient();
		
		String[] split = commands.split("\n");
		for (String string : split) {
			try {
				CommandLineParser.getInstance().parseAndExecute(client, line);
			} catch (ClientException e) {
				log.info("Client exception", e);
				log.error(e);
			}
		}
		
		Thread.currentThread().wait();
	}
	

}
