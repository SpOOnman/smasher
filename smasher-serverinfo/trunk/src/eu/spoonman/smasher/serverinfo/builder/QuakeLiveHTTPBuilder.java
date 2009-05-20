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

import java.util.ArrayList;
import java.util.List;

import eu.spoonman.smasher.serverinfo.Games;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.Version;
import eu.spoonman.smasher.serverinfo.header.Header;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;
import eu.spoonman.smasher.serverinfo.parser.gameinfo.QuakeLiveGameInfoParser;
import eu.spoonman.smasher.serverinfo.parser.gameinfo.QuakeLiveJSONGameInfoParser;
import eu.spoonman.smasher.serverinfo.parser.playerinfo.QuakeLiveJSONPlayerInfoParser;
import eu.spoonman.smasher.serverinfo.parser.teaminfo.QuakeLiveJSONTeamInfoParser;
import eu.spoonman.smasher.serverinfo.parser.timeinfo.QuakeLiveJSONTimeInfoParser;
import eu.spoonman.smasher.serverinfo.reader.Reader;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class QuakeLiveHTTPBuilder extends BuilderFactory implements Builder {

    @Override
    public Games getGame() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Version getGameVersion(ServerInfo serverInfo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Header getHeader() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Version getModVersion(ServerInfo serverInfo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ServerInfoParser> getParserList(ServerInfo serverInfo) {
        List<ServerInfoParser> parsers = new ArrayList<ServerInfoParser>();
        
        parsers.add(new QuakeLiveJSONGameInfoParser());
        parsers.add(new QuakeLiveJSONTimeInfoParser());
        parsers.add(new QuakeLiveJSONTeamInfoParser());
        parsers.add(new QuakeLiveJSONPlayerInfoParser());
        
        return parsers;
    }

    @Override
    public Reader getReader() {
        // TODO Auto-generated method stub
        return null;
    }
    
    

}
