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

import eu.spoonman.smasher.common.DiffData;
import eu.spoonman.smasher.output.OutputConfiguration;
import eu.spoonman.smasher.output.OutputConfigurationFactory;
import eu.spoonman.smasher.scorebot.Scorebot;
import eu.spoonman.smasher.serverinfo.GameInfo;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ProgressInfo;
import eu.spoonman.smasher.serverinfo.TeamInfo;

public class ConsoleSubscription extends Subscription {
	
	private ConsoleFormatter formatter;
	private boolean firstRun = true;
	
	public ConsoleSubscription(Scorebot scorebot, Client client, ConsoleFormatter formatter) {
		super(scorebot, client);
		this.formatter = formatter;
	}
	
	@Override
	protected void postRegister() {
		super.postRegister();
		formatter.formatScorebotStart(getScorebot());
		formatter.flush();
	}
	
	@Override
	protected void preUnregister() {
		super.preUnregister();
		formatter.ensureMainLine();
		formatter.formatScorebotStop(getScorebot());
		formatter.flush();
	}
	
	@Override
	public void showOnDemand() {
		formatter.ensureMainLine();
	}
	
	@Override
	public void showPlayers() {
		formatter.showPlayers();
	}
	
	@Override
	public void renameTeams(String redTeam, String blueTeam) {
		formatter.setTeamNames(redTeam, blueTeam);
	}
	
	@Override
	protected void onDifferenceStartEvent(DiffData<Scorebot> pair) {
		super.onDifferenceStartEvent(pair);
		
		//If scorebot was already running and it's first run of a client - we need to force setting new OutputConfiguration.
		if (firstRun != false && pair.getSecond().getCurrentServerInfo() != null)
			formatter.setOutputConfiguration(OutputConfigurationFactory.getInstance().createOutputConfiguration(pair.getSecond().getCurrentServerInfo().getGameInfo().getGameType()));
	}
	
	@Override
	protected void onDifferenceStopEvent(DiffData<Scorebot> pair) {
		super.onDifferenceStopEvent(pair);
		
		if (firstRun)
			formatter.ensureMainLine();
		
		formatter.formatDifferenceStopEvent(pair);
		
		formatter.flush();
		
		firstRun = false;
	}
	
	@Override
	protected void onScorebotStopEvent(DiffData<Scorebot> pair) {
		super.onScorebotStopEvent(pair);
		
		formatter.ensureMainLine();
		formatter.formatScorebotStop(getScorebot());
		formatter.flush();
	}

	@Override
	protected void onGameInfoChange(DiffData<GameInfo> pair) {
		super.onGameInfoChange(pair);
		
		assert(pair.getSecond() != null);
		
		if (pair.getFirst() == null || pair.getFirst().getGameType() != pair.getSecond().getGameType())
			formatter.setOutputConfiguration(OutputConfigurationFactory.getInstance().createOutputConfiguration(pair.getSecond().getGameType()));
		
		formatter.formatGameInfoChange(pair);
	}

	@Override
	protected void onPlayerConnectedEvent(DiffData<PlayerInfo> pair) {
		super.onPlayerConnectedEvent(pair);
		formatter.formatPlayerConnectedEvent(pair);
	}

	@Override
	protected void onPlayerDisconnectedEvent(DiffData<PlayerInfo> pair) {
		super.onPlayerDisconnectedEvent(pair);
		formatter.formatPlayerDisconnectedEvent(pair);
	}

	@Override
	protected void onPlayerNameChangeEvent(DiffData<PlayerInfo> pair) {
		super.onPlayerNameChangeEvent(pair);
		formatter.formatPlayerNameChange(pair);
	}

	@Override
	protected void onPlayerPingChangeEvent(DiffData<PlayerInfo> pair) {
		super.onPlayerPingChangeEvent(pair);
		formatter.formatPlayerNameChange(pair);
	}

	@Override
	protected void onPlayerScoreChangeEvent(DiffData<PlayerInfo> pair) {
		super.onPlayerScoreChangeEvent(pair);
		formatter.formatPlayerScoreChange(pair);
	}

	@Override
	protected void onProgressInfoChange(DiffData<ProgressInfo> pair) {
		super.onProgressInfoChange(pair);
		formatter.formatProgressInfoChange(pair);
	}

	@Override
	protected void onTeamNameChangeEvent(DiffData<TeamInfo> pair) {
		super.onTeamNameChangeEvent(pair);
		formatter.formatTeamNameChange(pair);
	}

	@Override
	protected void onTeamScoreChangeEvent(DiffData<TeamInfo> pair) {
		super.onTeamScoreChangeEvent(pair);
		formatter.formatTeamScoreChange(pair);
	}

}
