/**
 * 
 */
package eu.spoonman.smasher.serverinfo.parser.serverstatusinfo;

import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.ServerStatusInfo;

/**
 * @author Tomasz Kalkosiński
 *
 */
public interface ServerStatusInfoParser {
    
    public ServerStatusInfo getServerStatusInfo(ServerInfo serverInfo);

}
