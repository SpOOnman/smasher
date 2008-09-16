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
		
		serverQuery.parseData(serverInfo, sb.toString().getBytes());
		
		System.out.println(serverInfo);
	}

}
