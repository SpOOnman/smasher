package eu.spoonman.smasher.serverinfo;

/**
 * Simple time info with raw information and status flag.
 * 
 * @author Tomasz Kalkosiński
 * 
 */
public class TimeInfo {

    private TimeInfoFlags timeInfoFlags;

    private String rawText;

    /**
     * 
     */
    public TimeInfo(String rawText) {
        this.rawText = rawText;
    }

    /**
     * @return the timeInfoFlags
     */
    public TimeInfoFlags getTimeInfoFlags() {
        return timeInfoFlags;
    }

    /**
     * @param timeInfoFlags
     *            the timeInfoFlags to set
     */
    public void setTimeInfoFlags(TimeInfoFlags timeInfoFlags) {
        this.timeInfoFlags = timeInfoFlags;
    }

}
