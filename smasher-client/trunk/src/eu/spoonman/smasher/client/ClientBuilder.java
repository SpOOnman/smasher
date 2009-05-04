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

import java.util.HashMap;
import java.util.Map;

import eu.spoonman.smasher.output.OutputConfiguration;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class ClientBuilder {

	private static ClientBuilder clientBuilder;

	private Client consoleClient;
	private Map<String, Client> ircClients = new HashMap<String, Client>();

	private ClientBuilder() {
	}

	public static synchronized ClientBuilder getInstance() {
		if (clientBuilder == null)
			clientBuilder = new ClientBuilder();

		return clientBuilder;
	}

	public Client getConsoleClient() {

		if (consoleClient != null) {
			consoleClient = new Client("__console");
		}

		return consoleClient;
	}

	public Client getIRCClient(String channelName) {
		
		if (ircClients.get(channelName) == null) {
			ircClients.put(channelName, new Client(channelName));
		}
		
		return ircClients.get(channelName);
	}

}
