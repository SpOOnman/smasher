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
public enum ProgressInfoFlags {
    
    UNKONWN,
    
    WARMUP,
    COUNTDOWN,
    
    IN_PLAY,
    OVERTIME,
    SUDDEN_DEATH,
    
    TIMELIMIT,
    FRAGLIMIT,
    CAPLIMIT,
    ROUNDLIMIT,
    
    TIMEOUT,
    TIMEOUT_RED,
    TIMEOUT_BLUE;
    
    public static ProgressInfoFlags[] getLimitFlags() {
        ProgressInfoFlags[] limitFlags = { TIMELIMIT, FRAGLIMIT, CAPLIMIT, ROUNDLIMIT } ;
        return limitFlags ;
    }
}
