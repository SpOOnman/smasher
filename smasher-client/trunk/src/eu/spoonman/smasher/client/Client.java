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

/**
 * Default abstract implementation of scorebot subscription.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public abstract class Client {

	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger.getLogger(Client.class);

	private String loggingPattern = "< %s > : < %s >";

	protected void register(Scorebot scorebot) {
		registerOnGameInfoChange(scorebot);
		
		registerOnPlayerConnectedEvent(scorebot);
		registerOnPlayerDisconnectedEvent(scorebot);
		registerOnPlayerNameChangeEvent(scorebot);
		registerOnPlayerPingChangeEvent(scorebot);
		registerOnPlayerScoreChangeEvent(scorebot);

	}

	protected <T> void logEvent(Pair<T, T> pair) {
		log.debug(String.format(loggingPattern, pair.getFirst(), pair.getSecond()));
	}

	protected void registerOnGameInfoChange(Scorebot scorebot) {
		scorebot.getGameInfoChange().register(new Observer<Pair<GameInfo, GameInfo>>() {
			public void notify(Pair<GameInfo, GameInfo> t) {
				onGameInfoChange(t);
			}
		});
	}

	protected void registerOnPlayerConnectedEvent(Scorebot scorebot) {
		scorebot.getPlayerConnectedEvent().register(new Observer<Pair<PlayerInfo, PlayerInfo>>() {
			public void notify(eu.spoonman.smasher.common.Pair<PlayerInfo, PlayerInfo> t) {
				onPlayerConnectedEvent(t);
			}
		});
	}
	
	protected void registerOnPlayerDisconnectedEvent(Scorebot scorebot) {
		scorebot.getPlayerDisconnectedEvent().register(new Observer<Pair<PlayerInfo, PlayerInfo>>() {
			public void notify(eu.spoonman.smasher.common.Pair<PlayerInfo, PlayerInfo> t) {
				onPlayerDisconnectedEvent(t);
			}
		});
	}
	
	protected void registerOnPlayerNameChangeEvent(Scorebot scorebot) {
		scorebot.getPlayerNameChangedEvent().register(new Observer<Pair<PlayerInfo, PlayerInfo>>() {
			public void notify(eu.spoonman.smasher.common.Pair<PlayerInfo, PlayerInfo> t) {
				onPlayerNameChangeEvent(t);
			}
		});
	}
	
	protected void registerOnPlayerPingChangeEvent(Scorebot scorebot) {
		scorebot.getPlayerPingChangedEvent().register(new Observer<Pair<PlayerInfo, PlayerInfo>>() {
			public void notify(eu.spoonman.smasher.common.Pair<PlayerInfo, PlayerInfo> t) {
				onPlayerPingChangeEvent(t);
			}
		});
	}
	
	protected void registerOnPlayerScoreChangeEvent(Scorebot scorebot) {
		scorebot.getPlayerScoreChangedEvent().register(new Observer<Pair<PlayerInfo, PlayerInfo>>() {
			public void notify(eu.spoonman.smasher.common.Pair<PlayerInfo, PlayerInfo> t) {
				onPlayerScoreChangeEvent(t);
			}
		});
	}
	
	protected void onGameInfoChange(Pair<GameInfo, GameInfo> pair) {
		logEvent(pair);
	}

	protected void onPlayerConnectedEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		logEvent(pair);
	}
	
	protected void onPlayerDisconnectedEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		logEvent(pair);
	}
	
	protected void onPlayerNameChangeEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		logEvent(pair);
	}
	
	protected void onPlayerPingChangeEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		logEvent(pair);
	}
	
	protected void onPlayerScoreChangeEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		logEvent(pair);
	}

}
