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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class Version {
    
    /**
     * Pattern that should work for most versions: 4.2, 1.03a, 2.5.229 etc.
     */
    private static final Pattern genericVersionPattern = Pattern.compile("(\\d+)\\.?(\\d+)?\\.?(\\d+)?\\.?(\\d+)?\\.?(\\w+)?\\s?.*");
    
    private Integer major;
    private Integer minor;
    private Integer build;
    private Integer revision;
    private String  codeLetter;
    private String  codeName;
    private PlatformSystem system;
    private Platform platform;
    private DateTime buildTime;
    
    
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
     * Try to parse most common version strings.
     * @param versionString
     */
    public static Version tryParse(String versionString) {
        
        Matcher matcher = genericVersionPattern.matcher(versionString);
        
        if (!(matcher.matches()))
            return null;
        
        Version version = new Version();
        
        if (matcher.group(1) != null)
            version.setMajor(Integer.valueOf(matcher.group(1)));
        if (matcher.group(2) != null)
            version.setMinor(Integer.valueOf(matcher.group(2)));
        if (matcher.group(3) != null)
            version.setBuild(Integer.valueOf(matcher.group(3)));
        if (matcher.group(4) != null)
            version.setRevision(Integer.valueOf(matcher.group(4)));
        
        if (matcher.group(5) != null)
            version.setCodeLetter(matcher.group(5));
        
        return version;
        
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
    
    /**
     * @return the system
     */
    public PlatformSystem getSystem() {
        return system;
    }

    /**
     * @param system the system to set
     */
    public void setSystem(PlatformSystem system) {
        this.system = system;
    }

    /**
     * @return the platform
     */
    public Platform getPlatform() {
        return platform;
    }

    /**
     * @param platform the platform to set
     */
    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    /**
     * @return the buildTime
     */
    public DateTime getBuildTime() {
        return buildTime;
    }

    /**
     * @param buildTime the buildTime to set
     */
    public void setBuildTime(DateTime buildTime) {
        this.buildTime = buildTime;
    }
    
    
    
    
    

}
