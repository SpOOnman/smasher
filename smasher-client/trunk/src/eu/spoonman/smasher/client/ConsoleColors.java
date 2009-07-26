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

package eu.spoonman.smasher.client;

/**
 * @author Tomasz Kalkosińśki
 *
 */
public class ConsoleColors extends Colors{
	
	private final String UNIX_FORMAT = "\033[%sm";
	
	public String getBold() {
		return String.format(UNIX_FORMAT, "1");
	}
	
	public String getReset() {
		return String.format(UNIX_FORMAT, "0");
	}
	
	public String getRed() {
		return String.format(UNIX_FORMAT, "31");
	}
	
	public String getBlue() {
		return String.format(UNIX_FORMAT, "34");
	}

}
