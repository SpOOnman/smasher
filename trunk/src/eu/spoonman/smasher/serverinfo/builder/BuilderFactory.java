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

package eu.spoonman.smasher.serverinfo.builder;

import eu.spoonman.smasher.serverinfo.Games;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class BuilderFactory {
    
    /**
     * Empty constructor to avoid creating children outside this package. 
     */
    BuilderFactory() {
    }

    public static Builder createBuilder(Games game) {

        switch (game) {
        case QUAKE3ARENA:
            return new Quake3ArenaBuilder();
        default:
            // it's our fault if we don't have builder for game we say is supported
            throw new EnumConstantNotPresentException(Games.class, game.toString());
        }

    }

}
