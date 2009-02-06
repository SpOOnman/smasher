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

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import eu.spoonman.smasher.serverinfo.LocalTimeInfo;
import eu.spoonman.smasher.serverinfo.ProgressInfoFlags;
import eu.spoonman.smasher.serverinfo.ServerInfo;
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

    @Override
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException {

        String gameState = serverInfo.getNamedAttributes().get("g_gameState");
        String levelStartTime = serverInfo.getNamedAttributes().get("g_levelStartTime");
        
        LocalTimeInfo localTimeInfo = new LocalTimeInfo(gameState + levelStartTime);

        if (gameState == null)
            throw new AttributeNotFoundException("g_gameState");
        
        if (levelStartTime == null)
            throw new AttributeNotFoundException("g_levelStartTime");
        
        if (gameState.equals("IN_PROGRESS"))
            localTimeInfo.getProgressInfoFlags().add(ProgressInfoFlags.IN_PLAY);
        else if (gameState.equals("PRE_GAME"))
            localTimeInfo.getProgressInfoFlags().add(ProgressInfoFlags.WARMUP);
        else if (gameState.equals("COUNT_DOWN"))
            localTimeInfo.getProgressInfoFlags().add(ProgressInfoFlags.COUNTDOWN);
        else
            localTimeInfo.getProgressInfoFlags().add(ProgressInfoFlags.UNKONWN);
        
        LocalDateTime now = new LocalDateTime();
        LocalDateTime start = new LocalDateTime(Long.parseLong(levelStartTime) * 1000);
        Period period = new Period(start, now);
        
        localTimeInfo.setLocalTime(new LocalTime(period));
    }
}
