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

import java.util.List;

import eu.spoonman.smasher.common.DiffData;
import eu.spoonman.smasher.scorebot.Scorebot;
import eu.spoonman.smasher.serverinfo.GameInfo;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ProgressInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TeamInfo;

/**
 * Persister is a special class that simulates stateful parser.
 * It usually runs after all parsers complete and uses information from previous iteration to improve serverinfo.
 *  
 * @author Tomasz Kalkosiński
 *
 */
public abstract class ScorebotPersister {
	
	protected Scorebot scorebot;
	
	public ScorebotPersister(Scorebot parent) {
		scorebot = parent;
	}
	
	public void prePersist() {}
	
	public void persist(List<DiffData<PlayerInfo>> playerPairs) {}
	
	public void persist(List<PlayerInfo> left, List<PlayerInfo> right) {}
	
	public void persist(PlayerInfo left, PlayerInfo right) {}
	
	public void persist(TeamInfo left, TeamInfo right) {}
	
	public void persist(GameInfo left, GameInfo right) {}
	
	public void persist(ServerInfo left, ServerInfo right) {}
	
	public void persist(ProgressInfo left, ProgressInfo right) {}
	
	public void postPersist() {}
		
}
