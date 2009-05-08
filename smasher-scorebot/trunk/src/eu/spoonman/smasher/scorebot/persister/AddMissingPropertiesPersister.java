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

import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.TeamInfo;

/**
 * Add missing player assignments and team score. Used only for QuakeLive.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public class AddMissingPropertiesPersister extends ScorebotPersister {

	@Override
	public void persist(PlayerInfo left, PlayerInfo right) {
		super.persist(left, right);

		if (left == null || right == null)
			return;

		if (right.getTeamKey() == null)
			right.setTeamKey(left.getTeamKey());

		if (right.getPlayerFlags() == null)
			right.setPlayerFlags(left.getPlayerFlags());
	}

	@Override
	public void persist(TeamInfo left, TeamInfo right) {
		super.persist(left, right);

		if (left == null || right == null)
			return;

		if (right.getScore() == null)
			right.setScore(left.getScore());
	}

}
