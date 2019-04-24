/**
 * Class to hold a single leaderboard entry (name and score)
 */
public class LeaderboardEntry implements Comparable{

	private String name;
	private int score;

	/**
	 * standard constructor
	 * @param name name of player
	 * @param score player's score
	 */
	public LeaderboardEntry(String name, int score){
		this.name = name;
		this.score = score;
	}

	/**
	 * getter for name
	 * @return name
	 */
    public String getName() {
        return name;
    }

	/**
	 * getter for score
	 * @return score
	 */
	public int getScore() {
        return score;
    }

	/**
	 * compareTo method to compare this to another object
	 * @param o object to compare to
	 * @return > 0 if this.score > o.score, 0 if equal, < 0 otherwise
	 */
	@Override
    public int compareTo(Object o) {
        if (o == null) return 1;
        return this.score - ((LeaderboardEntry) o).score;
    }
}
