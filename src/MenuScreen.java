import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Main menu screen that appears when application is run, has option to start game, view leaderboard, and exit game
 */
public class MenuScreen extends GameScreen {
	/**
	 * constructor that sets up the GUI with all necessary buttons and fields
	 * @param gameRunner reference to GameRunner object to use a lot of back end functions
	 */
	public MenuScreen(GameRunner gameRunner) {
		this.game = gameRunner;

		setBackground(Color.white);

		add(Box.createRigidArea(new Dimension(5, 25)));

		JButton playGame = new JButton("Start Game");
		playGame.addActionListener(this);
		playGame.setActionCommand("play");
		playGame.setVerticalTextPosition(JButton.CENTER);
		playGame.setHorizontalTextPosition(JButton.CENTER);
		playGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(playGame);

		add(Box.createRigidArea(new Dimension(5, 50)));

		JButton leaderboard = new JButton("Leaderboard");
		leaderboard.addActionListener(this);
		leaderboard.setActionCommand("leaderboard");
		leaderboard.setVerticalTextPosition(JButton.CENTER);
		leaderboard.setHorizontalTextPosition(JButton.CENTER);
		leaderboard.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(leaderboard);

		add(Box.createRigidArea(new Dimension(5, 50)));

		JButton exitGame = new JButton("Exit Game");
		exitGame.addActionListener(this);
		exitGame.setActionCommand("exit");
		exitGame.setVerticalTextPosition(JButton.CENTER);
		exitGame.setHorizontalTextPosition(JButton.CENTER);
		exitGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(exitGame);

		add(Box.createRigidArea(new Dimension(5, 25)));
	}

	/**
	 * Part of ActionListener interface, interprets button clicks, list selection, etc.
	 * @param e Action event
	 */
	public void actionPerformed(ActionEvent e) {
		if ("play".equals(e.getActionCommand())) {
			game.changeScreen(Screen.LevelSelect);
		} else if ("leaderboard".equals(e.getActionCommand())) {
			game.changeScreen(Screen.Leaderboard);
		} else {
			game.exit();
		}
	}
}
