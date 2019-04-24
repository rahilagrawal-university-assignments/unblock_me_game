import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * GameRunner class, majority of back end controls lie in this class, creates and runs the game.
 */
public class GameRunner {
	private JFrame frame;
	private int gridSize = 6;
    private Difficulty difficulty = Difficulty.Easy;
    private LevelGenerator levelGenerator = new LevelGenerator();
    private GameState gameState = levelGenerator.generateLevel(gridSize, difficulty);
    private int score = 0;

	/**
	 * main method, creates instance of GameRunner and runs game
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
        GameRunner gameRunner = new GameRunner();
        gameRunner.runGame();
    }

	/**
	 * method to run game
	 */
	private void runGame() {
    	createGUI();

		//runTerminalGame();
    }

	/**
	 * method to create gui which begins running game
	 */
	private void createGUI() {
		frame = new JFrame();
		frame.setSize(gridSize * 100, gridSize * 105);
		frame.setMinimumSize(new Dimension(gridSize*100, gridSize*105));
		frame.setPreferredSize(new Dimension(gridSize*100, gridSize*105));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameScreen gameScreen = new MenuScreen(this);
		frame.setContentPane(gameScreen);
		frame.setLayout(new BoxLayout(gameScreen, BoxLayout.Y_AXIS));
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * method to run game in terminal (unused)
	 */
	private void runTerminalGame() {
		gameState.getGrid().printGrid();
		while (!gameState.isSolved()) {
			Scanner sc = new Scanner(System.in);
			String[] parameters = sc.nextLine().trim().replaceAll(" +", " ").split(" ");
			int vehicleId = Integer.parseInt(parameters[0]);
			int direction = Integer.parseInt(parameters[1]);
			gameState.updateGameState(vehicleId, direction);
			gameState.getGrid().printGrid();
		}
		if (gameState.isSolved()) {
			System.out.println("CONGRATULATIONS");
		}
	}

	/**
	 * method to change current screen showing in frame
	 * @param screen Screen to select (Enum Screen)
	 */
	public void changeScreen(Screen screen) {
		GameScreen newScreen;
		switch (screen) {
			case Main: newScreen = new MenuScreen(this); break;
			case Leaderboard: newScreen = new LeaderboardScreen(this); break;
			case Grid: newScreen = new GridScreen(this); break;
			case Finished: newScreen = new FinishedScreen(this); break;
			case LevelSelect: newScreen = new DifficultyScreen(this); break;
			default: newScreen = new MenuScreen(this);
		}
		frame.setContentPane(newScreen);
		frame.setLayout(new BoxLayout(newScreen, BoxLayout.Y_AXIS));
		frame.validate();
	}

	/**
	 * method to create a new game based on specified difficulty
	 * @param difficulty selected difficulty
	 */
	public void newGame(Difficulty difficulty) {
    	score = 0;
		gameState = levelGenerator.generateLevel(gridSize, difficulty);
	}

	/**
	 * incrments score count
	 */
	public void incrementScore() {
		score++;
	}

	/**
	 * method to check if game is completed
	 * @return true if finished, false otherwise
	 */
	public boolean isFinished() {
    	return gameState.isSolved();
	}

	/**
	 * method called when game is finished, replaces current screen with game finished screen
	 */
	public void finishGame() {
    	changeScreen(Screen.Finished);
	}

	/**
	 * getter for gameState
	 * @return gameState
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * getter for frame
	 * @return frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * getter for score
	 * @return score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * exits game and closes window
	 */
	public void exit() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	/**
	 * method to add score to leaderboard
	 * @param name of player
	 */
	public void addScore(String name) {
    	LeaderboardEntry entry = new LeaderboardEntry(name, score);
    	Leaderboard leaderboard = new Leaderboard();
    	try {
			leaderboard.addLeaderboardEntry(entry, this.difficulty);
		} catch (Exception e) {}
	}

	/**
	 * method to set game difficulty
	 * @param difficulty difficulty to set to
	 */
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
