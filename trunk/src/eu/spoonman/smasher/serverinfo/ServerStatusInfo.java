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

/**
 * @author Tomasz Kalkosiński
 *
 */
public class ServerStatusInfo {
    
    private String name;
    private int playersCount;
    private int playersMax;
    private int ping;
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the playersCount
     */
    public int getPlayersCount() {
        return playersCount;
    }
    /**
     * @param playersCount the playersCount to set
     */
    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }
    /**
     * @return the playersMax
     */
    public int getPlayersMax() {
        return playersMax;
    }
    /**
     * @param playersMax the playersMax to set
     */
    public void setPlayersMax(int playersMax) {
        this.playersMax = playersMax;
    }
    /**
     * @return the ping
     */
    public int getPing() {
        return ping;
    }
    /**
     * @param ping the ping to set
     */
    public void setPing(int ping) {
        this.ping = ping;
    }
    
    

}
