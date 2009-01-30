package eu.spoonman.smasher.serverinfo.parser.serverstatusinfo;

import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.ServerStatusInfo;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class QuakeEngineServerStatusInfoParser implements ServerStatusInfoParser{
    
    @Override
    public ServerStatusInfo getServerStatusInfo(ServerInfo serverInfo) {
        return new ServerStatusInfo();
    }

}
