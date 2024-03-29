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
 * Supported games enum.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public enum Games {

    QUAKE2 ("Quake 2", 27910),
    QUAKE3ARENA ("Quake 3 Arena", 27960),
    QUAKELIVE ("QuakeLive", 27000);
    
    private int defaultPort;
    private String fullName;
    
    private Games(String fullName, int defaultPort) {
        this.fullName = fullName;
        this.defaultPort = defaultPort;
    }
    
    public String getFullName() {
        return fullName;
    }

    public int getDefaultPort() {
        return defaultPort;
    }
}
