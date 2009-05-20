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

package eu.spoonman.smasher.serverinfo.parser.timeinfo;

import org.apache.log4j.Logger;

import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.parser.ParserException;

public class QuakeLiveJSONTimeInfoParser extends QuakeLiveTimeInfoParser {
    
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(QuakeLiveJSONTimeInfoParser.class);
    
    @Override
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException {
        
        if (serverInfo.getJson() == null)
            throw new ParserException("Cannot parse time - no JSON object.");
        

        String gameState = (String) serverInfo.getJson().get(G_GAME_STATE.toLowerCase());
        String levelStartTime = (String) serverInfo.getNamedAttributes().get(G_LEVEL_START_TIME.toLowerCase());
        
        parseTime(serverInfo, gameState, levelStartTime);
    }

}
