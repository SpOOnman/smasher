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

import eu.spoonman.smasher.common.Pair;
import eu.spoonman.smasher.output.OutputConfiguration;
import eu.spoonman.smasher.scorebot.Scorebot;
import eu.spoonman.smasher.serverinfo.GameInfo;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ProgressInfo;
import eu.spoonman.smasher.serverinfo.TeamInfo;

public class ConsoleClient extends Client {
	
	enum State {
		STARTING,
		RUNNING,
		STOPPING
	}
	
	private ConsoleFormatter formatter;
	private State state;
	
	public ConsoleClient(ConsoleFormatter formatter) {
		this.formatter = formatter;
		state = State.STARTING;
	}
	
	@Override
	protected void onDifferenceStartEvent(Pair<Scorebot, Scorebot> pair) {
		super.onDifferenceStartEvent(pair);
		
		//If scorebot was already running and it's first run of a client - we need to force setting new OutputConfiguration.
		if (state == State.RUNNING && pair.getSecond().getCurrentServerInfo() != null)
			formatter.setOutputConfiguration(new OutputConfiguration());
	}
	
	@Override
	protected void onDifferenceStopEvent(Pair<Scorebot, Scorebot> pair) {
		super.onDifferenceStopEvent(pair);
		
		switch (state) {
		case STARTING:
			formatter.formatScorebotStart(pair.getSecond());
			break;
			
		case STOPPING:
			formatter.formatScorebotStop(pair.getSecond());
			break;
			
			default:
				break;
		
		}
		
		formatter.flush();
		
		if (state == State.STARTING)
			state = State.RUNNING;
	}

	@Override
	protected void onGameInfoChange(Pair<GameInfo, GameInfo> pair) {
		super.onGameInfoChange(pair);
		
		assert(pair.getSecond() != null);
		
		if (pair.getFirst() == null || pair.getFirst().getGameType() != pair.getSecond().getGameType())
			formatter.setOutputConfiguration(new OutputConfiguration());
		
		formatter.formatGameInfoChange(pair);
	}

	@Override
	protected void onPlayerConnectedEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		super.onPlayerConnectedEvent(pair);
		formatter.formatPlayerConnectedEvent(pair);
	}

	@Override
	protected void onPlayerDisconnectedEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		super.onPlayerDisconnectedEvent(pair);
		formatter.formatPlayerDisconnectedEvent(pair);
	}

	@Override
	protected void onPlayerNameChangeEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		super.onPlayerNameChangeEvent(pair);
		formatter.formatPlayerNameChange(pair);
	}

	@Override
	protected void onPlayerPingChangeEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		super.onPlayerPingChangeEvent(pair);
		formatter.formatPlayerNameChange(pair);
	}

	@Override
	protected void onPlayerScoreChangeEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		super.onPlayerScoreChangeEvent(pair);
		formatter.formatPlayerScoreChange(pair);
	}

	@Override
	protected void onProgressInfoChange(Pair<ProgressInfo, ProgressInfo> pair) {
		super.onProgressInfoChange(pair);
		formatter.formatProgressInfoChange(pair);
	}

	@Override
	protected void onTeamNameChangeEvent(Pair<TeamInfo, TeamInfo> pair) {
		super.onTeamNameChangeEvent(pair);
		formatter.formatTeamNameChange(pair);
	}

	@Override
	protected void onTeamScoreChangeEvent(Pair<TeamInfo, TeamInfo> pair) {
		super.onTeamScoreChangeEvent(pair);
		formatter.formatTeamScoreChange(pair);
	}

}
