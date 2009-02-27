/**
 * 
 */
package eu.spoonman.smasher.serverinfo.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.LocalDateTime;

import eu.spoonman.smasher.serverinfo.Platform;
import eu.spoonman.smasher.serverinfo.PlatformSystem;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.Version;
import eu.spoonman.smasher.serverinfo.header.Header;
import eu.spoonman.smasher.serverinfo.header.QuakeEngineHeader;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;
import eu.spoonman.smasher.serverinfo.parser.gameinfo.Quake3OSPGameInfoParser;
import eu.spoonman.smasher.serverinfo.parser.playerinfo.QuakeLivePlayerInfoParser;
import eu.spoonman.smasher.serverinfo.parser.timeinfo.Quake3OSPTimeInfoParser;
import eu.spoonman.smasher.serverinfo.parser.timeinfo.QuakeLiveTimeInfoParser;
import eu.spoonman.smasher.serverinfo.persister.ServerInfoPersister;
import eu.spoonman.smasher.serverinfo.reader.QuakeLiveReader;
import eu.spoonman.smasher.serverinfo.reader.Reader;

/**
 * @author spoonman
 * 
 */
public class QuakeLiveBuilder extends BuilderFactory implements Builder {

    private static final Pattern versionPattern = Pattern
            .compile("QuakeLive\\s+(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)\\s(\\w+)\\-(\\w+)\\s(.*)");

    @Override
    public Header getHeader() {
        return new QuakeEngineHeader();
    }

    @Override
    public Reader getReader() {
        return new QuakeLiveReader();
    }

    @Override
    public List<ServerInfoParser> getParserList(ServerInfo serverInfo) {
        List<ServerInfoParser> list = new ArrayList<ServerInfoParser>();
        list.add(new QuakeLiveTimeInfoParser());
        list.add(new QuakeLivePlayerInfoParser());
        return list;
    }
    
    @Override
    public List<ServerInfoPersister> getPersisterList(ServerInfo serverInfo) {
        List<ServerInfoPersister> list = new ArrayList<ServerInfoPersister>();
        return list;
    }

    @Override
    public Version getGameVersion(ServerInfo serverInfo) {
        // QuakeLive 0.1.0.214 linux-i386 Feb 4 2009 21:24:26
        String gamename = serverInfo.getNamedAttributes().get("version");
        Matcher matcher = versionPattern.matcher(gamename);

        if (matcher.matches()) {
            Version version = new Version("QuakeLive", Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)), Integer.valueOf(matcher
                    .group(3)), Integer.valueOf(matcher.group(4)), null, null);
            
            //PlatformSystem.valueOf(matcher.group(5));
            //Platform.valueOf(matcher.group(6));
            //new LocalDateTime(matcher.group(7));
            
            return version;
        }

        return null;
    }
    
    /* (non-Javadoc)
     * @see eu.spoonman.smasher.serverinfo.builder.Builder#getModVersion(eu.spoonman.smasher.serverinfo.ServerInfo)
     */
    @Override
    public Version getModVersion(ServerInfo serverInfo) {
        // TODO Auto-generated method stub
        return null;
    }

    

}
