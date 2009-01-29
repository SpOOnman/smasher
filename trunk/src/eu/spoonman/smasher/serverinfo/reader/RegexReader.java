/**
 * 
 */
package eu.spoonman.smasher.serverinfo.reader;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.spoonman.smasher.serverinfo.PlayerInfo;
import eu.spoonman.smasher.serverinfo.ServerInfo;

/**
 * @author spoonman
 * 
 */
public abstract class RegexReader implements Reader {

    public void parseData(ServerInfo serverInfo, byte[] bytes) {
        parseServerData(serverInfo, bytes);
        parsePlayerData(serverInfo, bytes);
    }

    public void parseServerData(ServerInfo serverInfo, byte[] bytes) {

        String data = new String(bytes);

        Matcher matcher = getServerPattern().matcher(data);

        while (matcher.find()) {
            MatchResult matchResult = matcher.toMatchResult();

            serverInfo.getNamedAttributes().put(matchResult.group(1), matchResult.group(2));
        }
    }

    protected abstract Pattern getServerPattern();

    protected abstract Pattern getPlayerPattern();

    public void parsePlayerData(ServerInfo serverInfo, byte[] bytes) {
        String data = new String(bytes);

        Matcher matcher = getPlayerPattern().matcher(data);

        while (matcher.find()) {
            MatchResult matchResult = matcher.toMatchResult();
            PlayerInfo playerInfo = new PlayerInfo();

            /*
             * if (ordinalName > -1)
             * playerInfo.setName(matchResult.group(ordinalName));
             * 
             * if (ordinalPing > -1)
             * playerInfo.setPing(Integer.parseInt(matchResult
             * .group(ordinalPing)));
             * 
             * if (ordinalScore > -1)
             * playerInfo.setScore(Integer.parseInt(matchResult
             * .group(ordinalScore)));
             */

            // if (ordinalClan > -1)
            // playerInfo.setClan(matchResult.group(ordinalClan));
            serverInfo.getPlayerInfos().add(playerInfo);
        }
    }

}
