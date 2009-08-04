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

package eu.spoonman.smasher.serverinfo;

/**
 * @author Tomasz Kalkosiński
 *
 */
public enum PlayerFlags {
    
    /**
     * Player is in play.
     */
    IN_PLAY,
    
    /**
     * Player is a first level administrator - he can change maps or force actions on other players.
     * This is considered often as 'admin' on 'referee'. 
     */
    FIRST_LEVEL_ADMIN,
    
    /**
     * Player is a top level administrator.
     * This is considered often as 'rcon'.
     */
    ADMIN_OR_RCON,
    
    /**
     * Player is in fact television player for streaming purposes - GTV/HLTV/UTV etc. 
     */
    TV
    

}
