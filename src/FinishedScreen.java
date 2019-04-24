import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Screen that appears when game is finished, has option to enter name for leaderboard, displays score, and returns to
 * main menu
 */
public class FinishedScreen extends GameScreen {

	private JTextPane nameField;
	private JButton submitName;

	/**
	 * constructor that sets up the GUI with all necessary buttons and fields
	 * @param gameRunner reference to GameRunner object to use a lot of back end functions
	 */
	public FinishedScreen(GameRunner gameRunner) {
		this.game = gameRunner;

		setBackground(Color.white);

		add(Box.createRigidArea(new Dimension(10, 40)));

		JButton mainMenu = new JButton("Main Menu");
		mainMenu.addActionListener(this);
		mainMenu.setActionCommand("main");
		mainMenu.setVerticalTextPosition(JButton.CENTER);
		mainMenu.setHorizontalTextPosition(JButton.CENTER);
		mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainMenu.setAlignmentY(Component.TOP_ALIGNMENT);
		add(mainMenu);

		add(Box.createRigidArea(new Dimension(10, 40)));

		JTextPane finishedText = new JTextPane();
		finishedText.setText("Congratulations, you won!");
		finishedText.setEditable(false);
		finishedText.setMaximumSize(new Dimension(600, 150));
		finishedText.setAlignmentY(Component.CENTER_ALIGNMENT);
		//stylising text
		StyledDocument doc = finishedText.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		Font f = finishedText.getFont();
		Font newFont = new Font(f.getFontName(), f.getStyle(), 30);
		finishedText.setFont(newFont);
		add(finishedText);

		JTextPane scoreText = new JTextPane();
		scoreText.setText("Your score was " + game.getScore());
		scoreText.setEditable(false);
		scoreText.setMaximumSize(new Dimension(600, 100));
		scoreText.setAlignmentY(Component.CENTER_ALIGNMENT);
		//stylising text
		doc = scoreText.getStyledDocument();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		f = scoreText.getFont();
		newFont = new Font(f.getFontName(), f.getStyle(), 25);
		scoreText.setFont(newFont);
		add(scoreText);

//		add(Box.createRigidArea(new Dimension(10, 10)));

		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.setMaximumSize(new Dimension(600, 100));

		JTextPane enterName = new JTextPane();
		enterName.setText("Please enter name: ");
		enterName.setEditable(false);
		enterName.setMaximumSize(new Dimension(200, 30));

		//stylising text
		doc = enterName.getStyledDocument();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		f = enterName.getFont();
		newFont = new Font(f.getFontName(), f.getStyle(), 20);
		enterName.setFont(newFont);
		box.add(enterName);

		nameField = new JTextPane();
		nameField.setEditable(true);
		nameField.setBackground(Color.GRAY);
		nameField.setMaximumSize(new Dimension(200, 30));
		//stylising text
		doc = nameField.getStyledDocument();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		f = nameField.getFont();
		newFont = new Font(f.getFontName(), f.getStyle(), 20);
		nameField.setFont(newFont);
		box.add(nameField);

		submitName = new JButton("Submit");
		submitName.addActionListener(this);
		submitName.setActionCommand("submit");
		submitName.setVerticalTextPosition(JButton.CENTER);
		submitName.setHorizontalTextPosition(JButton.CENTER);
		box.add(submitName);

		add(box);
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
			nameField.setEditable(false);
			submitName.setEnabled(false);
			game.addScore(nameField.getText());
		}
	}
}
