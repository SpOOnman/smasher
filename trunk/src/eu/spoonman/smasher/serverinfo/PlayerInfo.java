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

public class PlayerInfo
{
    private String name;
    private int score;
    private int ping;
    private ArrayList<String> rawAttributes;
    private EnumSet<PlayerFlags> playerFlags;
    private TeamKey teamKey;
    
    @Override
    public String toString() {
        return String.format("[PlayerInfo: %s, P: %d ms, S: %d, %s", name, ping, score, playerFlags);
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
}
