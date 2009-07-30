package eu.spoonman.smasher.scorebot.persister;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.spoonman.smasher.common.DiffData;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TeamInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;

public class TeamAssignPersisterTest {
    
    //Real life problem, where TeamAssignPersister didn't find any solution.
    ServerInfo getServerInfo() {
        ServerInfo serverInfo = new ServerInfo();
        
        serverInfo.getTeamInfos().put(TeamKey.RED_TEAM, new TeamInfo(TeamKey.RED_TEAM, null, 23));
        serverInfo.getTeamInfos().put(TeamKey.BLUE_TEAM, new TeamInfo(TeamKey.BLUE_TEAM, null, 3));
        
        return serverInfo;
    }
    
    List<DiffData<PlayerInfo>> getLCSPairs() {
    	List<DiffData<PlayerInfo>> pairs = new ArrayList<DiffData<PlayerInfo>>();
    	
        pairs.add(new DiffData<PlayerInfo>(new PlayerInfo ("dza.mp3", 2, 10), new PlayerInfo ("dza.mp3", 2, 10)));
        pairs.add(new DiffData<PlayerInfo>(new PlayerInfo (":czesio:", 0, 20), new PlayerInfo (":czesio:", 0, 20)));
        pairs.add(new DiffData<PlayerInfo>(new PlayerInfo ("[klan]jaro", 9, 30), new PlayerInfo ("[klan]jaro", 9, 30)));
        pairs.add(new DiffData<PlayerInfo>(new PlayerInfo ("zw", -1, 40), new PlayerInfo ("zw", -1, 40)));
        pairs.add(new DiffData<PlayerInfo>(new PlayerInfo ("roln!ck^^", 2, 50), new PlayerInfo ("roln!ck^^", 2, 50)));
        pairs.add(new DiffData<PlayerInfo>(new PlayerInfo ("DARKMAN^^", 5, 60), new PlayerInfo ("DARKMAN^^", 5, 60)));
        pairs.add(new DiffData<PlayerInfo>(new PlayerInfo ("a", 0, 70), new PlayerInfo ("a", 0, 70)));
        pairs.add(new DiffData<PlayerInfo>(new PlayerInfo ("Tre", 7, 80), new PlayerInfo ("Tre", 7, 80)));
        pairs.add(new DiffData<PlayerInfo>(new PlayerInfo ("M-c/B0R0", 2, 90), new PlayerInfo ("M-c/B0R0", 2, 90)));
        
        return pairs;

    }
    
    @Test
    public void testPersist() {
        ServerInfo serverInfo = getServerInfo();
        
        TeamAssignPersister persister = new TeamAssignPersister(null);
        persister.persist(null, serverInfo);
        
        List<DiffData<PlayerInfo>> pairs = getLCSPairs();
        
        for (DiffData<PlayerInfo> pair : pairs) {
			pair.setFirst(null);
		}
        
        persister.persist(pairs);
        
        assertNull(pairs.get(0).getSecond().getTeamKey());
        assertNull(pairs.get(1).getSecond().getTeamKey());
        assertEquals(TeamKey.RED_TEAM, pairs.get(2).getSecond().getTeamKey());
        assertEquals(TeamKey.BLUE_TEAM, pairs.get(3).getSecond().getTeamKey());
        assertNull(pairs.get(4).getSecond().getTeamKey());
        assertEquals(TeamKey.RED_TEAM, pairs.get(5).getSecond().getTeamKey());
        assertNull(pairs.get(6).getSecond().getTeamKey());
        assertEquals(TeamKey.RED_TEAM, pairs.get(7).getSecond().getTeamKey());
        assertNull(pairs.get(8).getSecond().getTeamKey());
        
    }
    
    //Same as above example but player with -1 score gains to have 1 score in next iteration.
    @Test
    public void testPersistTwo() {
        
    	ServerInfo serverInfo = getServerInfo();
        
        TeamAssignPersister persister = new TeamAssignPersister(null);
        persister.persist(null, serverInfo);
        
        List<DiffData<PlayerInfo>> pairs = getLCSPairs();
        
        for (DiffData<PlayerInfo> pair : pairs) {
			pair.setFirst(null);
		}
        
        persister.persist(pairs);
        
        ServerInfo serverInfo2 = getServerInfo();
        serverInfo2.getTeamInfos().get(TeamKey.RED_TEAM).setScore(23);
        serverInfo2.getTeamInfos().get(TeamKey.BLUE_TEAM).setScore(5);
        
        persister.persist(serverInfo, serverInfo2);
        
        pairs = getLCSPairs();
        pairs.get(3).getSecond().setScore(1);
        
        persister.persist(pairs);
        
        assertNull(pairs.get(0).getSecond().getTeamKey());
        assertNull(pairs.get(1).getSecond().getTeamKey());
        assertEquals(TeamKey.RED_TEAM, pairs.get(2).getSecond().getTeamKey());
        assertEquals(TeamKey.BLUE_TEAM, pairs.get(3).getSecond().getTeamKey());
        assertNull(pairs.get(4).getSecond().getTeamKey());
        assertEquals(TeamKey.RED_TEAM, pairs.get(5).getSecond().getTeamKey());
        assertNull(pairs.get(6).getSecond().getTeamKey());
        assertEquals(TeamKey.RED_TEAM, pairs.get(7).getSecond().getTeamKey());
        assertNull(pairs.get(8).getSecond().getTeamKey());
        
    }

}
