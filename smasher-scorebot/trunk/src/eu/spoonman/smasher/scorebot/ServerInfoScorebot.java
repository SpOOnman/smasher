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

import eu.spoonman.smasher.common.Pair;
import eu.spoonman.smasher.serverinfo.GameInfo;
import eu.spoonman.smasher.serverinfo.Games;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.ServerQuery;
import eu.spoonman.smasher.serverinfo.ServerQueryManager;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class ServerInfoScorebot extends Scorebot {
	
	private final int interval = 800; //ms
	
	private ServerQuery serverQuery;
	private ServerInfo previousServerInfo;
	private ServerInfo currentServerInfo;
	
	@Override
	public void start() {
		try {
			serverQuery = ServerQueryManager.createServerQuery(Games.QUAKE3ARENA, Inet4Address.getByName("194.187.43.245"), 27971);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		new Thread(new Runnable(){
		
			@Override
			public void run() {
				internalStart();
			}
			
		});
	}
	
	protected void internalStart() {
		
		currentServerInfo = serverQuery.query();
		
		difference();
		
		previousServerInfo = currentServerInfo;
		
	}
	
	private void difference() {
		if (previousServerInfo == null && currentServerInfo != null && currentServerInfo.getGameInfo() != null)
			gameInfoChange.notifyAll(new Pair<GameInfo, GameInfo>(null, currentServerInfo.getGameInfo()));
	}

}
