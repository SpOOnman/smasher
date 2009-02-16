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
import java.util.List;

import eu.spoonman.smasher.common.TwoRowEquationSolver;
import eu.spoonman.smasher.common.TwoRowMatrix;
import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.persister.ServerInfoPersister;

/**
 * Persister that uses TwoRowEquations to assign players to teams.
 * 
 * @author Tomasz Kalkosiński
 *
 */
public class TeamAssignPersister implements ServerInfoPersister {
    
    private TwoRowMatrix previousOverlapMatrix;
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
        
        solver.setX(X);
        solver.setB(B);
        solver.setD(null);
        solver.setTemplateMatrix(previousOverlapMatrix);
        
        ArrayList<TwoRowMatrix> list = solver.search();
        
    }
    
    private ArrayList<Integer> prepareMatrixB(ServerInfo serverInfo) {
        ArrayList<Integer> scores = new ArrayList<Integer>();
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
     * Gets all players which score is different than 0 sorted descending by score.
     * 
     * @param serverInfo
     * @return
     */
    private ArrayList<PlayerInfo> getPlayers(ServerInfo serverInfo) {
        ArrayList<PlayerInfo> copy = new ArrayList<PlayerInfo>();
        Collections.copy(copy, serverInfo.getPlayerInfos());
        
        //Remove all players with score 0
        for (Iterator<PlayerInfo> iterator = copy.iterator(); iterator.hasNext();) {
            if (iterator.next().getScore() == 0)
                iterator.remove();
        }
        
        //Sort by score descending
        Collections.sort(copy, new Comparator<PlayerInfo> (
                ){
                    @Override
                    public int compare(PlayerInfo o1, PlayerInfo o2) {
                        return o1.getScore() - o2.getScore();
                    }
                });
        
        return copy;
    }
    
}
