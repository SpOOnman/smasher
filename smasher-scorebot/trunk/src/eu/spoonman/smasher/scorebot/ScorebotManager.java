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
import java.util.Iterator;
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

	private final static ScorebotManager scorebotManager = new ScorebotManager();
	
	private final ExecutorService executorService;
	private Random random = new Random();
	
	private List<ScorebotThread> threads;


	private ScorebotManager() {
		threads = new ArrayList<ScorebotThread>();
		executorService = Executors.newCachedThreadPool();
	}

	public synchronized static ScorebotManager getInstance() {
		return scorebotManager;
	}

	public ServerInfoScorebot createOrGetScorebot(Games game, InetAddress address, int port, List<String> args) {
		
		log.debug(String.format("Create or get scorebot for %s %s %d %s", game, address, port, args));

		synchronized (threads) {
			clearOldThreads();
			AbstractQuery abstractQuery = ServerQueryManager.createServerQuery(game, address, port, args);
			
			for(ScorebotThread thread : threads) {
			
				if (thread.getScorebot().getServerQuery().equals(abstractQuery)) {
					log.debug(String.format("Scorebot %s founded as matching description", thread.getScorebot().getId()));
					return thread.getScorebot();
				}
			}

			log.debug("Scorebot with given description not found, creating new one");
			ServerInfoScorebot scorebot = new ServerInfoScorebot(createUniqueId(), abstractQuery);
			
			runScorebot(scorebot);
			return scorebot;
		}
	}

	/**
	 * Call only in already synchronized environment.
	 */
	private void clearOldThreads() {
		for (Iterator<ScorebotThread> iterator = threads.iterator(); iterator.hasNext();) {
			if (!iterator.next().isAlive())
				iterator.remove();
		}
	}

	public void stopScorebot(Scorebot scorebot) {
		scorebot.stop();
	}

	private void runScorebot(final ServerInfoScorebot scorebot) {
		ScorebotThread scorebotThread = new ScorebotThread(scorebot);
		scorebotThread.run();
		
		threads.add(scorebotThread);
	}

	private String createUniqueId() {

		boolean found = false;
		String id = null;

		do {
			char letter = (char) (random.nextInt(26) + 65); // 'A' = 65
			id = String.format("%c%d", letter, random.nextInt(8) + 1);
			
			for (ScorebotThread thread : threads) {
				if (thread.getScorebot().getId().equals(id)) {
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

		synchronized (threads) {
			clearOldThreads();
			
			for (ScorebotThread thread : threads) {
				if (thread.getScorebot().getId().equals(word))
					return thread.getScorebot();
			}
		}

		return null;
	}
	
	public void shutdown() {
		executorService.shutdown();
	}
}
