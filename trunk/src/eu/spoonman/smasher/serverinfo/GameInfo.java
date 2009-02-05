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

package eu.spoonman.smasher.serverinfo;

/**
 * Information about game name, game type, versions etc.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public class GameInfo {
    
    private Games game;
    private Version gameVersion;
    private Mod mod;
    
    private GameTypes gameType;
    private String rawGameType;
    
    /**
     * @return the game
     */
    public Games getGame() {
        return game;
    }
    /**
     * @param game the game to set
     */
    public void setGame(Games game) {
        this.game = game;
    }
    /**
     * @return the gameVersion
     */
    public Version getGameVersion() {
        return gameVersion;
    }
    /**
     * @param gameVersion the gameVersion to set
     */
    public void setGameVersion(Version gameVersion) {
        this.gameVersion = gameVersion;
    }
    /**
     * @return the mod
     */
    public Mod getMod() {
        return mod;
    }
    /**
     * @param mod the mod to set
     */
    public void setMod(Mod mod) {
        this.mod = mod;
    }
    /**
     * @return the gameType
     */
    public GameTypes getGameType() {
        return gameType;
    }
    /**
     * @param gameType the gameType to set
     */
    public void setGameType(GameTypes gameType) {
        this.gameType = gameType;
    }
    /**
     * @return the rawGameType
     */
    public String getRawGameType() {
        return rawGameType;
    }
    /**
     * @param rawGameType the rawGameType to set
     */
    public void setRawGameType(String rawGameType) {
        this.rawGameType = rawGameType;
    }
}
