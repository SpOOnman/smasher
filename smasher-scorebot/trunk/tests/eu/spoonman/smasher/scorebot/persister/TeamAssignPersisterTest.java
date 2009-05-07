package eu.spoonman.smasher.scorebot.persister;

import static org.junit.Assert.*;

import org.junit.Test;

import eu.spoonman.smasher.scorebot.persister.TeamAssignPersister;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TeamInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;

public class TeamAssignPersisterTest {
    
    //Real life problem, where TeamAssignPersister didn't find any solution.
    ServerInfo getServerInfo() {
        ServerInfo serverInfo = new ServerInfo();
        
        serverInfo.getPlayerInfos().add(new PlayerInfo ("dza.mp3", 2, 10));
        serverInfo.getPlayerInfos().add(new PlayerInfo (":czesio:", 0, 20));
        serverInfo.getPlayerInfos().add(new PlayerInfo ("[klan]jaro", 9, 30));
        serverInfo.getPlayerInfos().add(new PlayerInfo ("zw", -1, 40));
        serverInfo.getPlayerInfos().add(new PlayerInfo ("roln!ck^^", 2, 50));
        serverInfo.getPlayerInfos().add(new PlayerInfo ("DARKMAN^^", 5, 60));
        serverInfo.getPlayerInfos().add(new PlayerInfo ("a", 0, 70));
        serverInfo.getPlayerInfos().add(new PlayerInfo ("Tre", 7, 80));
        serverInfo.getPlayerInfos().add(new PlayerInfo ("M-c/B0R0", 2, 90));
        
        serverInfo.getTeamInfos().put(TeamKey.RED_TEAM, new TeamInfo(TeamKey.RED_TEAM, null, 23));
        serverInfo.getTeamInfos().put(TeamKey.BLUE_TEAM, new TeamInfo(TeamKey.BLUE_TEAM, null, 3));
        
        return serverInfo;
    }

    @Test
    public void testPersist() {
        ServerInfo serverInfo = getServerInfo();
        
        TeamAssignPersister persister = new TeamAssignPersister();
        persister.persist(null, serverInfo);
        
        assertNull(serverInfo.getPlayerInfos().get(0).getTeamKey());
        assertNull(serverInfo.getPlayerInfos().get(1).getTeamKey());
        assertEquals(TeamKey.RED_TEAM, serverInfo.getPlayerInfos().get(2).getTeamKey());
        assertEquals(TeamKey.BLUE_TEAM, serverInfo.getPlayerInfos().get(3).getTeamKey());
        assertNull(serverInfo.getPlayerInfos().get(4).getTeamKey());
        assertEquals(TeamKey.RED_TEAM, serverInfo.getPlayerInfos().get(5).getTeamKey());
        assertNull(serverInfo.getPlayerInfos().get(6).getTeamKey());
        assertEquals(TeamKey.RED_TEAM, serverInfo.getPlayerInfos().get(7).getTeamKey());
        assertNull(serverInfo.getPlayerInfos().get(8).getTeamKey());
        
    }
    
    //Same as above example but player with -1 score gains to have 1 score in next iteration.
    @Test
    public void testPersistTwo() {
        
        ServerInfo serverInfo = getServerInfo();
        
        TeamAssignPersister persister = new TeamAssignPersister();
        persister.persist(null, serverInfo);
        
        ServerInfo serverInfo2 = getServerInfo();
        serverInfo2.getPlayerInfos().get(3).setScore(1);
        serverInfo2.getTeamInfos().get(TeamKey.RED_TEAM).setScore(23);
        serverInfo2.getTeamInfos().get(TeamKey.BLUE_TEAM).setScore(5);
        
        persister.persist(serverInfo, serverInfo2);
        
        assertNull(serverInfo2.getPlayerInfos().get(0).getTeamKey());
        assertNull(serverInfo2.getPlayerInfos().get(1).getTeamKey());
        assertEquals(TeamKey.RED_TEAM, serverInfo2.getPlayerInfos().get(2).getTeamKey());
        assertEquals(TeamKey.BLUE_TEAM, serverInfo2.getPlayerInfos().get(3).getTeamKey());
        assertNull(serverInfo2.getPlayerInfos().get(4).getTeamKey());
        assertEquals(TeamKey.RED_TEAM, serverInfo2.getPlayerInfos().get(5).getTeamKey());
        assertNull(serverInfo2.getPlayerInfos().get(6).getTeamKey());
        assertEquals(TeamKey.RED_TEAM, serverInfo2.getPlayerInfos().get(7).getTeamKey());
        assertNull(serverInfo2.getPlayerInfos().get(8).getTeamKey());
        
    }

}
