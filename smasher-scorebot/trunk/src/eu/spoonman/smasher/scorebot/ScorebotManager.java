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
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import eu.spoonman.smasher.serverinfo.AbstractQuery;
import eu.spoonman.smasher.serverinfo.Games;
import eu.spoonman.smasher.serverinfo.ServerQueryManager;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class ScorebotManager {
	
	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger.getLogger(ScorebotManager.class);

	private final ExecutorService executorService;
	private List<ServerInfoScorebot> scorebots;
	private Random random = new Random();

	private final static ScorebotManager scorebotManager = new ScorebotManager(); 

	private ScorebotManager() {
		scorebots = new ArrayList<ServerInfoScorebot>();
		executorService = Executors.newCachedThreadPool();
	}

	public synchronized static ScorebotManager getInstance() {
		return scorebotManager;
	}

	public ServerInfoScorebot createOrGetScorebot(Games game, InetAddress address, int port, List<String> args) {
		
		log.debug(String.format("Create or get scorebot for %s %s %d %s", game.toString(), address != null ? address.toString() : "null", port, args.toString()));

		synchronized (scorebots) {
			AbstractQuery abstractQuery = ServerQueryManager.createServerQuery(game, address, port, args);
			
			for (ServerInfoScorebot scorebot : scorebots) {
				if (scorebot.getServerQuery().equals(abstractQuery)) {
					log.debug(String.format("Scorebot %s founded as matching description", scorebot.getId()));
					return scorebot;
				}
			}

			log.debug("Scorebot with given description not found, creating new one");
			ServerInfoScorebot scorebot = new ServerInfoScorebot(createUniqueId(), abstractQuery);
			
			synchronized (scorebots) {
				scorebots.add(scorebot);
				runScorebot(scorebot);
				return scorebot;
			}

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
