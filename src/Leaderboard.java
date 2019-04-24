import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Leaderboard class that reads relevant leaderboard files (based on difficulty) and writes to them
 */
public class Leaderboard implements Iterable {
    private PriorityQueue<LeaderboardEntry> leaderboard;
    private int difficulty;

	/**
	 * default constructor
	 */
	public Leaderboard(){
        this.leaderboard = new PriorityQueue<>();
    }

	/**
	 * method to get Leaderboard for a certain difficulty
	 * @param difficulty Difficulty enum with specified difficulty
	 * @return iterator of leaderboard entries
	 */
	public Iterator<LeaderboardEntry> getLeaderboard(Difficulty difficulty){
		switch (difficulty) {
			case Easy: this.difficulty = 1; break;
			case Medium: this.difficulty = 2; break;
			default: this.difficulty = 3; break;
		}
        return iterator();
    }

	/**
	 * method to read from file and return iterator
	 * @return iterator over selected leaderboard entries (based on difficulty)
	 */
	@Override
    public Iterator<LeaderboardEntry> iterator() {
        Scanner sc = null;
        String filename = "leaderboard" + this.difficulty + ".csv";
        leaderboard.clear();
        try
        {
            sc = new Scanner(new File(filename));
            while(sc.hasNextLine()){
                String[] args = sc.nextLine().split(" ");
                LeaderboardEntry entry = new LeaderboardEntry(args[0], Integer.parseInt(args[1]));
                leaderboard.add(entry);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            if (sc != null) sc.close();
        }
        return leaderboard.iterator();
    }

	/**
	 * method to add leaderboard entry
	 * @param entry entry to add
	 * @param difficulty difficulty (to find relevant leaderboard)
	 * @pre true
	 * @post adds entry to leaderboard if the score is less than the lowest score on the leaderboard
	 * @throws IOException if can't find file
	 */
    public void addLeaderboardEntry(LeaderboardEntry entry, Difficulty difficulty) throws IOException {
        // Copy existing leader board from the file and add it to a PQ
		int diffNum = 0;
		switch (difficulty) {
			case Easy: diffNum = 1; break;
			case Medium: diffNum = 2; break;
			case Hard: diffNum = 3; break;
		}
        String filename = "leaderboard" + diffNum + ".csv";
        File file = new File(filename);
        if (!file.exists()) {
            while (file.createNewFile() != true) {
            }
        }
        FileInputStream fileInput = null;
        BufferedReader br = null;
        try
        {
            String[] args;
            String line;
            fileInput = new FileInputStream(
                    file);
            br = new BufferedReader(new InputStreamReader(
                    fileInput));
            LeaderboardEntry worstScore = null;
            while((line = br.readLine()) != null){
                args = line.split(" ");
                LeaderboardEntry e = new LeaderboardEntry(args[0], Integer.parseInt(args[1]));
                worstScore = e;
                leaderboard.add(e);
            }
            // Add the new entry to the PQ
            if (entry.compareTo(worstScore) < 0 || leaderboard.size() < 10) {
				leaderboard.add(entry);
			}
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        finally {
            if(br != null) br.close();
            fileInput.close();
        }

        // Empty the file and then write the new PQ to it

        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));

        try
        {
        	int i = 0;
            while(!leaderboard.isEmpty() && i < 10){
                LeaderboardEntry e = leaderboard.poll();
                bw.write(e.getName() + " " + e.getScore() + "\n");
                i++;
            }
        } finally {
            if (bw != null)
                bw.close();
        }

    }
}

