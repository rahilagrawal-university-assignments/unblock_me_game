import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Screen for difficulty selection, displays before game begins so player can select difficulty level
 */
public class DifficultyScreen extends GameScreen {
	private String[] difficulties = { "Easy", "Medium", "Hard" };

	/**
	 * constructor that sets up the GUI with all necessary buttons and fields
	 * @param gameRunner reference to GameRunner object to use a lot of back end functions
	 */
	public DifficultyScreen(GameRunner gameRunner) {
		this.game = gameRunner;

		setBackground(Color.white);


		add(Box.createRigidArea(new Dimension(10, 40)));


		//Create the combo box, select the item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		JComboBox difficultyList = new JComboBox(difficulties);
		difficultyList.setEditable(false);
		difficultyList.addActionListener(this);
		difficultyList.setActionCommand("difficulty");
		difficultyList.setMaximumSize(new Dimension(150, 20));
		add(difficultyList);

		add(Box.createRigidArea(new Dimension(10, 40)));

		JButton mainMenu = new JButton("Play Game");
		mainMenu.addActionListener(this);
		mainMenu.setActionCommand("play");
		mainMenu.setVerticalTextPosition(JButton.CENTER);
		mainMenu.setHorizontalTextPosition(JButton.CENTER);
		mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainMenu.setAlignmentY(Component.TOP_ALIGNMENT);
		add(mainMenu);


		add(Box.createRigidArea(new Dimension(5, 50)));
	}

	/**
	 * Part of ActionListener interface, interprets button clicks, list selection, etc.
	 * @param e Action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("play".equals(e.getActionCommand())) {
			game.changeScreen(Screen.Grid);
		} else {
			JComboBox cb = (JComboBox) e.getSource();
			int index = cb.getSelectedIndex();
			Difficulty difficulty;
			switch (index) {
				case 0 : difficulty = Difficulty.Easy; break;
				case 1 : difficulty = Difficulty.Medium; break;
				default : difficulty = Difficulty.Hard; break;
			}
			game.setDifficulty(difficulty);
		}
	}
}
