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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.packet.DiscoverItems;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * @author Tomasz Kalkosiński
 * QuakeLive Service to retrieve information. It emulates internet browser to get match and players information.
 */
public class QuakeLiveService {

    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(QuakeLiveService.class);

    private final static String QUAKELIVE_URL_LOGIN_STRING = "http://www.quakelive.com/user/login";
    private final static String QUAKELIVE_URL_LOAD_STRING = "http://www.quakelive.com/user/load";

    private final static String QUAKELIVE_XMPP_SERVER = "xmpp.quakelive.com";
    private final static String QUAKELIVE_XMPP_RESOURCE = "quakelive";
    private final static String QUAKELIVE_XMPP_USERNAME = "USERNAME";
    private final static String QUAKELIVE_XMPP_XAID = "XAID";

    private final static String QUAKELIVE_USER = "tomasz2k@poczta.onet.pl";
    private final static String QUAKELIVE_PASS = "";
    private final static String QUAKELIVE_PARAMETERS = "u=%s&p=%s&r=0";
    
    //http://www.quakelive.com/#profile/matches/Napastnik/5793181/CTF

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

    private String httpQuery(String method, String urlString, String parameters) throws LoginException, IOException {
        try {

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

    public void login(String username, String password) throws LoginException, IOException, XMPPException {
        try {

            String parameters = String.format(QUAKELIVE_PARAMETERS, username, password);
            String content = httpQuery("POST", QUAKELIVE_URL_LOGIN_STRING, parameters);

            load();

        } catch (MalformedURLException e) {
            log.error("URL", e);
        } catch (ProtocolException e) {
            log.error("Protocol", e);
        }

    }

    public void load() throws IOException, LoginException, XMPPException {
        try {

            String content = httpQuery("POST", QUAKELIVE_URL_LOAD_STRING, null);

            Object parsed = JSONValue.parse(content);
                        
            if (parsed == null)
                 throw new IOException("Cannot parse QuakeLive response to JSON.");
                        
             //"SESSION":"75f4c0f5a1b0706b1d2ffe5a57803f57","USERNAME":"QScorebot","XAID":"2c3705b73658aeefc2363fc2e7181b4b8efc81e6","STATUS":"ACTIVE","USERID":"2943560","INFO":{"PLAYER_EMAIL":"tomasz2k@poczta.onet.pl","EULA_DATE":"27-MAR-09","JOIN_DATE":"27-MAR-09","FIRSTNAME":"Maggie","IGNORED_NOTICES":"","PLAYER_CLAN":"","COUNTRY_ABBREV":"PL","BROWSER_FILTER":""},"CVARS":{"headmodel":"ranger\/default","model":"ranger\/default","name":"QScorebot","r_inBrowserMode":"9","team_headmodel":"ranger\/default","team_model":"ranger\/default","web_botskill":"easy","web_configVersion":"4"},"BINDS":{"+":"sizeup","-":"sizedown","0x00":"+zoom","1":"weapon 1","2":"weapon 2","3":"weapon 3","4":"weapon 4","5":"","6":"weapon 6","7":"","8":"weapon 8","9":"weapon 9","=":"sizeup","CTRL":"+movedown","F1":"vote yes","F11":"screenshot","F2":"vote no","F3":"readyup","MOUSE1":"+attack","MOUSE2":"+moveup","MOUSE3":"+button2","MWHEELDOWN":"weapprev","MWHEELUP":"weapnext","PAUSE":"pause","SHIFT":"weapon 5","SPACE":"weapon 6","TAB":"+scores","_":"sizedown","a":"weapon 7","alt":"+speed","c":"weapon 2","d":"+back","e":"+forward","f":"+moveright","g":"weapon 8","h":"+chat","q":"","r":"","s":"+moveleft","t":"messagemode","w":"weapon 4","y":"messagemode2","z":"weapon 3"},"QUEUED":"0","NEW_PLAYER":true
             JSONObject json = (JSONObject)parsed;
             
             Object username = json.get(QUAKELIVE_XMPP_USERNAME);
             Object xaid = json.get(QUAKELIVE_XMPP_XAID);
             
             if (username == null || xaid == null)
                 throw new LoginException(String.format("Cannot login to XMPP - field %s or %s not found in response.", QUAKELIVE_XMPP_USERNAME, QUAKELIVE_XMPP_XAID));
             
             xmppLogin(username.toString(), xaid.toString());
             
        } catch (MalformedURLException e) {
            log.error("URL", e);
        } catch (ProtocolException e) {
            log.error("Protocol", e);
        }
    }
    
    private void getPresence(Roster roster, String user) {
        log.info("Looking for presence of " + user);
        Presence presence = roster.getPresence(String.format("%s", user));
        log.info(presence.toXML());
        
        presence = roster.getPresence(String.format("%s@xmpp.quakelive.com", user));
        log.info(presence.toXML());
        
        presence = roster.getPresence(String.format("%s@xmpp.quakelive.com/quakelive", user));
        log.info(presence.toXML());
        
    }

    public void xmppLogin(String user, String xaid) throws XMPPException {
        XMPPConnection.DEBUG_ENABLED = true;
        XMPPConnection connection = new XMPPConnection(QUAKELIVE_XMPP_SERVER);
        connection.connect();
        connection.login(user, xaid, QUAKELIVE_XMPP_RESOURCE);
        
        log.debug("Logged in as " + user);
        
        PacketListener myListener = new PacketListener() {
            public void processPacket(Packet packet) {
                //if (packet instanceof Message) {
                    //Message msg = (Message) packet;
                    log.info(String.format("Received message: ", packet.toXML()));
                //}
            }
        };
        
        // Register the listener.
        connection.addPacketListener(myListener, null);
        System.out.println("biore rooster");
        Roster roster = connection.getRoster();
        
        /*roster.addRosterListener(new RosterListener() {
            public void entriesAdded(Collection<String> addresses) {
                log.info("Entries added: " + addresses.toString());
            }
            public void entriesDeleted(Collection<String> addresses) {
                log.info("Entries deleted: " + addresses.toString());
            }
            public void entriesUpdated(Collection<String> addresses) {
                log.info("Entries updated: " + addresses.toString());
            }
            public void presenceChanged(Presence presence) {
                log.info("Presence changed: " + presence.getFrom() + " " + presence.toXML());
            }
        });*/
        
        
        Collection<RosterEntry> entries = roster.getEntries();
         
        System.out.println("przed rooster");
        
        log.info("" + entries.size() + " friends:");
        for(RosterEntry r:entries) {
            log.info(String.format("RoosterEntry: %s, %s, %s, %s, %s ", r.getName(), r.getType().toString(), r.getUser(), r.getStatus(), r.getGroups()));
        }
        
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Po sleep");
        
        //getPresence(roster, "serek");
        getPresence(roster, "razorsl");
        getPresence(roster, "QScorebot");
        //getPresence(roster, "DeeDoubleU");
        //getPresence(roster, "AlexMax");
        
        // Obtain the ServiceDiscoveryManager associated with my XMPPConnection
        ServiceDiscoveryManager discoManager = ServiceDiscoveryManager.getInstanceFor(connection);
        
        // Get the items of a given XMPP entity
        // This example gets the items associated with online catalog service
        DiscoverItems discoItems = discoManager.discoverItems("xmpp.quakelive.com");

        // Get the discovered items of the queried XMPP entity
        Iterator it = discoItems.getItems();
        // Display the items of the remote XMPP entity
        while (it.hasNext()) {
            DiscoverItems.Item item = (DiscoverItems.Item) it.next();
            System.out.println(item.getEntityID());
            System.out.println(item.getNode());
            System.out.println(item.getName());
        }

        
    }

    public static void main(String[] args) throws LoginException, IOException, XMPPException {
        QuakeLiveService qls = new QuakeLiveService();
        qls.login(QUAKELIVE_USER, QUAKELIVE_PASS);

    }

}
