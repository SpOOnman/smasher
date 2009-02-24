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

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import eu.spoonman.smasher.common.LCS;
import eu.spoonman.smasher.common.Pair;
import eu.spoonman.smasher.serverinfo.GameInfo;
import eu.spoonman.smasher.serverinfo.Games;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ProgressInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.ServerQuery;
import eu.spoonman.smasher.serverinfo.ServerQueryManager;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class ServerInfoScorebot extends Scorebot {
	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger.getLogger(ServerInfoScorebot.class);

	private final long interval = 800; // ms
	private int count = 0;

	private Boolean running = false;

	private ServerQuery serverQuery;
	private ServerInfo previousServerInfo;
	private ServerInfo currentServerInfo;

	@Override
	public void start() {
		try {
			serverQuery = ServerQueryManager.createServerQuery(Games.QUAKE3ARENA, Inet4Address
					.getByName("194.187.43.245"), 27972);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		setRunning(true);

		new Thread(new Runnable() {

			@Override
			public void run() {
				log.debug("Starting new thread.");
				internalStart();
			}

		}).run();

	}

	@Override
	public void stop() {
		setRunning(false);
	}

	private void setRunning(boolean bool) {
		synchronized (running) {
			running = bool;
		}
	}

	private boolean isRunning() {
		synchronized (running) {
			return running;
		}
	}

	protected synchronized void internalStart() {

		while (isRunning()) {

			currentServerInfo = serverQuery.query();
			log.info(currentServerInfo);

			difference();

			previousServerInfo = currentServerInfo;

			count++;

			try {
				wait(interval);
			} catch (InterruptedException e) {
				log.error("Scorebot thread unexpectedly interrupted.", e);
			}
		}
	}

	private void differenceGameInfo() {
		GameInfo prevGameInfo = previousServerInfo == null ? null : previousServerInfo.getGameInfo();

		if (prevGameInfo == null && currentServerInfo.getGameInfo() != null) {
			gameInfoChange.notifyAll(new Pair<GameInfo, GameInfo>(null, currentServerInfo.getGameInfo()));
			return;
		}

		if (!(prevGameInfo.equals(currentServerInfo.getGameInfo())))
			gameInfoChange.notifyAll(new Pair<GameInfo, GameInfo>(previousServerInfo.getGameInfo(),
					currentServerInfo.getGameInfo()));
	}

	private void differenceProgressInfo() {
		ProgressInfo prevProgressInfo = previousServerInfo == null ? null : previousServerInfo
				.getProgressInfo();

		if (prevProgressInfo == null && currentServerInfo.getProgressInfo() != null) {
			progressInfoChange.notifyAll(new Pair<ProgressInfo, ProgressInfo>(null, currentServerInfo
					.getProgressInfo()));
			return;
		}

		if (!(prevProgressInfo.equals(currentServerInfo.getProgressInfo())))
			progressInfoChange.notifyAll(new Pair<ProgressInfo, ProgressInfo>(previousServerInfo
					.getProgressInfo(), currentServerInfo.getProgressInfo()));
	}

	private void differencePlayerInfos() {
		List<PlayerInfo> left = previousServerInfo == null ? new ArrayList<PlayerInfo>() : previousServerInfo
				.getPlayerInfos();
		
		LCS<PlayerInfo> lcs = new LCS<PlayerInfo>(left, currentServerInfo.getPlayerInfos(),
				new Comparator<PlayerInfo>() {

					@Override
					public int compare(PlayerInfo o1, PlayerInfo o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});

		List<Pair<PlayerInfo, PlayerInfo>> pairs = lcs.getLCSPairs();
		reviseLCSPlayerPairs(pairs);

		for (Pair<PlayerInfo, PlayerInfo> pair : pairs) {
			differencePlayerInfo(pair);
		}
	}

	private void differencePlayerInfo(Pair<PlayerInfo, PlayerInfo> pair) {
		
		if (pair.getFirst() == null && pair.getSecond() != null) {
			playerConnectedEvent.notifyAll(pair);
			return;
		}
		
		if (pair.getFirst() != null && pair.getSecond() == null) {
			playerDisconnectedEvent.notifyAll(pair);
			return;
		}
		
		if (!(pair.getFirst().getName().equals(pair.getSecond().getName())))
			playerNameChangedEvent.notifyAll(pair);
		
		if (pair.getFirst().getScore() != pair.getSecond().getScore())
			playerScoreChangedEvent.notifyAll(pair);
		
		if (pair.getFirst().getPing() != pair.getSecond().getPing())
			playerPingChangedEvent.notifyAll(pair);
	}

	/**
	 * Check if player changed name, connect or disconnect based on LCS pairs.
	 * 
	 */
	void reviseLCSPlayerPairs(List<Pair<PlayerInfo, PlayerInfo>> pairs) {

		Pair<PlayerInfo, PlayerInfo> prevPair = null;

		for (Iterator<Pair<PlayerInfo, PlayerInfo>> iterator = pairs.iterator(); iterator.hasNext();) {
			Pair<PlayerInfo, PlayerInfo> pair = iterator.next();

			// This is the case when player renames.
			// Other case are old disconnected or new connected - leave them as it is.
			if (pair.getFirst() == null) {
				if (prevPair != null && prevPair.getSecond() == null) {
					prevPair.setSecond(pair.getSecond());
					iterator.remove();
				}
			}

			prevPair = pair;
		}
	}

	private void difference() {
		assert (previousServerInfo != null || count == 0);
		assert (currentServerInfo != null);

		differenceGameInfo();
		differenceProgressInfo();
		differencePlayerInfos();
	}

}
