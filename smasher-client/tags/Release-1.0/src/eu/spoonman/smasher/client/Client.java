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

import java.util.List;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class Client {
	
	private final String name;
	protected Colors colors;
	
	public Client(String name) {
		this.name = name;
		colors = new ConsoleColors();
	}
	
	public void print(List<String> lines) {
		if (lines == null || lines.size() == 0)
			return;
		
		for (String line : lines) {
			System.out.println(line);
		}
	}

	public String getName() {
		return name;
	}
	
	public Colors getColors() {
		return colors;
	}
}
