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

import eu.spoonman.smasher.output.OutputConfiguration;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class ClientBuilder {
	
	public static Client getConsoleClient() {
		//Color scheme
		ConsoleColors consoleColors = new ConsoleColors();
		
		//Formatting theme
		ConsoleFormatter consoleFormatter = new ConsoleFormatter(consoleColors, new OutputConfiguration());
		
		//Client is console based - lines with ASCII characters.
		Client client = new ConsoleClient(consoleFormatter);
		consoleFormatter.setClient(client);
		
		return client;
	}
	
	public static Client getIRCClient() {
		return null;
	}

}
