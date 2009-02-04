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

import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.StandardTimeInfo;
import eu.spoonman.smasher.serverinfo.TimeInfo;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;
import eu.spoonman.smasher.serverinfo.parser.ParserException;

/**
 * ProgressInfo parser for Quake 3 Arena OSP and CPMA mods.
 * 
 * @author Tomasz Kalkosiński
 */
public class Quake3OSPTimeInfoParser implements ServerInfoParser {

    @Override
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException {
        String scoreTime = serverInfo.getNamedAttributes().get("Score_Time");
        
        if (scoreTime == null)
            throw new ParserException("Field Score_Time not found for parsing");
        
        if (scoreTime.equals("Warmup") || scoreTime.equals("Waiting for Players")) {
            serverInfo.setProgressStatus
            
        }
    }

}
