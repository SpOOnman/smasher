package eu.spoonman.smasher.common;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import eu.spoonman.smasher.scorebot.TestHelper;

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
	
	private List<DiffData<String>> getLCSPairs(List<String> left, List<String> right) {
		LCS<String> lcs = new LCS<String>(left, right, new Comparator<String>() {
			
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		
		return lcs.getLCSPairs();
		
	}
	
	@Test
	public void testGetLCSPairs() {
		List<DiffData<String>> pairs = getLCSPairs(getList(), getList());
		
		for (DiffData<String> pair : pairs) {
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
	
	@Test
	public void testGetLCSPairsRename() {
		List<String> right = getList();
		right.set(3, "Armadillo");
		
		List<DiffData<String>> pairs = getLCSPairs(getList(), right);
		TestHelper.printPairs(pairs);
		assertEquals(right.size() + 1, pairs.size());
		assertEquals("Dave", pairs.get(3).getFirst());
		assertEquals("Armadillo", pairs.get(4).getSecond());
		
	}
	
	public void testGetLCSPairsMissingRight(int index) {
		List<String> right = getList();
		right.remove(index);
		
		List<DiffData<String>> pairs = getLCSPairs(getList(), right);
		
		assertEquals(right.size() + 1, pairs.size());
		assertNotNull(pairs.get(index).getFirst());
		assertNull(pairs.get(index).getSecond());
	}
	
	public void testGetLCSPairsMissingLeft(int index) {
		List<String> left = getList();
		left.remove(index);
		
		List<DiffData<String>> pairs = getLCSPairs(left, getList());
		
		assertEquals(left.size() + 1, pairs.size());
		assertNull(pairs.get(index).getFirst());
		assertNotNull(pairs.get(index).getSecond());
	}
}
