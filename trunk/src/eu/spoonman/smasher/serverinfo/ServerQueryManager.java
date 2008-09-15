package eu.spoonman.smasher.serverinfo;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerQueryManager {

    private static final String path = "src/eu/spoonman/smasher/serverinfo/";

    public static ServerQuery CreateServerQuery(String gameName, String address, int port) throws CannotLoadPropertyException,
            RequiredPropertyNotFoundException, UnknownHostException {

        Properties properties = null;

        try {
            properties = LoadProperties(path + gameName + ".properties");
        } catch (IOException e) {
            throw new CannotLoadPropertyException(e);
        }

        ServerQuery serverQuery = new ServerQuery();

        InetAddress iaddress = InetAddress.getByName(address);

        serverQuery.setAddress(iaddress);
        serverQuery.setPort(port);

        serverQuery.setQueryHeader(getStringProperty(properties, Fields.QUERYHEADER));
        serverQuery.setResponseHeader(getStringProperty(properties, Fields.RESPONSEHEADER));
        serverQuery.setServerRegex(getStringProperty(properties, Fields.SERVERSECTION, Fields.SERVERREGEX));
        serverQuery.setOrdinalKey(getIntProperty(properties, Fields.SERVERSECTION, Fields.ORDINALKEY));
        serverQuery.setOrdinalValue(getIntProperty(properties, Fields.SERVERSECTION, Fields.ORDINALVALUE));

        serverQuery.setPlayerRegex(getStringProperty(properties, Fields.PLAYERSECTION, Fields.PLAYERREGEX));
        serverQuery.setOrdinalPing(getIntProperty(properties, Fields.PLAYERSECTION, Fields.ORDINALPING, "-1"));
        serverQuery.setOrdinalName(getIntProperty(properties, Fields.PLAYERSECTION, Fields.ORDINALNAME, "-1"));
        serverQuery.setOrdinalClan(getIntProperty(properties, Fields.PLAYERSECTION, Fields.ORDINALCLAN, "-1"));
        serverQuery.setOrdinalScore(getIntProperty(properties, Fields.PLAYERSECTION, Fields.ORDINALSCORE, "-1"));

        return serverQuery;
    }

    private static String getProperty(Properties properties, String key, String defaultValue, boolean isRequired)
            throws RequiredPropertyNotFoundException {

        String value = properties.getProperty(key, defaultValue);
        if (value == null && isRequired)
            throw new RequiredPropertyNotFoundException(key);

        return value;
    }

    private static String getStringProperty(Properties properties, Fields fieldone, Fields fieldtwo, String defaultValue)
            throws RequiredPropertyNotFoundException {

        return getProperty(properties, fieldone.toString() + "." + fieldtwo.toString(), defaultValue, false);
    }

    private static String getStringProperty(Properties properties, Fields fieldone) throws RequiredPropertyNotFoundException {
        return getProperty(properties, fieldone.toString(), null, true);
    }

    private static String getStringProperty(Properties properties, Fields fieldone, Fields fieldtwo)
            throws RequiredPropertyNotFoundException {
        return getProperty(properties, fieldone.toString() + "." + fieldtwo.toString(), null, true);
    }

    private static int getIntProperty(Properties properties, Fields fieldone, Fields fieldtwo, String defaultValue)
            throws RequiredPropertyNotFoundException {

        return Integer.parseInt(getProperty(properties, fieldone.toString() + "." + fieldtwo.toString(), defaultValue, false));
    }

    private static int getIntProperty(Properties properties, Fields fieldone, Fields fieldtwo) throws RequiredPropertyNotFoundException {
        return Integer.parseInt(getProperty(properties, fieldone.toString() + "." + fieldtwo.toString(), null, true));
    }

    private static Properties LoadProperties(String fileName) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(fileName));

        return properties;
    }
}
