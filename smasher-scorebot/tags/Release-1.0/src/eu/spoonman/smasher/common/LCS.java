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
import java.util.Comparator;
import java.util.List;

/**
 * Least Common Subsequence algorithm.
 * 
 * This algorithm was based on LCS article published in english Wikipedia:
 * http://en.wikipedia.org/wiki/Longest_common_subsequence_problem
 * @author Tomasz Kalkosiński
 * 
 */
public class LCS<T> {
	
	private List<T> left;
	private List<T> right;
	private Comparator<T> comparator;
	
	private int[][] lcsMatrix;
	
	public LCS(List<T> left, List<T> right, Comparator<T> comparator) {
		this.left = left;
		this.right = right;
		this.comparator = comparator;
	}
	
	public List<DiffData<T>> getLCSPairs() {
		createLCSMatrix();
		return readPairs();
	}
	
	private void createLCSMatrix() {
		lcsMatrix = new int[left.size() + 1][right.size() + 1];
		
		for (int i = 0; i <= left.size(); i++)
            lcsMatrix [i][0] = 0;

        for (int j = 0; j <= right.size(); j++)
            lcsMatrix [0][j] = 0;

        for (int i = 1; i <= left.size(); i++)
            for (int j = 1; j <= right.size(); j++)
                if (comparator.compare(left.get(i-1), right.get(j-1)) == 0)
                    lcsMatrix [i][j] = lcsMatrix [i-1][j-1] + 1;
                else
                    lcsMatrix [i][j] = Math.max(lcsMatrix [i][j-1], lcsMatrix[i-1][j]);
	}
	
	private List<DiffData<T>> readPairs() {
		List<DiffData<T>> pairs = new ArrayList<DiffData<T>>();

        //Starting from bottom right corner
        int i = left.size ();
        int j = right.size ();

        while (i > 0 || j > 0)
        {
            if (i > 0 && j > 0 && comparator.compare(left.get(i - 1), right.get(j - 1)) == 0)
            {
            	pairs.add(0, new DiffData<T>(left.get(i - 1), right.get(j - 1)));
                i--;
                j--;
            }
            else
            {
                if (j > 0 && (i == 0 || lcsMatrix [i][j-1] >= lcsMatrix [i-1][j]))
                {
                    //New object on the right
                	pairs.add(0, new DiffData<T>(null, right.get(j - 1)));
                    j--;
                }
                else if (i > 0 && (j == 0 || lcsMatrix [i][j-1] < lcsMatrix [i-1][j]))
                {
                    //New object on the left
                	pairs.add(0, new DiffData<T>(left.get(i - 1), null));
                    i--;
                }
            }
        }
        
        return pairs;
	}
	
	

}
