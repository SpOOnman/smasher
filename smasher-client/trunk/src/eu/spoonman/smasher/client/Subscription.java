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

import eu.spoonman.smasher.common.Observer;
import eu.spoonman.smasher.common.DiffData;
import eu.spoonman.smasher.scorebot.Scorebot;
import eu.spoonman.smasher.serverinfo.GameInfo;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ProgressInfo;
import eu.spoonman.smasher.serverinfo.TeamInfo;

/**
 * Default abstract implementation of scorebot subscription.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public abstract class Subscription {

	private Observer<DiffData<GameInfo>> gameInfoChangeObserver;
	private Observer<DiffData<ProgressInfo>> progressInfoChangeObserver;
	private Observer<DiffData<TeamInfo>> teamNameChangeEventObserver;
	private Observer<DiffData<TeamInfo>> teamScoreChangeEventObserver;
	private Observer<DiffData<PlayerInfo>> playerConnectedEventObserver;
	private Observer<DiffData<PlayerInfo>> playerDisconnectedEventObserver;
	private Observer<DiffData<PlayerInfo>> playerNameChangeEventObserver;
	private Observer<DiffData<PlayerInfo>> playerPingChangeEventObserver;
	private Observer<DiffData<PlayerInfo>> playerScoreChangeEventObserver;
	private Observer<DiffData<Scorebot>> differenceStartEventObserver;
	private Observer<DiffData<Scorebot>> differenceStopEventObserver;
	private Observer<DiffData<Scorebot>> scorebotStartEventObserver;
	private Observer<DiffData<Scorebot>> scorebotStopEventObserver;
	
	private Scorebot scorebot;
	private Client client;
	
	protected Subscription(Scorebot scorebot, Client client) {
		if (scorebot == null)
			throw new IllegalArgumentException("scorebot cannot be null");
		
		if (client == null)
			throw new IllegalArgumentException("client cannot be null");
		
		this.scorebot = scorebot;
		this.client = client;
	}

	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger.getLogger(Subscription.class);

	private String loggingPattern = "Different %s : < %s > : < %s >";
	
	protected void register() {
		
		registerOnDifferenceStartEvent(scorebot);
		registerOnDifferenceStopEvent(scorebot);
		
		registerOnScorebotStartEvent(scorebot);
		registerOnScorebotStopEvent(scorebot);
		
		registerOnGameInfoChange(scorebot);

		registerOnProgressInfoChange(scorebot);

		registerOnTeamNameChangeEvent(scorebot);
		registerOnTeamScoreChangeEvent(scorebot);

		registerOnPlayerConnectedEvent(scorebot);
		registerOnPlayerDisconnectedEvent(scorebot);
		registerOnPlayerNameChangeEvent(scorebot);
		registerOnPlayerPingChangeEvent(scorebot);
		registerOnPlayerScoreChangeEvent(scorebot);
		
		postRegister();
	}
	
	protected void postRegister() {}
	
	protected void preUnregister() {}
	
	protected void unregister() {
		
		preUnregister();
		
		scorebot.getDifferenceStartEvent().unregister(differenceStartEventObserver);
		scorebot.getDifferenceStopEvent().unregister(differenceStopEventObserver);
		
		scorebot.getScorebotStartEvent().unregister(scorebotStartEventObserver);
		scorebot.getScorebotStopEvent().unregister(scorebotStopEventObserver);
		
		scorebot.getGameInfoChange().unregister(gameInfoChangeObserver);
		
		scorebot.getProgressInfoChange().unregister(progressInfoChangeObserver);
		
		scorebot.getTeamNameChangedEvent().unregister(teamNameChangeEventObserver);
		scorebot.getTeamScoreChangedEvent().unregister(teamScoreChangeEventObserver);
		
		scorebot.getPlayerConnectedEvent().unregister(playerConnectedEventObserver);
		scorebot.getPlayerDisconnectedEvent().unregister(playerDisconnectedEventObserver);
		scorebot.getPlayerNameChangedEvent().unregister(playerNameChangeEventObserver);
		scorebot.getPlayerPingChangedEvent().unregister(playerPingChangeEventObserver);
		scorebot.getPlayerScoreChangedEvent().unregister(playerScoreChangeEventObserver);
		
		differenceStartEventObserver = null;
		differenceStopEventObserver = null;
		scorebotStartEventObserver = null;
		scorebotStopEventObserver = null;
		gameInfoChangeObserver = null;
		progressInfoChangeObserver = null;
		teamNameChangeEventObserver = null;
		teamScoreChangeEventObserver = null;
		playerConnectedEventObserver = null;
		playerDisconnectedEventObserver = null;
		playerNameChangeEventObserver = null;
		playerPingChangeEventObserver = null;
		playerScoreChangeEventObserver = null;
	}
	
	public abstract void showPlayers();
	
	public abstract void renameTeams(String redTeam, String blueTeam);

	protected <T> void logEvent(String name, DiffData<T> pair) {
		log.debug(String.format(loggingPattern, name, pair.getFirst(), pair.getSecond()));
	}

	protected void registerOnGameInfoChange(Scorebot scorebot) {
		gameInfoChangeObserver = new Observer<DiffData<GameInfo>>() {
			public void notify(DiffData<GameInfo> pair) {
				onGameInfoChange(pair);
			}
		};

		scorebot.getGameInfoChange().register(gameInfoChangeObserver);
	}

	protected void registerOnProgressInfoChange(Scorebot scorebot) {
		progressInfoChangeObserver = new Observer<DiffData<ProgressInfo>>() {
			public void notify(DiffData<ProgressInfo> pair) {
				onProgressInfoChange(pair);
			}
		};
		scorebot.getProgressInfoChange().register(progressInfoChangeObserver);
	}

	protected void registerOnTeamNameChangeEvent(Scorebot scorebot) {
		teamNameChangeEventObserver = new Observer<DiffData<TeamInfo>>() {
			public void notify(eu.spoonman.smasher.common.DiffData<TeamInfo> pair) {
				onTeamNameChangeEvent(pair);

			};
		};

		scorebot.getTeamNameChangedEvent().register(teamNameChangeEventObserver);
	}

	protected void registerOnTeamScoreChangeEvent(Scorebot scorebot) {
		teamScoreChangeEventObserver = new Observer<DiffData<TeamInfo>>() {
			public void notify(eu.spoonman.smasher.common.DiffData<TeamInfo> pair) {
				onTeamScoreChangeEvent(pair);

			};
		};
		scorebot.getTeamScoreChangedEvent().register(teamScoreChangeEventObserver);
	}

	protected void registerOnPlayerConnectedEvent(Scorebot scorebot) {
		playerConnectedEventObserver = new Observer<DiffData<PlayerInfo>>() {
			public void notify(eu.spoonman.smasher.common.DiffData<PlayerInfo> pair) {
				onPlayerConnectedEvent(pair);
			}
		};

		scorebot.getPlayerConnectedEvent().register(playerConnectedEventObserver);
	}

	protected void registerOnPlayerDisconnectedEvent(Scorebot scorebot) {
		playerDisconnectedEventObserver = new Observer<DiffData<PlayerInfo>>() {
			public void notify(eu.spoonman.smasher.common.DiffData<PlayerInfo> pair) {
				onPlayerDisconnectedEvent(pair);
			}
		};

		scorebot.getPlayerDisconnectedEvent().register(playerDisconnectedEventObserver);
	}

	protected void registerOnPlayerNameChangeEvent(Scorebot scorebot) {
		playerNameChangeEventObserver = new Observer<DiffData<PlayerInfo>>() {
			public void notify(eu.spoonman.smasher.common.DiffData<PlayerInfo> pair) {
				onPlayerNameChangeEvent(pair);
			}
		};

		scorebot.getPlayerNameChangedEvent().register(playerNameChangeEventObserver);
	}

	protected void registerOnPlayerPingChangeEvent(Scorebot scorebot) {
		playerPingChangeEventObserver = new Observer<DiffData<PlayerInfo>>() {
			public void notify(eu.spoonman.smasher.common.DiffData<PlayerInfo> pair) {
				onPlayerPingChangeEvent(pair);
			}
		};

		scorebot.getPlayerPingChangedEvent().register(playerPingChangeEventObserver);
	}

	protected void registerOnPlayerScoreChangeEvent(Scorebot scorebot) {
		playerScoreChangeEventObserver = new Observer<DiffData<PlayerInfo>>() {
			public void notify(eu.spoonman.smasher.common.DiffData<PlayerInfo> pair) {
				onPlayerScoreChangeEvent(pair);
			}
		};

		scorebot.getPlayerScoreChangedEvent().register(playerScoreChangeEventObserver);
	}
	
	protected void registerOnDifferenceStartEvent(Scorebot scorebot) {
		differenceStartEventObserver = new Observer<DiffData<Scorebot>>() {
			public void notify(eu.spoonman.smasher.common.DiffData<Scorebot> pair) {
				onDifferenceStartEvent(pair);
			}
		};
		
		scorebot.getDifferenceStartEvent().register(differenceStartEventObserver);
	}
	
	protected void registerOnDifferenceStopEvent(Scorebot scorebot) {
		differenceStopEventObserver = new Observer<DiffData<Scorebot>>() {
			public void notify(eu.spoonman.smasher.common.DiffData<Scorebot> pair) {
				onDifferenceStopEvent(pair);
			}
		};
		
		scorebot.getDifferenceStopEvent().register(differenceStopEventObserver);
	}
	
	protected void registerOnScorebotStartEvent(Scorebot scorebot) {
		scorebotStartEventObserver = new Observer<DiffData<Scorebot>>() {
			public void notify(eu.spoonman.smasher.common.DiffData<Scorebot> pair) {
				onScorebotStartEvent(pair);
			}
		};
		
		scorebot.getScorebotStartEvent().register(scorebotStartEventObserver);
	}
	
	protected void registerOnScorebotStopEvent(Scorebot scorebot) {
		scorebotStopEventObserver = new Observer<DiffData<Scorebot>>() {
			public void notify(eu.spoonman.smasher.common.DiffData<Scorebot> pair) {
				onScorebotStopEvent(pair);
			}
		};
		
		scorebot.getScorebotStopEvent().register(scorebotStopEventObserver);
	}

	protected void onGameInfoChange(DiffData<GameInfo> pair) {
		logEvent("GameInfo", pair);
	}

	protected void onProgressInfoChange(DiffData<ProgressInfo> pair) {
		logEvent("ProgressInfo", pair);
	}

	protected void onTeamNameChangeEvent(DiffData<TeamInfo> pair) {
		logEvent("TeamName", pair);
	}

	protected void onTeamScoreChangeEvent(DiffData<TeamInfo> pair) {
		logEvent("TeamScore", pair);
	}

	protected void onPlayerConnectedEvent(DiffData<PlayerInfo> pair) {
		logEvent("PlayerConnected", pair);
	}

	protected void onPlayerDisconnectedEvent(DiffData<PlayerInfo> pair) {
		logEvent("PlayerDisconnected", pair);
	}

	protected void onPlayerNameChangeEvent(DiffData<PlayerInfo> pair) {
		logEvent("PlayerName", pair);
	}

	protected void onPlayerPingChangeEvent(DiffData<PlayerInfo> pair) {
		logEvent("PlayerPing", pair);
	}

	protected void onPlayerScoreChangeEvent(DiffData<PlayerInfo> pair) {
		logEvent("PlayerScore", pair);
	}
	
	protected void onDifferenceStartEvent(DiffData<Scorebot> pair) {
		logEvent("DifferenceStart", pair);
	}
	
	protected void onDifferenceStopEvent(DiffData<Scorebot> pair) {
		logEvent("DifferenceStop", pair);
	}
	
	protected void onScorebotStartEvent(DiffData<Scorebot> pair) {
		logEvent("ScorebotStart", pair);
	}
	
	protected void onScorebotStopEvent(DiffData<Scorebot> pair) {
		logEvent("ScorebotStop", pair);
	}
	
	public Scorebot getScorebot() {
		return scorebot;
	}
	
	public Client getClient() {
		return client;
	}

}
