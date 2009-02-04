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

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.ServerQuery;

public class ServerQueryTest {

    @Test
    public void testParseData() throws UnsupportedEncodingException {

        String text = "ff ff ff ff 73 74 61 74 75 73 52 65 73 70 6f 6e 73 65 a 5c 6d 6f 64 65 5f 63 75 72 72 65 6e 74 5c 56 51 33 54 44 4d 5c 67 5f 6e 65 65 64 70 61 73 73 5c 30 5c 47 54 56 5f 43 4e 5c 31 5c 50 6c 61 79 65 72 73 5f 42 6c 75 65 5c 32 20 33 20 35 20 37 20 5c 50 6c 61 79 65 72 73 5f 52 65 64 5c 31 20 34 20 38 20 39 20 5c 53 63 6f 72 65 5f 42 6c 75 65 5c 38 30 5c 53 63 6f 72 65 5f 52 65 64 5c 38 31 5c 53 63 6f 72 65 5f 54 69 6d 65 5c 39 3a 32 37 5c 73 76 5f 61 72 65 6e 61 73 5c 31 5c 67 61 6d 65 76 65 72 73 69 6f 6e 5c 31 2e 34 36 5c 67 61 6d 65 64 61 74 65 5c 41 70 72 20 32 36 20 32 30 30 38 5c 67 61 6d 65 6e 61 6d 65 5c 63 70 6d 61 5c 67 61 6d 65 5c 43 50 4d 41 5c 73 76 5f 6d 69 6e 52 61 74 65 5c 30 5c 73 76 5f 70 72 69 76 61 74 65 43 6c 69 65 6e 74 73 5c 30 5c 6d 61 70 6e 61 6d 65 5c 70 72 6f 2d 71 33 64 6d 36 5c 70 72 6f 74 6f 63 6f 6c 5c 36 38 5c 67 5f 67 61 6d 65 74 79 70 65 5c 33 5c 76 65 72 73 69 6f 6e 5c 43 4e 51 33 20 31 2e 34 32 20 6c 69 6e 75 78 2d 69 33 38 36 20 41 70 72 20 32 32 20 32 30 30 38 5c 67 5f 6d 61 78 47 61 6d 65 43 6c 69 65 6e 74 73 5c 30 5c 73 65 72 76 65 72 5f 67 61 6d 65 70 6c 61 79 5c 56 51 33 5c 73 76 5f 61 6c 6c 6f 77 44 6f 77 6e 6c 6f 61 64 5c 30 5c 73 76 5f 66 6c 6f 6f 64 50 72 6f 74 65 63 74 5c 30 5c 73 76 5f 6d 61 78 50 69 6e 67 5c 30 5c 73 76 5f 6d 69 6e 50 69 6e 67 5c 30 5c 73 76 5f 6d 61 78 52 61 74 65 5c 30 5c 73 76 5f 6d 61 78 63 6c 69 65 6e 74 73 5c 31 34 5c 73 76 5f 68 6f 73 74 6e 61 6d 65 5c 41 53 54 45 52 20 54 65 61 6d 73 20 23 31 20 56 51 33 5c 73 76 5f 66 70 73 5c 33 30 a 31 33 20 30 20 22 5e 37 3c 5e 30 4d 75 72 63 69 65 6c 61 67 30 5e 37 3e 22 a 32 31 20 30 20 22 63 69 70 65 6b 22 a 31 38 20 38 20 22 78 22 a 31 36 20 30 20 22 5e 35 4e 5e 34 5a 20 5e 37 2f 20 5e 30 68 5e 37 65 6e 5e 30 6b 22 a 31 35 20 30 20 22 5e 37 6e 5e 31 30 5e 37 6f 70 2e 5e 31 45 22 a 33 35 20 33 33 20 22 73 65 70 75 6b 75 22 a 32 36 20 30 20 22 4d 61 53 5e 30 6e 6f 63 6c 61 6e 22 a 33 35 20 30 20 22 6d 65 66 61 6a 70 65 5e 66 5e 5e 22 a 31 37 20 37 32 20 22 55 6e 6e 61 6d 65 64 50 6c 61 79 65 72 22 a 32 31 20 31 38 20 22 5e 6f 6f 70 74 59 6d 21 73 74 41 22 a 32 31 20 33 31 20 22 5e 41 6c 5e 42 61 5e 43 67 20 5e 44 66 5e 45 72 5e 46 6f 5e 47 6d 20 5e 48 78 5e 49 46 22 a";

        StringBuilder sb = new StringBuilder();

        Pattern pattern = Pattern.compile("([0-9a-f]{1,2})");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            MatchResult matchResult = matcher.toMatchResult();

            char c = (char) Integer.parseInt(matchResult.group(1), 16);
            sb.append(c);
        }

        System.out.println(sb.toString());

        ServerQuery serverQuery2 = ServerQueryManager.createServerQuery(Games.QUAKE3ARENA, null, 0);
        ServerInfo serverInfo = serverQuery2.query(sb.toString().getBytes("ISO8859-1"));

        System.out.println(serverInfo);
    }

}
