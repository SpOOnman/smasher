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
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import eu.spoonman.smasher.serverinfo.ServerInfo;
import eu.spoonman.smasher.serverinfo.ServerQuery;


public class ServerQueryTest {

	@Test
	public void testParseData() {
				
		String text = null;
		
		try {
			File file = new File ("src/eu/spoonman/spider/serverinfo/tests/quake3arena-1.response");
			
			System.out.println(file.getAbsolutePath());
			FileReader fr = new FileReader (file);
			BufferedReader br = new BufferedReader(fr);
			text = br.readLine();
			br.close();
			fr.close();
		} catch (IOException e) {
			fail (e.getMessage());
		}
		
		StringBuilder sb = new StringBuilder();
		
		
		Pattern pattern = Pattern.compile("\\\\u00([0-9a-f]{2})");
		Matcher matcher = pattern.matcher(text);
		
		while (matcher.find()) {
			MatchResult matchResult = matcher.toMatchResult();
            
			char c = (char)Integer.parseInt(matchResult.group(1), 16);
			sb.append(c);
		}
		
		System.out.println(sb.toString());
		
		ServerInfo serverInfo = new ServerInfo ();
		ServerQuery serverQuery = new ServerQuery();
		serverQuery.setServerRegex("\\\\([^\\\\]*)\\\\([^\\\\\\u000a]*)");
		serverQuery.setPlayerRegex("\\u000a(\\d+) (\\d+) \"(.*)\"");
		
		serverQuery.setOrdinalScore(1);
		serverQuery.setOrdinalPing(2);
		serverQuery.setOrdinalName(3);
		
		serverQuery.readData(serverInfo, sb.toString().getBytes());
		
		System.out.println(serverInfo);
	}

}
