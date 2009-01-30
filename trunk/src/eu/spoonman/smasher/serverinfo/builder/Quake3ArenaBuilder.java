/**
 * 
 */
package eu.spoonman.smasher.serverinfo.builder;

import java.util.ArrayList;
import java.util.List;

import eu.spoonman.smasher.serverinfo.Mod;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.Version;
import eu.spoonman.smasher.serverinfo.parser.PlayerInfoParser;
import eu.spoonman.smasher.serverinfo.parser.ServerInfoParser;
import eu.spoonman.smasher.serverinfo.parser.gameinfo.Quake3OSPGameInfoParser;
import eu.spoonman.smasher.serverinfo.parser.timeinfo.Quake3OSPTimeInfoParser;

/**
 * @author spoonman
 *
 */
public class Quake3ArenaBuilder implements Builder {
    
    /* (non-Javadoc)
     * @see eu.spoonman.smasher.serverinfo.builder.Builder#getPlayerInfoParsers(eu.spoonman.smasher.serverinfo.ServerInfo)
     */
    @Override
    public List<PlayerInfoParser> getPlayerInfoParsers(ServerInfo serverInfo) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /* (non-Javadoc)
     * @see eu.spoonman.smasher.serverinfo.builder.Builder#getServerInfoParsers(eu.spoonman.smasher.serverinfo.ServerInfo)
     */
    @Override
    public List<ServerInfoParser> getServerInfoParsers(ServerInfo serverInfo) {
        List<ServerInfoParser> list = new ArrayList<ServerInfoParser>();
        list.add(new Quake3OSPGameInfoParser());
        list.add(new Quake3OSPTimeInfoParser());
        return list;
    }
    
    @Override
    public Version getGameVersion(ServerInfo serverInfo) {
        return null;
    }
    
    @Override
    public Mod getMod(ServerInfo serverInfo) {
        String gamename = serverInfo.getNamedAttributes().get("gamename");
        
        if (gamename.equals("osp")) {
            //TODO
            Mod mod = new Mod();
            mod.setName("OSP");
            mod.setVersion(new Version(1, 3, null, null, "a", null));
            return mod;
        } else if (gamename.equals("cpma")) {
            
        }
        
        return null;
    }
    

}
