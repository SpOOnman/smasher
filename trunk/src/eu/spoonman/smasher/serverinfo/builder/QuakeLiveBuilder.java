/**
 * 
 */
package eu.spoonman.smasher.serverinfo.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.LocalDateTime;

import eu.spoonman.smasher.serverinfo.Mod;
import eu.spoonman.smasher.serverinfo.Platform;
import eu.spoonman.smasher.serverinfo.PlatformSystem;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.Version;
import eu.spoonman.smasher.serverinfo.header.Header;
import eu.spoonman.smasher.serverinfo.header.QuakeEngineHeader;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;
import eu.spoonman.smasher.serverinfo.parser.gameinfo.Quake3OSPGameInfoParser;
import eu.spoonman.smasher.serverinfo.parser.timeinfo.Quake3OSPTimeInfoParser;
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
        list.add(new Quake3OSPGameInfoParser());
        list.add(new Quake3OSPTimeInfoParser());
        return list;
    }

    @Override
    public Version getGameVersion(ServerInfo serverInfo) {
        // QuakeLive 0.1.0.214 linux-i386 Feb 4 2009 21:24:26
        String gamename = serverInfo.getNamedAttributes().get("version");
        Matcher matcher = versionPattern.matcher(gamename);

        if (matcher.matches()) {
            Version version = new Version(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)), Integer.valueOf(matcher
                    .group(3)), Integer.valueOf(matcher.group(4)), null, null);
            
            PlatformSystem.valueOf(matcher.group(5));
            Platform.valueOf(matcher.group(6));
            new LocalDateTime(matcher.group(7));
            
            return version;
        }

        return null;
    }

    @Override
    public Mod getMod(ServerInfo serverInfo) {
        String gamename = serverInfo.getNamedAttributes().get("gamename");

        if (gamename.equals("osp")) {
            // TODO
            Mod mod = new Mod();
            mod.setName("OSP");
            mod.setVersion(new Version(1, 3, null, null, "a", null));
            return mod;
        } else if (gamename.equals("cpma")) {

        }

        return null;
    }

}
