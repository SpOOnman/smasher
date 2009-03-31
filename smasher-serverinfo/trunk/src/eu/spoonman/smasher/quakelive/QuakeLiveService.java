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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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

    private final static String QUAKELIVE_XMPP_SERVER = "xmpp.quakelive.com";
    private final static int QUAKELIVE_XMPP_PORT = 5222;
    private final static String QUAKELIVE_XMPP_RESOURCE = "quakelive";

    private final static String QUAKELIVE_USER = "tomasz2k@poczta.onet.pl";
    private final static String QUAKELIVE_PASS = "";
    private final static String QUAKELIVE_PARAMETERS = "u=%s&p=%s&r=0";

    private List<String> cookies = new ArrayList<String>();

    private String getCookies() {
        StringBuilder sb = new StringBuilder();
        for (String cookie : cookies) {
            String[] split = cookie.split("; ");
            for (String splitted : split) {
                if (splitted.startsWith("path") || splitted.startsWith("HttpOnly") || splitted.startsWith("expires"))
                    continue;

                sb.append(splitted);
                sb.append("; ");
            }
        }

        return sb.toString();

    }

    private String httpQuery(String method, String urlString, String parameters, boolean useCookies) throws LoginException, IOException {
        try {

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(method);
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            if (parameters != null) {
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                connection.setRequestProperty("Content-Length", new Integer(parameters.length()).toString());

                connection.setDoOutput(true);
                connection.setReadTimeout(10000);

                OutputStream dataOut = connection.getOutputStream();
                dataOut.write(parameters.getBytes());
                dataOut.flush();
                dataOut.close();
            }

            if (useCookies) {
                connection.setRequestProperty("Cookie", getCookies());
                connection.connect();
            }

            Object responseMessage = connection.getResponseMessage();
            Map<String, List<String>> headerFields = connection.getHeaderFields();
            cookies = headerFields.get("Set-Cookie");

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

    public void login(String username, String password) throws LoginException, IOException, XMPPException {
        try {

            String parameters = String.format(QUAKELIVE_PARAMETERS, username, password);
            String content = httpQuery("POST", QUAKELIVE_URL_LOGIN_STRING, parameters, false);

            load();

        } catch (MalformedURLException e) {
            log.error("URL", e);
        } catch (ProtocolException e) {
            log.error("Protocol", e);
        }

    }

    public void load() throws IOException, LoginException, XMPPException {
        try {

            String content = httpQuery("POST", QUAKELIVE_URL_LOAD_STRING, null, true);

            // "SESSION":"75f4c0f5a1b0706b1d2ffe5a57803f57","USERNAME":"QScorebot","XAID":"2c3705b73658aeefc2363fc2e7181b4b8efc81e6","STATUS":"ACTIVE","USERID":"2943560","INFO":{"PLAYER_EMAIL":"tomasz2k@poczta.onet.pl","EULA_DATE":"27-MAR-09","JOIN_DATE":"27-MAR-09","FIRSTNAME":"Maggie","IGNORED_NOTICES":"","PLAYER_CLAN":"","COUNTRY_ABBREV":"PL","BROWSER_FILTER":""},"CVARS":{"headmodel":"ranger\/default","model":"ranger\/default","name":"QScorebot","r_inBrowserMode":"9","team_headmodel":"ranger\/default","team_model":"ranger\/default","web_botskill":"easy","web_configVersion":"4"},"BINDS":{"+":"sizeup","-":"sizedown","0x00":"+zoom","1":"weapon
            // 1","2":"weapon 2","3":"weapon 3","4":"weapon
            // 4","5":"","6":"weapon 6","7":"","8":"weapon 8","9":"weapon
            // 9","=":"sizeup","CTRL":"+movedown","F1":"vote
            // yes","F11":"screenshot","F2":"vote
            // no","F3":"readyup","MOUSE1":"+attack","MOUSE2":"+moveup","MOUSE3":"+button2","MWHEELDOWN":"weapprev","MWHEELUP":"weapnext","PAUSE":"pause","SHIFT":"weapon
            // 5","SPACE":"weapon 6","TAB":"+scores","_":"sizedown","a":"weapon
            // 7","alt":"+speed","c":"weapon
            // 2","d":"+back","e":"+forward","f":"+moveright","g":"weapon
            // 8","h":"+chat","q":"","r":"","s":"+moveleft","t":"messagemode","w":"weapon
            // 4","y":"messagemode2","z":"weapon
            // 3"},"QUEUED":"0","NEW_PLAYER":true}

             Object parsed = JSONValue.parse(content);
                        
             if (parsed == null)
             throw new
             IOException("Cannot parse QuakeLive response to JSON.");
                        
             JSONObject json = (JSONObject)parsed;
             
             xmppLogin(json.get("USERNAME").toString(), json.get("XAID").toString());
            //"SESSION":"75f4c0f5a1b0706b1d2ffe5a57803f57","USERNAME":"QScorebot","XAID":"2c3705b73658aeefc2363fc2e7181b4b8efc81e6","STATUS":"ACTIVE","USERID":"2943560","INFO":{"PLAYER_EMAIL":"tomasz2k@poczta.onet.pl","EULA_DATE":"27-MAR-09","JOIN_DATE":"27-MAR-09","FIRSTNAME":"Maggie","IGNORED_NOTICES":"","PLAYER_CLAN":"","COUNTRY_ABBREV":"PL","BROWSER_FILTER":""},"CVARS":{"headmodel":"ranger\/default","model":"ranger\/default","name":"QScorebot","r_inBrowserMode":"9","team_headmodel":"ranger\/default","team_model":"ranger\/default","web_botskill":"easy","web_configVersion":"4"},"BINDS":{"+":"sizeup","-":"sizedown","0x00":"+zoom","1":"weapon 1","2":"weapon 2","3":"weapon 3","4":"weapon 4","5":"","6":"weapon 6","7":"","8":"weapon 8","9":"weapon 9","=":"sizeup","CTRL":"+movedown","F1":"vote yes","F11":"screenshot","F2":"vote no","F3":"readyup","MOUSE1":"+attack","MOUSE2":"+moveup","MOUSE3":"+button2","MWHEELDOWN":"weapprev","MWHEELUP":"weapnext","PAUSE":"pause","SHIFT":"weapon 5","SPACE":"weapon 6","TAB":"+scores","_":"sizedown","a":"weapon 7","alt":"+speed","c":"weapon 2","d":"+back","e":"+forward","f":"+moveright","g":"weapon 8","h":"+chat","q":"","r":"","s":"+moveleft","t":"messagemode","w":"weapon 4","y":"messagemode2","z":"weapon 3"},"QUEUED":"0","NEW_PLAYER":true
        } catch (MalformedURLException e) {
            log.error("URL", e);
        } catch (ProtocolException e) {
            log.error("Protocol", e);
        }
    }

    public void xmppLogin(String user, String xaid) throws XMPPException {
        XMPPConnection connection = new XMPPConnection(QUAKELIVE_XMPP_SERVER);
        connection.connect();
        connection.login(user, xaid, QUAKELIVE_XMPP_RESOURCE);
        
        log.debug("Logged in as " + user);
        
        Roster roster = connection.getRoster();
        Collection<RosterEntry> entries = roster.getEntries();
         
        log.info("" + entries.size() + " friends:");
        for(RosterEntry r:entries) {
            log.info(String.format("RoosterEntry: %s, %s, %s, %s ", r.getName(), r.getType().toString(), r.getUser(), r.getStatus()));
        }

        PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class));

        PacketListener myListener = new PacketListener() {
            public void processPacket(Packet packet) {
                if (packet instanceof Message) {
                    Message msg = (Message) packet;
                    log.info(String.format("Received message: ", msg.toXML()));
                }
            }
        };
        // Register the listener.
        connection.addPacketListener(myListener, filter);
    }

    public static void main(String[] args) throws LoginException, IOException, XMPPException {
        QuakeLiveService qls = new QuakeLiveService();
        qls.login(QUAKELIVE_USER, QUAKELIVE_PASS);

    }

}
