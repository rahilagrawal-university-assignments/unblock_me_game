import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * abstract superclass for each GameScreen, acts as both JPanel and action listener
 */
public abstract class GameScreen extends JPanel implements ActionListener {
		GameRunner game;
}
