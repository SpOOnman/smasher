package eu.spoonman.smasher.serverinfo.header;

import java.io.UnsupportedEncodingException;

/**
 * General Quake engine based games header configuration.
 * @author Tomasz Kalkosiński
 */
public class QuakeEngineHeader implements Header{

    private final static String queryHeaderString = "\u00ff\u00ff\u00ff\u00ffgetstatus";
    private final static String responseHeaderString = "\u00ff\u00ff\u00ff\u00ffstatusResponse";
    
    private byte[] queryHeader;
    private byte[] responseHeader;
    
    public QuakeEngineHeader() {
        
        try {
            this.queryHeader = QuakeEngineHeader.queryHeaderString.getBytes("ISO8859-1");
            this.responseHeader = QuakeEngineHeader.responseHeaderString.getBytes("ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            //ISO8859-1 is always supported
        }
    }

    /**
     * @return the queryHeader
     */
    @Override
    public byte[] getQueryHeader() {
        return queryHeader;
    }

    /**
     * @return the responseHeader
     */
    @Override
    public byte[] getResponseHeader() {
        return responseHeader;
    }
}
