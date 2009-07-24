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
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.spoonman.smasher.serverinfo.AbstractQuery;
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
	private Random random = new Random();

	private static ScorebotManager scorebotManager;

	private ScorebotManager() {
		scorebots = new ArrayList<ServerInfoScorebot>();
		executorService = Executors.newCachedThreadPool();
	}

	public synchronized static ScorebotManager getInstance() {

		if (scorebotManager == null)
			scorebotManager = new ScorebotManager();

		return scorebotManager;
	}

	public ServerInfoScorebot createOrGetScorebot(Games game, InetAddress address, int port, List<String> args) {

		synchronized (scorebots) {
			AbstractQuery abstractQuery = ServerQueryManager.createServerQuery(game, address, port, args);
			
			for (ServerInfoScorebot scorebot : scorebots) {
				if (scorebot.getServerQuery().equals(abstractQuery))
					return scorebot;
			}

			ServerInfoScorebot scorebot = new ServerInfoScorebot(createUniqueId(), abstractQuery);

			scorebots.add(scorebot);
			runScorebot(scorebot);
			return scorebot;
		}
	}

	public void stopScorebot(Scorebot scorebot) {
		scorebot.stop();
	}

	private void runScorebot(final Scorebot scorebot) {
		executorService.execute(new ScorebotThread(scorebot));
	}

	private String createUniqueId() {

		boolean found = false;
		String id = null;

		do {
			char letter = (char) (random.nextInt(26) + 65); // 'A' = 65
			id = String.format("%c%d", letter, random.nextInt(8) + 1);
			
			for (ServerInfoScorebot scorebot : scorebots) {
				if (scorebot.getId().equals(id)) {
					found = true;
					continue;
				}
				
			}
		} while (found == true);
		
		return id;
	}

	/**
	 * @param word
	 * @return
	 */
	public Scorebot getScorebotById(String word) {

		synchronized (scorebots) {
			for (ServerInfoScorebot scorebot : scorebots) {
				if (scorebot.getId().equals(word))
					return scorebot;
			}
		}

		return null;
	}
	
	public void shutdown() {
		executorService.shutdown();
	}
}
