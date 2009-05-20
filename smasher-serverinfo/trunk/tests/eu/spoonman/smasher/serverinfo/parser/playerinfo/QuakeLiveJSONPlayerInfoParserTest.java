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

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Test;

import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;
import eu.spoonman.smasher.serverinfo.parser.ParserException;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class QuakeLiveJSONPlayerInfoParserTest {
    
    private final String players = "{\"public_id\":411661,\"ECODE\":0,\"g_levelstarttime\":1242808738,\"timelimit\":0,\"max_clients\":10,\"roundtimelimit\":180,\"map_title\":\"Asylum\",\"skillDelta\":0,\"game_type_title\":\"Clan Arena\",\"map\":\"qzca1\",\"ranked\":1,\"g_bluescore\":5,\"g_gamestate\":\"IN_PROGRESS\",\"host_address\":\"79.141.160.101:27008\",\"fraglimit\":20,\"num_clients\":7,\"capturelimit\":8,\"game_type\":4,\"players\":[{\"clan\":null,\"name\":\"^7laun^41^7zed\",\"bot\":\"0\",\"rank\":2,\"score\":\"8\",\"team\":2,\"model\":\"doom/red\"},{\"clan\":\"^6fun.^7\",\"name\":\"Ma^6Q^7u\",\"bot\":\"0\",\"rank\":2,\"score\":\"47\",\"team\":2,\"model\":\"anarki/default\"},{\"clan\":\"^7rape.\",\"name\":\"^3k_0\",\"bot\":\"0\",\"rank\":2,\"score\":\"0\",\"team\":1,\"model\":\"anarki\"},{\"clan\":null,\"name\":\"Wanglyih\",\"bot\":\"0\",\"rank\":2,\"score\":\"0\",\"team\":0,\"model\":\"anarki\"},{\"clan\":null,\"name\":\"Rogon\",\"bot\":\"0\",\"rank\":2,\"score\":\"17\",\"team\":1,\"model\":\"anarki\"},{\"clan\":null,\"name\":\"AnDrIxx\",\"bot\":\"0\",\"rank\":2,\"score\":\"5\",\"team\":1,\"model\":\"doom/default\"},{\"clan\":\"BoH\",\"name\":\"^155\",\"bot\":\"0\",\"rank\":2,\"score\":\"13\",\"team\":2,\"model\":\"uriel\"}],\"roundlimit\":10,\"host_name\":\"Clan Arena PL #1\",\"g_redscore\":5}";
    
    public ServerInfo testParse(String jsonString) throws ParserException {
        QuakeLiveJSONPlayerInfoParser parser = new QuakeLiveJSONPlayerInfoParser();
        
        JSONObject json = (JSONObject) JSONValue.parse(jsonString);
        
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setJson(json);
        
        parser.parseIntoServerInfo(serverInfo);
        
        return serverInfo;
    }
    
    @Test
    public void parseOne() throws ParserException {
        ServerInfo serverInfo = testParse(players);
        assertEquals(7, serverInfo.getPlayerInfos().size());
        
        assertPlayer(serverInfo.getPlayerInfos().get(0), "^7laun^41^7zed", 8, TeamKey.BLUE_TEAM);
        assertPlayer(serverInfo.getPlayerInfos().get(3), "Wanglyih", 0, TeamKey.SPECTATORS_TEAM);
        assertPlayer(serverInfo.getPlayerInfos().get(4), "Rogon", 17, TeamKey.RED_TEAM);
        assertPlayer(serverInfo.getPlayerInfos().get(6), "^155", 13, TeamKey.BLUE_TEAM);
    }
    
    private static void assertPlayer(PlayerInfo playerInfo, String name, int score, TeamKey teamKey) {
        assertEquals(name, playerInfo.getName());
        assertEquals(score, playerInfo.getScore());
        assertEquals(teamKey, playerInfo.getTeamKey());
    }
    

}
