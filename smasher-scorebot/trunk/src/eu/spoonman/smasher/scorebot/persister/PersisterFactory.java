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

import java.util.ArrayList;
import java.util.List;

import eu.spoonman.smasher.scorebot.Scorebot;
import eu.spoonman.smasher.serverinfo.Games;
import eu.spoonman.smasher.serverinfo.Version;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class PersisterFactory {

	/**
	 * @param game
	 * @param modVersion
	 */
	public static List<ScorebotPersister> getPersisters(Scorebot scorebot, Games game, Version gameVersion, Version modVersion) {

		List<ScorebotPersister> persisters = new ArrayList<ScorebotPersister>();

		switch (game) {
		
		case QUAKE3ARENA:
			persisters.add(new Quake3TimeInfoPersister(scorebot));
			break;
		
		case QUAKELIVE:
			persisters.add(new QuakeLiveLimitsPersister(scorebot));
			//persisters.add(new AddMissingPropertiesPersister(scorebot));
			//persisters.add(new AddUpPlayerScoresPersister(scorebot));
			break;
		}

		return persisters;
	}
}
