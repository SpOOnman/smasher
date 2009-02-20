package eu.spoonman.smasher.scorebot;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import eu.spoonman.smasher.common.Pair;
import eu.spoonman.smasher.scorebot.ServerInfoScorebot;
import eu.spoonman.smasher.serverinfo.PlayerInfo;

public class ServerInfoScorebotTest {

	@Test
	public void testReviseLCSPlayerPairs() {
		List<Pair<PlayerInfo,PlayerInfo>> playerInfoPairs = TestHelper.getPlayerInfoPairs();
		
		playerInfoPairs.get(3).setSecond(null);
		playerInfoPairs.get(4).setFirst(null);
		
		ServerInfoScorebot serverInfoScorebot = new ServerInfoScorebot();
		serverInfoScorebot.reviseLCSPlayerPairs(playerInfoPairs);
		
		assertEquals(5, playerInfoPairs.size());
		assertEquals("Dave", playerInfoPairs.get(3).getFirst().getName());
		assertEquals("Eve", playerInfoPairs.get(3).getSecond().getName());
	}

}
