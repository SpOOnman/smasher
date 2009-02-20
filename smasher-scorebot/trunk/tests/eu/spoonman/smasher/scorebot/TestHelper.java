package eu.spoonman.smasher.scorebot;

import java.util.ArrayList;
import java.util.List;

import eu.spoonman.smasher.common.Pair;
import eu.spoonman.smasher.serverinfo.PlayerInfo;

public class TestHelper {
	
	public static <T> void printPairs(List<Pair<T,T>> pairs) {
		for (Pair<T,T> pair : pairs) {
			System.out.println(pair);
		}
		
	}
	
	public static List<PlayerInfo> getPlayerInfos() {
		List<PlayerInfo> playerInfos = new ArrayList<PlayerInfo>();
		
		playerInfos.add(new PlayerInfo( "Ann", 19, 10));
		playerInfos.add(new PlayerInfo( "Bob", 17, 20));
		playerInfos.add(new PlayerInfo( "Cid", 11, 30));
		playerInfos.add(new PlayerInfo( "Dave", 9, 40));
		playerInfos.add(new PlayerInfo( "Eve", 2, 50));
		playerInfos.add(new PlayerInfo( "Frank", 1, 60));
		
		return playerInfos;
	}
	
	public static List<Pair<PlayerInfo, PlayerInfo>> getPlayerInfoPairs() {
		List<Pair<PlayerInfo, PlayerInfo>> playerInfos = new ArrayList<Pair<PlayerInfo,PlayerInfo>>();
		
		playerInfos.add(new Pair<PlayerInfo, PlayerInfo>(new PlayerInfo( "Ann", 19, 10), new PlayerInfo( "Ann", 19, 10)));
		playerInfos.add(new Pair<PlayerInfo, PlayerInfo>(new PlayerInfo( "Bob", 17, 20), new PlayerInfo( "Bob", 17, 20)));
		playerInfos.add(new Pair<PlayerInfo, PlayerInfo>(new PlayerInfo( "Cid", 11, 30), new PlayerInfo( "Cid", 11, 30)));
		playerInfos.add(new Pair<PlayerInfo, PlayerInfo>(new PlayerInfo( "Dave", 9, 40), new PlayerInfo( "Dave", 9, 40)));
		playerInfos.add(new Pair<PlayerInfo, PlayerInfo>(new PlayerInfo( "Eve", 2, 50), new PlayerInfo( "Eve", 2, 50)));
		playerInfos.add(new Pair<PlayerInfo, PlayerInfo>(new PlayerInfo( "Frank", 1, 60), new PlayerInfo( "Frank", 1, 60)));
		
		return playerInfos;
	}
}
