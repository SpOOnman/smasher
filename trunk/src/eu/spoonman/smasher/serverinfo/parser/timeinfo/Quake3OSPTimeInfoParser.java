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

import java.util.regex.Pattern;

import eu.spoonman.smasher.serverinfo.ProgressInfo;
import eu.spoonman.smasher.serverinfo.ProgressInfoFlags;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.parser.ParserException;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;

/**
 * ProgressInfo parser for Quake 3 Arena OSP and CPMA mods.
 * 
 * @author Tomasz Kalkosiński
 */
public class Quake3OSPTimeInfoParser implements ServerInfoParser {
    
    private static final Pattern normalTimeRegex = Pattern.compile("(OT )?([0-9]{1,2}):([0-9]{2})");         
    private static final Pattern roundTimeRegex = Pattern.compile("^Round ([0-9]{1,3})/([0-9]{1,3})");         

    @Override
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException {
        String scoreTime = serverInfo.getNamedAttributes().get("Score_Time");
        
        if (scoreTime == null)
            throw new ParserException("Field Score_Time not found for parsing");
        
        if (scoreTime.equals("Warmup") || scoreTime.equals("Waiting for Players")) {
            serverInfo.setProgressInfo(new ProgressInfo(scoreTime, ProgressInfoFlags.IN_PLAY));
            
        }
    }

}
