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

package eu.spoonman.smasher.serverinfo.parser.playerinfo;

import java.util.EnumSet;
import java.util.Scanner;

import org.apache.log4j.Logger;

import eu.spoonman.smasher.serverinfo.PlayerFlags;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;
import eu.spoonman.smasher.serverinfo.parser.AttributeNotFoundException;
import eu.spoonman.smasher.serverinfo.parser.ParserException;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;

/**
 * Parser gets player numbers from attribute and assigns these players to team.
 * 
 * @author Tomasz Kalkosińśki
 * 
 */
public class NumbersPlayerInfoParser implements ServerInfoParser {
    
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(NumbersPlayerInfoParser.class);
    
    private String attributeName;
    private int playerOffset;
    private TeamKey teamKey;
    private EnumSet<PlayerFlags> playerFlags;
    private String delimitier;
    
    
    
    /**
     * @param playerFlags 
     * 
     */
    public NumbersPlayerInfoParser(String attributeName, int playerOffset, TeamKey teamKey, EnumSet<PlayerFlags> playerFlags, String delimitier) {
        this.attributeName = attributeName;
        this.playerOffset = playerOffset;
        this.teamKey = teamKey;
        this.playerFlags = playerFlags;
        this.delimitier = delimitier;
    }

    @Override
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException {

        //Map returns null either if key is mapped to null or there is no key at all.
        if (!(serverInfo.getNamedAttributes().containsKey(attributeName)))
            throw new AttributeNotFoundException(attributeName);

        String value = serverInfo.getNamedAttributes().get(attributeName);
        
        if (log.isDebugEnabled()) {
            log.debug(String.format(ServerInfoParser.fieldLogFormat, attributeName, value));
        }
        
        if (value == null) {
            log.debug("No players found.");
            return;
        }
        
        Scanner scanner = new Scanner(value).useDelimiter(delimitier);
        while (scanner.hasNextInt()) {
            int nr = scanner.nextInt();
            PlayerInfo playerInfo = serverInfo.getPlayerInfos().get(nr + playerOffset);
            
            if (playerInfo == null)
                throw new ParserException("Cannot find player info number " + (nr + playerOffset) );
            
            if (log.isDebugEnabled()) {
                log.debug(String.format("Setting teamKey %s, adding flags %s for playerInfo %d (%s)", teamKey, playerFlags, nr + playerOffset, playerInfo.getName()));
            }
            
            playerInfo.setTeamKey(teamKey);
            playerInfo.getPlayerFlags().addAll(playerFlags);
        }
    }

}
