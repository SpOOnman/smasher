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
    
    // QL: \u00ff\u00ff\u00ff\u00ffstatusResponse\n\\sv_ranked\\1\\g_voteFlags\\202\\sv_allowDownload\\1\\sv_maxclients\\12\\sv_hostname\\CTF DE #1\\g_gametype\\5\\capturelimit\\8\\timelimit\\20\\g_maxSkillTier\\5\\gt_realm\\quakelive\\version\\QuakeLive  0.1.0.220 linux-i386 Feb 19 2009 22:11:01\\dmflags\\0\\fraglimit\\20\\protocol\\73\\mapname\\qzteam7\\sv_privateClients\\0\\sv_gtid\\143584\\sv_punkbuster\\1\\sv_minPing\\0\\sv_maxPing\\0\\sv_skillRating\\39\\sv_monkeysOnly\\0\\gamename\\baseqz\\g_maxGameClients\\0\\roundlimit\\5\\roundtimelimit\\180\\g_needpass\\0\\g_redteam\\Stroggs\\g_blueteam\\Pagans\\g_instaGib\\0\\g_gameState\\PRE_GAME\\g_maxDeferredSpawns\\4\\g_levelStartTime\\1236120885\n0 999 \"APEENIE\" 0\n0 29 \"mexi\" 0\n0 67 \"Pricex\" 0\n0 69 \"dax29\" 0\n0 999 \"Shugmeister\" 0\n0 66 \"0neshot\" 0\n0 33 \"Guidloien\" 0\n0 64 \"maexel\" 0\n0 999 \"halabuz\" 0\n0 57 \"Choksi\" 0\n
    // QW: \u00ff\u00ff\u00ff\u00ffn\\fraglimit\\0\\watervis\\0\\*version\\MVDSV 0.20.05-CVS\\*z_ext\\75\\maxspectators\\8\\teamplay\\2\\hostname\\Aster KTX 27500\\timelimit\\20\\deathmatch\\1\\maxclients\\8\\*gamedir\\qw\\fpd\\206\\pm_ktjump\\1\\maxfps\\77\\*admin\\spoonman@op.pl\\location\\Warsaw, Poland\\*progs\\47106\\xmod\\1.33\\xbuild\\00231\\map\\dm3\\status\\Standby\n\u0000
    // Q2: \u00ff\u00ff\u00ff\u00ffprint\n\\Q2Admin\\1.17.44\\mapname\\q2dm1\\anticheat\\2\\#Time_Left\\8:20\\#Score_B\\5\\#Score_A\\1\\domination\\0\\needpass\\0\\maxspectators\\4\\gamedate\\Apr 19 2005\\gamename\\Quake2 TeamPlay DM v0.9.1\\fastweapons\\0\\instagib\\0\\allow_hud\\1\\allow_bfg\\1\\allow_gibs\\1\\allow_powerups\\1\\hostname\\ASTER TDM #2\\cheats\\0\\timelimit\\10\\fraglimit\\0\\dmflags\\1072\\deathmatch\\1\\version\\R1Q2 b7864 i386 Oct  1 2008 Linux\\gamedir\\tdm\\game\\tdm\\maxclients\\12\n0 20 \"Cider\"\n1 16 \"keFir\"\n0 28 \"Player\"\n4 44 \"Ag3_\"\n1 14 \"aL)Minus\"\n
    
    
    

}
