/**
 * 
 */
package eu.spoonman.smasher.serverinfo.parser;

import eu.spoonman.smasher.serverinfo.ServerInfo;

/**
 * @author spoonman
 *
 */
public interface ServerInfoParser {
    
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException;

}
