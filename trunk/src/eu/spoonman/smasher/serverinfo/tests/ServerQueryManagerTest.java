package eu.spoonman.smasher.serverinfo.tests;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.Test;

import eu.spoonman.smasher.serverinfo.*;

public class ServerQueryManagerTest {
    
    @Test
    public void testCreateServerQuery() {
        ServerQuery serverQuery = null;
        
        try {
            serverQuery = ServerQueryManager.CreateServerQuery("quake3arena", "194.187.43.245", 27971);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CannotLoadPropertyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RequiredPropertyNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        assertNotNull(serverQuery);
        
        ServerInfo serverInfo = serverQuery.Query();
        System.out.println(serverInfo);
        
        assertNotNull(serverInfo);

    }

}
