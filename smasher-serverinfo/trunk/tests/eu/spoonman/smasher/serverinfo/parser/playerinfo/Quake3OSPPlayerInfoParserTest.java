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

import java.util.ArrayList;

import org.junit.Test;

import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.parser.ParserException;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class Quake3OSPPlayerInfoParserTest {
    
    @Test
    public void parseIntoServerInfo() throws ParserException {
        
        ArrayList<String> player = new ArrayList<String>();
        player.add("50");
        player.add("10");
        player.add("MyName");
        
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setRawAttributes(player);
        
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.getPlayerInfos().add(playerInfo);
        
        Quake3OSPPlayerInfoParser quake3OSPPlayerInfoParser = new Quake3OSPPlayerInfoParser();
        quake3OSPPlayerInfoParser.parseIntoServerInfo(serverInfo);
        
        assertEquals("MyName", serverInfo.getPlayerInfos().get(0).getName());
        assertEquals(50, serverInfo.getPlayerInfos().get(0).getPing());
        assertEquals(10, serverInfo.getPlayerInfos().get(0).getScore());
    }

}
