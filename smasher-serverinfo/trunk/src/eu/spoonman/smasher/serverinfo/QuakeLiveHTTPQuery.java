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

import java.util.List;

import org.json.simple.JSONObject;

import eu.spoonman.smasher.quakelive.QuakeLiveHTTPService;
import eu.spoonman.smasher.serverinfo.builder.Builder;
import eu.spoonman.smasher.serverinfo.parser.ParserException;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class QuakeLiveHTTPQuery {

    private static final Logger log = Logger.getLogger(QuakeLiveHTTPQuery.class);

    private int matchId;
    private int gametype;

    private List<ServerInfoParser> parserList;
    
    private Builder builder;

    private QuakeLiveHTTPService httpService;
    
    private boolean alreadyBuilded;

    public QuakeLiveHTTPQuery(Builder builder, int matchId, int gametype) {
        this.builder = builder;
        httpService = new QuakeLiveHTTPService();
        this.matchId = matchId;
        this.gametype = gametype;
        
        alreadyBuilded = false;
    }
    
    private void buildParsers(ServerInfo serverInfo) {
        parserList = builder.getParserList(serverInfo);
        alreadyBuilded = true;
    }

    public ServerInfo query() {
        ServerInfo serverInfo = new ServerInfo();
        try {
            
            if (!alreadyBuilded)
                buildParsers(serverInfo);
            
            JSONObject json = httpService.getMatchDetails(matchId, gametype);
            serverInfo.setJson(json);
            
            parseData(serverInfo);
            
            serverInfo.setStatus(ServerInfoStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serverInfo;
    }

    private void parseData(ServerInfo serverInfo) {
        for (ServerInfoParser parser : parserList) {
            try {
                parser.parseIntoServerInfo(serverInfo);
            } catch (ParserException e) {
                log.error("Parsing error", e);
            }
        }
    }
}
