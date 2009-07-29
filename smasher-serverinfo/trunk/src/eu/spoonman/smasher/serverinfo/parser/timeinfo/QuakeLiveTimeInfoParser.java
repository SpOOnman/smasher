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

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;

import eu.spoonman.smasher.serverinfo.ProgressInfoFlags;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TimePeriodInfo;
import eu.spoonman.smasher.serverinfo.parser.AttributeNotFoundException;
import eu.spoonman.smasher.serverinfo.parser.ParserException;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;

/**
 * Progress info parser for QuakeLive.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public class QuakeLiveTimeInfoParser implements ServerInfoParser {
    
    public static final String G_LEVEL_START_TIME = "g_levelStartTime";
    public static final String G_GAME_STATE = "g_gameState";
    public static final String CAPTURELIMIT = "capturelimit";
    
    private static final Logger log = Logger.getLogger(QuakeLiveTimeInfoParser.class);
    
    @Override
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException {

        String gameState = serverInfo.getNamedAttributes().get(G_GAME_STATE);
        String levelStartTime = serverInfo.getNamedAttributes().get(G_LEVEL_START_TIME);
        
        parseTime(serverInfo, gameState, levelStartTime);
    }

    protected void parseTime(ServerInfo serverInfo, String gameState, String levelStartTime) throws AttributeNotFoundException {
        if (log.isDebugEnabled()) {
            log.debug(String.format(ServerInfoParser.fieldLogFormat, G_GAME_STATE, gameState));
            log.debug(String.format(ServerInfoParser.fieldLogFormat, G_LEVEL_START_TIME, levelStartTime));
        }
        
        TimePeriodInfo timePeriodInfo = new TimePeriodInfo(gameState + levelStartTime);

        if (gameState == null)
            throw new AttributeNotFoundException(G_GAME_STATE);
        
        if (levelStartTime == null)
            throw new AttributeNotFoundException(G_LEVEL_START_TIME);
        
        if (gameState.equals("IN_PROGRESS"))
            timePeriodInfo.getProgressInfoFlags().add(ProgressInfoFlags.IN_PLAY);
        else if (gameState.equals("PRE_GAME"))
            timePeriodInfo.getProgressInfoFlags().add(ProgressInfoFlags.WARMUP);
        else if (gameState.equals("COUNT_DOWN"))
            timePeriodInfo.getProgressInfoFlags().add(ProgressInfoFlags.COUNTDOWN);
        else {
            log.warn("Game state unknown: " + gameState);
            timePeriodInfo.getProgressInfoFlags().add(ProgressInfoFlags.UNKONWN);
        }
        
        LocalDateTime now = new LocalDateTime();
        LocalDateTime start = new LocalDateTime(Long.parseLong(levelStartTime) * 1000);
        Period period = new Period(start, now);
        
        timePeriodInfo.setPeriod(period);
        
        serverInfo.setProgressInfo(timePeriodInfo);
    }
}
