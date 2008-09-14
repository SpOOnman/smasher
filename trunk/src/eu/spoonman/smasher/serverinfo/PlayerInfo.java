package eu.spoonman.smasher.serverinfo;

import java.util.*;

public class PlayerInfo
{
    private String name;
    private int score;
    private int ping;
    private ArrayList<String> rawAttributes;
    private Dictionary<String, String> namedAttributes;
    
    @Override
    public String toString() {
    	return this.name + "\t" + this.ping + " ms\t" + this.score;
    }
    
    /**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the ping
	 */
	public int getPing() {
		return ping;
	}

	/**
	 * @param ping the ping to set
	 */
	public void setPing(int ping) {
		this.ping = ping;
	}

	/**
	 * @return the rawAttributes
	 */
	public ArrayList<String> getRawAttributes() {
		return rawAttributes;
	}

	/**
	 * @param rawAttributes the rawAttributes to set
	 */
	public void setRawAttributes(ArrayList<String> rawAttributes) {
		this.rawAttributes = rawAttributes;
	}

	/**
	 * @return the namedAttributes
	 */
	public Dictionary<String, String> getNamedAttributes() {
		return namedAttributes;
	}

	/**
	 * @param namedAttributes the namedAttributes to set
	 */
	public void setNamedAttributes(Dictionary<String, String> namedAttributes) {
		this.namedAttributes = namedAttributes;
	}

	public void setNamedAttribute (int ordinal, String name) {
        namedAttributes.put(name, rawAttributes.get(ordinal));
    }
}
