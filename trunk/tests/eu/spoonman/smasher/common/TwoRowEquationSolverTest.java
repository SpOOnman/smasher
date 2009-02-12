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
        ArrayList<TwoRowMatrix> arrayList = solver.search();
        
        assertEquals(1, arrayList.size());
        
        int checkedCount = solver.getCheckedCombinationCount();
        int count = solver.getCombinationCount();
        
        System.out.println(String.format("Checked %d combinations out of %d possible: %d%%", checkedCount, count, (int) ((checkedCount * 100/count) )));
    }

}
