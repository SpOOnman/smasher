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

package eu.spoonman.smasher.serverinfo.builder;

import java.util.ArrayList;
import java.util.List;

import eu.spoonman.smasher.serverinfo.Mod;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.Version;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;
import eu.spoonman.smasher.serverinfo.parser.gameinfo.Quake3OSPGameInfoParser;
import eu.spoonman.smasher.serverinfo.parser.timeinfo.Quake3OSPTimeInfoParser;

/**
 * @author spoonman
 *
 */
public class Quake3ArenaBuilder extends BuilderFactory implements Builder {
    
    
    @Override
    public List<ServerInfoParser> getParserList(ServerInfo serverInfo) {
        List<ServerInfoParser> list = new ArrayList<ServerInfoParser>();
        list.add(new Quake3OSPGameInfoParser());
        list.add(new Quake3OSPTimeInfoParser());
        return list;
    }
    
    @Override
    public Version getGameVersion(ServerInfo serverInfo) {
        return null;
    }
    
    @Override
    public Mod getMod(ServerInfo serverInfo) {
        String gamename = serverInfo.getNamedAttributes().get("gamename");
        
        if (gamename.equals("osp")) {
            //TODO
            Mod mod = new Mod();
            mod.setName("OSP");
            mod.setVersion(new Version(1, 3, null, null, "a", null));
            return mod;
        } else if (gamename.equals("cpma")) {
            
        }
        
        return null;
    }
    

}
