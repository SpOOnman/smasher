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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import eu.spoonman.smasher.common.LCS;
import eu.spoonman.smasher.common.DiffData;
import eu.spoonman.smasher.scorebot.persister.PersisterFactory;
import eu.spoonman.smasher.scorebot.persister.ScorebotPersister;
import eu.spoonman.smasher.serverinfo.AbstractQuery;
import eu.spoonman.smasher.serverinfo.GameInfo;
import eu.spoonman.smasher.serverinfo.Games;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ProgressInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfoStatus;
import eu.spoonman.smasher.serverinfo.TeamInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class ServerInfoScorebot extends Scorebot {
	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger.getLogger(ServerInfoScorebot.class);

	protected final long interval = 1500; // ms
	protected int count = 0;

	private Boolean running = false;

	protected AbstractQuery serverQuery;
	protected ServerInfo previousServerInfo;
	protected ServerInfo currentServerInfo;
	
	protected List<ScorebotPersister> persisters;
	
	public ServerInfoScorebot(String id, AbstractQuery serverQuery) {
		super(id);
		this.serverQuery = serverQuery;
	}
	
	@Override
	public Games getGame() {
		return serverQuery.getGame();
	}
	
	@Override
	public String getIdentification() {
		return serverQuery.getIdentification();
	}
	
	@Override
	public void start() {
		
		if (isRunning()) {
			log.debug("Scorebot is already running. It won't be started again.");
			return;
		}

		setRunning(true);
		
		try {
			internalStart();
		} catch (ScorebotFatalException e) {
			log.error(e);
			exitMessage = e.getMessage();
			setRunning(false);
		}
		
		scorebotStopEvent.notifyAll(new DiffData<Scorebot>(this, this));
		
		log.info(String.format("Scorebot %s has stopped.", getId()));
	}

	@Override
	public void stop() {
		log.debug("Stopping scorebot " + this.getId());
		setRunning(false);
	}

	private void setRunning(boolean bool) {
		synchronized (running) {
			running = bool;
		}
	}

	protected boolean isRunning() {
		synchronized (running) {
			return running;
		}
	}

	protected void internalStart() throws ScorebotFatalException {

		scorebotStartEvent.notifyAll(new DiffData<Scorebot>(this, this));
		
		while (isRunning()) {

			currentServerInfo = internalQuery();
			log.info(currentServerInfo);
			
			if (currentServerInfo.getStatus() == ServerInfoStatus.FATAL_RESPONSE)
				throw new ScorebotFatalException("Not valid response. Maybe the match was ended already?");
			
			//Don't difference when response is not valid.
			if (currentServerInfo.getStatus() == ServerInfoStatus.OK) {
				
				//Create persisters right after first good query. 
				if (persisters == null)
					persisters = PersisterFactory.getPersisters(this, serverQuery.getGame(), currentServerInfo.getGameVersion(), currentServerInfo.getModVersion());
				
				difference();
	
				previousServerInfo = currentServerInfo;
			}

			count++;

			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				log.error("Scorebot thread unexpectedly interrupted.", e);
			}
		}
	}
	
	protected ServerInfo internalQuery() {
		return serverQuery.query();
	}

	protected void differenceGameInfo() {
		GameInfo prevGameInfo = previousServerInfo == null ? null : previousServerInfo.getGameInfo();

		if (prevGameInfo == null && currentServerInfo.getGameInfo() != null) {
			gameInfoChange.notifyAll(new DiffData<GameInfo>(null, currentServerInfo.getGameInfo()));
			return;
		}

		if (!(prevGameInfo.equals(currentServerInfo.getGameInfo())))
			gameInfoChange.notifyAll(new DiffData<GameInfo>(previousServerInfo.getGameInfo(),
					currentServerInfo.getGameInfo()));
	}

	protected void differenceProgressInfo() {
		ProgressInfo prevProgressInfo = previousServerInfo == null ? null : previousServerInfo
				.getProgressInfo();
		
		for (ScorebotPersister persister : persisters)
			persister.persist(prevProgressInfo, currentServerInfo.getProgressInfo());

		if (prevProgressInfo == null && currentServerInfo.getProgressInfo() != null) {
			progressInfoChange.notifyAll(new DiffData<ProgressInfo>(null, currentServerInfo
					.getProgressInfo()));
			return;
		}

		if (!(prevProgressInfo.equals(currentServerInfo.getProgressInfo())))
			progressInfoChange.notifyAll(new DiffData<ProgressInfo>(previousServerInfo
					.getProgressInfo(), currentServerInfo.getProgressInfo()));
	}
	
	protected void differenceTeamInfos() {
		
		for(TeamKey key : TeamKey.values()) {
			TeamInfo left = previousServerInfo == null || previousServerInfo.getTeamInfos() == null ? null : previousServerInfo.getTeamInfos().get(key);
			TeamInfo right = currentServerInfo == null || currentServerInfo.getTeamInfos() == null ? null : currentServerInfo.getTeamInfos().get(key);
			
			if (left == null && right == null)
				continue;
			
			if (left != null && right != null) {
				
				for (ScorebotPersister persister : persisters)
					persister.persist(left, right);
				
				differenceTeamInfo(new DiffData<TeamInfo>(left, right));
			}
			
			if ((left == null && right != null) || (left != null && right == null)) {
				log.debug("One of teams disappeared.");
			}
		}
	}

	protected void differenceTeamInfo(DiffData<TeamInfo> pair) {
		if (!(pair.getFirst().getName() == null && pair.getSecond().getName() == null) &&
				(pair.getFirst().getName() == null && pair.getSecond().getName() != null ||
				pair.getFirst().getName() != null && pair.getSecond().getName() == null ||
				!(pair.getFirst().getName().equals(pair.getSecond().getName())))) {
			teamNameChangedEvent.notifyAll(pair);
		}
		
		if (pair.getFirst().getScore() != pair.getSecond().getScore()) {
			teamScoreChangedEvent.notifyAll(pair);
		}
	}

	protected void differencePlayerInfos() {
		List<PlayerInfo> left = previousServerInfo == null ? new ArrayList<PlayerInfo>() : previousServerInfo
				.getPlayerInfos();
		
		for (ScorebotPersister persister : persisters)
			persister.persist(left, currentServerInfo.getPlayerInfos());
		
		//TODO: move this into persister.
		LCS<PlayerInfo> lcs = new LCS<PlayerInfo>(left, currentServerInfo.getPlayerInfos(),
				new Comparator<PlayerInfo>() {

					@Override
					public int compare(PlayerInfo o1, PlayerInfo o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});

		List<DiffData<PlayerInfo>> pairs = lcs.getLCSPairs();
		reviseLCSPlayerPairs(pairs);
		
		for (ScorebotPersister persister : persisters)
			persister.persist(pairs);

		for (DiffData<PlayerInfo> pair : pairs) {
			differencePlayerInfo(pair);
		}
	}

	protected void differencePlayerInfo(DiffData<PlayerInfo> pair) {
		
		for (ScorebotPersister persister : persisters)
			persister.persist(pair.getFirst(), pair.getSecond());
		
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
		
		//Do not difference with ping changes. Who cares anyway?
		/*if (pair.getFirst().getPing() != pair.getSecond().getPing())
			playerPingChangedEvent.notifyAll(pair);*/
	}

	/**
	 * Check if player changed name, connect or disconnect based on LCS pairs.
	 * 
	 */
	void reviseLCSPlayerPairs(List<DiffData<PlayerInfo>> pairs) {

		DiffData<PlayerInfo> prevPair = null;

		for (Iterator<DiffData<PlayerInfo>> iterator = pairs.iterator(); iterator.hasNext();) {
			DiffData<PlayerInfo> pair = iterator.next();

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

	void difference() {
		assert (previousServerInfo != null || count == 0);
		assert (currentServerInfo != null);
		
		differenceStartEvent.notifyAll(new DiffData<Scorebot>(this, this));
		
		for (ScorebotPersister persister : persisters)
			persister.prePersist();
		
		for (ScorebotPersister persister : persisters)
			persister.persist(previousServerInfo, currentServerInfo);

		//Please ensure that game info is differenced first so in case of change we can change
		//display rules before any other changes are differenced.
		differenceFirst();
		differenceSecond();
		differenceThird();
		differenceFourth();
		
		for (ScorebotPersister persister : persisters)
			persister.postPersist();
		
		differenceStopEvent.notifyAll(new DiffData<Scorebot>(this, this));
	}
	
	protected void differenceFirst() {
		differenceGameInfo();
	}
	
	protected void differenceSecond() {
		differenceProgressInfo();
	}
	
	protected void differenceThird() {
		differenceTeamInfos();
	}
	
	protected void differenceFourth() {
		differencePlayerInfos();
	}

	//Needed for tests
	public ServerInfo getPreviousServerInfo() {
		return previousServerInfo;
	}

	void setPreviousServerInfo(ServerInfo previousServerInfo) {
		this.previousServerInfo = previousServerInfo;
	}

	public ServerInfo getCurrentServerInfo() {
		return currentServerInfo;
	}

	void setCurrentServerInfo(ServerInfo currentServerInfo) {
		this.currentServerInfo = currentServerInfo;
	}
	
	AbstractQuery getServerQuery() {
		return serverQuery;
	}
}
