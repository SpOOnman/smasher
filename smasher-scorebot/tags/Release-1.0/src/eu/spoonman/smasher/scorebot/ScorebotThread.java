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
package eu.spoonman.smasher.scorebot;

import java.io.IOException;

/**
 * @author Tomasz Kalkosinski
 *
 */
class ScorebotThread extends Thread {
	
	private final Scorebot scorebot;
	
	ScorebotThread(Scorebot scorebot) {
		super("scbot " + scorebot.getId());
		this.scorebot = scorebot;
	}
	
	@Override
	public void run(){
		super.run();
		scorebot.start();
	}

	public Scorebot getScorebot() {
		return scorebot;
	}
}
