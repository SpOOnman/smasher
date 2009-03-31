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

import eu.spoonman.smasher.serverinfo.GameInfo;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ProgressInfo;
import eu.spoonman.smasher.serverinfo.TeamInfo;

public abstract class Scorebot {
	
	protected GameDelegate<Scorebot> differenceStartEvent;
	protected GameDelegate<Scorebot> differenceStopEvent;
	
	protected GameDelegate<GameInfo> gameInfoChange;
	protected GameDelegate<ProgressInfo> progressInfoChange;
	protected GameDelegate<PlayerInfo> playerInfoChange;
	
	protected GameDelegate<PlayerInfo> playerConnectedEvent;
	protected GameDelegate<PlayerInfo> playerDisconnectedEvent;
	protected GameDelegate<PlayerInfo> playerNameChangedEvent;
	protected GameDelegate<PlayerInfo> playerScoreChangedEvent;
	protected GameDelegate<PlayerInfo> playerPingChangedEvent;
	
	protected GameDelegate<TeamInfo> teamNameChangedEvent;
	protected GameDelegate<TeamInfo> teamScoreChangedEvent;
	
	public Scorebot() {
		
		differenceStartEvent = new GameDelegate<Scorebot>();
		differenceStopEvent = new GameDelegate<Scorebot>();
		
		gameInfoChange = new GameDelegate<GameInfo>();
		progressInfoChange = new GameDelegate<ProgressInfo>();
		playerInfoChange = new GameDelegate<PlayerInfo>();
		
		playerConnectedEvent = new GameDelegate<PlayerInfo>();
		playerDisconnectedEvent = new GameDelegate<PlayerInfo>();
		playerNameChangedEvent = new GameDelegate<PlayerInfo>();
		playerScoreChangedEvent = new GameDelegate<PlayerInfo>();
		playerPingChangedEvent = new GameDelegate<PlayerInfo>();
		
		teamNameChangedEvent = new GameDelegate<TeamInfo>();
		teamScoreChangedEvent = new GameDelegate<TeamInfo>();
		
	}
	
	public abstract void start();
	
	public abstract void stop();

	public GameDelegate<GameInfo> getGameInfoChange() {
		return gameInfoChange;
	}
	
	public GameDelegate<ProgressInfo> getProgressInfoChange() {
		return progressInfoChange;
	}

	public GameDelegate<PlayerInfo> getPlayerInfoChange() {
		return playerInfoChange;
	}

	public GameDelegate<PlayerInfo> getPlayerConnectedEvent() {
		return playerConnectedEvent;
	}

	public GameDelegate<PlayerInfo> getPlayerDisconnectedEvent() {
		return playerDisconnectedEvent;
	}

	public GameDelegate<PlayerInfo> getPlayerNameChangedEvent() {
		return playerNameChangedEvent;
	}

	public GameDelegate<PlayerInfo> getPlayerScoreChangedEvent() {
		return playerScoreChangedEvent;
	}

	public GameDelegate<PlayerInfo> getPlayerPingChangedEvent() {
		return playerPingChangedEvent;
	}

	public GameDelegate<TeamInfo> getTeamNameChangedEvent() {
		return teamNameChangedEvent;
	}

	public GameDelegate<TeamInfo> getTeamScoreChangedEvent() {
		return teamScoreChangedEvent;
	}
}
