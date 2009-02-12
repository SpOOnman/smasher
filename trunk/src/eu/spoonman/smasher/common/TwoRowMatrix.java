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

import java.util.ArrayList;

/**
 * Simple two row matrix with few convenience methods.
 * 
 * @author Tomasz Kalkosińśki
 *
 */
public class TwoRowMatrix {
    
    private ArrayList<Integer> firstRow;
    private ArrayList<Integer> secondRow;
    
    /**
     * 
     */
    public TwoRowMatrix(int size, int value) {
        firstRow = new ArrayList<Integer> (size);
        secondRow = new ArrayList<Integer> (size);
        
        for (int i = 0 ; i < size ; i++) {
            firstRow.add(value);
            secondRow.add(value);
        }
    }
}
