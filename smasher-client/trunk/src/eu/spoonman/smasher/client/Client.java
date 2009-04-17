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
import eu.spoonman.smasher.common.Pair;
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
public abstract class Client {

	private Observer<Pair<GameInfo, GameInfo>> gameInfoChangeObserver;
	private Observer<Pair<ProgressInfo, ProgressInfo>> progressInfoChangeObserver;
	private Observer<Pair<TeamInfo, TeamInfo>> teamNameChangeEventObserver;
	private Observer<Pair<TeamInfo, TeamInfo>> teamScoreChangeEventObserver;
	private Observer<Pair<PlayerInfo, PlayerInfo>> playerConnectedEventObserver;
	private Observer<Pair<PlayerInfo, PlayerInfo>> playerDisconnectedEventObserver;
	private Observer<Pair<PlayerInfo, PlayerInfo>> playerNameChangeEventObserver;
	private Observer<Pair<PlayerInfo, PlayerInfo>> playerPingChangeEventObserver;
	private Observer<Pair<PlayerInfo, PlayerInfo>> playerScoreChangeEventObserver;
	private Observer<Pair<Scorebot, Scorebot>> differenceStartEventObserver;
	private Observer<Pair<Scorebot, Scorebot>> differenceStopEventObserver;
	
	private Scorebot scorebot;

	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger.getLogger(Client.class);

	private String loggingPattern = "Differentt %s : < %s > : < %s >";

	protected void register(Scorebot scorebot) {
		
		registerOnDifferenceStartEvent(scorebot);
		registerOnDifferenceStopEvent(scorebot);
		
		registerOnGameInfoChange(scorebot);

		registerOnProgressInfoChange(scorebot);

		registerOnTeamNameChangeEvent(scorebot);
		registerOnTeamScoreChangeEvent(scorebot);

		registerOnPlayerConnectedEvent(scorebot);
		registerOnPlayerDisconnectedEvent(scorebot);
		registerOnPlayerNameChangeEvent(scorebot);
		registerOnPlayerPingChangeEvent(scorebot);
		registerOnPlayerScoreChangeEvent(scorebot);
		
		this.scorebot = scorebot;

	}
	
	protected void unregister(Scorebot scorebot) {
		scorebot.getDifferenceStartEvent().unregister(differenceStartEventObserver);
		scorebot.getDifferenceStopEvent().unregister(differenceStopEventObserver);
		
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
		gameInfoChangeObserver = null;
		progressInfoChangeObserver = null;
		teamNameChangeEventObserver = null;
		teamScoreChangeEventObserver = null;
		playerConnectedEventObserver = null;
		playerDisconnectedEventObserver = null;
		playerNameChangeEventObserver = null;
		playerPingChangeEventObserver = null;
		playerScoreChangeEventObserver = null;
		
		this.scorebot = null;
	}

	protected <T> void logEvent(String name, Pair<T, T> pair) {
		log.debug(String.format(loggingPattern, name, pair.getFirst(), pair.getSecond()));
	}

	protected void registerOnGameInfoChange(Scorebot scorebot) {
		gameInfoChangeObserver = new Observer<Pair<GameInfo, GameInfo>>() {
			public void notify(Pair<GameInfo, GameInfo> pair) {
				onGameInfoChange(pair);
			}
		};

		scorebot.getGameInfoChange().register(gameInfoChangeObserver);
	}

	protected void registerOnProgressInfoChange(Scorebot scorebot) {
		progressInfoChangeObserver = new Observer<Pair<ProgressInfo, ProgressInfo>>() {
			public void notify(Pair<ProgressInfo, ProgressInfo> pair) {
				onProgressInfoChange(pair);
			}
		};
		scorebot.getProgressInfoChange().register(progressInfoChangeObserver);
	}

	protected void registerOnTeamNameChangeEvent(Scorebot scorebot) {
		teamNameChangeEventObserver = new Observer<Pair<TeamInfo, TeamInfo>>() {
			public void notify(eu.spoonman.smasher.common.Pair<TeamInfo, TeamInfo> pair) {
				onTeamNameChangeEvent(pair);

			};
		};

		scorebot.getTeamNameChangedEvent().register(teamNameChangeEventObserver);
	}

	protected void registerOnTeamScoreChangeEvent(Scorebot scorebot) {
		teamScoreChangeEventObserver = new Observer<Pair<TeamInfo, TeamInfo>>() {
			public void notify(eu.spoonman.smasher.common.Pair<TeamInfo, TeamInfo> pair) {
				onTeamScoreChangeEvent(pair);

			};
		};
		scorebot.getTeamScoreChangedEvent().register(teamScoreChangeEventObserver);
	}

	protected void registerOnPlayerConnectedEvent(Scorebot scorebot) {
		playerConnectedEventObserver = new Observer<Pair<PlayerInfo, PlayerInfo>>() {
			public void notify(eu.spoonman.smasher.common.Pair<PlayerInfo, PlayerInfo> pair) {
				onPlayerConnectedEvent(pair);
			}
		};

		scorebot.getPlayerConnectedEvent().register(playerConnectedEventObserver);
	}

	protected void registerOnPlayerDisconnectedEvent(Scorebot scorebot) {
		playerDisconnectedEventObserver = new Observer<Pair<PlayerInfo, PlayerInfo>>() {
			public void notify(eu.spoonman.smasher.common.Pair<PlayerInfo, PlayerInfo> pair) {
				onPlayerDisconnectedEvent(pair);
			}
		};

		scorebot.getPlayerDisconnectedEvent().register(playerDisconnectedEventObserver);
	}

	protected void registerOnPlayerNameChangeEvent(Scorebot scorebot) {
		playerNameChangeEventObserver = new Observer<Pair<PlayerInfo, PlayerInfo>>() {
			public void notify(eu.spoonman.smasher.common.Pair<PlayerInfo, PlayerInfo> pair) {
				onPlayerNameChangeEvent(pair);
			}
		};

		scorebot.getPlayerNameChangedEvent().register(playerNameChangeEventObserver);
	}

	protected void registerOnPlayerPingChangeEvent(Scorebot scorebot) {
		playerPingChangeEventObserver = new Observer<Pair<PlayerInfo, PlayerInfo>>() {
			public void notify(eu.spoonman.smasher.common.Pair<PlayerInfo, PlayerInfo> pair) {
				onPlayerPingChangeEvent(pair);
			}
		};

		scorebot.getPlayerPingChangedEvent().register(playerPingChangeEventObserver);
	}

	protected void registerOnPlayerScoreChangeEvent(Scorebot scorebot) {
		playerScoreChangeEventObserver = new Observer<Pair<PlayerInfo, PlayerInfo>>() {
			public void notify(eu.spoonman.smasher.common.Pair<PlayerInfo, PlayerInfo> pair) {
				onPlayerScoreChangeEvent(pair);
			}
		};

		scorebot.getPlayerScoreChangedEvent().register(playerScoreChangeEventObserver);
	}
	
	protected void registerOnDifferenceStartEvent(Scorebot scorebot) {
		differenceStartEventObserver = new Observer<Pair<Scorebot, Scorebot>>() {
			public void notify(eu.spoonman.smasher.common.Pair<Scorebot, Scorebot> pair) {
				onDifferenceStartEvent(pair);
			}
		};
		
		scorebot.getDifferenceStartEvent().register(differenceStartEventObserver);
	}
	
	protected void registerOnDifferenceStopEvent(Scorebot scorebot) {
		differenceStopEventObserver = new Observer<Pair<Scorebot, Scorebot>>() {
			public void notify(eu.spoonman.smasher.common.Pair<Scorebot, Scorebot> pair) {
				onDifferenceStopEvent(pair);
			}
		};
		
		scorebot.getDifferenceStopEvent().register(differenceStopEventObserver);
	}

	protected void onGameInfoChange(Pair<GameInfo, GameInfo> pair) {
		logEvent("GameInfo", pair);
	}

	protected void onProgressInfoChange(Pair<ProgressInfo, ProgressInfo> pair) {
		logEvent("ProgressInfo", pair);
	}

	protected void onTeamNameChangeEvent(Pair<TeamInfo, TeamInfo> pair) {
		logEvent("TeamName", pair);
	}

	protected void onTeamScoreChangeEvent(Pair<TeamInfo, TeamInfo> pair) {
		logEvent("TeamScore", pair);
	}

	protected void onPlayerConnectedEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		logEvent("PlayerConnected", pair);
	}

	protected void onPlayerDisconnectedEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		logEvent("PlayerDisconnected", pair);
	}

	protected void onPlayerNameChangeEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		logEvent("PlayerName", pair);
	}

	protected void onPlayerPingChangeEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		logEvent("PlayerPing", pair);
	}

	protected void onPlayerScoreChangeEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		logEvent("PlayerScore", pair);
	}
	
	protected void onDifferenceStartEvent(Pair<Scorebot, Scorebot> pair) {
		logEvent("DifferenceStart", pair);
	}
	
	protected void onDifferenceStopEvent(Pair<Scorebot, Scorebot> pair) {
		logEvent("DifferenceStop", pair);
	}
	
	public Scorebot getScorebot() {
		return scorebot;
	}

}
