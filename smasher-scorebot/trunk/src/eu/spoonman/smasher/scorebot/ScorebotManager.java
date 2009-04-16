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
package eu.spoonman.smasher.scorebot;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.spoonman.smasher.serverinfo.Games;
import eu.spoonman.smasher.serverinfo.ServerQuery;
import eu.spoonman.smasher.serverinfo.ServerQueryManager;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class ScorebotManager {
	
	private final ExecutorService executorService;
	private List<ServerInfoScorebot> scorebots;
	
	private static ScorebotManager scorebotManager;
	
	private ScorebotManager() {
		scorebots = new ArrayList<ServerInfoScorebot>();
		executorService = Executors.newCachedThreadPool();
	}
	
	public static ScorebotManager getInstance() {
		
		synchronized (scorebotManager) {
			if (scorebotManager == null)
				scorebotManager = new ScorebotManager();
			
			return scorebotManager;
		}
	}
	
	public ServerInfoScorebot createOrGetScorebot(Games game, InetAddress address, int port) {
		
		synchronized (scorebots) {
			for (ServerInfoScorebot scorebot : scorebots) {
				if (scorebot.getServerQuery().getGame().equals(game) &&
						scorebot.getServerQuery().getAddress().equals(address) &&
						scorebot.getServerQuery().getPort() == port)
					return scorebot;
			}
			
			ServerQuery serverQuery = ServerQueryManager.createServerQuery(game, address, port);
			ServerInfoScorebot scorebot = new ServerInfoScorebot(serverQuery);
			
			scorebots.add(scorebot);
			runScorebot(scorebot);
			return scorebot;
		}
	}
	
	private void runScorebot(final Scorebot scorebot) {
		executorService.execute(new Runnable() {
			public void run() {
				scorebot.start();
			}
		});
	}
}
