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
 * Information about game name, game type, versions etc.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public class GameInfo {
    
    private GameTypes gameType;
    private String rawGameType;
    
    private String map;
    
    private String hostName;
    
    private int playerCount;
    private int playerMaxCount;
    
    private boolean isPassworded;
    
    private int ping;
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("[GameInfo: %s (%s) @ %s, map %s, %d/%d, %dms, pass: %b]", gameType, rawGameType, hostName, map, playerCount, playerMaxCount, ping, isPassworded);
    }
    
    
    /**
     * @return the gameType
     */
    public GameTypes getGameType() {
        return gameType;
    }
    /**
     * @param gameType the gameType to set
     */
    public void setGameType(GameTypes gameType) {
        this.gameType = gameType;
    }
    /**
     * @return the rawGameType
     */
    public String getRawGameType() {
        return rawGameType;
    }
    /**
     * @param rawGameType the rawGameType to set
     */
    public void setRawGameType(String rawGameType) {
        this.rawGameType = rawGameType;
    }
    /**
     * @return the map
     */
    public String getMap() {
        return map;
    }
    /**
     * @param map the map to set
     */
    public void setMap(String map) {
        this.map = map;
    }
    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }
    /**
     * @param hostName the hostName to set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    /**
     * @return the playerCount
     */
    public Integer getPlayerCount() {
        return playerCount;
    }
    /**
     * @param playerCount the playerCount to set
     */
    public void setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
    }
    /**
     * @return the playerMaxCount
     */
    public Integer getPlayerMaxCount() {
        return playerMaxCount;
    }
    /**
     * @param playerMaxCount the playerMaxCount to set
     */
    public void setPlayerMaxCount(Integer playerMaxCount) {
        this.playerMaxCount = playerMaxCount;
    }
    /**
     * @return the isPassworded
     */
    public boolean isPassworded() {
        return isPassworded;
    }
    /**
     * @param isPassworded the isPassworded to set
     */
    public void setPassworded(boolean isPassworded) {
        this.isPassworded = isPassworded;
    }
    /**
     * @return the ping
     */
    public Integer getPing() {
        return ping;
    }
    /**
     * @param ping the ping to set
     */
    public void setPing(Integer ping) {
        this.ping = ping;
    }
    
    
}
