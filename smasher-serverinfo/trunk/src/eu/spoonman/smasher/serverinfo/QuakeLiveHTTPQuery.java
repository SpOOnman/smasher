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

import eu.spoonman.smasher.quakelive.QuakeLiveHTTPService;
import eu.spoonman.smasher.serverinfo.builder.Builder;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class QuakeLiveHTTPQuery {
    
    private QuakeLiveHTTPService httpService;
    
    public QuakeLiveHTTPQuery(Builder builder) {
        httpService = new QuakeLiveHTTPService();
    }
    
    /*
    ServerInfo query(byte[] data) {
        ServerInfo serverInfo = new ServerInfo();

        try {

            validateResponse(data);

            reader.readData(serverInfo, data);

            if (!alreadyBuilded)
                buildParsers(serverInfo);

            setGameAndMod(serverInfo);
            parseData(serverInfo);

            serverInfo.setStatus(ServerInfoStatus.OK);

        } catch (NotValidResponseException e) {
            serverInfo.setStatus(ServerInfoStatus.NOT_VALID_RESPONSE);
        } catch (ReaderException e) {
            serverInfo.setStatus(ServerInfoStatus.NOT_VALID_RESPONSE);
        }

        return serverInfo;
    }
    */

}
