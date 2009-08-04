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

package eu.spoonman.smasher.serverinfo.reader;

import eu.spoonman.smasher.serverinfo.ServerInfo;

/**
 * Reader to read from bytes array to serverinfo.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public interface Reader {

    /**
     * Parse server data into serverinfo field map and players map.
     * 
     * @param serverInfo
     * @param bytes
     */
    public void readData(ServerInfo serverInfo, byte[] bytes) throws ReaderException;

}
