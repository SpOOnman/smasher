/**
 * 
 */
package eu.spoonman.smasher.serverinfo.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import eu.spoonman.smasher.serverinfo.Games;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.Version;
import eu.spoonman.smasher.serverinfo.header.Header;
import eu.spoonman.smasher.serverinfo.header.Quake2EngineHeader;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;
import eu.spoonman.smasher.serverinfo.parser.gameinfo.QuakeEngineGameInfoParser;
import eu.spoonman.smasher.serverinfo.parser.playerinfo.Quake3OSPPlayerInfoParser;
import eu.spoonman.smasher.serverinfo.parser.timeinfo.Quake2TDMTimeInfoParser;
import eu.spoonman.smasher.serverinfo.reader.QuakeEngineReader;
import eu.spoonman.smasher.serverinfo.reader.Reader;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class Quake2Builder extends BuilderFactory implements Builder {

    private static final Pattern versionPattern = Pattern
            .compile("(\\w+)\\s+([\\.\\w]+)\\s(\\w+)\\-(\\w+)\\s(.+)");
    
    @Override
    public Games getGame() {
        return Games.QUAKE2;
    }

    @Override
    public Header getHeader() {
        return new Quake2EngineHeader();
    }

    @Override
    public Reader getReader() {
        return new QuakeEngineReader();
    }

    @Override
    public List<ServerInfoParser> getParserList(ServerInfo serverInfo) {
        List<ServerInfoParser> list = new ArrayList<ServerInfoParser>();
        list.add(new Quake2TDMTimeInfoParser());
        list.add(new Quake3OSPPlayerInfoParser()); //it's the same
        list.add(new QuakeEngineGameInfoParser("maxclients", "hostname", QuakeEngineGameInfoParser.KeyMapname, "needpass"));
        return list;
    }
    
    @Override
    public Version getGameVersion(ServerInfo serverInfo) {
        // QuakeLive 0.1.0.214 linux-i386 Feb 4 2009 21:24:26
        /*String gamename = serverInfo.getNamedAttributes().get("version");
        Matcher matcher = versionPattern.matcher(gamename);

        if (matcher.matches()) {
            
            Version version = new Version(matcher.group(1));
            version.setFullName("QuakeLive");
            version.tryParseVersion(matcher.group(2));
            version.tryParseAmericanDateTime(matcher.group(5));
            
            return version;
        }*/

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
