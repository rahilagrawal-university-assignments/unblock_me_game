import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Screen that game is played on, has option to return to main menu and can play the game
 */
public class GridScreen extends GameScreen {

	int size = 6;

	/**
	 * constructor that sets up the GUI with all necessary buttons and fields
	 * @param gameRunner reference to GameRunner object to use a lot of back end functions
	 */
	public GridScreen(GameRunner gameRunner) {
		this.game = gameRunner;

		//creating a new game
		game.newGame(gameRunner.getDifficulty());

		setBackground(Color.WHITE);

		Box box = Box.createHorizontalBox();
		box.setMaximumSize(new Dimension(111 * size, 10));

		JButton mainMenu = new JButton("Main Menu");
		mainMenu.addActionListener(this);
		mainMenu.setActionCommand("main");
		mainMenu.setVerticalTextPosition(JButton.CENTER);
		mainMenu.setHorizontalTextPosition(JButton.CENTER);
		mainMenu.setAlignmentX(Component.LEFT_ALIGNMENT);
		mainMenu.setAlignmentY(Component.TOP_ALIGNMENT);
		box.add(mainMenu);
/*
		JButton undo = new JButton("Undo");
		undo.addActionListener(this);
		undo.setActionCommand("undo");
		undo.setVerticalTextPosition(JButton.CENTER);
		undo.setHorizontalTextPosition(JButton.CENTER);
		undo.setAlignmentX(Component.LEFT_ALIGNMENT);
		undo.setAlignmentY(Component.TOP_ALIGNMENT);
		box.add(undo);

		JButton reset = new JButton("Reset");
		reset.addActionListener(this);
		reset.setActionCommand("reset");
		reset.setVerticalTextPosition(JButton.CENTER);
		reset.setHorizontalTextPosition(JButton.CENTER);
		reset.setAlignmentX(Component.LEFT_ALIGNMENT);
		reset.setAlignmentY(Component.TOP_ALIGNMENT);
		box.add(reset);
*/
		add(box);

		add(Box.createRigidArea(new Dimension(10, 5)));

		GridPanel panel = new GridPanel(size, game.getGameState());
        Mover mover = new Mover(panel, game);
		add(mover);

	}

	/**
	 * Part of ActionListener interface, interprets button clicks, list selection, etc.
	 * @param e Action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("main".equals(e.getActionCommand())) {
			game.changeScreen(Screen.Main);
		}
	}
}
