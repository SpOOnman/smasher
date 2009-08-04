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

package eu.spoonman.smasher.common;

import eu.spoonman.smasher.scorebot.Scorebot;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class DiffData<T> {
	
	private Scorebot scorebot;
	private T first;
	private T second;
	
	public DiffData() {
	}
	
	public DiffData(T first, T second) {
		this(null, first, second);
	}

	public DiffData(Scorebot scorebot, T first, T second) {
		this.scorebot = scorebot;
		this.first = first;
		this.second = second;
	}
	
	@Override
	public String toString() {
		return String.format("scorebot: %s, first: %s, second: %s", scorebot, first, second);
	}

	public T getFirst() {
		return first;
	}

	public void setFirst(T first) {
		this.first = first;
	}

	public T getSecond() {
		return second;
	}

	public void setSecond(T second) {
		this.second = second;
	}

	public Scorebot getScorebot() {
		return scorebot;
	}

	public void setScorebot(Scorebot scorebot) {
		this.scorebot = scorebot;
	}
}
