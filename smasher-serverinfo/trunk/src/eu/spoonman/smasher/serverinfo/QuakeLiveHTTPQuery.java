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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.json.simple.JSONObject;

import eu.spoonman.smasher.quakelive.QuakeLiveHTTPService;
import eu.spoonman.smasher.serverinfo.builder.Builder;
import eu.spoonman.smasher.serverinfo.parser.ParserException;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class QuakeLiveHTTPQuery extends AbstractQuery {

    private static final Logger log = Logger.getLogger(QuakeLiveHTTPQuery.class);

    private int matchId = -1;
    private String argument;

    private List<ServerInfoParser> parserList;
    
    private Builder builder;

    private QuakeLiveHTTPService httpService;
    
    private boolean alreadyBuilded;
    
    private String username;
    private String password;

    public QuakeLiveHTTPQuery(Builder builder, String argument) {
        this.builder = builder;
        httpService = new QuakeLiveHTTPService();
        this.argument = argument;
        
        alreadyBuilded = false;
    }
    
    private void login() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("quakelive.properties"));
            username = (String) properties.get("username");
            password = (String) properties.get("password");
            
            httpService.login(username, password);
            
        } catch (IOException e) {
            log.error(e);
        }
    }
    
    private void buildParsers(ServerInfo serverInfo) {
        parserList = builder.getParserList(serverInfo);
        alreadyBuilded = true;
    }
    
    private boolean ensureMatchId() {
        
        if (argument == null)
            return false;
        
        try {
            matchId = Integer.parseInt(argument);
            return true;
        } catch (NumberFormatException e) {
            log.warn("Cannot parse match id " + argument);
        }
        
        Integer id = httpService.searchForPlayer(argument);
        if (id == null) {
            log.warn("Cannot find a player" + argument);
            return false;
        }
        
        matchId = id;
        
        log.info(String.format("Player %s found playing in a game %d", argument, matchId));
        
        return true;
            
    }

    @Override
    public ServerInfo query() {
        ServerInfo serverInfo = new ServerInfo();
        try {
            
            if (!alreadyBuilded) {
                buildParsers(serverInfo);
                login();
                
                if (!ensureMatchId()) {
                    serverInfo.setStatus(ServerInfoStatus.FATAL_RESPONSE);
                    serverInfo.setMessage(String.format("Player %s not found playing QuakeLive" , argument));
                }
            }
            
            JSONObject json = httpService.getMatchDetails(matchId);
            
            if (json == null) {
                serverInfo.setStatus(ServerInfoStatus.FATAL_RESPONSE);
                serverInfo.setMessage(String.format("Match %d is unknown or has already finished.", matchId));
            } else {
                serverInfo.setJson(json);
                
                parseData(serverInfo);
                
                serverInfo.setStatus(ServerInfoStatus.OK);
            }
            
        } catch (Exception e) {
            log.error(e);
        }
        
        return serverInfo;
    }
    
    @Override
    byte[] queryBytes() throws IOException {
        throw new IOException("Not implemented");
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
    
    @Override
    public Games getGame() {
        return builder.getGame();
    }
    
    @Override
    public String getIdentification() {
        return Integer.toString(matchId);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + matchId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof QuakeLiveHTTPQuery))
            return false;
        QuakeLiveHTTPQuery other = (QuakeLiveHTTPQuery) obj;
        if (matchId != other.matchId)
            return false;
        return true;
    }
}
