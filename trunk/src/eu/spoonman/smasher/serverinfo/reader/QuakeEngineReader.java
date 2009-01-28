package eu.spoonman.smasher.serverinfo.reader;

import java.util.regex.Pattern;

/**
 * @author Tomasz Kalkosiński
 *
 */
public class QuakeEngineReader extends RegexReader {
    
    private final Pattern serverPattern = Pattern.compile("\\u005c([^\\u005c]*)\\u005c([^\\u005c\\u000a]*)");
    private final Pattern playerPattern = Pattern.compile("(\\d+) (\\d+) \"(.*)\"");
    
    /**
     * @return the serverPattern
     */
    @Override
    protected Pattern getServerPattern() {
        return serverPattern;
    }
    
    /**
     * @return the playerPattern
     */
    @Override
    protected Pattern getPlayerPattern() {
        return playerPattern;
    }

}
