package eu.spoonman.smasher.serverinfo;

import java.net.InetAddress;

import eu.spoonman.smasher.serverinfo.builder.Builder;
import eu.spoonman.smasher.serverinfo.builder.BuilderFactory;

public class ServerQueryManager {

    public static ServerQuery createServerQuery(Games game, InetAddress inetAddress, int port) {

        ServerQuery serverQuery = new ServerQuery();
        
        Builder builder = BuilderFactory.createBuilder(game);

        serverQuery.setAddress(inetAddress);
        serverQuery.setPort(port);
        serverQuery.setBuilder(builder);

        return serverQuery;
    }
}
