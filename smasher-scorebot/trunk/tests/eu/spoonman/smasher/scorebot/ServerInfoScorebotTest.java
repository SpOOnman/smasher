package eu.spoonman.smasher.scorebot;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import eu.spoonman.smasher.common.Observer;
import eu.spoonman.smasher.common.Pair;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;

public class ServerInfoScorebotTest {
	
	String assertMethodName = "";
	
	void setAssertMehodName (String name) {
		synchronized (assertMethodName) {
			assertMethodName = name;
		}
	}
	
	String getAssertMethodName () {
		synchronized (assertMethodName) {
			return assertMethodName;
		}
	}

	@Test
	public void testReviseLCSPlayerPairs() {
		List<Pair<PlayerInfo,PlayerInfo>> playerInfoPairs = TestHelper.getPlayerInfoPairs();
		
		playerInfoPairs.get(3).setSecond(null);
		playerInfoPairs.get(4).setFirst(null);
		
		ServerInfoScorebot serverInfoScorebot = new ServerInfoScorebot(null);
		serverInfoScorebot.reviseLCSPlayerPairs(playerInfoPairs);
		
		assertEquals(5, playerInfoPairs.size());
		assertEquals("Dave", playerInfoPairs.get(3).getFirst().getName());
		assertEquals("Eve", playerInfoPairs.get(3).getSecond().getName());
	}

	@Test
	public void testPlayerInfoChangeScore() throws InterruptedException {
		Observer<Pair<PlayerInfo, PlayerInfo>> observer = new Observer<Pair<PlayerInfo,PlayerInfo>> () {
			
			@Override
			public void notify(Pair<PlayerInfo, PlayerInfo> t) {
				assertEquals(9, t.getFirst().getScore());
				assertEquals(10, t.getSecond().getScore());
				setAssertMehodName("testPlayerInfoChangeScore");
			}
		};
			
				
		final ServerInfoScorebot scorebot =  new ServerInfoScorebot(null);
		scorebot.setPreviousServerInfo(TestHelper.getServerInfo());
		
		ServerInfo current = TestHelper.getServerInfo();
		current.getPlayerInfos().get(3).setScore(10);
		scorebot.setCurrentServerInfo(current);
		
		scorebot.getPlayerScoreChangedEvent().register(observer);
		Thread thread = new Thread(new Runnable() {
			public void run() {
				scorebot.difference();
			}
		});
		
		thread.run();
		thread.join();
		
		assertEquals("testPlayerInfoChangeScore", getAssertMethodName());
	}

}
