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

import eu.spoonman.smasher.scorebot.Scorebot;
import eu.spoonman.smasher.serverinfo.TeamInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;

/**
 * Persister that adds up all player scores to team score.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public class AddUpPlayerScoresPersister extends ScorebotPersister {

	public AddUpPlayerScoresPersister(Scorebot parent) {
		super(parent);
	}
	
	@Override
	public void persist(TeamInfo left, TeamInfo right) {
		super.persist(left, right);
		
		TeamKey key = right.getKey();
	}
	
	

}
