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

import eu.spoonman.smasher.common.Observer;
import eu.spoonman.smasher.common.Pair;
import eu.spoonman.smasher.scorebot.ServerInfoScorebot;
import eu.spoonman.smasher.serverinfo.GameInfo;
import eu.spoonman.smasher.serverinfo.PlayerInfo;

public class ConsoleClient implements Observer<Pair<GameInfo, GameInfo>> {

	public static void main(String[] args) throws InterruptedException {

		final ConsoleClient client = new ConsoleClient();

		ServerInfoScorebot scorebot = new ServerInfoScorebot();
		scorebot.getGameInfoChange().register(client);
		scorebot.getPlayerInfoChange().register(new Observer<Pair<PlayerInfo, PlayerInfo>>() {
			public void notify(Pair<PlayerInfo, PlayerInfo> pair) {
				client.onPlayerChange(pair);
			};
		});

		scorebot.start();

		synchronized (client) {
			client.wait(3000);
		}
		
		scorebot.stop();
	}

	/**
	 * @param pair
	 */
	protected void onPlayerChange(Pair<PlayerInfo, PlayerInfo> pair) {
		System.out.println("Zmiana " + pair);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.spoonman.smasher.common.Observer#notify(java.lang.Object)
	 */
	@Override
	public void notify(Pair<GameInfo, GameInfo> t) {
		System.out.println("Zmiana " + t);

	}

}
