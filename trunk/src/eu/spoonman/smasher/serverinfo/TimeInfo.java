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
 * Simple time info with raw information and status flag.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public class TimeInfo {

    private TimeInfoFlags timeInfoFlags;

    private String rawText;

    /**
     * 
     */
    public TimeInfo(String rawText) {
        this.rawText = rawText;
    }

    /**
     * @return the timeInfoFlags
     */
    public TimeInfoFlags getTimeInfoFlags() {
        return timeInfoFlags;
    }

    /**
     * @param timeInfoFlags
     *            the timeInfoFlags to set
     */
    public void setTimeInfoFlags(TimeInfoFlags timeInfoFlags) {
        this.timeInfoFlags = timeInfoFlags;
    }

}
