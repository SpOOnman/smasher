/**
 * 
 */
package eu.spoonman.smasher.serverinfo.parser;

import eu.spoonman.smasher.serverinfo.ServerInfo;

/**
 * @author spoonman
 *
 */
public interface Parser {
    
    public void parseIntoServerInfo(ServerInfo serverInfo) throws ParserException;

}
