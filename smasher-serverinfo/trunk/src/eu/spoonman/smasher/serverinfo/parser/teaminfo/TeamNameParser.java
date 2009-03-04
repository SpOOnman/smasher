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
import eu.spoonman.smasher.serverinfo.parser.ParserException;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class TeamNameParser implements ServerInfoParser {
    
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(TeamNameParser.class);

    protected final TeamKey teamKey;
    protected final String attributeName;
    
    public TeamNameParser(TeamKey teamKey, String attributeName) {
        this.teamKey = teamKey;
        this.attributeName = attributeName;
    }
    
    @Override
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException {
        TeamInfo teamInfo = parseTeam(serverInfo);
        
        if (teamInfo != null)
            serverInfo.getTeamInfos().put(teamKey, teamInfo);
    }
    
    public TeamInfo parseTeam(ServerInfo serverInfo) {
        
        String value = serverInfo.getNamedAttributes().get(attributeName);
        
        if (log.isDebugEnabled()) {
            log.debug(String.format(ServerInfoParser.fieldLogFormat, attributeName, value));
        }
        
        if (value == null || value.length() == 0) {
            log.warn("Value is empty. Not setting up.");
            return null;
        }
        
        return new TeamInfo(teamKey, value, 0);
    }
}
