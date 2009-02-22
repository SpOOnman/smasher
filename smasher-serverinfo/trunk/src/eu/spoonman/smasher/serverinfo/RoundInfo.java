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
public class RoundInfo extends ProgressInfo {
    
    private int roundNumber;
    private int roundLimit;
    
    /**
     * @param rawText
     * @param progressInfoFlags
     */
    public RoundInfo(String rawText, int roundNumber, int roundLimit, ProgressInfoFlags progressInfoFlags) {
        super(rawText, progressInfoFlags);
        this.roundNumber = roundNumber;
        this.roundLimit = roundLimit;
    }
    
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + roundLimit;
        result = prime * result + roundNumber;
        return result;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof RoundInfo))
            return false;
        RoundInfo other = (RoundInfo) obj;
        if (roundLimit != other.roundLimit)
            return false;
        if (roundNumber != other.roundNumber)
            return false;
        return true;
    }



    /**
     * @return the roundNumber
     */
    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * @param roundNumber the roundNumber to set
     */
    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    /**
     * @return the roundLimit
     */
    public int getRoundLimit() {
        return roundLimit;
    }

    /**
     * @param roundLimit the roundLimit to set
     */
    public void setRoundLimit(int roundLimit) {
        this.roundLimit = roundLimit;
    }
    
    
    
    

}
