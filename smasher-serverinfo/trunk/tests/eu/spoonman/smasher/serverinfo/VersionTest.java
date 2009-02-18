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

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class VersionTest {

    /**
     * Test method for {@link eu.spoonman.smasher.serverinfo.Version#tryParse(java.lang.String)}.
     */
    @Test
    public void tryParseTestOne() {
        Version version = new Version(null);
        
        version.tryParseVersion("1.2.3.4");
        
        assertEquals(1, version.getMajor());
        assertEquals(2, version.getMinor());
        assertEquals(3, version.getBuild());
        assertEquals(4, version.getRevision());
        assertNull(version.getCodeLetter());
        assertNull(version.getCodeName());
    }
    
    /**
     * Test method for {@link eu.spoonman.smasher.serverinfo.Version#tryParse(java.lang.String)}.
     */
    @Test
    public void tryParseTestTwo() {
        Version version = new Version(null);
        
        version.tryParseVersion("5.021a");
        
        assertEquals(5, version.getMajor());
        assertEquals(21, version.getMinor());
        assertNull(version.getBuild());
        assertNull(version.getRevision());
        assertEquals("a", version.getCodeLetter());
        assertNull(version.getCodeName());
    }
    
    @Test
    public void americanFormatterTestOne() {
        Version version = new Version(null);
        version.tryParseAmericanDate("Apr 24 2006");
        DateTime dateTime = version.getBuildTime();
        assertEquals(4, dateTime.getMonthOfYear());
        assertEquals(24, dateTime.getDayOfMonth());
        assertEquals(2006, dateTime.getYear());
        
        version.tryParseAmericanDate("Mar 8 2008");
        dateTime = version.getBuildTime();
        assertEquals(3, dateTime.getMonthOfYear());
        assertEquals(8, dateTime.getDayOfMonth());
        assertEquals(2008, dateTime.getYear());
        
        version.tryParseAmericanDate("Jan  1 2005");
        dateTime = version.getBuildTime();
        assertEquals(1, dateTime.getMonthOfYear());
        assertEquals(1, dateTime.getDayOfMonth());
        assertEquals(2005, dateTime.getYear());
    }

}
