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

import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.joda.time.Period;

import eu.spoonman.smasher.serverinfo.ProgressInfo;
import eu.spoonman.smasher.serverinfo.ProgressInfoFlags;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TimePeriodInfo;
import eu.spoonman.smasher.serverinfo.parser.AttributeNotFoundException;
import eu.spoonman.smasher.serverinfo.parser.ParserException;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class Quake2TDMTimeInfoParser implements ServerInfoParser {
    
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(Quake2TDMTimeInfoParser.class);
    
    private static final Pattern timeRegex = Pattern.compile("(OT )?([0-9]{1,2}):([0-9]{2})");
    
    @Override
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException {
        String scoreTime = serverInfo.getNamedAttributes().get("#Time_Left");

        if (scoreTime == null)
            throw new AttributeNotFoundException("#Time_Left");

        log.debug(String.format(ServerInfoParser.fieldLogFormat, "#Time_Left", scoreTime));
        
        if (scoreTime.equals("WARMUP") || scoreTime.equals("PREGAME")) {
            serverInfo.setProgressInfo(new ProgressInfo(scoreTime, EnumSet.of(ProgressInfoFlags.WARMUP)));
            return;
        }
        
        if (scoreTime.equals("COUNTDOWN")) {
            serverInfo.setProgressInfo(new ProgressInfo(scoreTime, EnumSet.of(ProgressInfoFlags.COUNTDOWN)));
            return;
        }
        
        if (scoreTime.startsWith("Winner")) {
            serverInfo.setProgressInfo(new ProgressInfo(scoreTime, EnumSet.of(ProgressInfoFlags.TIMELIMIT)));
            return;
        }
        
        Matcher matcher = timeRegex.matcher(scoreTime);
        
        if (!(matcher.matches())) {
            log.warn("Cannot parse time value");
            serverInfo.setProgressInfo(new ProgressInfo(scoreTime, EnumSet.of(ProgressInfoFlags.UNKONWN)));
            return;
        }
        
        TimePeriodInfo periodInfo = new TimePeriodInfo(scoreTime);
        periodInfo.setPeriod(new Period(0, Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), 0));
        periodInfo.setProgressInfoFlags(EnumSet.of(ProgressInfoFlags.IN_PLAY));
        serverInfo.setProgressInfo(periodInfo);
    }

}
