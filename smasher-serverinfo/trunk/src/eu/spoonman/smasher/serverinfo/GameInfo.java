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
    private String mapFullName;
    
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
        return String.format("[GameInfo: %s (%s) @ %s, map %s (%s), %d/%d, %dms, pass: %b]", gameType, rawGameType, hostName, map, mapFullName, playerCount, playerMaxCount, ping, isPassworded);
    }
    
    
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gameType == null) ? 0 : gameType.hashCode());
        result = prime * result + ((hostName == null) ? 0 : hostName.hashCode());
        result = prime * result + (isPassworded ? 1231 : 1237);
        result = prime * result + ((map == null) ? 0 : map.hashCode());
        result = prime * result + ((mapFullName == null) ? 0 : mapFullName.hashCode());
        result = prime * result + ping;
        result = prime * result + playerCount;
        result = prime * result + playerMaxCount;
        result = prime * result + ((rawGameType == null) ? 0 : rawGameType.hashCode());
        return result;
    }




    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof GameInfo))
            return false;
        GameInfo other = (GameInfo) obj;
        if (gameType == null) {
            if (other.gameType != null)
                return false;
        } else if (!gameType.equals(other.gameType))
            return false;
        if (hostName == null) {
            if (other.hostName != null)
                return false;
        } else if (!hostName.equals(other.hostName))
            return false;
        if (isPassworded != other.isPassworded)
            return false;
        if (map == null) {
            if (other.map != null)
                return false;
        } else if (!map.equals(other.map))
            return false;
        if (mapFullName == null) {
            if (other.mapFullName != null)
                return false;
        } else if (!mapFullName.equals(other.mapFullName))
            return false;
        if (ping != other.ping)
            return false;
        if (playerCount != other.playerCount)
            return false;
        if (playerMaxCount != other.playerMaxCount)
            return false;
        if (rawGameType == null) {
            if (other.rawGameType != null)
                return false;
        } else if (!rawGameType.equals(other.rawGameType))
            return false;
        return true;
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
    
    public String getMapFullName() {
        return mapFullName;
    }

    public void setMapFullName(String mapFullName) {
        this.mapFullName = mapFullName;
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
