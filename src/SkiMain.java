import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import javax.swing.JFrame;

/**
 * Creates the JFrame for Ultimate Downhill Ski Chase and plays the game using
 * the SkiGame class
 * 
 * @author Frank Long and Daniel Rawana
 * @version June 13, 2015
 */
public class SkiMain extends JFrame {
	// Variable for game
	private SkiGame game;

	/**
	 * Constructs a new SkiMain frame (sets up the game)
	 * 
	 * @throws FileNotFoundException
	 *             if highscores file are not found
	 */
	public SkiMain() throws FileNotFoundException {
		// Sets up frame
		super("ULTIMATE DOWNHILL SKI CHASE");
		setResizable(false);

		// Sets up icon image
		setIconImage(Toolkit.getDefaultToolkit().getImage("skiier.png"));

		// Sets up the ski game in the centre of the frame and places it in the
		// centre of the screen
		game = new SkiGame();
		add(game, BorderLayout.CENTER);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - game.panelSize.width) / 2, (screen.height - game.panelSize.height) / 2 - 50);
		System.out.println(screen.height);
		System.out.println(screen.width);
	}

	/**
	 * Sets up the SkiMain frame
	 * 
	 * @param args
	 *            array of strings (ignored)
	 * @throws FileNotFoundException
	 *             if highscores file is not found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// Starts up the SkiMain frame
		SkiMain frame = new SkiMain();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}
