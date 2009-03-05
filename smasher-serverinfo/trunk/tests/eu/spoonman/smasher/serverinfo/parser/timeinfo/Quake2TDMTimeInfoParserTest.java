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

package eu.spoonman.smasher.serverinfo.parser.timeinfo;

import static org.junit.Assert.*;

import java.util.EnumSet;

import org.junit.Test;

import eu.spoonman.smasher.serverinfo.ProgressInfoFlags;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TimePeriodInfo;
import eu.spoonman.smasher.serverinfo.parser.ParserException;

public class Quake2TDMTimeInfoParserTest {
    
    private Quake2TDMTimeInfoParser parser = new Quake2TDMTimeInfoParser();
    
    protected void simpleTest(String value, ProgressInfoFlags flag) throws ParserException {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.getNamedAttributes().put("#Time_Left", value);
        parser.parseIntoServerInfo(serverInfo);
        assertEquals(EnumSet.of(flag), serverInfo.getProgressInfo().getProgressInfoFlags());
        
    }

    @Test
    public void simpleTests() throws ParserException {
        simpleTest("WARMUP", ProgressInfoFlags.WARMUP);
        simpleTest("PREGAME", ProgressInfoFlags.WARMUP);
        simpleTest("COUNTDOWN", ProgressInfoFlags.COUNTDOWN);
        simpleTest("Winner: B", ProgressInfoFlags.TIMELIMIT);
    }
    
    @Test
    public void periodTest() throws ParserException {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.getNamedAttributes().put("#Time_Left", "12:34");
        parser.parseIntoServerInfo(serverInfo);
        assertEquals(TimePeriodInfo.class, serverInfo.getProgressInfo().getClass());
        assertEquals(EnumSet.of(ProgressInfoFlags.IN_PLAY), serverInfo.getProgressInfo().getProgressInfoFlags());
        assertEquals(12, ((TimePeriodInfo) serverInfo.getProgressInfo()).getPeriod().getMinutes());
        assertEquals(34, ((TimePeriodInfo) serverInfo.getProgressInfo()).getPeriod().getSeconds());
    }

}
