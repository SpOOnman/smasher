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

import org.joda.time.LocalTime;

/**
 * Standard time information with time set in minutes, seconds and miliseconds.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public class LocalTimeInfo extends ProgressInfo {

    private LocalTime localTime;

    /**
     * 
     */
    public LocalTimeInfo(String rawText) {
        super(rawText);
    }

    /**
     * @return the localTime
     */
    public LocalTime getLocalTime() {
        return localTime;
    }

    /**
     * @param localTime
     *            the localTime to set
     */
    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

}
