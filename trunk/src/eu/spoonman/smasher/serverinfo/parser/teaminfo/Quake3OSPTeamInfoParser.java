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

package eu.spoonman.smasher.serverinfo.parser.teaminfo;

import org.apache.log4j.Logger;

import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TeamInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;
import eu.spoonman.smasher.serverinfo.parser.AttributeNotFoundException;
import eu.spoonman.smasher.serverinfo.parser.ParserException;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;

/**
 * Team info parser for Quake 3 Arena OSP and CPMA mods.
 * 
 * @author Tomasz Kalkosińśki
 * 
 */
public class Quake3OSPTeamInfoParser implements ServerInfoParser {
    
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(Quake3OSPTeamInfoParser.class);
    
    private String attributeName;
    private String teamName;
    private TeamKey teamKey;
    
    public Quake3OSPTeamInfoParser(String attributeName, String teamName, TeamKey teamKey) {
        this.attributeName = attributeName;
        this.teamName = teamName;
        this.teamKey = teamKey;
    }

    @Override
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException {

        String value = serverInfo.getNamedAttributes().get(attributeName);
        
        if (log.isDebugEnabled()) {
            log.debug(String.format(ServerInfoParser.fieldLogFormat, attributeName, value));
        }
        
        if (value == null)
            throw new AttributeNotFoundException(attributeName);
        
        TeamInfo teamInfo = new TeamInfo(teamKey);
        
        teamInfo.setName(teamName);
        teamInfo.setScore(Integer.parseInt(value));
        
        serverInfo.getTeamInfos().add(teamInfo);
    }

}
