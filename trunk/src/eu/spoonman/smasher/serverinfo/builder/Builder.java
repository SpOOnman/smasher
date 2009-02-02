/**
 * 
 */
package eu.spoonman.smasher.serverinfo.builder;


import java.util.List;

import eu.spoonman.smasher.serverinfo.Mod;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.Version;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;

/**
 * @author spoonman
 *
 */
public interface Builder {
    
    public List<ServerInfoParser> getParserList(ServerInfo serverInfo);
    
    public Version getGameVersion(ServerInfo serverInfo);
    
    public Mod getMod(ServerInfo serverInfo);

}
