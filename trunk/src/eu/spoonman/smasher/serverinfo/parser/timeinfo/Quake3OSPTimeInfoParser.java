package eu.spoonman.smasher.serverinfo.parser.timeinfo;

import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.StandardTimeInfo;
import eu.spoonman.smasher.serverinfo.TimeInfo;
import eu.spoonman.smasher.serverinfo.parser.Parser;

/**
 * TimeInfo parser for Quake 3 Arena OSP and CPMA mods.
 * 
 * @author Tomasz Kalkosiński
 */
public class Quake3OSPTimeInfoParser extends Parser implements TimeInfoParser {

    public Quake3OSPTimeInfoParser(ServerInfo serverInfo) {
        super(serverInfo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * eu.spoonman.smasher.serverinfo.parser.timeinfo.TimeInfoParser#getTime()
     */
    @Override
    public TimeInfo getTime() {
        return new StandardTimeInfo();
    }

}
