package eu.spoonman.smasher.serverinfo.parser.playerinfo;

import static org.junit.Assert.assertEquals;

import java.util.EnumSet;

import org.junit.Test;

import eu.spoonman.smasher.serverinfo.PlayerFlags;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;
import eu.spoonman.smasher.serverinfo.parser.ParserException;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;

public class NumbersPlayerInfoParserTest {
    
    private ServerInfo createServerInfo() {
        ServerInfo serverInfo = new ServerInfo();
        
        serverInfo.getPlayerInfos().add(new PlayerInfo());
        serverInfo.getPlayerInfos().add(new PlayerInfo());
        serverInfo.getPlayerInfos().add(new PlayerInfo());
        serverInfo.getPlayerInfos().add(new PlayerInfo());
        serverInfo.getPlayerInfos().add(new PlayerInfo());
        serverInfo.getPlayerInfos().add(new PlayerInfo());
        
        return serverInfo;
    }

    @Test
    public void parseIntoServerInfoOne() throws ParserException {
        ServerInfoParser parser = new NumbersPlayerInfoParser("testingAtt", 0, TeamKey.RED_TEAM, EnumSet.of(PlayerFlags.IN_PLAY), ";");
        
        ServerInfo serverInfo = createServerInfo();
        serverInfo.getNamedAttributes().put("testingAtt", "0;1;4;5");
        
        parser.parseIntoServerInfo(serverInfo);
        
        assertEquals(TeamKey.RED_TEAM, serverInfo.getPlayerInfos().get(0).getTeamKey());
        assertEquals(TeamKey.RED_TEAM, serverInfo.getPlayerInfos().get(1).getTeamKey());
        assertEquals(null, serverInfo.getPlayerInfos().get(2).getTeamKey());
        assertEquals(null, serverInfo.getPlayerInfos().get(3).getTeamKey());
        assertEquals(TeamKey.RED_TEAM, serverInfo.getPlayerInfos().get(4).getTeamKey());
        assertEquals(TeamKey.RED_TEAM, serverInfo.getPlayerInfos().get(5).getTeamKey());
        
        assertEquals(EnumSet.of(PlayerFlags.IN_PLAY), serverInfo.getPlayerInfos().get(0).getPlayerFlags());
        assertEquals(EnumSet.of(PlayerFlags.IN_PLAY), serverInfo.getPlayerInfos().get(1).getPlayerFlags());
        assertEquals(EnumSet.noneOf(PlayerFlags.class), serverInfo.getPlayerInfos().get(2).getPlayerFlags());
        assertEquals(EnumSet.noneOf(PlayerFlags.class), serverInfo.getPlayerInfos().get(3).getPlayerFlags());
        assertEquals(EnumSet.of(PlayerFlags.IN_PLAY), serverInfo.getPlayerInfos().get(4).getPlayerFlags());
        assertEquals(EnumSet.of(PlayerFlags.IN_PLAY), serverInfo.getPlayerInfos().get(5).getPlayerFlags());
        
    }
    
    @Test
    public void parseIntoServerInfoTwo() throws ParserException {
        ServerInfoParser parser = new NumbersPlayerInfoParser("teams", -2, TeamKey.BLUE_TEAM, EnumSet.noneOf(PlayerFlags.class), " ");
        
        ServerInfo serverInfo = createServerInfo();
        serverInfo.getNamedAttributes().put("teams", " 2 4 7 ");
        
        parser.parseIntoServerInfo(serverInfo);
        
        assertEquals(TeamKey.BLUE_TEAM, serverInfo.getPlayerInfos().get(0).getTeamKey());
        assertEquals(null, serverInfo.getPlayerInfos().get(1).getTeamKey());
        assertEquals(TeamKey.BLUE_TEAM, serverInfo.getPlayerInfos().get(2).getTeamKey());
        assertEquals(null, serverInfo.getPlayerInfos().get(3).getTeamKey());
        assertEquals(null, serverInfo.getPlayerInfos().get(4).getTeamKey());
        assertEquals(TeamKey.BLUE_TEAM, serverInfo.getPlayerInfos().get(5).getTeamKey());
        
        assertEquals(EnumSet.noneOf(PlayerFlags.class), serverInfo.getPlayerInfos().get(0).getPlayerFlags());
        assertEquals(EnumSet.noneOf(PlayerFlags.class), serverInfo.getPlayerInfos().get(1).getPlayerFlags());
        assertEquals(EnumSet.noneOf(PlayerFlags.class), serverInfo.getPlayerInfos().get(2).getPlayerFlags());
        assertEquals(EnumSet.noneOf(PlayerFlags.class), serverInfo.getPlayerInfos().get(3).getPlayerFlags());
        assertEquals(EnumSet.noneOf(PlayerFlags.class), serverInfo.getPlayerInfos().get(4).getPlayerFlags());
        assertEquals(EnumSet.noneOf(PlayerFlags.class), serverInfo.getPlayerInfos().get(5).getPlayerFlags());
    }

}
