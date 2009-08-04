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

import org.apache.log4j.Logger;

import eu.spoonman.smasher.serverinfo.AbstractQuery;
import eu.spoonman.smasher.serverinfo.QuakeLiveHTTPQuery;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfoStatus;
import eu.spoonman.smasher.serverinfo.ServerQueryManager;

/**
 * Mixed type scorebot. It gathers as much information as it can from server, but also queries for statistics
 * thru HTTP if needed. It's very experimental mode.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public class QuakeLiveScorebot extends ServerInfoScorebot {
	
	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger.getLogger(QuakeLiveScorebot.class);
	
	private static final String MATCHID_KEY = "sv_gtid";
	private static final String GAMETYPE_KEY = "g_gametype";
	/**
	 * Query via HTTP each QUERY_PERIOD times.
	 */
	private static int QUERY_PERIOD = 10;
	private int serverQueryCounter = 0;
	
	private QuakeLiveHTTPQuery httpQuery = null;
	
	/**
	 * Flag that indicates if HTTP query is needed to obtain things.
	 */
	private Boolean httpQueryNeeded = false;
	

	public QuakeLiveScorebot(String id, AbstractQuery serverQuery) {
		super(id, serverQuery);
	}
	
	@Override
	protected void internalStart() {
		
		httpQueryNeeded = true;
		
		//super.internalStart();
	}
	
	@Override
	protected ServerInfo internalQuery() {
		
		if (currentServerInfo != null && currentServerInfo.getStatus() == ServerInfoStatus.OK &&
				(serverQueryCounter + 1 == QUERY_PERIOD || httpQueryNeeded == true)) {
			
			if (httpQuery == null)
				startHTTPQuery();
			
			serverQueryCounter = 0;
			httpQueryNeeded = false;
			
			return httpQuery.query();
		}
		
		serverQueryCounter++;
		return serverQuery.query();
	}
	
	private void startHTTPQuery() {
		log.debug(currentServerInfo.toString());
		Integer matchId = Integer.valueOf(currentServerInfo.getNamedAttributes().get(MATCHID_KEY));
		Integer gametype = Integer.valueOf(currentServerInfo.getNamedAttributes().get(GAMETYPE_KEY));
		
		//httpQuery = ServerQueryManager.createQuakeLiveHTTPQuery(matchId, gametype);
	}
	
	public void forceToHTTPQuery() {
		synchronized (httpQueryNeeded) {
			httpQueryNeeded = true;
		}
	}
	
	@Override
	protected void differenceThird() {
		super.differencePlayerInfos();
	}
	
	@Override
	protected void differenceFourth() {
		super.differenceTeamInfos();
	}

}
