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
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class PlayerInfo
{
    private String name;
    private int score;
    private int ping;
    private ArrayList<String> rawAttributes;
    private Map<String, String> namedAttributes;
    private EnumSet<PlayerFlags> playerFlags;
    private TeamKey teamKey;
    
    public PlayerInfo() {
        rawAttributes = new ArrayList<String>();
        namedAttributes = new HashMap<String, String>();
        playerFlags = EnumSet.noneOf(PlayerFlags.class);
    }
    
    /**
     * @param name
     * @param score
     * @param ping
     */
    public PlayerInfo(String name, int score, int ping) {
        this();
        this.name = name;
        this.score = score;
        this.ping = ping;
    }

    @Override
    public String toString() {
        return String.format("[PlayerInfo: %s, P: %d ms, S: %d, T: %s, %s (%s)]", name, ping, score, teamKey, namedAttributes, playerFlags);
    }
    
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((namedAttributes == null) ? 0 : namedAttributes.hashCode());
        result = prime * result + ping;
        result = prime * result + ((playerFlags == null) ? 0 : playerFlags.hashCode());
        result = prime * result + ((rawAttributes == null) ? 0 : rawAttributes.hashCode());
        result = prime * result + score;
        result = prime * result + ((teamKey == null) ? 0 : teamKey.hashCode());
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
        if (!(obj instanceof PlayerInfo))
            return false;
        PlayerInfo other = (PlayerInfo) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (namedAttributes == null) {
            if (other.namedAttributes != null)
                return false;
        } else if (!namedAttributes.equals(other.namedAttributes))
            return false;
        if (ping != other.ping)
            return false;
        if (playerFlags == null) {
            if (other.playerFlags != null)
                return false;
        } else if (!playerFlags.equals(other.playerFlags))
            return false;
        if (rawAttributes == null) {
            if (other.rawAttributes != null)
                return false;
        } else if (!rawAttributes.equals(other.rawAttributes))
            return false;
        if (score != other.score)
            return false;
        if (teamKey == null) {
            if (other.teamKey != null)
                return false;
        } else if (!teamKey.equals(other.teamKey))
            return false;
        return true;
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
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
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
	 * @return the rawAttributes
	 */
	public ArrayList<String> getRawAttributes() {
		return rawAttributes;
	}

	/**
	 * @param rawAttributes the rawAttributes to set
	 */
	public void setRawAttributes(ArrayList<String> rawAttributes) {
		this.rawAttributes = rawAttributes;
	}

    /**
     * @return the playerFlags
     */
    public EnumSet<PlayerFlags> getPlayerFlags() {
        return playerFlags;
    }

    /**
     * @param playerFlags the playerFlags to set
     */
    public void setPlayerFlags(EnumSet<PlayerFlags> playerFlags) {
        this.playerFlags = playerFlags;
    }

    /**
     * @return the teamKey
     */
    public TeamKey getTeamKey() {
        return teamKey;
    }

    /**
     * @param teamKey the teamKey to set
     */
    public void setTeamKey(TeamKey teamKey) {
        this.teamKey = teamKey;
    }

    /**
     * @return the namedAttributes
     */
    public Map<String, String> getNamedAttributes() {
        return namedAttributes;
    }

    /**
     * @param namedAttributes the namedAttributes to set
     */
    public void setNamedAttributes(Map<String, String> namedAttributes) {
        this.namedAttributes = namedAttributes;
    }
    
    
}
