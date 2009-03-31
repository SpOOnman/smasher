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

package eu.spoonman.smasher.quakelive;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;

/**
 * @author Tomasz Kalkosiński QuakeLive Service to retrieve information.
 */
public class QuakeLiveService {

    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(QuakeLiveService.class);

    private final static String QUAKELIVE_URL_LOGIN_STRING = "http://www.quakelive.com/user/login";
    private final static String QUAKELIVE_URL_LOAD_STRING = "http://www.quakelive.com/user/load";
    private final static String QUAKELIVE_USER = "tomasz2k@poczta.onet.pl";
    private final static String QUAKELIVE_PASS = "";
    private final static String QUAKELIVE_PARAMETERS = "u=%s&p=%s&r=0";
    
    private List<String> cookies = null;
    
    private String httpQuery(String urlString, String parameters, boolean useCookies) throws LoginException, IOException {
        try {
            
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", new Integer(parameters.length()).toString());
            if (useCookies)
                connection.addRequestProperty("Cookie", cookies.toString().substring(1, cookies.toString().length() - 2));
            
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);
            
            System.out.println(connection.getHeaderFields());
 
            OutputStream dataOut = connection.getOutputStream();
            dataOut.write(parameters.getBytes());
            dataOut.flush();
            dataOut.close();
            
            Object responseMessage = connection.getResponseMessage();
            Map<String, List<String>> headerFields = connection.getHeaderFields();
            cookies = headerFields.get("Set-Cookie");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line);
            String content = sb.toString();

            log.debug(String.format("Response %s: %s", responseMessage, content));
            
            return content;
        } catch (MalformedURLException e) {
            log.error("URL", e);
        } catch (ProtocolException e) {
            log.error("Protocol", e);
        }
        
        return null;
        
    }

    public void login(String username, String password) throws LoginException, IOException {
        try {
            
            String parameters = String.format(QUAKELIVE_PARAMETERS, username, password);
            String content = httpQuery(QUAKELIVE_URL_LOGIN_STRING, parameters, false);
            
            load(username, password, cookies);
            
        } catch (MalformedURLException e) {
            log.error("URL", e);
        } catch (ProtocolException e) {
            log.error("Protocol", e);
        }

    }
    
    public void load(String username, String password, List<String> cookies) throws IOException, LoginException {
        try {
            
            String parameters = String.format(QUAKELIVE_PARAMETERS, username, password);
            String content = httpQuery(QUAKELIVE_URL_LOAD_STRING, parameters, true);
            

//            Object parsed = JSONValue.parse(responseMessage);
//            
//            if (parsed == null)
//                throw new IOException("Cannot parse QuakeLive response to JSON.");
//            
//            JSONObject json = (JSONObject)parsed;
        } catch (MalformedURLException e) {
            log.error("URL", e);
        } catch (ProtocolException e) {
            log.error("Protocol", e);
        }
    }
    
    public static void main(String[] args) throws LoginException, IOException {
        QuakeLiveService qls = new QuakeLiveService();
        qls.login(QUAKELIVE_USER, QUAKELIVE_PASS);
        
    }

}
