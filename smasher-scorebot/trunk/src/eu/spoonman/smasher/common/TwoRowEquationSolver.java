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

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Two row equation solver.
 * A * X = B - D = K
 * 
 * @author spoonman
 *
 */
public class TwoRowEquationSolver {
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(TwoRowEquationSolver.class);

    private ArrayList<Integer> X;
    private TwoRowMatrix A;
    private ArrayList<Integer> B;
    private ArrayList<Integer> D;
    
    private ArrayList<Integer> K; // = B - D
    
    private ArrayList<Integer> scoreResult;
    
    private TwoRowMatrix templateMatrix;
    
    private ArrayList<TwoRowMatrix> solutions;
    
    private int checkedCombinationCount = 0;
    
    /**
     * 
     */
    public TwoRowEquationSolver() {
    }

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
    
    public ArrayList<TwoRowMatrix> solve() {
        
        prepareSearch();
        
        searchRecursive(0);
        
        if (log.isDebugEnabled()) {
            int allPossible = (int) Math.pow(2, X.size());
            log.debug(String.format("Checked %d combinations out of %d possible: %d%%", checkedCombinationCount, allPossible, ((checkedCombinationCount * 100/allPossible))));
        }
        
        return solutions;
    }

    private void prepareSearch() {
        solutions = new ArrayList<TwoRowMatrix>();
        checkedCombinationCount = 0;
        
        A = new TwoRowMatrix(X.size(), 0);
        
        K = new ArrayList<Integer>();
        K.add(B.get(0) - (D == null ? 0 : D.get(0)));
        K.add(B.get(1) - (D == null ? 0 : D.get(1)));
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
        scoreResult = A.multiply(X);
        checkedCombinationCount++;
        
        logEquation("Checking combination", Level.TRACE);
        
        if (scoreResult.equals(K)) {
            solutions.add(new TwoRowMatrix(A));
            logEquation("Good combination found", Level.DEBUG);
        } else {
            if (scoreResult.get(row) <= K.get(row) && column + 1 < X.size())
                searchRecursive(++column);
        }
    }
    
    public void logEquation(String title, Level level) {
        log.log(level, title);
        log.log(level, String.format("%s * %sT = %d ? %d", A.getFirstRow(), X, scoreResult.get(0), K.get(0)));
        log.log(level, String.format("%s * %sT = %d ? %d", A.getSecondRow(), X, scoreResult.get(1), K.get(1)));
        
    }

    /**
     * @return the solutions
     */
    public ArrayList<TwoRowMatrix> getSolutions() {
        return solutions;
    }

    /**
     * @return the x
     */
    public ArrayList<Integer> getX() {
        return X;
    }

    /**
     * @param x the x to set
     */
    public void setX(ArrayList<Integer> x) {
        X = x;
    }

    /**
     * @return the a
     */
    public TwoRowMatrix getA() {
        return A;
    }

    /**
     * @param a the a to set
     */
    public void setA(TwoRowMatrix a) {
        A = a;
    }

    /**
     * @return the b
     */
    public ArrayList<Integer> getB() {
        return B;
    }

    /**
     * @param b the b to set
     */
    public void setB(ArrayList<Integer> b) {
        B = b;
    }

    /**
     * @return the d
     */
    public ArrayList<Integer> getD() {
        return D;
    }

    /**
     * @param d the d to set
     */
    public void setD(ArrayList<Integer> d) {
        D = d;
    }

    /**
     * @return the templateMatrix
     */
    public TwoRowMatrix getTemplateMatrix() {
        return templateMatrix;
    }

    /**
     * @param templateMatrix the templateMatrix to set
     */
    public void setTemplateMatrix(TwoRowMatrix templateMatrix) {
        this.templateMatrix = templateMatrix;
    }
    
    
}
