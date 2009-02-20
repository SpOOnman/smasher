package eu.spoonman.smasher.common;

import static org.junit.Assert.*;

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
	
	private List<Pair<String,String>> getLCSPairs(List<String> left, List<String> right) {
		LCS<String> lcs = new LCS<String>(left, right, new Comparator<String>() {
			
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		
		return lcs.getLCSPairs();
		
	}
	
	private void printPairs(List<Pair<String,String>> pairs) {
		for (Pair<String,String> pair : pairs) {
			System.out.println(String.format("Left %s right %s", pair.getFirst(), pair.getSecond()));
		}
		
	}

	@Test
	public void testGetLCSPairs() {
		List<Pair<String,String>> pairs = getLCSPairs(getList(), getList());
		
		for (Pair<String,String> pair : pairs) {
			assertEquals(pair.getFirst(), pair.getSecond());
			//System.out.println(String.format("Left %s right %s", pair.getFirst(), pair.getSecond()));
		}
		
		//Assert good order
		assertEquals(pairs.get(0).getFirst(), "Ann");
	}

	@Test
	public void testGetLCSPairsMissing() {
		testGetLCSPairsMissingRight(0);
		testGetLCSPairsMissingRight(3);
		testGetLCSPairsMissingRight(5);
		
		testGetLCSPairsMissingLeft(0);
		testGetLCSPairsMissingLeft(3);
		testGetLCSPairsMissingLeft(5);
	}
	
	public void testGetLCSPairsMissingRight(int index) {
		List<String> right = getList();
		right.remove(index);
		
		List<Pair<String,String>> pairs = getLCSPairs(getList(), right);
		
		assertEquals(right.size() + 1, pairs.size());
		assertNotNull(pairs.get(index).getFirst());
		assertNull(pairs.get(index).getSecond());
	}
	
	public void testGetLCSPairsMissingLeft(int index) {
		List<String> left = getList();
		left.remove(index);
		
		List<Pair<String,String>> pairs = getLCSPairs(left, getList());
		
		assertEquals(left.size() + 1, pairs.size());
		assertNull(pairs.get(index).getFirst());
		assertNotNull(pairs.get(index).getSecond());
	}
}
