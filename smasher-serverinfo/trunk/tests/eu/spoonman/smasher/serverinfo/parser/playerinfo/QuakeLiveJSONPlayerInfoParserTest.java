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

import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;
import eu.spoonman.smasher.serverinfo.parser.ParserException;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class QuakeLiveJSONPlayerInfoParserTest {
    
    private final String players = "{\"BLUE_SCOREBOARD\":[{\"MG\":0,\"BFG\":0,\"NG_A\":0,\"DEATHS\":6,\"PM\":0,\"GT_A\":0,\"GT\":0,\"PLAYER_NICK\":\"LS1942\",\"RG\":0,\"PG\":0,\"MIN\":482,\"CG_A\":0,\"GL\":0,\"DEFENDS\":6,\"PLAYER_COUNTRY\":\"PL\",\"ACCURACY\":21,\"SG_A\":0,\"RL_A\":27,\"TEAM\":\"Blue\",\"TEAM_RANK\":1,\"PLAYER_ID\":1840680,\"LG_A\":0,\"KILLS\":11,\"ASSISTS\":1,\"CG\":0,\"NG\":0,\"RANK\":1,\"PM_A\":0,\"GL_A\":0,\"BFG_A\":0,\"PLAYER_MODEL\":\"klesk_flisk\",\"PG_A\":0,\"CAPTURES\":5,\"RL\":0,\"MG_A\":13,\"SCORE\":804,\"SG\":0,\"RG_A\":14,\"LG\":0},{\"MG\":0,\"NG_A\":0,\"BFG\":0,\"DEATHS\":14,\"PM\":0,\"GT_A\":0,\"GT\":0,\"MIN\":482,\"PG\":0,\"RG\":0,\"PLAYER_NICK\":\"Napastnik\",\"CG_A\":0,\"GL\":0,\"DEFENDS\":11,\"PLAYER_COUNTRY\":\"PL\",\"ACCURACY\":19,\"SG_A\":66,\"RL_A\":64,\"TEAM\":\"Blue\",\"TEAM_RANK\":2,\"PLAYER_ID\":2187513,\"LG_A\":0,\"KILLS\":21,\"ASSISTS\":2,\"CG\":0,\"NG\":0,\"RANK\":2,\"BFG_A\":0,\"GL_A\":0,\"PM_A\":0,\"PLAYER_MODEL\":\"bones_bones\",\"RL\":0,\"CAPTURES\":1,\"PG_A\":10,\"SCORE\":639,\"MG_A\":17,\"RG_A\":52,\"SG\":0,\"LG\":0},{\"MG\":0,\"BFG\":0,\"NG_A\":0,\"DEATHS\":11,\"PM\":0,\"GT_A\":0,\"GT\":0,\"PLAYER_NICK\":\"Legend4ry\",\"RG\":0,\"PG\":0,\"MIN\":482,\"CG_A\":0,\"GL\":0,\"DEFENDS\":12,\"PLAYER_COUNTRY\":\"GB\",\"ACCURACY\":31,\"SG_A\":71,\"RL_A\":43,\"TEAM\":\"Blue\",\"TEAM_RANK\":3,\"PLAYER_ID\":1836336,\"LG_A\":0,\"KILLS\":16,\"ASSISTS\":3,\"CG\":0,\"NG\":0,\"RANK\":3,\"PM_A\":0,\"GL_A\":0,\"BFG_A\":0,\"PLAYER_MODEL\":\"keel_sport\",\"PG_A\":7,\"CAPTURES\":1,\"RL\":0,\"MG_A\":28,\"SCORE\":571,\"SG\":0,\"RG_A\":42,\"LG\":0},{\"MG\":0,\"NG_A\":0,\"BFG\":0,\"DEATHS\":12,\"PM\":0,\"GT_A\":0,\"GT\":0,\"MIN\":347,\"PG\":0,\"RG\":0,\"PLAYER_NICK\":\"Superi1337\",\"CG_A\":0,\"GL\":0,\"DEFENDS\":6,\"PLAYER_COUNTRY\":\"FI\",\"ACCURACY\":20,\"SG_A\":100,\"RL_A\":42,\"TEAM\":\"Blue\",\"TEAM_RANK\":4,\"PLAYER_ID\":1470739,\"LG_A\":0,\"KILLS\":10,\"ASSISTS\":1,\"CG\":0,\"NG\":0,\"RANK\":7,\"BFG_A\":0,\"GL_A\":0,\"PM_A\":0,\"PLAYER_MODEL\":\"klesk_flisk\",\"RL\":0,\"CAPTURES\":1,\"PG_A\":18,\"SCORE\":400,\"MG_A\":18,\"RG_A\":37,\"SG\":0,\"LG\":0},{\"MG\":0,\"BFG\":0,\"NG_A\":0,\"DEATHS\":4,\"PM\":0,\"GT_A\":0,\"GT\":0,\"PLAYER_NICK\":\"Cychu666\",\"RG\":0,\"PG\":0,\"MIN\":261,\"CG_A\":0,\"GL\":0,\"DEFENDS\":6,\"PLAYER_COUNTRY\":\"PL\",\"ACCURACY\":24,\"SG_A\":37,\"RL_A\":43,\"TEAM\":\"Blue\",\"TEAM_RANK\":5,\"PLAYER_ID\":2050125,\"LG_A\":0,\"KILLS\":9,\"ASSISTS\":1,\"CG\":0,\"NG\":0,\"RANK\":8,\"PM_A\":0,\"GL_A\":0,\"BFG_A\":0,\"PLAYER_MODEL\":\"anarki_default\",\"PG_A\":15,\"CAPTURES\":0,\"RL\":0,\"MG_A\":21,\"SCORE\":183,\"SG\":0,\"RG_A\":38,\"LG\":0},{\"MG\":0,\"NG_A\":0,\"BFG\":0,\"DEATHS\":1,\"PM\":0,\"GT_A\":0,\"GT\":0,\"MIN\":63,\"PG\":0,\"RG\":0,\"PLAYER_NICK\":\"acuratebob\",\"CG_A\":0,\"GL\":0,\"DEFENDS\":0,\"PLAYER_COUNTRY\":\"GB\",\"ACCURACY\":16,\"SG_A\":0,\"RL_A\":0,\"TEAM\":\"Blue\",\"TEAM_RANK\":6,\"PLAYER_ID\":2378331,\"LG_A\":0,\"KILLS\":1,\"ASSISTS\":0,\"CG\":0,\"NG\":0,\"RANK\":12,\"BFG_A\":0,\"GL_A\":0,\"PM_A\":0,\"PLAYER_MODEL\":\"razor_id\",\"RL\":0,\"CAPTURES\":0,\"PG_A\":0,\"SCORE\":46,\"MG_A\":14,\"RG_A\":33,\"SG\":0,\"LG\":0},{\"MG\":0,\"BFG\":0,\"NG_A\":0,\"DEATHS\":0,\"PM\":0,\"GT_A\":0,\"GT\":0,\"PG\":0,\"MIN\":0,\"RG\":0,\"PLAYER_NICK\":\"QUITTERS\",\"CG_A\":0,\"GL\":0,\"DEFENDS\":0,\"ACCURACY\":\"N/A\",\"SG_A\":0,\"RL_A\":0,\"TEAM_RANK\":\"Q\",\"PLAYER_ID\":0,\"LG_A\":0,\"KILLS\":0,\"ASSISTS\":0,\"CG\":0,\"NG\":0,\"RANK\":\"Q\",\"PM_A\":0,\"BFG_A\":0,\"GL_A\":0,\"PG_A\":0,\"CAPTURES\":0,\"RL\":0,\"MG_A\":0,\"SCORE\":0,\"SG\":0,\"RG_A\":0,\"LG\":0}]}";
    
    private final String someTDM = "{\"RED_SCOREBOARD_QUITTERS\":[{\"MG\":0,\"NG_A\":0,\"BFG\":0,\"DEATHS\":8,\"HUMILIATION\":0,\"PM\":0,\"GT_A\":0,\"GT\":0,\"MIN\":364,\"PG\":0,\"RG\":0,\"PLAYER_NICK\":\"DakSmyth\",\"CG_A\":0,\"GL\":0,\"PLAYER_COUNTRY\":\"US\",\"ACCURACY\":22,\"EXCELLENT\":0,\"SG_A\":33,\"RL_A\":0,\"TEAM\":\"Red\",\"TEAM_RANK\":\"Q\",\"PLAYER_ID\":3472616,\"LG_A\":16,\"KILLS\":4,\"CG\":0,\"NG\":0,\"RANK\":\"Q\",\"BFG_A\":20,\"GL_A\":0,\"PM_A\":0,\"PLAYER_MODEL\":\"sarge_default\",\"RL\":0,\"PG_A\":42,\"SCORE\":0,\"MG_A\":0,\"IMPRESSIVE\":0,\"RG_A\":0,\"SG\":0,\"LG\":0},{\"MG\":0,\"BFG\":0,\"NG_A\":0,\"DEATHS\":5,\"PM\":0,\"HUMILIATION\":0,\"GT_A\":0,\"GT\":0,\"PLAYER_NICK\":\"Xcbone\",\"RG\":0,\"PG\":0,\"MIN\":516,\"CG_A\":0,\"GL\":0,\"PLAYER_COUNTRY\":\"US\",\"ACCURACY\":12,\"EXCELLENT\":0,\"SG_A\":100,\"RL_A\":25,\"TEAM\":\"Red\",\"TEAM_RANK\":\"Q\",\"PLAYER_ID\":3484727,\"LG_A\":13,\"KILLS\":4,\"CG\":0,\"NG\":0,\"RANK\":\"Q\",\"PM_A\":0,\"GL_A\":0,\"BFG_A\":0,\"PLAYER_MODEL\":\"sarge_default\",\"PG_A\":12,\"RL\":0,\"MG_A\":14,\"SCORE\":0,\"IMPRESSIVE\":0,\"SG\":0,\"RG_A\":0,\"LG\":0},{\"MG\":0,\"NG_A\":0,\"BFG\":0,\"DEATHS\":2,\"HUMILIATION\":0,\"PM\":0,\"GT_A\":0,\"GT\":0,\"MIN\":55,\"PG\":0,\"RG\":0,\"PLAYER_NICK\":\"sueme2\",\"CG_A\":0,\"GL\":0,\"PLAYER_COUNTRY\":\"US\",\"ACCURACY\":35,\"EXCELLENT\":0,\"SG_A\":100,\"RL_A\":0,\"TEAM\":\"Red\",\"TEAM_RANK\":\"Q\",\"PLAYER_ID\":3486905,\"LG_A\":0,\"KILLS\":0,\"CG\":0,\"NG\":0,\"RANK\":\"Q\",\"BFG_A\":0,\"GL_A\":0,\"PM_A\":0,\"PLAYER_MODEL\":\"sarge_default\",\"RL\":0,\"PG_A\":0,\"SCORE\":0,\"MG_A\":31,\"IMPRESSIVE\":0,\"RG_A\":0,\"SG\":0,\"LG\":0}],\"NUM_PLAYERS\":11,\"MOST_FRAGS\":{\"PLAYER_NICK\":\"xRequiem\",\"PLAYER_TEAM\":\"Blue\",\"PLAYER_ID\":3409944,\"PLAYER_MODEL\":\"anarki_blue\",\"PLAYER_COUNTRY\":\"US\",\"NUM\":24},\"MOST_ACCURATE\":{\"PLAYER_TEAM\":\"Blue\",\"PLAYER_NICK\":\"xRequiem\",\"PLAYER_ID\":3409944,\"PLAYER_MODEL\":\"anarki_blue\",\"PLAYER_COUNTRY\":\"US\",\"NUM\":38},\"DMG_DELIVERED\":{\"PLAYER_NICK\":\"xRequiem\",\"PLAYER_TEAM\":\"Blue\",\"PLAYER_ID\":3409944,\"PLAYER_MODEL\":\"anarki_blue\",\"PLAYER_COUNTRY\":\"US\",\"NUM\":4849},\"GAME_LENGTH\":900,\"BLUE_SCOREBOARD\":[{\"MG\":0,\"BFG\":0,\"NG_A\":0,\"DEATHS\":19,\"PM\":0,\"HUMILIATION\":0,\"GT_A\":0,\"GT\":0,\"PLAYER_NICK\":\"xRequiem\",\"RG\":0,\"PG\":0,\"MIN\":899,\"CG_A\":0,\"GL\":0,\"PLAYER_COUNTRY\":\"US\",\"ACCURACY\":38,\"EXCELLENT\":0,\"SG_A\":59,\"RL_A\":30,\"TEAM\":\"Blue\",\"TEAM_RANK\":1,\"PLAYER_ID\":3409944,\"LG_A\":0,\"KILLS\":24,\"CG\":0,\"NG\":0,\"RANK\":1,\"PM_A\":0,\"GL_A\":20,\"BFG_A\":46,\"PLAYER_MODEL\":\"anarki_blue\",\"PG_A\":28,\"RL\":0,\"MG_A\":33,\"SCORE\":21,\"IMPRESSIVE\":5,\"SG\":0,\"RG_A\":48,\"LG\":0},{\"MG\":0,\"NG_A\":0,\"BFG\":0,\"DEATHS\":7,\"HUMILIATION\":0,\"PM\":0,\"GT_A\":0,\"GT\":0,\"MIN\":899,\"PG\":0,\"RG\":0,\"PLAYER_NICK\":\"Smirlap\",\"CG_A\":0,\"GL\":0,\"PLAYER_COUNTRY\":\"DE\",\"ACCURACY\":23,\"EXCELLENT\":0,\"SG_A\":67,\"RL_A\":26,\"TEAM\":\"Blue\",\"TEAM_RANK\":2,\"PLAYER_ID\":2762083,\"LG_A\":4,\"KILLS\":14,\"CG\":0,\"NG\":0,\"RANK\":2,\"BFG_A\":24,\"GL_A\":0,\"PM_A\":0,\"PLAYER_MODEL\":\"bones_bones\",\"RL\":0,\"PG_A\":10,\"SCORE\":12,\"MG_A\":29,\"IMPRESSIVE\":5,\"RG_A\":44,\"SG\":0,\"LG\":0},{\"MG\":0,\"BFG\":0,\"NG_A\":0,\"DEATHS\":6,\"PM\":0,\"HUMILIATION\":0,\"GT_A\":0,\"GT\":0,\"PLAYER_NICK\":\"FearFrost\",\"RG\":0,\"PG\":0,\"MIN\":604,\"CG_A\":0,\"GL\":0,\"PLAYER_COUNTRY\":\"CA\",\"ACCURACY\":17,\"EXCELLENT\":1,\"SG_A\":78,\"RL_A\":25,\"TEAM\":\"Blue\",\"TEAM_RANK\":3,\"PLAYER_ID\":3476204,\"LG_A\":0,\"KILLS\":10,\"CG\":0,\"NG\":0,\"RANK\":5,\"PM_A\":0,\"GL_A\":21,\"BFG_A\":36,\"PLAYER_MODEL\":\"doom_blue\",\"PG_A\":15,\"RL\":0,\"MG_A\":14,\"SCORE\":9,\"IMPRESSIVE\":0,\"SG\":0,\"RG_A\":33,\"LG\":0},{\"MG\":0,\"NG_A\":0,\"BFG\":0,\"DEATHS\":12,\"HUMILIATION\":0,\"PM\":0,\"GT_A\":0,\"GT\":0,\"MIN\":417,\"PG\":0,\"RG\":0,\"PLAYER_NICK\":\"Zenverak\",\"CG_A\":0,\"GL\":0,\"PLAYER_COUNTRY\":\"US\",\"ACCURACY\":15,\"EXCELLENT\":0,\"SG_A\":33,\"RL_A\":43,\"TEAM\":\"Blue\",\"TEAM_RANK\":4,\"PLAYER_ID\":3137445,\"LG_A\":0,\"KILLS\":2,\"CG\":0,\"NG\":0,\"RANK\":7,\"BFG_A\":0,\"GL_A\":0,\"PM_A\":0,\"PLAYER_MODEL\":\"anarki_default\",\"RL\":0,\"PG_A\":0,\"SCORE\":2,\"MG_A\":15,\"IMPRESSIVE\":0,\"RG_A\":0,\"SG\":0,\"LG\":0},{\"MG\":0,\"BFG\":0,\"NG_A\":0,\"DEATHS\":2,\"PM\":0,\"HUMILIATION\":0,\"GT_A\":0,\"GT\":0,\"PLAYER_NICK\":\"namcap\",\"RG\":0,\"PG\":0,\"MIN\":64,\"CG_A\":0,\"GL\":0,\"PLAYER_COUNTRY\":\"US\",\"ACCURACY\":3,\"EXCELLENT\":0,\"SG_A\":25,\"RL_A\":0,\"TEAM\":\"Blue\",\"TEAM_RANK\":5,\"PLAYER_ID\":3472430,\"LG_A\":0,\"KILLS\":0,\"CG\":0,\"NG\":0,\"RANK\":9,\"PM_A\":0,\"GL_A\":0,\"BFG_A\":0,\"PLAYER_MODEL\":\"sarge_default\",\"PG_A\":0,\"RL\":0,\"MG_A\":0,\"SCORE\":-1,\"IMPRESSIVE\":0,\"SG\":0,\"RG_A\":0,\"LG\":0}],\"TOTAL_KILLS\":89,\"GAME_TYPE_FULL\":\"Team Death Match\",\"RED_SCOREBOARD\":[{\"MG\":0,\"BFG\":0,\"NG_A\":0,\"DEATHS\":11,\"PM\":0,\"HUMILIATION\":0,\"GT_A\":0,\"GT\":0,\"PLAYER_NICK\":\"MidniteToker09\",\"RG\":0,\"PG\":0,\"MIN\":826,\"CG_A\":0,\"GL\":0,\"PLAYER_COUNTRY\":\"US\",\"ACCURACY\":20,\"EXCELLENT\":2,\"SG_A\":59,\"RL_A\":20,\"TEAM\":\"Red\",\"TEAM_RANK\":1,\"PLAYER_ID\":1810456,\"LG_A\":0,\"KILLS\":12,\"CG\":0,\"NG\":0,\"RANK\":3,\"PM_A\":0,\"GL_A\":0,\"BFG_A\":28,\"PLAYER_MODEL\":\"janet_default\",\"PG_A\":12,\"RL\":0,\"MG_A\":21,\"SCORE\":11,\"IMPRESSIVE\":0,\"SG\":0,\"RG_A\":0,\"LG\":0},{\"MG\":0,\"NG_A\":0,\"BFG\":0,\"DEATHS\":14,\"HUMILIATION\":0,\"PM\":0,\"GT_A\":0,\"GT\":0,\"MIN\":555,\"PG\":0,\"RG\":0,\"PLAYER_NICK\":\"zTGzIceman\",\"CG_A\":0,\"GL\":0,\"PLAYER_COUNTRY\":\"US\",\"ACCURACY\":18,\"EXCELLENT\":0,\"SG_A\":50,\"RL_A\":50,\"TEAM\":\"Red\",\"TEAM_RANK\":2,\"PLAYER_ID\":3484291,\"LG_A\":16,\"KILLS\":10,\"CG\":0,\"NG\":0,\"RANK\":4,\"BFG_A\":71,\"GL_A\":0,\"PM_A\":0,\"PLAYER_MODEL\":\"sarge_default\",\"RL\":0,\"PG_A\":0,\"SCORE\":10,\"MG_A\":16,\"IMPRESSIVE\":1,\"RG_A\":40,\"SG\":0,\"LG\":0},{\"MG\":0,\"BFG\":0,\"NG_A\":0,\"DEATHS\":6,\"PM\":0,\"HUMILIATION\":0,\"GT_A\":0,\"GT\":0,\"PLAYER_NICK\":\"DakSmyth\",\"RG\":0,\"PG\":0,\"MIN\":493,\"CG_A\":0,\"GL\":0,\"PLAYER_COUNTRY\":\"US\",\"ACCURACY\":13,\"EXCELLENT\":0,\"SG_A\":0,\"RL_A\":0,\"TEAM\":\"Red\",\"TEAM_RANK\":3,\"PLAYER_ID\":3472616,\"LG_A\":8,\"KILLS\":6,\"CG\":0,\"NG\":0,\"RANK\":6,\"PM_A\":0,\"GL_A\":0,\"BFG_A\":19,\"PLAYER_MODEL\":\"sarge_default\",\"PG_A\":15,\"RL\":0,\"MG_A\":21,\"SCORE\":3,\"IMPRESSIVE\":0,\"SG\":0,\"RG_A\":0,\"LG\":0},{\"MG\":0,\"NG_A\":0,\"BFG\":0,\"DEATHS\":4,\"HUMILIATION\":0,\"PM\":0,\"GT_A\":0,\"GT\":0,\"MIN\":203,\"PG\":0,\"RG\":0,\"PLAYER_NICK\":\"sueme2\",\"CG_A\":0,\"GL\":0,\"PLAYER_COUNTRY\":\"US\",\"ACCURACY\":36,\"EXCELLENT\":0,\"SG_A\":0,\"RL_A\":50,\"TEAM\":\"Red\",\"TEAM_RANK\":4,\"PLAYER_ID\":3486905,\"LG_A\":0,\"KILLS\":2,\"CG\":0,\"NG\":0,\"RANK\":7,\"BFG_A\":0,\"GL_A\":0,\"PM_A\":0,\"PLAYER_MODEL\":\"sarge_default\",\"RL\":0,\"PG_A\":24,\"SCORE\":2,\"MG_A\":54,\"IMPRESSIVE\":0,\"RG_A\":0,\"SG\":0,\"LG\":0},{\"MG\":0,\"BFG\":0,\"NG_A\":0,\"DEATHS\":6,\"PM\":0,\"HUMILIATION\":0,\"GT_A\":0,\"GT\":0,\"PLAYER_NICK\":\"Whitee\",\"RG\":0,\"PG\":0,\"MIN\":185,\"CG_A\":0,\"GL\":0,\"PLAYER_COUNTRY\":\"DE\",\"ACCURACY\":17,\"EXCELLENT\":0,\"SG_A\":0,\"RL_A\":75,\"TEAM\":\"Red\",\"TEAM_RANK\":5,\"PLAYER_ID\":2121239,\"LG_A\":0,\"KILLS\":1,\"CG\":0,\"NG\":0,\"RANK\":8,\"PM_A\":0,\"GL_A\":0,\"BFG_A\":0,\"PLAYER_MODEL\":\"sarge_default\",\"PG_A\":11,\"RL\":0,\"MG_A\":23,\"SCORE\":0,\"IMPRESSIVE\":0,\"SG\":0,\"RG_A\":0,\"LG\":0}],\"LEAST_DEATHS\":{\"PLAYER_TEAM\":\"Blue\",\"PLAYER_NICK\":\"namcap\",\"PLAYER_ID\":3472430,\"PLAYER_MODEL\":\"sarge_default\",\"PLAYER_COUNTRY\":\"US\",\"NUM\":2},\"MAP_NAME\":\"Dredwerkz\",\"DMG_TAKEN\":{\"PLAYER_TEAM\":\"Red\",\"PLAYER_NICK\":\"DakSmyth\",\"PLAYER_ID\":3472616,\"PLAYER_MODEL\":\"sarge_default\",\"PLAYER_COUNTRY\":\"US\",\"NUM\":31241},\"TEAM_SCOREBOARD\":[{\"MG\":0,\"BFG\":0,\"NG_A\":0,\"DEATHS\":46,\"PM\":0,\"HUMILIATION\":0,\"GT_A\":0,\"GT\":0,\"PG\":0,\"RG\":0,\"MIN\":2883,\"CG_A\":0,\"GL\":0,\"ACCURACY\":19.2,\"EXCELLENT\":1,\"SG_A\":52,\"RL_A\":25,\"TEAM\":\"Blue\",\"LG_A\":1,\"CG\":0,\"NG\":0,\"PM_A\":0,\"BFG_A\":21,\"GL_A\":8,\"PG_A\":11,\"RL\":0,\"MG_A\":18,\"SCORE\":43,\"IMPRESSIVE\":10,\"SG\":0,\"RG_A\":25,\"LG\":0},{\"MG\":0,\"NG_A\":0,\"BFG\":0,\"DEATHS\":56,\"HUMILIATION\":0,\"PM\":0,\"GT_A\":0,\"GT\":0,\"MIN\":3197,\"RG\":0,\"PG\":0,\"CG_A\":0,\"GL\":0,\"ACCURACY\":21.6,\"EXCELLENT\":2,\"SG_A\":43,\"RL_A\":28,\"TEAM\":\"Red\",\"LG_A\":7,\"CG\":0,\"NG\":0,\"PM_A\":0,\"BFG_A\":17,\"GL_A\":0,\"RL\":0,\"PG_A\":15,\"SCORE\":33,\"MG_A\":23,\"IMPRESSIVE\":1,\"RG_A\":5,\"SG\":0,\"LG\":0}],\"AVG_ACC\":21,\"MOST_DEATHS\":{\"PLAYER_NICK\":\"xRequiem\",\"PLAYER_TEAM\":\"Blue\",\"PLAYER_ID\":3409944,\"PLAYER_MODEL\":\"anarki_blue\",\"PLAYER_COUNTRY\":\"US\",\"NUM\":19},\"WINNING_TEAM\":\"Blue\",\"GAME_TIMESTAMP\":\"05/05/2009 12:55 PM\",\"GAME_TYPE\":\"TDM\",\"MAP_NAME_SHORT\":\"qzdm12\",\"PUBLIC_ID\":9865952,\"GAME_TIMESTAMP_NICE\":\"17 hours\",\"GAME_LENGTH_NICE\":\"15.0 minutes\",\"GAME_EXPIRES_FULL\":\"06/01/2009 12:55 PM\"}";
    
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
        assertEquals(6, serverInfo.getPlayerInfos().size());
    }
    
    @Test
    public void parseTwo() throws ParserException {
        ServerInfo serverInfo = testParse(someTDM);
        
        assertEquals(10, serverInfo.getPlayerInfos().size());
        assertEquals(TeamKey.RED_TEAM, serverInfo.getPlayerInfos().get(0).getTeamKey());
        assertEquals(TeamKey.RED_TEAM, serverInfo.getPlayerInfos().get(1).getTeamKey());
        assertEquals(TeamKey.RED_TEAM, serverInfo.getPlayerInfos().get(2).getTeamKey());
        assertEquals(TeamKey.RED_TEAM, serverInfo.getPlayerInfos().get(3).getTeamKey());
        assertEquals(TeamKey.RED_TEAM, serverInfo.getPlayerInfos().get(4).getTeamKey());
        assertEquals(TeamKey.BLUE_TEAM, serverInfo.getPlayerInfos().get(5).getTeamKey());
        assertEquals(TeamKey.BLUE_TEAM, serverInfo.getPlayerInfos().get(6).getTeamKey());
        assertEquals(TeamKey.BLUE_TEAM, serverInfo.getPlayerInfos().get(7).getTeamKey());
        assertEquals(TeamKey.BLUE_TEAM, serverInfo.getPlayerInfos().get(8).getTeamKey());
        assertEquals(TeamKey.BLUE_TEAM, serverInfo.getPlayerInfos().get(9).getTeamKey());
        
    }

}
