import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;

/**
 * Leaderboard screen, has option to return to main menu and select difficulty to display for leaderboard
 */
public class LeaderboardScreen extends GameScreen {
	private String[] difficulties = { "Easy", "Medium", "Hard" };
	private JTextPane currLeaderboard;
	private Leaderboard leaderboard = new Leaderboard();

	/**
	 * constructor that sets up the GUI with all necessary buttons and fields
	 * @param gameRunner reference to GameRunner object to use a lot of back end functions
	 */
	public LeaderboardScreen(GameRunner gameRunner) {
		this.game = gameRunner;

		setBackground(Color.white);

		JButton mainMenu = new JButton("Main Menu");
		mainMenu.addActionListener(this);
		mainMenu.setActionCommand("main");
		mainMenu.setVerticalTextPosition(JButton.CENTER);
		mainMenu.setHorizontalTextPosition(JButton.CENTER);
		mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainMenu.setAlignmentY(Component.TOP_ALIGNMENT);
		add(mainMenu);

		add(Box.createRigidArea(new Dimension(10, 40)));


		//Create the combo box, select the item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		JComboBox difficultyList = new JComboBox(difficulties);
		difficultyList.setEditable(false);
		difficultyList.addActionListener(this);
		difficultyList.setActionCommand("list");
		difficultyList.setMaximumSize(new Dimension(150, 110));
		add(difficultyList);

		add(Box.createRigidArea(new Dimension(10, 40)));

		Box box = Box.createHorizontalBox();
		box.add(Box.createRigidArea(new Dimension(250, 10)));

		currLeaderboard = new JTextPane();
		currLeaderboard.setEditable(false);
		currLeaderboard.setBackground(Color.white);
		currLeaderboard.setAlignmentX(Component.CENTER_ALIGNMENT);
		currLeaderboard.setAlignmentY(Component.CENTER_ALIGNMENT);
		//stylising text
		StyledDocument doc = currLeaderboard.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);


		box.add(currLeaderboard);

		add(box);

		add(Box.createRigidArea(new Dimension(5, 50)));
	}

	/**
	 * method to add leaderboard text to leaderboard panel
	 * @param difficulty selected difficulty
	 */
	private void showLeaderboard(String difficulty) {

		// clearing text
		currLeaderboard.setText("");

		Iterator<LeaderboardEntry> leaderboardIterator;
		if (difficulty.equals("Easy")) {
			leaderboardIterator = leaderboard.getLeaderboard(Difficulty.Easy);

		} else if (difficulty.equals("Medium")) {
			leaderboardIterator = leaderboard.getLeaderboard(Difficulty.Medium);

		} else {
			leaderboardIterator = leaderboard.getLeaderboard(Difficulty.Hard);
		}

		int i;
		LeaderboardEntry entry;
		for (i = 1; leaderboardIterator.hasNext() && i <= 10; i++) {
			entry = leaderboardIterator.next();
			appendLeaderboard(String.format("%-5d %20.20s %10d\n", i, entry.getName(), entry.getScore()));
		}


	}

	/**
	 * Part of ActionListener interface, interprets button clicks, list selection, etc.
	 * @param e Action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("main".equals(e.getActionCommand())) {
			game.changeScreen(Screen.Main);
		} else {
			JComboBox cb = (JComboBox) e.getSource();
			String difficulty = (String) cb.getSelectedItem();
			showLeaderboard(difficulty);
			game.getFrame().validate();
		}
	}

	/**
	 * method to append leaderboard entry as a string to the end of the existing list
	 * @param s string to append
	 */
	private void appendLeaderboard(String s) {
		try {
			Document doc = currLeaderboard.getDocument();
			doc.insertString(doc.getLength(), s, null);
		} catch(BadLocationException exc) {
			exc.printStackTrace();
		}
	}
}
