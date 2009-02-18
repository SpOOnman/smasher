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

import org.joda.time.Period;

/**
 * Standard time information with time set in minutes, seconds and milliseconds.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public class TimePeriodInfo extends ProgressInfo {

    private Period period;

    /**
     * 
     */
    public TimePeriodInfo(String rawText) {
        super(rawText);
    }
    

    /**
     * @param rawText
     * @param localTime
     */
    public TimePeriodInfo(String rawText, Period period) {
        this(rawText);
        this.period = period;
    }
    
    @Override
    public String toString() {
        return String.format("[TimePeriodInfo: %s, (%s)]", period, this.getProgressInfoFlags().toString());
    }

    /**
     * @return the period
     */
    public Period getPeriod() {
        return period;
    }


    /**
     * @param period the period to set
     */
    public void setPeriod(Period period) {
        this.period = period;
    }

}
