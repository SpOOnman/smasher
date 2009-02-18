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


import java.util.List;

import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.Version;
import eu.spoonman.smasher.serverinfo.header.Header;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;
import eu.spoonman.smasher.serverinfo.persister.ServerInfoPersister;
import eu.spoonman.smasher.serverinfo.reader.Reader;

/**
 * @author Tomasz Kalkosiński
 *
 */
public interface Builder {
    
    public Header getHeader();
    
    public Reader getReader();
    
    public List<ServerInfoParser> getParserList(ServerInfo serverInfo);
    
    public List<ServerInfoPersister> getPersisterList(ServerInfo serverInfo);
    
    public Version getGameVersion(ServerInfo serverInfo);
    
    public Version getModVersion(ServerInfo serverInfo);

}
