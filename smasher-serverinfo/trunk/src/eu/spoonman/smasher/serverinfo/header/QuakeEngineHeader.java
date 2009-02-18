/*
 * This file is part of Smasher.
 * Copyright 2008, 2009 Tomasz 'SpOOnman' Kalkosiński <spoonman@op.pl>
 * 
 * Smasher is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Smasher is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Smasher.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.spoonman.smasher.serverinfo.header;

import java.io.UnsupportedEncodingException;

/**
 * General Quake engine based games header configuration.
 * 
 * Source of information:
 * {@linkplain ftp://ftp.idsoftware.com/idstuff/quake3/docs/server.txt}
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
