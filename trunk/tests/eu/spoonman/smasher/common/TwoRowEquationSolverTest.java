package eu.spoonman.smasher.common;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

public class TwoRowEquationSolverTest {

    @Test
    public void testSearch() {
        Integer[] players = { 19, 17, 11, 9, 2, 1 };
        Integer[] score  = { 32, 27 };
        ArrayList<Integer> playersScore = new ArrayList<Integer>(Arrays.asList(players));
        ArrayList<Integer> scores = new ArrayList<Integer>(Arrays.asList(score));
        
        TwoRowEquationSolver solver = new TwoRowEquationSolver(playersScore, scores, null, null);
        ArrayList<TwoRowMatrix> arrayList = solver.solve();
        
        assertEquals(1, arrayList.size());
        
        assertEquals(1, arrayList.get(0).getFirstRow().get(0));
        assertEquals(0, arrayList.get(0).getFirstRow().get(1));
        assertEquals(1, arrayList.get(0).getFirstRow().get(2));
        assertEquals(0, arrayList.get(0).getFirstRow().get(3));
        assertEquals(1, arrayList.get(0).getFirstRow().get(4));
        assertEquals(0, arrayList.get(0).getFirstRow().get(5));
        
        assertEquals(0, arrayList.get(0).getSecondRow().get(0));
        assertEquals(1, arrayList.get(0).getSecondRow().get(1));
        assertEquals(0, arrayList.get(0).getSecondRow().get(2));
        assertEquals(1, arrayList.get(0).getSecondRow().get(3));
        assertEquals(0, arrayList.get(0).getSecondRow().get(4));
        assertEquals(1, arrayList.get(0).getSecondRow().get(5));
    }
    
    @Test
    public void testSearchTwo() {
        //was               { 19, 17, 11,  9, 2, 1 }   
        Integer[] players = { 19, 17, 10, 10, 2, 1 };
        //was              { 32, 27 }
        Integer[] score  = { 31, 28 };
        ArrayList<Integer> playersScore = new ArrayList<Integer>(Arrays.asList(players));
        ArrayList<Integer> scores = new ArrayList<Integer>(Arrays.asList(score));
        
        TwoRowEquationSolver solver = new TwoRowEquationSolver(playersScore, scores, null, null);
        ArrayList<TwoRowMatrix> arrayList = solver.solve();
        
        assertEquals(2, arrayList.size());
        
        assertEquals(1, arrayList.get(0).getFirstRow().get(0));
        assertEquals(0, arrayList.get(0).getFirstRow().get(1));
        assertEquals(1, arrayList.get(0).getFirstRow().get(2));
        assertEquals(0, arrayList.get(0).getFirstRow().get(3));
        assertEquals(1, arrayList.get(0).getFirstRow().get(4));
        assertEquals(0, arrayList.get(0).getFirstRow().get(5));
        
        assertEquals(0, arrayList.get(0).getSecondRow().get(0));
        assertEquals(1, arrayList.get(0).getSecondRow().get(1));
        assertEquals(0, arrayList.get(0).getSecondRow().get(2));
        assertEquals(1, arrayList.get(0).getSecondRow().get(3));
        assertEquals(0, arrayList.get(0).getSecondRow().get(4));
        assertEquals(1, arrayList.get(0).getSecondRow().get(5));
        
        assertEquals(1, arrayList.get(1).getFirstRow().get(0));
        assertEquals(0, arrayList.get(1).getFirstRow().get(1));
        assertEquals(0, arrayList.get(1).getFirstRow().get(2));
        assertEquals(1, arrayList.get(1).getFirstRow().get(3));
        assertEquals(1, arrayList.get(1).getFirstRow().get(4));
        assertEquals(0, arrayList.get(1).getFirstRow().get(5));
        
        assertEquals(0, arrayList.get(1).getSecondRow().get(0));
        assertEquals(1, arrayList.get(1).getSecondRow().get(1));
        assertEquals(1, arrayList.get(1).getSecondRow().get(2));
        assertEquals(0, arrayList.get(1).getSecondRow().get(3));
        assertEquals(0, arrayList.get(1).getSecondRow().get(4));
        assertEquals(1, arrayList.get(1).getSecondRow().get(5));
    }

}
