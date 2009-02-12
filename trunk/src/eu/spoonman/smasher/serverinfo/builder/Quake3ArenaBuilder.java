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
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import eu.spoonman.smasher.serverinfo.PlayerFlags;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;
import eu.spoonman.smasher.serverinfo.Version;
import eu.spoonman.smasher.serverinfo.header.Header;
import eu.spoonman.smasher.serverinfo.header.QuakeEngineHeader;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;
import eu.spoonman.smasher.serverinfo.parser.gameinfo.Quake3OSPGameInfoParser;
import eu.spoonman.smasher.serverinfo.parser.playerinfo.NumbersPlayerInfoParser;
import eu.spoonman.smasher.serverinfo.parser.playerinfo.Quake3OSPPlayerInfoParser;
import eu.spoonman.smasher.serverinfo.parser.teaminfo.Quake3OSPTeamInfoParser;
import eu.spoonman.smasher.serverinfo.parser.timeinfo.Quake3OSPTimeInfoParser;
import eu.spoonman.smasher.serverinfo.reader.QuakeEngineReader;
import eu.spoonman.smasher.serverinfo.reader.Reader;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class Quake3ArenaBuilder extends BuilderFactory implements Builder {
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(Quake3ArenaBuilder.class);
    
    private static final Pattern versionPattern = Pattern
        .compile("(\\w+)\\s([\\.\\w]+)\\s(\\w+)\\-(\\w+)\\s(.+)");
    
    @Override
    public Header getHeader() {
        return new QuakeEngineHeader();
    }
    
    @Override
    public Reader getReader() {
        return new QuakeEngineReader();
    }
    
    
    @Override
    public List<ServerInfoParser> getParserList(ServerInfo serverInfo) {
        List<ServerInfoParser> list = new ArrayList<ServerInfoParser>();
        list.add(new Quake3OSPPlayerInfoParser());
        list.add(new NumbersPlayerInfoParser("Players_Red", -1, TeamKey.RED_TEAM, EnumSet.of(PlayerFlags.IN_PLAY), " "));
        list.add(new NumbersPlayerInfoParser("Players_Blue", -1, TeamKey.BLUE_TEAM, EnumSet.of(PlayerFlags.IN_PLAY), " "));
        list.add(new Quake3OSPTeamInfoParser("Score_Red", "Red", TeamKey.RED_TEAM));
        list.add(new Quake3OSPTeamInfoParser("Score_Blue", "Blue", TeamKey.BLUE_TEAM));
        list.add(new Quake3OSPTimeInfoParser());
        list.add(new Quake3OSPGameInfoParser());
        return list;
    }
    
    @Override
    public Version getGameVersion(ServerInfo serverInfo) {
        //CNQ3 1.42 linux-i386 Apr 22 2008
        Matcher matcher = versionPattern.matcher(serverInfo.getNamedAttributes().get("version"));
        
        if (!matcher.matches())
            return null;
        
        Version version = Version.tryParse(matcher.group(2));
        
        if (version == null) {
            if (log.isInfoEnabled()) {
                log.info("Cannot parse version string. Creating new version."); //$NON-NLS-1$
            }
            version = new Version(null);
        }
        
        version.setName(matcher.group(1));
        version.setFullName("Quake 3 Arena");
        
        //Sometimes it's 'Mar  8 2006' instead of 'Mar 8 2006'
        version.setBuildTime(Version.getAmericanDateTimeFormatter().parseDateTime(matcher.group(5).replace("  ", " ")));
        
        return version;
    }
    
    @Override
    public Version getModVersion(ServerInfo serverInfo) {
        String gamename = serverInfo.getNamedAttributes().get("gamename");
        
        if (gamename.equals("osp")) {
            //TODO
            //mod.setVersion(new Version(1, 3, null, null, "a", null));
            return new Version("osp");
        } else if (gamename.equals("cpma")) {
            
            Version version = Version.tryParse(serverInfo.getNamedAttributes().get("gameversion"));
            
            if (version == null) {
                if (log.isInfoEnabled()) {
                    log.info("Cannot parse version string. Creating new version."); //$NON-NLS-1$
                }
                version = new Version(null);
            }
            
            
            version.setName("cpma");
            version.setFullName("Challenge Pro Mode Arena");
            
            version.setBuildTime(Version.getAmericanDateTimeFormatter().parseDateTime(serverInfo.getNamedAttributes().get("gamedate")));
            
            return version;
        }
        
        return null;
    }
}
