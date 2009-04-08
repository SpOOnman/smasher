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
import eu.spoonman.smasher.scorebot.ServerInfoScorebot;
import eu.spoonman.smasher.serverinfo.GameInfo;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ProgressInfo;
import eu.spoonman.smasher.serverinfo.TeamInfo;

public class ConsoleClient extends Client {
	

	private ConsoleFormatter consoleFormatter;

	public static void main(String[] args) throws InterruptedException {

		final ConsoleClient client = new ConsoleClient();

		ServerInfoScorebot scorebot = new ServerInfoScorebot();
		client.register(scorebot);
		
		scorebot.start();

		synchronized (client) {
			client.wait(3000);
		}
		
		scorebot.stop();
	}

	@Override
	protected void onGameInfoChange(Pair<GameInfo, GameInfo> pair) {
		super.onGameInfoChange(pair);
	}

	@Override
	protected void onPlayerConnectedEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		// TODO Auto-generated method stub
		super.onPlayerConnectedEvent(pair);
	}

	@Override
	protected void onPlayerDisconnectedEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		// TODO Auto-generated method stub
		super.onPlayerDisconnectedEvent(pair);
	}

	@Override
	protected void onPlayerNameChangeEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		// TODO Auto-generated method stub
		super.onPlayerNameChangeEvent(pair);
	}

	@Override
	protected void onPlayerPingChangeEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		// TODO Auto-generated method stub
		super.onPlayerPingChangeEvent(pair);
	}

	@Override
	protected void onPlayerScoreChangeEvent(Pair<PlayerInfo, PlayerInfo> pair) {
		// TODO Auto-generated method stub
		super.onPlayerScoreChangeEvent(pair);
	}

	@Override
	protected void onProgressInfoChange(Pair<ProgressInfo, ProgressInfo> pair) {
		// TODO Auto-generated method stub
		super.onProgressInfoChange(pair);
	}

	@Override
	protected void onTeamNameChangeEvent(Pair<TeamInfo, TeamInfo> pair) {
		// TODO Auto-generated method stub
		super.onTeamNameChangeEvent(pair);
	}

	@Override
	protected void onTeamScoreChangeEvent(Pair<TeamInfo, TeamInfo> pair) {
		// TODO Auto-generated method stub
		super.onTeamScoreChangeEvent(pair);
	}

}
