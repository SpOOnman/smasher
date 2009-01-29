package eu.spoonman.smasher.serverinfo.parser.timeinfo;

import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.StandardTimeInfo;
import eu.spoonman.smasher.serverinfo.TimeInfo;

/**
 * TimeInfo parser for Quake 3 Arena OSP and CPMA mods.
 * 
 * @author Tomasz Kalkosiński
 */
public class Quake3OSPTimeInfoParser implements TimeInfoParser {

    @Override
    public TimeInfo getTimeInfo(ServerInfo serverInfo) {
        return new StandardTimeInfo("asd");
    }

}
