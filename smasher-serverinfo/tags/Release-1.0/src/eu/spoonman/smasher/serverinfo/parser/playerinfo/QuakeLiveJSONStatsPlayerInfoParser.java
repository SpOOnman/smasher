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

package eu.spoonman.smasher.serverinfo.parser.playerinfo;

import java.util.EnumSet;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import eu.spoonman.smasher.serverinfo.PlayerFlags;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;
import eu.spoonman.smasher.serverinfo.parser.ParserException;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class QuakeLiveJSONStatsPlayerInfoParser implements ServerInfoParser {
    
    private final String JSONDuelKey = "SCOREBOARD";
    private final String JSONRedKey = "RED_SCOREBOARD";
    private final String JSONBlueKey = "BLUE_SCOREBOARD";
    
    private final String JSONPlayerName = "PLAYER_NICK";
    private final String JSONPlayerScore = "SCORE";
    
    private final String JSONQuittersValue = "QUITTERS";
    
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(QuakeLiveJSONStatsPlayerInfoParser.class);
    
    @Override
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException {
        
        if (serverInfo.getJson() == null)
            throw new ParserException("Cannot parse players - no JSON object.");
        
        parseTeam(serverInfo, JSONDuelKey, null, EnumSet.of(PlayerFlags.IN_PLAY));
        parseTeam(serverInfo, JSONRedKey, TeamKey.RED_TEAM, EnumSet.of(PlayerFlags.IN_PLAY));
        parseTeam(serverInfo, JSONBlueKey, TeamKey.BLUE_TEAM, EnumSet.of(PlayerFlags.IN_PLAY));
    }
    
    private void parseTeam(ServerInfo serverInfo, String jsonKey, TeamKey teamKey, EnumSet<PlayerFlags> playerFlags) {
        
        log.debug(String.format("Looking for json key %s to put them to %s with %s", jsonKey, teamKey, playerFlags));
        
        JSONArray array = (JSONArray) serverInfo.getJson().get(jsonKey);
        
        if (array == null) {
            log.debug("Not found");
            return;
        }
        
        for (Iterator<JSONObject> iterator = array.iterator(); iterator.hasNext();) {
            PlayerInfo playerInfo = new PlayerInfo();
            playerInfo.setNamedAttributes(iterator.next());
            
            playerInfo.setName((String) playerInfo.getNamedAttributes().get(JSONPlayerName));
            
            //Don't add QUITTERS
            if (playerInfo.getName().equals(JSONQuittersValue))
                continue;
            
            playerInfo.setScore(((Long) playerInfo.getNamedAttributes().get(JSONPlayerScore)).intValue());
            playerInfo.setPlayerFlags(playerFlags);
            playerInfo.setTeamKey(teamKey);
            
            serverInfo.getPlayerInfos().add(playerInfo);
        }
    }
}
