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
import eu.spoonman.smasher.serverinfo.Games;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.parser.AttributeNotFoundException;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;
import eu.spoonman.smasher.serverinfo.parser.ParserException;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class Quake3OSPGameInfoParser implements ServerInfoParser {

    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(Quake3OSPGameInfoParser.class);

    @Override
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException {
        String gametype = serverInfo.getNamedAttributes().get("gametype");

        log.debug("Parsing gametype: " + gametype);

        if (gametype == null)
            throw new AttributeNotFoundException("gametype");

        GameInfo gameInfo = new GameInfo();

        gameInfo.setGame(Games.QUAKE3ARENA);
        gameInfo.setGameType(parseGametype(gametype));

        serverInfo.setGameInfo(gameInfo);
    }

    /**
     * @param gametype
     * @return
     */
    private GameTypes parseGametype(String gametype) {
        int type = Integer.parseInt(gametype);

        switch (type) {

        case 1:
            return GameTypes.DUEL;

        case 3:
            return GameTypes.TEAM_DEATHMATCH;

        case 4:
            return GameTypes.CAPTURE_THE_FLAG;

        default:
            return GameTypes.UNKOWN;

        }
    }
}
