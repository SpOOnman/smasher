/**
 * 
 */
package eu.spoonman.smasher.serverinfo.parser.playerinfo;

import java.util.List;

import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;

/**
 * @author Tomasz Kalkosiński
 *
 */
public interface PlayerInfoParser {
    
    public List<PlayerInfo> getPlayerInfo(PlayerInfo playerInfo, ServerInfo serverInfo);

}
