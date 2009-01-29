package eu.spoonman.smasher.serverinfo;

/**
 * Simple class that reflects modification name and version. It is convinient to
 * set mod field in serverinfo to null when no mod is used.
 * 
 * @author Tomasz Kalkosiński
 */
public class Mod {
    
    private Version version;
    private String name;
    /**
     * @return the version
     */
    public Version getVersion() {
        return version;
    }
    /**
     * @param version the version to set
     */
    public void setVersion(Version version) {
        this.version = version;
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
    
    

}
