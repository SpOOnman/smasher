package eu.spoonman.smasher.serverinfo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.UnknownHostException;

import org.junit.Test;

public class ServerQueryManagerTest {
    
    @Test
    public void testCreateServerQuery() {
        ServerQuery serverQuery = null;
        
        try {
            serverQuery = ServerQueryManager.CreateServerQuery(Games.QUAKE3ARENA, "194.187.43.245", 27971);
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
        
        ServerInfo serverInfo = serverQuery.query();
        System.out.println(serverInfo);
        
        assertNotNull(serverInfo);

    }
    
    @Test
    public void creationTest() {
        for (Games game : Games.values()) {
            try {
                @SuppressWarnings("unused")
                ServerQuery serverQuery = ServerQueryManager.CreateServerQuery(game, "127.0.0.1", 20000);
                assertNotNull(serverQuery);
            } catch (Exception e) {
                e.printStackTrace();
                fail(game.toString());
            }
        }
    }

}
