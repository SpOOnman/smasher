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
 * @author spoonman
 *
 */
public class Version {
    
    private Integer major;
    private Integer minor;
    private Integer build;
    private Integer revision;
    private String  codeLetter;
    private String  codeName;
    
    public Version() {
    }
    
    public Version(Integer major, Integer minor, Integer build, Integer revision, String codeLetter, String codeName) {
        this();
        this.major = major;
        this.minor = minor;
        this.build = build;
        this.revision = revision;
        this.codeLetter = codeLetter;
        this.codeName = codeName;
    }
    /**
     * @return the major
     */
    public Integer getMajor() {
        return major;
    }
    /**
     * @param major the major to set
     */
    public void setMajor(Integer major) {
        this.major = major;
    }
    /**
     * @return the minor
     */
    public Integer getMinor() {
        return minor;
    }
    /**
     * @param minor the minor to set
     */
    public void setMinor(Integer minor) {
        this.minor = minor;
    }
    /**
     * @return the build
     */
    public Integer getBuild() {
        return build;
    }
    /**
     * @param build the build to set
     */
    public void setBuild(Integer build) {
        this.build = build;
    }
    /**
     * @return the revision
     */
    public Integer getRevision() {
        return revision;
    }
    /**
     * @param revision the revision to set
     */
    public void setRevision(Integer revision) {
        this.revision = revision;
    }
    /**
     * @return the codeLetter
     */
    public String getCodeLetter() {
        return codeLetter;
    }
    /**
     * @param codeLetter the codeLetter to set
     */
    public void setCodeLetter(String codeLetter) {
        this.codeLetter = codeLetter;
    }
    /**
     * @return the codeName
     */
    public String getCodeName() {
        return codeName;
    }
    /**
     * @param codeName the codeName to set
     */
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
    
    
    

}
