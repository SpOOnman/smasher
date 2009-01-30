/**
 * 
 */
package eu.spoonman.smasher.serverinfo.parser.gameinfo;

import eu.spoonman.smasher.serverinfo.GameInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class Quake3OSPGameInfoParser implements GameInfoParser{
    
    @Override
    public GameInfo getGameInfo(ServerInfo serverInfo) {
        return new GameInfo();
    }

}
