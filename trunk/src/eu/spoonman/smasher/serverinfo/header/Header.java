package eu.spoonman.smasher.serverinfo.header;

/**
 * @author spoonman
 *
 */
public interface Header {
    
    public byte[] getQueryHeader();
    
    public byte[] getResponseHeader();

}
