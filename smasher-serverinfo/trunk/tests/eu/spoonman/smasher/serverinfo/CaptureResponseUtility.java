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

package eu.spoonman.smasher.serverinfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class CaptureResponseUtility {

    private static String QL_IP_PL = "79.141.60.101";
    private static String QL_IP_DE = "94.76.229.6";
    private static String QL_IP_XX = "91.198.152.225";

    private static String QL_OLD1 = "92.48.121.75";
    private static String QL_OLD2 = "85.25.86.234";

    public static void main(String[] args) throws IOException {
        InetAddress address = InetAddress.getByName(QL_IP_PL); 

        for (int port = 27000; port < 27011; port++) {

            try {
                query(address, port);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private static void query(InetAddress address, int port) throws IOException {
        ServerQuery serverQuery = ServerQueryManager.createServerQuery(Games.QUAKELIVE, address, port);

        String captured = captureResponse(serverQuery);
        System.out.println(captured);
    }

    public static String captureResponse(ServerQuery serverQuery) throws IOException {
        byte[] bytes = serverQuery.queryBytes();
        return toJavaString(bytes);
    }

    public static String toJavaString(byte[] bytes) {
        try {
            String text = new String(bytes, "ISO8859-1");

            StringBuilder sb = new StringBuilder();

            for (char c : text.toCharArray()) {
                if (c == '\n')
                    sb.append("\\n");
                else if (c == '\r')
                    sb.append("\\r");
                else if (c < ' ' || c > '}')
                    sb.append("\\u").append(String.format("%04x", (int) c));
                else if (c == '\\' || c == '"')
                    sb.append("\\").append(c);
                else
                    sb.append(c);
            }

            return sb.toString();

        } catch (UnsupportedEncodingException e) {
            return null;
        }

    }

}
