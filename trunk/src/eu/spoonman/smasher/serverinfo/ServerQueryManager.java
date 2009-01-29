package eu.spoonman.smasher.serverinfo;

import java.net.InetAddress;

public class ServerQueryManager {

    public static ServerQuery createServerQuery(Games game, InetAddress inetAddress, int port) {

        ServerQuery serverQuery = new ServerQuery();

        serverQuery.setAddress(inetAddress);
        serverQuery.setPort(port);

        return serverQuery;
    }
}
