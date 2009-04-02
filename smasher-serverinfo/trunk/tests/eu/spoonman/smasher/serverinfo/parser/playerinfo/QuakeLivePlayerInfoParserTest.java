/**
 * 
 */
package eu.spoonman.smasher.serverinfo.parser.playerinfo;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Test;

import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.parser.ParserException;

/**
 * @author spoonman
 *
 */
public class QuakeLivePlayerInfoParserTest {
    
    private final String players = "{\"BLUE_SCOREBOARD\":[{\"MG\":0,\"BFG\":0,\"NG_A\":0,\"DEATHS\":6,\"PM\":0,\"GT_A\":0,\"GT\":0,\"PLAYER_NICK\":\"LS1942\",\"RG\":0,\"PG\":0,\"MIN\":482,\"CG_A\":0,\"GL\":0,\"DEFENDS\":6,\"PLAYER_COUNTRY\":\"PL\",\"ACCURACY\":21,\"SG_A\":0,\"RL_A\":27,\"TEAM\":\"Blue\",\"TEAM_RANK\":1,\"PLAYER_ID\":1840680,\"LG_A\":0,\"KILLS\":11,\"ASSISTS\":1,\"CG\":0,\"NG\":0,\"RANK\":1,\"PM_A\":0,\"GL_A\":0,\"BFG_A\":0,\"PLAYER_MODEL\":\"klesk_flisk\",\"PG_A\":0,\"CAPTURES\":5,\"RL\":0,\"MG_A\":13,\"SCORE\":804,\"SG\":0,\"RG_A\":14,\"LG\":0},{\"MG\":0,\"NG_A\":0,\"BFG\":0,\"DEATHS\":14,\"PM\":0,\"GT_A\":0,\"GT\":0,\"MIN\":482,\"PG\":0,\"RG\":0,\"PLAYER_NICK\":\"Napastnik\",\"CG_A\":0,\"GL\":0,\"DEFENDS\":11,\"PLAYER_COUNTRY\":\"PL\",\"ACCURACY\":19,\"SG_A\":66,\"RL_A\":64,\"TEAM\":\"Blue\",\"TEAM_RANK\":2,\"PLAYER_ID\":2187513,\"LG_A\":0,\"KILLS\":21,\"ASSISTS\":2,\"CG\":0,\"NG\":0,\"RANK\":2,\"BFG_A\":0,\"GL_A\":0,\"PM_A\":0,\"PLAYER_MODEL\":\"bones_bones\",\"RL\":0,\"CAPTURES\":1,\"PG_A\":10,\"SCORE\":639,\"MG_A\":17,\"RG_A\":52,\"SG\":0,\"LG\":0},{\"MG\":0,\"BFG\":0,\"NG_A\":0,\"DEATHS\":11,\"PM\":0,\"GT_A\":0,\"GT\":0,\"PLAYER_NICK\":\"Legend4ry\",\"RG\":0,\"PG\":0,\"MIN\":482,\"CG_A\":0,\"GL\":0,\"DEFENDS\":12,\"PLAYER_COUNTRY\":\"GB\",\"ACCURACY\":31,\"SG_A\":71,\"RL_A\":43,\"TEAM\":\"Blue\",\"TEAM_RANK\":3,\"PLAYER_ID\":1836336,\"LG_A\":0,\"KILLS\":16,\"ASSISTS\":3,\"CG\":0,\"NG\":0,\"RANK\":3,\"PM_A\":0,\"GL_A\":0,\"BFG_A\":0,\"PLAYER_MODEL\":\"keel_sport\",\"PG_A\":7,\"CAPTURES\":1,\"RL\":0,\"MG_A\":28,\"SCORE\":571,\"SG\":0,\"RG_A\":42,\"LG\":0},{\"MG\":0,\"NG_A\":0,\"BFG\":0,\"DEATHS\":12,\"PM\":0,\"GT_A\":0,\"GT\":0,\"MIN\":347,\"PG\":0,\"RG\":0,\"PLAYER_NICK\":\"Superi1337\",\"CG_A\":0,\"GL\":0,\"DEFENDS\":6,\"PLAYER_COUNTRY\":\"FI\",\"ACCURACY\":20,\"SG_A\":100,\"RL_A\":42,\"TEAM\":\"Blue\",\"TEAM_RANK\":4,\"PLAYER_ID\":1470739,\"LG_A\":0,\"KILLS\":10,\"ASSISTS\":1,\"CG\":0,\"NG\":0,\"RANK\":7,\"BFG_A\":0,\"GL_A\":0,\"PM_A\":0,\"PLAYER_MODEL\":\"klesk_flisk\",\"RL\":0,\"CAPTURES\":1,\"PG_A\":18,\"SCORE\":400,\"MG_A\":18,\"RG_A\":37,\"SG\":0,\"LG\":0},{\"MG\":0,\"BFG\":0,\"NG_A\":0,\"DEATHS\":4,\"PM\":0,\"GT_A\":0,\"GT\":0,\"PLAYER_NICK\":\"Cychu666\",\"RG\":0,\"PG\":0,\"MIN\":261,\"CG_A\":0,\"GL\":0,\"DEFENDS\":6,\"PLAYER_COUNTRY\":\"PL\",\"ACCURACY\":24,\"SG_A\":37,\"RL_A\":43,\"TEAM\":\"Blue\",\"TEAM_RANK\":5,\"PLAYER_ID\":2050125,\"LG_A\":0,\"KILLS\":9,\"ASSISTS\":1,\"CG\":0,\"NG\":0,\"RANK\":8,\"PM_A\":0,\"GL_A\":0,\"BFG_A\":0,\"PLAYER_MODEL\":\"anarki_default\",\"PG_A\":15,\"CAPTURES\":0,\"RL\":0,\"MG_A\":21,\"SCORE\":183,\"SG\":0,\"RG_A\":38,\"LG\":0},{\"MG\":0,\"NG_A\":0,\"BFG\":0,\"DEATHS\":1,\"PM\":0,\"GT_A\":0,\"GT\":0,\"MIN\":63,\"PG\":0,\"RG\":0,\"PLAYER_NICK\":\"acuratebob\",\"CG_A\":0,\"GL\":0,\"DEFENDS\":0,\"PLAYER_COUNTRY\":\"GB\",\"ACCURACY\":16,\"SG_A\":0,\"RL_A\":0,\"TEAM\":\"Blue\",\"TEAM_RANK\":6,\"PLAYER_ID\":2378331,\"LG_A\":0,\"KILLS\":1,\"ASSISTS\":0,\"CG\":0,\"NG\":0,\"RANK\":12,\"BFG_A\":0,\"GL_A\":0,\"PM_A\":0,\"PLAYER_MODEL\":\"razor_id\",\"RL\":0,\"CAPTURES\":0,\"PG_A\":0,\"SCORE\":46,\"MG_A\":14,\"RG_A\":33,\"SG\":0,\"LG\":0},{\"MG\":0,\"BFG\":0,\"NG_A\":0,\"DEATHS\":0,\"PM\":0,\"GT_A\":0,\"GT\":0,\"PG\":0,\"MIN\":0,\"RG\":0,\"PLAYER_NICK\":\"QUITTERS\",\"CG_A\":0,\"GL\":0,\"DEFENDS\":0,\"ACCURACY\":\"N/A\",\"SG_A\":0,\"RL_A\":0,\"TEAM_RANK\":\"Q\",\"PLAYER_ID\":0,\"LG_A\":0,\"KILLS\":0,\"ASSISTS\":0,\"CG\":0,\"NG\":0,\"RANK\":\"Q\",\"PM_A\":0,\"BFG_A\":0,\"GL_A\":0,\"PG_A\":0,\"CAPTURES\":0,\"RL\":0,\"MG_A\":0,\"SCORE\":0,\"SG\":0,\"RG_A\":0,\"LG\":0}]}";
    
    @Test
    public void parseTest() throws ParserException {
        QuakeLivePlayerInfoParser parser = new QuakeLivePlayerInfoParser();
        
        JSONObject json = (JSONObject) JSONValue.parse(players);
        System.out.println(json.toJSONString());
        
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setJson(json);
        
        parser.parseIntoServerInfo(serverInfo);
        
        System.out.println(serverInfo);
    }

}
