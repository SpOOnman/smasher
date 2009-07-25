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

import java.net.InetAddress;
import java.util.List;

import eu.spoonman.smasher.serverinfo.builder.Builder;
import eu.spoonman.smasher.serverinfo.builder.BuilderFactory;

public class ServerQueryManager {

    public static AbstractQuery createServerQuery(Games game, InetAddress inetAddress, int port, List<String> args) {
        
        Builder builder = BuilderFactory.createBuilder(game);
        
        switch (game) {
            case QUAKELIVE:
                return getQuakeLiveHTTPQuery(args, builder);
                
                default:
                    return getServerQuery(inetAddress, port, builder);
        }
    }

    private static ServerQuery getServerQuery(InetAddress inetAddress, int port, Builder builder) {
        ServerQuery serverQuery = new ServerQuery(builder);

        serverQuery.setAddress(inetAddress);
        serverQuery.setPort(port);

        return serverQuery;
    }
    
    private static QuakeLiveHTTPQuery getQuakeLiveHTTPQuery(List<String> args, Builder builder) {
        QuakeLiveHTTPQuery quakeLiveHTTPQuery = new QuakeLiveHTTPQuery(builder, Integer.parseInt(args.get(0)), 4);
        
        return quakeLiveHTTPQuery;
        
    }
}
