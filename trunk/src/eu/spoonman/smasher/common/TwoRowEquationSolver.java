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
 * Two row equation solver.
 * A * X = B - D = K
 * 
 * @author spoonman
 *
 */
public class TwoRowEquationSolver {
    private ArrayList<Integer> X;
    private TwoRowMatrix A;
    private ArrayList<Integer> B;
    private ArrayList<Integer> D;
    
    private ArrayList<Integer> K; // = B - D
    
    private TwoRowMatrix templateMatrix;

    /**
     * @param x
     * @param b
     * @param d
     * @param templateMatrix
     */
    public TwoRowEquationSolver(ArrayList<Integer> x, ArrayList<Integer> b, ArrayList<Integer> d, TwoRowMatrix templateMatrix) {
        super();
        X = x;
        B = b;
        D = d;
        this.templateMatrix = templateMatrix;
    }
    
    private void searchRecursive(int column) {
        
        //Try 1/0 flags in this step
        A.getFirstRow().set(column, 1);
        A.getSecondRow().set(column, 0);
        
        //Check if it succeeds and eventually search deeper
        searchRecursiveCheck(column, 0);
        
        A.getFirstRow().set(column, 0);
        A.getSecondRow().set(column, 1);
        
        searchRecursiveCheck(column, 1);
        
        //Clean up when going back.
        A.getFirstRow().set(column, 0);
        A.getSecondRow().set(column, 0);

    }
    
    private void searchRecursiveCheck(int column, int row) {
        
        //Check scores
        ArrayList<Integer> scoreResult = A.multiply(B);
        
        if (scoreResult.equals(K)) {
            System.out.println("good");
        } else {
            if (scoreResult.get(row) <= K.get(row) && column + 1 < X.size())
                searchRecursive(column++);
        }
        
    }
}
