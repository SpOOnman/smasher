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

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class Version {
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(Version.class);
    
    /**
     * Pattern that should work for most versions: 4.2, 1.03a, 2.5.229 etc.
     */
    private static final Pattern genericVersionPattern = Pattern.compile("(\\d+)\\.?(\\d+)?\\.?(\\d+)?\\.?(\\d+)?\\.?(\\w+)?\\s?.*");

    private static DateTimeFormatter americanDateTimeFormatter;
    private static DateTimeFormatter americanDateFormatter;
    
    private String  name;
    private String  fullName;
    private Integer major;
    private Integer minor;
    private Integer build;
    private Integer revision;
    private String  codeLetter;
    private String  codeName;
    private PlatformSystem system;
    private Platform platform;
    private DateTime buildTime;
    
    
    public Version(String name) {
        this.name = name;
    }
    
    
    public Version(String name, Integer major, Integer minor, Integer build, Integer revision, String codeLetter, String codeName) {
        this(name);
        this.major = major;
        this.minor = minor;
        this.build = build;
        this.revision = revision;
        this.codeLetter = codeLetter;
        this.codeName = codeName;
    }
    
    @Override
    public String toString() {
        return String.format("[Version: %s (%s), %d.%d.%d.%d%s (codename %s), %s, %s, %s",
                name, fullName, major, minor, build, revision, codeLetter, codeName, system, platform, buildTime);
    }
    
    /**
     * Try to parse most common version strings.
     * @param text Common version string.
     * @return true if operation succeeded
     */
    public boolean tryParseVersion(String text) {
        
        if (log.isDebugEnabled())
            log.debug(String.format("Parsing version string '%s'", text));
        
        Matcher matcher = genericVersionPattern.matcher(text);
        
        if (!(matcher.matches()))
            return false;
        
        if (matcher.group(1) != null)
            setMajor(Integer.valueOf(matcher.group(1)));
        if (matcher.group(2) != null)
            setMinor(Integer.valueOf(matcher.group(2)));
        if (matcher.group(3) != null)
            setBuild(Integer.valueOf(matcher.group(3)));
        if (matcher.group(4) != null)
            setRevision(Integer.valueOf(matcher.group(4)));
        
        if (matcher.group(5) != null)
            setCodeLetter(matcher.group(5));

        if (log.isDebugEnabled())
            log.debug(String.format("Version parsed to '%s'", this));
        
        return true;
    }
    
    public boolean tryParseAmericanDateTime(String text) {
        return tryParseAmerican(getAmericanDateTimeFormatter(), text);
    }
    
    public boolean tryParseAmericanDate(String text) {
        return tryParseAmerican(getAmericanDateFormatter(), text);
    }
    
    /**
     * Try to parse text into version's date.
     * @param text American format MMM d YYYY date string.
     * @return true if operation succeeded
     */
    private boolean tryParseAmerican(DateTimeFormatter formatter, String text) {
        //In some cases it's necessary: 'Mar  8 2008' would fail.
        text = text.replace("  ", " ");
        
        if (log.isDebugEnabled())
            log.debug(String.format("Parsing american datetime string '%s'", text));
        
        try {
            buildTime = formatter.parseDateTime(text);
            
            if (log.isDebugEnabled())
                log.debug(String.format("String '%s' parsed to '%s'", text, buildTime));
            
        } catch (IllegalArgumentException e) {
            log.error(String.format("Cannot parse american datetime string '%s'", text), e);
            return false;
        }
        
        return true;
    }
    
    /**
     * @return popular american date time formatter MMM dd YYYY, e.g. Apr 26 2004.
     */
    private DateTimeFormatter getAmericanDateFormatter() {
        if (americanDateFormatter == null) {
            americanDateFormatter = DateTimeFormat.forPattern("MMM d YYYY");
            americanDateFormatter.withLocale(Locale.US);
        }
        
        return americanDateFormatter;
    }
    
    /**
     * @return popular american date time formatter MMM dd YYYY, e.g. Apr 26 2004 12:24:22.
     */
    private DateTimeFormatter getAmericanDateTimeFormatter() {
        if (americanDateTimeFormatter == null) {
            americanDateTimeFormatter = DateTimeFormat.forPattern("MMM d YYYY HH:mm:ss");
            americanDateTimeFormatter.withLocale(Locale.US);
        }
        
        return americanDateTimeFormatter;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }
    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
