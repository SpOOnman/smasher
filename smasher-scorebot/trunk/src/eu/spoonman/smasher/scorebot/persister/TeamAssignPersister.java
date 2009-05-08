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

package eu.spoonman.smasher.scorebot.persister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import eu.spoonman.smasher.common.Pair;
import eu.spoonman.smasher.common.TwoRowEquationSolver;
import eu.spoonman.smasher.common.TwoRowMatrix;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;

/**
 * Persister that uses TwoRowEquations to assign players to teams.
 * 
 * @author Tomasz Kalkosiński
 *
 */
public class TeamAssignPersister extends ScorebotPersister {
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(TeamAssignPersister.class);
    
    private TwoRowEquationSolver solver;
    private boolean couldntSolve = false;

	private ArrayList<Pair<PlayerInfo, PlayerInfo>> sortedPlayerPairs;
    
    public TeamAssignPersister() {
        solver = new TwoRowEquationSolver();
    }
    
    @Override
    public void persist(List<Pair<PlayerInfo, PlayerInfo>> playerPairs) {
    	super.persist(playerPairs);
    	prepareMatricesOverlayAndX(playerPairs);
    	
    	solve();
    }
    
    private void solve() {
    	
    	solver.setD(null);
    	
    	//Get all solutions
    	ArrayList<TwoRowMatrix> list = solver.solve();
    	
    	if (list.size() == 0) {
    		log.warn("No solutions were found. Resetting template matrix.");
    		
    		if (couldntSolve == true) {
    			log.error("Couldnt solve for two times. Quitting.");
    			return;
    		}
    		
    		solver.setTemplateMatrix(new TwoRowMatrix(solver.getX().size(), 0));
    		couldntSolve = true;
    		solve();
    	}
    	
    	couldntSolve = false;
    	
    	//Remove all matrices that doesn't match old overlap matrix
    	for (Iterator<TwoRowMatrix> iterator = list.iterator(); iterator.hasNext();) {
    		if (!(matchesTemplateMatrix(solver.getTemplateMatrix(), iterator.next())))
    			iterator.remove();
    	}
    	
    	//Create overlap matrix;
    	TwoRowMatrix overlapMatrix = overlapMatrices(solver.getX().size(), list);
    	
    	//Assign players to teams
    	assignPlayers(overlapMatrix);
    }
    
    @Override
    public void persist(ServerInfo left, ServerInfo right) {
        prepareMatrixB(right);
        
    }
    
    private void assignPlayers(TwoRowMatrix overlapMatrix) {
        
        if (overlapMatrix == null) {
            if (log.isDebugEnabled())
                log.debug("Overlap matrix is null. No assigns will be taken.");
            return;
        }
        
        for (int i = 0 ; i < overlapMatrix.getFirstRow().size() ; i++) {
            int red = overlapMatrix.getFirstRow().get(i);
            int blue = overlapMatrix.getSecondRow().get(i);
            
            if (red == 0 && blue == 0)
                continue;
            
            if (red == 1)
                sortedPlayerPairs.get(i).getSecond().setTeamKey(TeamKey.RED_TEAM);
            else
                sortedPlayerPairs.get(i).getSecond().setTeamKey(TeamKey.BLUE_TEAM);
        }
        
        
    }
    
    private TwoRowMatrix overlapMatrices(int size, ArrayList<TwoRowMatrix> matrices) {
        //Create matrix full of 1s
        TwoRowMatrix matrix = new TwoRowMatrix(size, 1);
        
        if (matrices.isEmpty())
            solver.logEquation("There were no solutions found. Input data is inappropiate.", Level.WARN);
        
        for (TwoRowMatrix twoRowMatrix : matrices) {
            matrix.overlap(twoRowMatrix);
        }
        
        if (log.isDebugEnabled()) {
            log.debug(String.format("Created overlap matrix out of %d results:\n%s\n%s", matrices.size(), matrix.getFirstRow(), matrix.getSecondRow()));
        }
        
        return matrix;
    }
    
    private void prepareMatrixB(ServerInfo serverInfo) {
        ArrayList<Integer> scores = new ArrayList<Integer>();
        
        scores.add(serverInfo.getTeamInfos().get(TeamKey.RED_TEAM).getScore());
        scores.add(serverInfo.getTeamInfos().get(TeamKey.BLUE_TEAM).getScore());
        
        solver.setB(scores);
    }
    
    private void prepareMatricesOverlayAndX(List<Pair<PlayerInfo, PlayerInfo>> pairs) {
    	sortedPlayerPairs = new ArrayList<Pair<PlayerInfo,PlayerInfo>>(pairs);
    	
    	//Remove all players with score 0
        for (Iterator<Pair<PlayerInfo, PlayerInfo>> iterator = sortedPlayerPairs.iterator(); iterator.hasNext();) {
            if (iterator.next().getSecond().getScore() == 0)
                iterator.remove();
        }
        
        //Sort by score descending
        Collections.sort(sortedPlayerPairs, new Comparator<Pair<PlayerInfo, PlayerInfo>> (
                ){
            
                    /**
                     * Put negative numbers in front of positive!
                     * @param o1
                     * @param o2
                     * @return
                     */
                    @Override
                    public int compare(Pair<PlayerInfo, PlayerInfo> o1, Pair<PlayerInfo, PlayerInfo> o2) {
                        
                        int s1 = o1.getSecond().getScore();
                        int s2 = o2.getSecond().getScore();
                        
                        if (s1 >= 0 && s2 >= 0)
                            return s2 - s1;
                        
                        if (s1 < 0 && s2 < 0)
                            return s1 - s2;
                        
                        if (s1 < 0)
                            return -1;
                        
                        return 1;
                    }
                });
        
        ArrayList<Integer> playerScores = new ArrayList<Integer>();
        TwoRowMatrix templateMatrix = new TwoRowMatrix();
        
        for (Pair<PlayerInfo, PlayerInfo> pair : sortedPlayerPairs) {
            playerScores.add(pair.getSecond().getScore());
            templateMatrix.getFirstRow().add(pair.getFirst() != null && pair.getFirst().getTeamKey() == TeamKey.RED_TEAM ? 1 : 0);
            templateMatrix.getSecondRow().add(pair.getFirst() != null && pair.getFirst().getTeamKey() == TeamKey.BLUE_TEAM ? 1 : 0);
        }
        
        solver.setX(playerScores);
        solver.setTemplateMatrix(templateMatrix);
    }
    
    /**
     * Checks if actual solution matches template matrix.
     * Match means that for every element equals to 1 in template matrix - exact element in solution matrix must be equal to 1 too.
     * @return
     */
    private boolean matchesTemplateMatrix (TwoRowMatrix templateMatrix, TwoRowMatrix matrix) {
        //If there is no overlap matrix every solution is acceptable.
        if (templateMatrix == null)
            return true;
        
        //Check each element
        for (int i = 0 ; i < 2 ; i++)
            for (int j = 0 ; j < templateMatrix.getFirstRow().size() ; j ++){
                if (templateMatrix.getRow(i).get(j) == 1 && matrix.getRow(i).get(j) != 1)
                    return false;
            }
        
        return true;
    }
    
    
}
