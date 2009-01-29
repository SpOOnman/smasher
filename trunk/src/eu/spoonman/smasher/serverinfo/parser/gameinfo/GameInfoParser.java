package eu.spoonman.smasher.serverinfo.parser.gameinfo;

import eu.spoonman.smasher.serverinfo.GameInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public interface GameInfoParser {
    
    public GameInfo getGameInfo(ServerInfo serverInfo);

}
