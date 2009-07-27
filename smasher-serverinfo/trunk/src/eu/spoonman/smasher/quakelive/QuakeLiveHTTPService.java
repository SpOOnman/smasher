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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.XMPPException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * @author Tomasz Kalkosiński
 * QuakeLive HTTP Service to retrieve information. It emulates internet browser to get match and players information.
 */
public class QuakeLiveHTTPService {

    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(QuakeLiveHTTPService.class);

    private final static String QUAKELIVE_URL_LOGIN_STRING = "http://www.quakelive.com/user/login";
    private final static String QUAKELIVE_URL_STATS_FORMAT = "http://www.quakelive.com/stats/matchdetails/%d/%s";
    private final static String QUAKELIVE_URL_MATCH_FORMAT = "http://www.quakelive.com/home/matchdetails/%d";

    private final static String JSON_ERROR_CODE = "ECODE";
    private final static String JSON_ERROR_MSG = "MSG";
    private final static String JSON_NOT_LOGGED_IN = "-1";
    
    private final static String QUAKELIVE_PARAMETERS = "u=%s&p=%s&r=0";
    
    private final static Map<Integer, String> gametypeAddressMap;
    
    static {
        gametypeAddressMap = new HashMap<Integer, String>();
        gametypeAddressMap.put(1, "Tourney");
        gametypeAddressMap.put(3, "TDM");
        gametypeAddressMap.put(5, "CTF");
    }

    private String cookies = null;

    private void setCookies(List<String> cookieList) {
        StringBuilder sb = new StringBuilder();
        for (String cookie : cookieList) {
            String[] split = cookie.split("; ");
            for (String splitted : split) {
                if (splitted.startsWith("path") || splitted.startsWith("HttpOnly") || splitted.startsWith("expires"))
                    continue;

                sb.append(splitted);
                sb.append("; ");
            }
        }
        
        this.cookies = sb.toString();
    }
    
    JSONObject jsonQuery(String method, String urlString, String parameters, boolean reLogin) throws IOException {
        String content = httpQuery(method, urlString, parameters);
        log.debug(content);
        
        if (content == null || content.trim().length() == 0 || content.equals("[]"))
            return null;
        
        Object parsed = JSONValue.parse(content);
        
        if (parsed != null) {
            JSONObject json = (JSONObject)parsed;
            
            Object error = json.get(JSON_ERROR_CODE);
            if (error != null && Integer.parseInt(error.toString()) < 0) {
                log.error(String.format("JSON response error: %s : %s", error.toString(), json.get(JSON_ERROR_MSG)));
                if (error.toString().equals(JSON_NOT_LOGGED_IN) && reLogin) {
                    log.debug("Logging in");
                    login("", "");
                    return jsonQuery(method, urlString, parameters, false);
                }
            }
            
            log.debug(json.toJSONString());
            
            return json;
        }
        
        return null;
    }

    String httpQuery(String method, String urlString, String parameters) throws IOException {
        try {
            
            log.debug(String.format("Querying %s with parameters %s", urlString, parameters));

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(method);
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            //Send out parameters if given
            if (parameters != null && parameters.length() > 0) {
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                connection.setRequestProperty("Content-Length", new Integer(parameters.length()).toString());

                connection.setDoOutput(true);
                connection.setReadTimeout(10000);

                OutputStream dataOut = connection.getOutputStream();
                dataOut.write(parameters.getBytes());
                dataOut.flush();
                dataOut.close();
            }

            //Use cookies if they are set.
            if (cookies != null && cookies.length() > 0) {
                connection.setRequestProperty("Cookie", this.cookies);
                connection.connect();
            }

            Object responseMessage = connection.getResponseMessage();
            Map<String, List<String>> headerFields = connection.getHeaderFields();
            List<String> cookieList = headerFields.get("Set-Cookie");
            if (cookieList != null && cookieList.size() > 0)
                setCookies(cookieList);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line);
            String content = sb.toString();

            log.debug(String.format("Header: %s", headerFields));
            log.debug(String.format("Response %s: %s", responseMessage, content));

            return content;
        } catch (MalformedURLException e) {
            log.error("URL", e);
        } catch (ProtocolException e) {
            log.error("Protocol", e);
        }

        return null;

    }

    public void login(String username, String password) throws IOException {
        try {

            String parameters = String.format(QUAKELIVE_PARAMETERS, username, password);
            httpQuery("POST", QUAKELIVE_URL_LOGIN_STRING, parameters);

        } catch (MalformedURLException e) {
            log.error("URL", e);
        } catch (ProtocolException e) {
            log.error("Protocol", e);
        }
    }
    
    public JSONObject getMatchDetails(Integer matchId) {
        String url = String.format(QUAKELIVE_URL_MATCH_FORMAT, matchId);
        try {
            return jsonQuery("GET", url, null, true);
        } catch (IOException e) {
            log.error(e);
        }
        
        return null;
        
    }
    
    public Integer searchForPlayer(String name) {
        return null;
    }
    
    public JSONObject getStatsDetails(Integer matchId, Integer gametype) {
        String url = String.format(QUAKELIVE_URL_STATS_FORMAT, matchId, gametypeAddressMap.get(gametype));
        
        try {
            return jsonQuery("GET", url, null, true);
        } catch (IOException e) {
            log.error(e);
        }
        
        return null;
        
    }

    public static void main(String[] args) throws LoginException, IOException, XMPPException {
        QuakeLiveHTTPService qls = new QuakeLiveHTTPService();
        //qls.getMatchDetails(9865953, 1);
        //qls.getMatchDetails(5793181, 4);
        qls.login("tomasz2k@poczta.onet.pl", "");
        //qls.httpQuery("GET", "http://www.quakelive.com/stats/overall", null);
        qls.httpQuery("GET", "http://www.quakelive.com/stats/recordstats/838199", null);
        qls.httpQuery("GET", "http://www.quakelive.com/stats/playerdetails/838199", null);

    }

}
