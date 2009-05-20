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
import java.util.List;

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
public class QuakeLiveJSONPlayerInfoParser implements ServerInfoParser {
    
    private final String JSONPlayersKey = "players";
    
    private final String JSONPlayerNameKey = "name";
    private final String JSONPlayerScoreKey = "score";
    private final String JSONPlayerClanKey = "clan";
    private final String JSONPlayerRankKey = "rank";
    private final String JSONPlayerTeamKey = "team";
    private final String JSONPlayerModelKey = "model";
    
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(QuakeLiveJSONStatsPlayerInfoParser.class);
    
    @Override
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException {
        
        if (serverInfo.getJson() == null)
            throw new ParserException("Cannot parse players - no JSON object.");
        
        Object players = serverInfo.getJson().get(JSONPlayersKey);
        
        if (log.isDebugEnabled())
            log.debug(String.format(ServerInfoParser.fieldLogFormat, "players", players));
        
        if (players == null || !(players instanceof JSONArray))
            throw new ParserException("Cannot parse players - players are either null or not array.");
        
        for (Iterator<JSONObject> iterator = ((JSONArray)players).iterator(); iterator.hasNext();) {
            parsePlayer(serverInfo, iterator.next());
        }
    }
    
    private void parsePlayer(ServerInfo serverInfo, JSONObject player) {
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setNamedAttributes(player);
        
        playerInfo.setName((String)player.get(JSONPlayerNameKey));
        playerInfo.setScore(((Long)player.get(JSONPlayerScoreKey)).intValue());
        
        int team = ((Long)player.get(JSONPlayerTeamKey)).intValue();
        
        switch(team) {
        case 0:
            playerInfo.setTeamKey(TeamKey.SPECTATORS_TEAM);
            break;
            
        case 1:
            playerInfo.setTeamKey(TeamKey.RED_TEAM);
            break;
            
        case 2:
            playerInfo.setTeamKey(TeamKey.BLUE_TEAM);
            break;
        }
        
        serverInfo.getPlayerInfos().add(playerInfo);
    }
}
