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

import org.apache.log4j.Logger;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.EnumSet;

import org.junit.Test;

public class ServerQueryTest {
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(ServerQueryTest.class);

    public final String responseOne = "\u00ff\u00ff\u00ff\u00ffstatusResponse\n\\mode_current\\VQ3TDM\\g_needpass\\0\\GTV_CN\\1\\Players_Blue\\2 3 5 7 \\Players_Red\\1 4 8 9 \\Score_Blue\\80\\Score_Red\\81\\Score_Time\\9:27\\sv_arenas\\1\\gameversion\\1.46\\gamedate\\Apr 26 2008\\gamename\\cpma\\game\\CPMA\\sv_minRate\\0\\sv_privateClients\\0\\mapname\\pro-q3dm6\\protocol\\68\\g_gametype\\3\\version\\CNQ3 1.42 linux-i386 Apr 22 2008\\g_maxGameClients\\0\\server_gameplay\\VQ3\\sv_allowDownload\\0\\sv_floodProtect\\0\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\0\\sv_maxclients\\14\\sv_hostname\\ASTER Teams #1 VQ3\\sv_fps\\30\n13 0 \"^7<^0Murcielag0^7>\"\n21 0 \"cipek\"\n18 8 \"x\"\n16 0 \"^5N^4Z ^7/ ^0h^7en^0k\"\n15 0 \"^7n^10^7op.^1E\"\n35 33 \"sepuku\"\n26 0 \"MaS^0noclan\"\n35 0 \"mefajpe^f^^\"\n17 72 \"UnnamedPlayer\"\n21 18 \"^ooptYm!stA\"\n21 31 \"^Al^Ba^Cg ^Df^Er^Fo^Gm ^Hx^IF\"\n";

    @Test
    public void testParseData() throws UnsupportedEncodingException {

        ServerQuery serverQuery = ServerQueryManager.createServerQuery(Games.QUAKE3ARENA, null, 0);
        ServerInfo serverInfo = serverQuery.query(responseOne.getBytes("ISO8859-1"));

        log.debug(serverInfo.toString());
    }

    public final String QLResponseOne = "\u00ff\u00ff\u00ff\u00ffstatusResponse\n\\sv_ranked\\1\\g_voteFlags\\202\\sv_allowDownload\\1\\sv_maxclients\\12\\sv_hostname\\CTF DE #1\\g_gametype\\5\\capturelimit\\8\\timelimit\\20\\g_maxSkillTier\\5\\gt_realm\\quakelive\\version\\QuakeLive  0.1.0.220 linux-i386 Feb 19 2009 22:11:01\\dmflags\\0\\fraglimit\\20\\protocol\\73\\mapname\\qzteam7\\sv_privateClients\\0\\sv_gtid\\143584\\sv_punkbuster\\1\\sv_minPing\\0\\sv_maxPing\\0\\sv_skillRating\\39\\sv_monkeysOnly\\0\\gamename\\baseqz\\g_maxGameClients\\0\\roundlimit\\5\\roundtimelimit\\180\\g_needpass\\0\\g_redteam\\Stroggs\\g_blueteam\\Pagans\\g_instaGib\\0\\g_gameState\\PRE_GAME\\g_maxDeferredSpawns\\4\\g_levelStartTime\\1236120885\n0 999 \"APEENIE\" 0\n0 29 \"mexi\" 0\n0 67 \"Pricex\" 0\n0 69 \"dax29\" 0\n0 999 \"Shugmeister\" 0\n0 66 \"0neshot\" 0\n0 33 \"Guidloien\" 0\n0 64 \"maexel\" 0\n0 999 \"halabuz\" 0\n0 57 \"Choksi\" 0\n";

    @Test
    public void testQL() throws UnsupportedEncodingException {
        ServerQuery serverQuery = ServerQueryManager.createServerQuery(Games.QUAKELIVE, null, 0);
        ServerInfo serverInfo = serverQuery.query(QLResponseOne.getBytes("ISO8859-1"));

        // Some random checks.
        assertEquals("CTF DE #1", serverInfo.getGameInfo().getHostName());
        assertEquals(GameTypes.CAPTURE_THE_FLAG, serverInfo.getGameInfo().getGameType());
        assertEquals("qzteam7", serverInfo.getGameInfo().getMap());
        assertEquals("APEENIE", serverInfo.getPlayerInfos().get(0).getName());
        assertEquals(999, serverInfo.getPlayerInfos().get(0).getPing());
        assertEquals("Choksi", serverInfo.getPlayerInfos().get(9).getName());
        assertEquals(57, serverInfo.getPlayerInfos().get(9).getPing());
        assertEquals("Pagans", serverInfo.getTeamInfos().get(TeamKey.BLUE_TEAM).getName());
        assertEquals(EnumSet.of(ProgressInfoFlags.WARMUP), serverInfo.getProgressInfo().getProgressInfoFlags());

        log.debug(serverInfo);
    }

    public final String Q2TDMResponseOne = "\u00ff\u00ff\u00ff\u00ffprint\n\\Q2Admin\\1.17.44\\mapname\\q2dm1\\anticheat\\2\\#Time_Left\\8:20\\#Score_B\\5\\#Score_A\\1\\domination\\0\\needpass\\0\\maxspectators\\4\\gamedate\\Apr 19 2005\\gamename\\Quake2 TeamPlay DM v0.9.1\\fastweapons\\0\\instagib\\0\\allow_hud\\1\\allow_bfg\\1\\allow_gibs\\1\\allow_powerups\\1\\hostname\\ASTER TDM #2\\cheats\\0\\timelimit\\10\\fraglimit\\0\\dmflags\\1072\\deathmatch\\1\\version\\R1Q2 b7864 i386 Oct  1 2008 Linux\\gamedir\\tdm\\game\\tdm\\maxclients\\12\n0 20 \"Cider\"\n1 16 \"keFir\"\n0 28 \"Player\"\n4 44 \"Ag3_\"\n1 14 \"aL)Minus\"\n";

    @Test
    public void testQ2TDM() throws UnsupportedEncodingException {

        ServerQuery serverQuery = ServerQueryManager.createServerQuery(Games.QUAKE2, null, 0);
        ServerInfo serverInfo = serverQuery.query(Q2TDMResponseOne.getBytes("ISO8859-1"));

        log.debug(serverInfo);
    }

    public final String QLHTTPResponseOne = "{\"public_id\":411661,\"ECODE\":0,\"g_levelstarttime\":1242808738,\"timelimit\":0,\"max_clients\":10,\"roundtimelimit\":180,\"map_title\":\"Asylum\",\"skillDelta\":0,\"game_type_title\":\"Clan Arena\",\"map\":\"qzca1\",\"ranked\":1,\"g_bluescore\":5,\"g_gamestate\":\"IN_PROGRESS\",\"host_address\":\"79.141.160.101:27008\",\"fraglimit\":20,\"num_clients\":7,\"capturelimit\":8,\"game_type\":4,\"players\":[{\"clan\":null,\"name\":\"^7laun^41^7zed\",\"bot\":\"0\",\"rank\":2,\"score\":\"8\",\"team\":2,\"model\":\"doom/red\"},{\"clan\":\"^6fun.^7\",\"name\":\"Ma^6Q^7u\",\"bot\":\"0\",\"rank\":2,\"score\":\"47\",\"team\":2,\"model\":\"anarki/default\"},{\"clan\":\"^7rape.\",\"name\":\"^3k_0\",\"bot\":\"0\",\"rank\":2,\"score\":\"0\",\"team\":1,\"model\":\"anarki\"},{\"clan\":null,\"name\":\"Wanglyih\",\"bot\":\"0\",\"rank\":2,\"score\":\"0\",\"team\":0,\"model\":\"anarki\"},{\"clan\":null,\"name\":\"Rogon\",\"bot\":\"0\",\"rank\":2,\"score\":\"17\",\"team\":1,\"model\":\"anarki\"},{\"clan\":null,\"name\":\"AnDrIxx\",\"bot\":\"0\",\"rank\":2,\"score\":\"5\",\"team\":1,\"model\":\"doom/default\"},{\"clan\":\"BoH\",\"name\":\"^155\",\"bot\":\"0\",\"rank\":2,\"score\":\"13\",\"team\":2,\"model\":\"uriel\"}],\"roundlimit\":10,\"host_name\":\"Clan Arena PL #1\",\"g_redscore\":5}";

    // QW: \u00ff\u00ff\u00ff\u00ffn\\fraglimit\\0\\watervis\\0\\*version\\MVDSV
    // 0.20.05-CVS\\*z_ext\\75\\maxspectators\\8\\teamplay\\2\\hostname\\Aster
    // KTX
    // 27500\\timelimit\\20\\deathmatch\\1\\maxclients\\8\\*gamedir\\qw\\fpd\\206\\pm_ktjump\\1\\maxfps\\77\\*admin\\spoonman@op.pl\\location\\Warsaw,
    // Poland\\*progs\\47106\\xmod\\1.33\\xbuild\\00231\\map\\dm3\\status\\Standby\n\u0000

}
