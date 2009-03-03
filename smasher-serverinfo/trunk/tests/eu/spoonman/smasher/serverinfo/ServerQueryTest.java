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

package eu.spoonman.smasher.serverinfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class ServerQueryTest {
    
    public final String responseOne = "\u00ff\u00ff\u00ff\u00ffstatusResponse\n\\mode_current\\VQ3TDM\\g_needpass\\0\\GTV_CN\\1\\Players_Blue\\2 3 5 7 \\Players_Red\\1 4 8 9 \\Score_Blue\\80\\Score_Red\\81\\Score_Time\\9:27\\sv_arenas\\1\\gameversion\\1.46\\gamedate\\Apr 26 2008\\gamename\\cpma\\game\\CPMA\\sv_minRate\\0\\sv_privateClients\\0\\mapname\\pro-q3dm6\\protocol\\68\\g_gametype\\3\\version\\CNQ3 1.42 linux-i386 Apr 22 2008\\g_maxGameClients\\0\\server_gameplay\\VQ3\\sv_allowDownload\\0\\sv_floodProtect\\0\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\0\\sv_maxclients\\14\\sv_hostname\\ASTER Teams #1 VQ3\\sv_fps\\30\n13 0 \"^7<^0Murcielag0^7>\"\n21 0 \"cipek\"\n18 8 \"x\"\n16 0 \"^5N^4Z ^7/ ^0h^7en^0k\"\n15 0 \"^7n^10^7op.^1E\"\n35 33 \"sepuku\"\n26 0 \"MaS^0noclan\"\n35 0 \"mefajpe^f^^\"\n17 72 \"UnnamedPlayer\"\n21 18 \"^ooptYm!stA\"\n21 31 \"^Al^Ba^Cg ^Df^Er^Fo^Gm ^Hx^IF\"\n";

    @Test
    public void testParseData() throws UnsupportedEncodingException {

        ServerQuery serverQuery = ServerQueryManager.createServerQuery(Games.QUAKE3ARENA, null, 0);
        ServerInfo serverInfo = serverQuery.query(responseOne.getBytes("ISO8859-1"));

        System.out.println(serverInfo);
    }
    
    
    

}
