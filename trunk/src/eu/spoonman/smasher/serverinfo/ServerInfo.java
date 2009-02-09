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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ServerInfo {
	
	private ServerInfoStatus status;
	
    private String name;
    private String map;
    private String modification;
    private String version;

    private int playersCount;
    private int ping;
    private String gameName;
	
	private List<PlayerInfo> playerInfos;
    private Map<String, String> namedAttributes;
    
    private ProgressInfo progressInfo;
    private GameInfo gameInfo;
    
    public ServerInfo(ServerInfoStatus status) {
        this();
        this.status = status;
    }
    
    public ServerInfo() {
    	this.playerInfos = new ArrayList<PlayerInfo>();
		this.namedAttributes = new LinkedHashMap<String, String>();
	}
    
    
    /**
	 * @param namedAttributes the namedAttributes to set
	 */
	public void setNamedAttributes(Map<String, String> namedAttributes) {
		this.namedAttributes = namedAttributes;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder ();
        
        sb.append("status\t : " + this.status + "\n");
		
/*		for (Map.Entry<String, String> entry : this.namedAttributes.entrySet()) {
			sb.append(entry.getKey() + "\t : " + entry.getValue() + "\n");
		}
*/		
        
        sb.append(gameInfo.toString());
        sb.append("\n");
        
		sb.append(progressInfo.toString());
		sb.append("\n");
		
		for (PlayerInfo pi : this.playerInfos) {
		    sb.append("\t");
			sb.append(pi.toString());
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * @return the status
	 */
	public ServerInfoStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(ServerInfoStatus status) {
		this.status = status;
	}
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
	 * @return the modification
	 */
	public String getModification() {
		return modification;
	}
	/**
	 * @param modification the modification to set
	 */
	public void setModification(String modification) {
		this.modification = modification;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
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
	/**
	 * @return the gameName
	 */
	public String getGameName() {
		return gameName;
	}
	/**
	 * @param gameName the gameName to set
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	/**
	 * @return the playerInfos
	 */
	public List<PlayerInfo> getPlayerInfos() {
		return playerInfos;
	}
	/**
	 * @param playerInfos the playerInfos to set
	 */
	public void setPlayerInfos(ArrayList<PlayerInfo> playerInfos) {
		this.playerInfos = playerInfos;
	}
	/**
	 * @return the namedAttributes
	 */
	public Map<String, String> getNamedAttributes() {
		return namedAttributes;
	}

    /**
     * @return the progressInfo
     */
    public ProgressInfo getProgressInfo() {
        return progressInfo;
    }

    /**
     * @param progressInfo the progressInfo to set
     */
    public void setProgressInfo(ProgressInfo progressInfo) {
        this.progressInfo = progressInfo;
    }

    /**
     * @param playerInfos the playerInfos to set
     */
    public void setPlayerInfos(List<PlayerInfo> playerInfos) {
        this.playerInfos = playerInfos;
    }

    /**
     * @return the gameInfo
     */
    public GameInfo getGameInfo() {
        return gameInfo;
    }

    /**
     * @param gameInfo the gameInfo to set
     */
    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }
}
