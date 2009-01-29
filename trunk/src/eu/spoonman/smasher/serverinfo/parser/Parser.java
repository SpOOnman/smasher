/**
 * 
 */
package eu.spoonman.smasher.serverinfo.parser;

import eu.spoonman.smasher.serverinfo.ServerInfo;

/**
 * Abstract parser with parent serverinfo. I need this implementation to have
 * both serverinfo in constructor and interface implementation of derived
 * parsers.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public abstract class Parser {

    protected ServerInfo serverInfo;

    /**
     * @param serverInfo
     *            Parent serverinfo.
     */
    public Parser(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

}
