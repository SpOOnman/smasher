/**
 * 
 */
package eu.spoonman.smasher.serverinfo.builder;

import eu.spoonman.smasher.serverinfo.Games;

/**
 * @author Tomasz Kalkosiński
 * 
 */
public class BuilderFactory {
    
    /**
     * Empty constructor to avoid creating children outside this package. 
     */
    BuilderFactory() {
    }

    public static Builder createBuilder(Games game) {

        switch (game) {
        case QUAKE3ARENA:
            return new Quake3ArenaBuilder();
        default:
            // it's our fault if we don't have builder for game we say is supported
            throw new EnumConstantNotPresentException(Games.class, game.toString());
        }

    }

}
