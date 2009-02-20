package eu.spoonman.smasher.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class LCSTest {

	private List<String> getList() {
		List<String> list = new ArrayList<String>();
		list.add("Ann");
		list.add("Bob");
		list.add("Cid");
		list.add("Dave");
		list.add("Eve");
		list.add("Frank");
		return list;

	}

	@Test
	public void testGetLCSPairs() {
		LCS<String> lcs = new LCS<String>(getList(), getList(), new Comparator<String>() {
		
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		
		List<Pair<String,String>> pairs = lcs.getLCSPairs();
		
		for (Pair<String,String> pair : pairs) {
			System.out.println(String.format("Left %s right %s", pair.getFirst(), pair.getSecond()));
		}
	}
}
