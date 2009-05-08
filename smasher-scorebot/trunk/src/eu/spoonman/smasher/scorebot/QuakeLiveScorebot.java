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

import eu.spoonman.smasher.serverinfo.QuakeLiveHTTPQuery;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfoStatus;
import eu.spoonman.smasher.serverinfo.ServerQuery;
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
	

	public QuakeLiveScorebot(String id, ServerQuery serverQuery) {
		super(id, serverQuery);
	}
	
	@Override
	protected void internalStart() {

		while (isRunning()) {

			currentServerInfo = internalQuery();
			log.info(currentServerInfo);
						
			//Don't difference when response is not valid.
			if (currentServerInfo.getStatus() == ServerInfoStatus.OK) {
				difference();
	
				previousServerInfo = currentServerInfo;
			}

			count++;

			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				log.error("Scorebot thread unexpectedly interrupted.", e);
			}
		}
		
		log.info(String.format("Scorebot %s has stopped.", getId()));
	}
	
	private ServerInfo internalQuery() {
		
		//It it's first time - create HTTP query.
		if (currentServerInfo == null) {
			startHTTPQuery();
		}
		
		if (serverQueryCounter + 1 == QUERY_PERIOD || httpQueryNeeded == true || currentServerInfo == null) {
			
			serverQueryCounter = 0;
			httpQueryNeeded = false;
			
			return httpQuery.query();
		} else {
			
			serverQueryCounter++;
			return serverQuery.query();
		}
	}
	
	private void startHTTPQuery() {
		Integer matchId = Integer.valueOf(currentServerInfo.getNamedAttributes().get(MATCHID_KEY));
		Integer gametype = Integer.valueOf(currentServerInfo.getNamedAttributes().get(GAMETYPE_KEY));
		
		httpQuery = ServerQueryManager.createQuakeLiveHTTPQuery(matchId, gametype);
	}
	
	public void forceToHTTPQuery() {
		synchronized (httpQueryNeeded) {
			httpQueryNeeded = true;
		}
	}

}
