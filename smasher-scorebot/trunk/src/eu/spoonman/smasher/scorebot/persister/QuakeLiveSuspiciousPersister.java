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

import java.util.List;

import eu.spoonman.smasher.scorebot.QuakeLiveScorebot;
import eu.spoonman.smasher.scorebot.Scorebot;
import eu.spoonman.smasher.serverinfo.PlayerInfo;

/**
 * Persister that checks if something suspicious is being cought to force scorebot to query stats with HTTP
 * next time.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public class QuakeLiveSuspiciousPersister extends ScorebotPersister {
	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger.getLogger(QuakeLiveSuspiciousPersister.class);

	public QuakeLiveSuspiciousPersister(Scorebot parent) {
		super(parent);
	}
	
	private void forceQLScorebot(String cause) {
		if (!(scorebot instanceof QuakeLiveScorebot)) {
			log.error("This persister works only with QuakeLiveScorebot.");
			return;
		}
		
		log.debug(String.format("Forcing to HTTP query next time, because of: %s", cause));
		
		((QuakeLiveScorebot)scorebot).forceToHTTPQuery();
	}
	
	@Override
	public void persist(List<PlayerInfo> left, List<PlayerInfo> right) {
		super.persist(left, right);
		
		if (left.size() != right.size())
			forceQLScorebot("Plyerlists count differ");
	}
	
	
	
	

}
