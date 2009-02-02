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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ServerQueryManagerTest {

    @Test
    public void testCreateServerQuery() {
        ServerQuery serverQuery = null;

        serverQuery = ServerQueryManager.createServerQuery(Games.QUAKE3ARENA, "194.187.43.245", 27971);

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
