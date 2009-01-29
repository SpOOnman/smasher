/**
 * 
 */
package eu.spoonman.smasher.serverinfo.builder;

import eu.spoonman.smasher.serverinfo.Mod;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.Version;
import eu.spoonman.smasher.serverinfo.parser.gameinfo.GameInfoParser;
import eu.spoonman.smasher.serverinfo.parser.timeinfo.Quake3OSPTimeInfoParser;
import eu.spoonman.smasher.serverinfo.parser.timeinfo.TimeInfoParser;

/**
 * @author spoonman
 *
 */
public class Quake3ArenaBuilder implements Builder {

    @Override
    public GameInfoParser getGameInfoParser(ServerInfo serverInfo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TimeInfoParser getTimeInfoParser(ServerInfo serverInfo) {
        return new Quake3OSPTimeInfoParser();
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
