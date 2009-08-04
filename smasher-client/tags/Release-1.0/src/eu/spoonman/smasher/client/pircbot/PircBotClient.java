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

package eu.spoonman.smasher.client.pircbot;

import java.util.List;

import eu.spoonman.smasher.client.Client;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class PircBotClient extends Client {
	
	SmasherPircBot parent;
	String channelName;

	public PircBotClient(SmasherPircBot parent, String channelName) {
		super(String.format("PIRCBOT-%s", channelName));
		
		this.parent = parent;
		this.channelName = channelName;
		this.colors = new PircBotColors();
	}
	
	@Override
	public void print(List<String> lines) {
		for (String line : lines) {
			parent.sendMessage(channelName, line);
		}
	}
	
	

}
