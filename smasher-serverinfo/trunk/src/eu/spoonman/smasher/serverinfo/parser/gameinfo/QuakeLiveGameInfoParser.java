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
import eu.spoonman.smasher.serverinfo.GameTypes;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.parser.AttributeNotFoundException;
import eu.spoonman.smasher.serverinfo.parser.ParserException;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;

/**
 * GameInfo parser for Quake 3 Arena OSP and CPMA mods.
 * @author Tomasz Kalkosiński
 * 
 */
public class QuakeLiveGameInfoParser extends QuakeEngineGameInfoParser {

    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(QuakeLiveGameInfoParser.class);

    /**
     * This method should be called after playerInfos has been parsed.
     */
    @Override
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException {
        GameInfo gameInfo = parseGameInfo(serverInfo);

        String gametype = serverInfo.getNamedAttributes().get("g_gametype");

        log.debug(String.format(ServerInfoParser.fieldLogFormat, "g_gametype", gametype));

        if (gametype == null)
            throw new AttributeNotFoundException("g_gametype");

        gameInfo.setGameType(parseGametype(gametype));
        
        gameInfo.setPlayerCount(serverInfo.getPlayerInfos().size());

        serverInfo.setGameInfo(gameInfo);

    }

    /**
     * @param gametype
     * @return
     */
    private GameTypes parseGametype(String gametype) {
        int type = Integer.parseInt(gametype);

        switch (type) {
        
        case 0:
            return GameTypes.FREE_FOR_ALL;
            
        case 3:
            return GameTypes.TEAM_DEATHMATCH;

        case 5:
            return GameTypes.CAPTURE_THE_FLAG;

        default:
            return GameTypes.UNKNOWN;

        }
    }
}
