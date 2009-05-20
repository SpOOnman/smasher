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

package eu.spoonman.smasher.serverinfo.parser.teaminfo;

import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TeamInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;
import eu.spoonman.smasher.serverinfo.parser.ParserException;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;

public class QuakeLiveJSONTeamInfoParser implements ServerInfoParser {
    
    public final String JSON_RED_KEY = "g_redscore";
    public final String JSON_BLUE_KEY = "g_bluescore";

    @Override
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException {
        
        if (serverInfo.getJson() == null)
            throw new ParserException("Cannot parse time - no JSON object.");
        
        TeamInfo red = new TeamInfo(TeamKey.RED_TEAM);
        red.setScore((Integer)serverInfo.getJson().get(JSON_RED_KEY));
        serverInfo.getTeamInfos().put(TeamKey.RED_TEAM, red);
        
        TeamInfo blue = new TeamInfo(TeamKey.BLUE_TEAM);
        blue.setScore((Integer)serverInfo.getJson().get(JSON_BLUE_KEY));
        serverInfo.getTeamInfos().put(TeamKey.BLUE_TEAM, blue);
    }

}
