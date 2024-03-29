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

package eu.spoonman.smasher.serverinfo.parser.gameinfo;

import eu.spoonman.smasher.serverinfo.GameInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.parser.ParserException;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class QuakeEngineGameInfoParser implements ServerInfoParser {
    
    public static final String KeyMaxClients = "sv_maxclients";
    public static final String KeyHostname   = "sv_hostname";
    public static final String KeyMapname    = "mapname";
    public static final String KeyNeedPass   = "g_needpass";
    
    protected final String maxClients;
    protected final String hostname;
    protected final String mapname;
    protected final String needpass;
    

    @Override
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException {

        serverInfo.setGameInfo(parseGameInfo(serverInfo));
    }
    
    /**
     * 
     */
    public QuakeEngineGameInfoParser() {
        this(KeyMaxClients, KeyHostname, KeyMapname, KeyNeedPass);
    }
    
    /**
     * @param maxClients
     * @param hostname
     * @param mapname
     * @param needpass
     */
    public QuakeEngineGameInfoParser(String maxClients, String hostname, String mapname, String needpass) {
        super();
        this.maxClients = maxClients;
        this.hostname = hostname;
        this.mapname = mapname;
        this.needpass = needpass;
    }

    /**
     * Typical Quake engine GameInfo parsing. If you need to change something -
     * set returned result and alter it.
     * 
     * @param serverInfo
     * @return
     * @throws ParserException
     */
    protected GameInfo parseGameInfo(ServerInfo serverInfo) throws ParserException {
        GameInfo gameInfo = new GameInfo();

        gameInfo.setPlayerMaxCount(Integer.valueOf(serverInfo.getNamedAttributes().get(maxClients)));
        gameInfo.setHostName(serverInfo.getNamedAttributes().get(hostname));
        gameInfo.setMap(serverInfo.getNamedAttributes().get(mapname));
        
        gameInfo.setPassworded(serverInfo.getNamedAttributes().get(needpass) != null && serverInfo.getNamedAttributes().get(needpass).equals("1"));

        return gameInfo;

    }
}
