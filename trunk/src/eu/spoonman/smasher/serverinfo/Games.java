package eu.spoonman.smasher.serverinfo;

public enum Games {
    
    QUAKE3ARENA;
    
    private final static String path = "eu/spoonman/smasher/serverinfo/";
    private final static String extension = ".properties";
        
    public String getFileName () {
        return path + this.toString().toLowerCase() + extension;
    }
    
    public static void main(String[] args) {
        System.out.println(Games.QUAKE3ARENA.getFileName());
    }
    

}
