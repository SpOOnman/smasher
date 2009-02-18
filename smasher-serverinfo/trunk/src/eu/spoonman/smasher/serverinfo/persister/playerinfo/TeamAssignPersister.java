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

package eu.spoonman.smasher.serverinfo.persister.playerinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import eu.spoonman.smasher.common.TwoRowEquationSolver;
import eu.spoonman.smasher.common.TwoRowMatrix;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.TeamKey;
import eu.spoonman.smasher.serverinfo.persister.ServerInfoPersister;

/**
 * Persister that uses TwoRowEquations to assign players to teams.
 * 
 * @author Tomasz Kalkosiński
 *
 */
/**
 * @author spoonman
 *
 */
public class TeamAssignPersister implements ServerInfoPersister {
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(TeamAssignPersister.class);
    
    private TwoRowMatrix overlapMatrix;
    private TwoRowEquationSolver solver;
    
    /**
     * Descending sorted players with score different than 0
     */
    private ArrayList<PlayerInfo> sortedPlayers;
    
    /**
     * 
     */
    public TeamAssignPersister() {
        solver = new TwoRowEquationSolver();
    }
    
    @Override
    public void persist(ServerInfo serverInfo) {
        ArrayList<Integer> X = prepareMatrixX(serverInfo);
        ArrayList<Integer> B = prepareMatrixB(serverInfo);
        //TODO: out of pairs.
        //TwoRowMatrix overlap = prepareOverlapMatrix();
        
        solver.setX(X);
        solver.setB(B);
        solver.setD(null);
        solver.setTemplateMatrix(overlapMatrix);
        
        //Get all solutions
        ArrayList<TwoRowMatrix> list = solver.solve();
        
        //TODO: zero solutions
        
        //Remove all matrices that doesn't match old overlap matrix
        for (Iterator<TwoRowMatrix> iterator = list.iterator(); iterator.hasNext();) {
            if (!(matchesTemplateMatrix(iterator.next())))
                iterator.remove();
        }
        
        //Create overlap matrix;
        overlapMatrix = overlapMatrices(X.size(), list);
        
        //Assign players to teams
        assignPlayers();
    }
    
    private void assignPlayers() {
        
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
                sortedPlayers.get(i).setTeamKey(TeamKey.RED_TEAM);
            else
                sortedPlayers.get(i).setTeamKey(TeamKey.BLUE_TEAM);
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
    
    private ArrayList<Integer> prepareMatrixB(ServerInfo serverInfo) {
        ArrayList<Integer> scores = new ArrayList<Integer>();
        scores.add(serverInfo.getTeamInfos().get(TeamKey.RED_TEAM).getScore());
        scores.add(serverInfo.getTeamInfos().get(TeamKey.BLUE_TEAM).getScore());
        return scores;
    }
    
    /**
     * Get player scores vector.
     * 
     * @param serverInfo
     * @return
     */
    private ArrayList<Integer> prepareMatrixX(ServerInfo serverInfo) {
        sortedPlayers = getPlayers(serverInfo);
        ArrayList<Integer> playerScores = new ArrayList<Integer>();
        
        for (PlayerInfo playerInfo : sortedPlayers)
            playerScores.add(playerInfo.getScore());
        
        return playerScores;
    }
    
    /**
     * Checks if actual solution matches template matrix.
     * Match means that for every element equals to 1 in template matrix - exact element in solution matrix must be equal to 1 too.
     * @return
     */
    private boolean matchesTemplateMatrix (TwoRowMatrix matrix) {
        //If there is no overlap matrix every solution is acceptable.
        if (overlapMatrix == null)
            return true;
        
        //Check each element
        for (int i = 0 ; i < 2 ; i++)
            for (int j = 0 ; j < sortedPlayers.size() ; j ++){
                if (overlapMatrix.getRow(i).get(j) == 1 && matrix.getRow(i).get(j) != 1)
                    return false;
            }
        
        return true;
    }
    
    /**
     * Gets all players which score is different than 0 sorted descending by score.
     * 
     * @param serverInfo
     * @return
     */
    private ArrayList<PlayerInfo> getPlayers(ServerInfo serverInfo) {
        ArrayList<PlayerInfo> copy = new ArrayList<PlayerInfo>(serverInfo.getPlayerInfos());
        
        //Remove all players with score 0
        for (Iterator<PlayerInfo> iterator = copy.iterator(); iterator.hasNext();) {
            if (iterator.next().getScore() == 0)
                iterator.remove();
        }
        
        //Sort by score descending
        Collections.sort(copy, new Comparator<PlayerInfo> (
                ){
            
                    /**
                     * Put negative numbers in front of positive!
                     * @param o1
                     * @param o2
                     * @return
                     */
                    @Override
                    public int compare(PlayerInfo o1, PlayerInfo o2) {
                        
                        int s1 = o1.getScore();
                        int s2 = o2.getScore();
                        
                        if (s1 >= 0 && s2 >= 0)
                            return s2 - s1;
                        
                        if (s1 < 0 && s2 < 0)
                            return s1 - s2;
                        
                        if (s1 < 0)
                            return -1;
                        
                        return 1;
                    }
                });
        
        return copy;
    }
    
}
