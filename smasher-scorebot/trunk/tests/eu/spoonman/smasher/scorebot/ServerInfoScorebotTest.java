package eu.spoonman.smasher.scorebot;

import java.util.List;

import org.junit.Test;

import eu.spoonman.smasher.common.Pair;
import eu.spoonman.smasher.scorebot.ServerInfoScorebot;
import eu.spoonman.smasher.serverinfo.PlayerInfo;

public class ServerInfoScorebotTest {

	@Test
	public void testReviseLCSPlayerPairs() {
		List<Pair<PlayerInfo,PlayerInfo>> playerInfoPairs = TestHelper.getPlayerInfoPairs();
		
		playerInfoPairs.get(3).getSecond().setName(null);
		playerInfoPairs.get(4).getFirst().setName(null);
		
		ServerInfoScorebot serverInfoScorebot = new ServerInfoScorebot();
		serverInfoScorebot.reviseLCSPlayerPairs(playerInfoPairs);
		
		TestHelper.printPairs(playerInfoPairs);
	
	}

}
