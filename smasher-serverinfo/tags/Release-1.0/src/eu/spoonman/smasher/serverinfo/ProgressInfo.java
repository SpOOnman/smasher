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

import java.util.EnumSet;

/**
 * Simple progress info with raw information and status flag.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
/**
 * @author spoonman
 *
 */
public class ProgressInfo {

    private String rawText;
    private EnumSet<ProgressInfoFlags> progressInfoFlags;

    /**
     * 
     */
    public ProgressInfo(String rawText) {
        progressInfoFlags = EnumSet.noneOf(ProgressInfoFlags.class);
        this.rawText = rawText;
    }
    
    /**
     * @param progressInfoFlags
     * @param rawText
     */
    public ProgressInfo(String rawText, EnumSet<ProgressInfoFlags> progressInfoFlags) {
        this(rawText);
        this.progressInfoFlags.addAll(progressInfoFlags);
    }
    
    public ProgressInfo(String rawText, ProgressInfoFlags progressInfoFlags) {
        this(rawText);
        this.progressInfoFlags.add(progressInfoFlags);
    }

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((progressInfoFlags == null) ? 0 : progressInfoFlags.hashCode());
        result = prime * result + ((rawText == null) ? 0 : rawText.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ProgressInfo))
            return false;
        ProgressInfo other = (ProgressInfo) obj;
        if (progressInfoFlags == null) {
            if (other.progressInfoFlags != null)
                return false;
        } else if (!progressInfoFlags.equals(other.progressInfoFlags))
            return false;
        if (rawText == null) {
            if (other.rawText != null)
                return false;
        } else if (!rawText.equals(other.rawText))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return String.format("[ProgressInfo: %s, (%s)]", rawText, this.getProgressInfoFlags().toString());
    }

    /**
     * @return the rawText
     */
    public String getRawText() {
        return rawText;
    }

    /**
     * @param rawText the rawText to set
     */
    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    /**
     * @return the progressInfoFlags
     */
    public EnumSet<ProgressInfoFlags> getProgressInfoFlags() {
        return progressInfoFlags;
    }

    /**
     * @param progressInfoFlags the progressInfoFlags to set
     */
    public void setProgressInfoFlags(EnumSet<ProgressInfoFlags> progressInfoFlags) {
        this.progressInfoFlags = progressInfoFlags;
    }

}
