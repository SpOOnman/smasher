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


package eu.spoonman.smasher.scorebot.persister;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import eu.spoonman.smasher.scorebot.Scorebot;
import eu.spoonman.smasher.serverinfo.GameTypes;
import eu.spoonman.smasher.serverinfo.ProgressInfoFlags;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;

/**
 * @author Tomasz Kalkosiński
 */
public class QuakeLiveLimitsPersister extends ScorebotPersister {
	
	private static final Logger log = Logger.getLogger(QuakeLiveLimitsPersister.class);
	
	private final static String CAPTURELIMIT = "capturelimit";

	public QuakeLiveLimitsPersister(Scorebot parent) {
		super(parent);
	}
	
	@Override
	public void persist(ServerInfo left, ServerInfo right) {
		super.persist(left, right);
		
		// We're interested only in actual serverinfo.
		if (right == null || right.getJson() == null)
			return;
		
		// And only in capture the flag.
		if (right.getGameInfo().getGameType() != GameTypes.CAPTURE_THE_FLAG)
			return;
		
		try {
			JSONObject json = right.getJson();
			Long climit = (Long) json.get(CAPTURELIMIT);
			
			if (climit == null)
				return;
			
			if (right.getTeamInfos().get(TeamKey.RED_TEAM) != null && right.getTeamInfos().get(TeamKey.RED_TEAM).getScore() == climit.intValue() ||
				right.getTeamInfos().get(TeamKey.BLUE_TEAM) != null && right.getTeamInfos().get(TeamKey.BLUE_TEAM).getScore() == climit.intValue()) {
				
				log.debug("One of teams has reached capturelimit");
				
				right.getProgressInfo().getProgressInfoFlags().add(ProgressInfoFlags.CAPLIMIT);
				right.getProgressInfo().getProgressInfoFlags().remove(ProgressInfoFlags.IN_PLAY);
			}
			
			
		} catch (Exception e) {
			log.error(e);
		}
	}
}
