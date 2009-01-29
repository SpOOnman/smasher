/**
 * 
 */
package eu.spoonman.smasher.serverinfo.reader;

import eu.spoonman.smasher.serverinfo.ServerInfo;

/**
 * Reader to read from bytes array to serverinfo.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public interface Reader {

    /**
     * Parse server data into serverinfo field map and players map.
     * 
     * @param serverInfo
     * @param bytes
     */
    public void readData(ServerInfo serverInfo, byte[] bytes);

}
