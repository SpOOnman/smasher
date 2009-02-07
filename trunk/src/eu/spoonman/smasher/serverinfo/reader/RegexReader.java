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

    public void readData(ServerInfo serverInfo, byte[] bytes) {
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
            for (int i = 0; i < matchResult.groupCount(); i++) {
                playerInfo.getRawAttributes().add(matchResult.group(i + 1));
            }

            serverInfo.getPlayerInfos().add(playerInfo);
        }
    }

}
