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

import org.apache.log4j.Logger;

import eu.spoonman.smasher.serverinfo.GameInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.parser.AttributeNotFoundException;
import eu.spoonman.smasher.serverinfo.parser.ParserException;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;

public class QuakeLiveJSONGameInfoParser extends QuakeLiveGameInfoParser {
    
    public final String JSON_HOSTNAME_KEY = "host_name";
    public final String JSON_MAX_PLAYERS_KEY = "max_clients";
    public final String JSON_MAP_KEY = "map"; 
    public final String JSON_GAMETYPE_KEY = "game_type"; 
    public final String JSON_PLAYERCOUNT_KEY = "num_clients";
    
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(QuakeLiveJSONGameInfoParser.class);
    
    @Override
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException {
        
        if (serverInfo.getJson() == null)
            throw new ParserException("Cannot parse game - no JSON object.");
        
        GameInfo gameInfo = new GameInfo();
        
        gameInfo.setHostName((String)serverInfo.getJson().get(JSON_HOSTNAME_KEY));
        gameInfo.setPlayerMaxCount(((Long)serverInfo.getJson().get(JSON_MAX_PLAYERS_KEY)).intValue());
        gameInfo.setMap((String)serverInfo.getJson().get(JSON_MAP_KEY));
        gameInfo.setPassworded(false);

        Long gametype = (Long)serverInfo.getJson().get(JSON_GAMETYPE_KEY);

        log.debug(String.format(ServerInfoParser.fieldLogFormat, "g_gametype", gametype.toString()));

        if (gametype == null)
            throw new AttributeNotFoundException("g_gametype");

        gameInfo.setGameType(parseGametype(gametype.toString()));
        
        gameInfo.setPlayerCount((Integer)serverInfo.getJson().get(JSON_PLAYERCOUNT_KEY));

        serverInfo.setGameInfo(gameInfo);
    }

}
