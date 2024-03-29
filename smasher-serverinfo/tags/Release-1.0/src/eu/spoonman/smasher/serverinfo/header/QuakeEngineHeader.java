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
 * @author Tomasz Kalkosiński
 */
public abstract class QuakeEngineHeader implements Header{

    private byte[] queryHeader;
    private byte[] responseHeader;
    
    public QuakeEngineHeader(String queryString, String responseString) {
        
        try {
            this.queryHeader = queryString.getBytes("ISO8859-1");
            this.responseHeader = responseString.getBytes("ISO8859-1");
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
