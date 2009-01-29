package eu.spoonman.smasher.serverinfo.parser.timeinfo;

import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TimeInfo;

/**
 * Interface for time parsers.
 * @author Tomasz Kalkosiński
 */
public interface TimeInfoParser {
    
    public TimeInfo getTimeInfo(ServerInfo serverInfo);

}
