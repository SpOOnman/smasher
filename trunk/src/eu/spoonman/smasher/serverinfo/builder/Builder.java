/**
 * 
 */
package eu.spoonman.smasher.serverinfo.builder;


import eu.spoonman.smasher.serverinfo.Mod;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.Version;
import eu.spoonman.smasher.serverinfo.parser.gameinfo.GameInfoParser;
import eu.spoonman.smasher.serverinfo.parser.timeinfo.TimeInfoParser;

/**
 * @author spoonman
 *
 */
public interface Builder {
    
    public GameInfoParser getGameInfoParser(ServerInfo serverInfo);
    
    public TimeInfoParser getTimeInfoParser(ServerInfo serverInfo);
    
    public Version getGameVersion(ServerInfo serverInfo);
    
    public Mod getMod(ServerInfo serverInfo);

}
