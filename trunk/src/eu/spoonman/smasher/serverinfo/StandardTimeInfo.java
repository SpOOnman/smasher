package eu.spoonman.smasher.serverinfo;

import org.joda.time.LocalTime;

/**
 * Standard time information with time set in minutes, seconds and miliseconds.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public class StandardTimeInfo extends TimeInfo {

    private LocalTime localTime;

    /**
     * 
     */
    public StandardTimeInfo(String rawText) {
        super(rawText);
    }

    /**
     * @return the localTime
     */
    public LocalTime getLocalTime() {
        return localTime;
    }

    /**
     * @param localTime
     *            the localTime to set
     */
    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

}
