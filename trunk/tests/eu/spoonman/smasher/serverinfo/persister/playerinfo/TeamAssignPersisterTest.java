package eu.spoonman.smasher.serverinfo.persister.playerinfo;

import static org.junit.Assert.*;

import org.junit.Test;

import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
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
        
        return serverInfo;
    }

    @Test
    public void testPersist() {
        ServerInfo serverInfo = getServerInfo();
        
        TeamAssignPersister persister = new TeamAssignPersister();
        persister.persist(serverInfo);
        
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

}
