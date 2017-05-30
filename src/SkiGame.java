import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Creates the SkiGame class
 * 
 * @author Frank Long and Daniel Rawana
 * @version June 13, 2015
 */
public class SkiGame extends JPanel implements MouseListener, KeyListener {
	// Set panel dimensions

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int panelWidth = (int) (screenSize.width / 2.732);
	private int panelHeight = (int) (screenSize.height / 1.239);
	Dimension panelSize = new Dimension(panelWidth, panelHeight);

	// Create timers and related variables
	private int TIME_INTERVAL = 100;
	private Timer movementTimer;
	private Timer timer;
	private Timer moneyTimer;
	private Timer playTimer;
	private Timer gateTimer;
	private Timer bonusTimer;
	private Timer robberTimer;
	private boolean bonus = false;
	private int time = 0;
	private int robberTime;

	// Create fonts
	final Font TIME_FONT = new Font("Arial", Font.PLAIN, (int) (panelHeight / 22.7));
	final Font MED_MENU_FONT = new Font("Arial", Font.PLAIN, (int) (panelHeight / 33.3));
	final Font LARGE_MENU_FONT = new Font("Impact", Font.PLAIN, (int) (panelHeight / 11.63));

	// Create images
	private Image mainMenu;
	private Image[] obstacleImages;
	private Image money;
	private Image player1Image;
	private Image robber;
	private Image gate;
	private Image imageBackground;
	private Image rightArrow;
	private Image leftArrow;
	private Image pauseButton;
	private Image playButton;
	private Image arrowKeys;

	// Object dimensions
	final int PLAYER_WIDTH = (int) (panelWidth / 12.5);
	final int PLAYER_HEIGHT = (int) (panelHeight / 15.625);
	final int ROCK_WIDTH = (int) (panelWidth / 12.5);
	final int ROCK_HEIGHT = (int) (panelHeight / 15.625);
	final int TREE_WIDTH = (int) (panelWidth / 12.5);
	final int TREE_HEIGHT = (int) (panelHeight / 15.625 * 2);
	final int MONEY_HEIGHT = (int) (panelHeight / 35.7);
	final int MONEY_WIDTH = (int) (panelWidth / 15.4);
	final int GATE_HEIGHT = (int) (panelHeight / 62.5);
	final int GATE_WIDTH = (int) (panelWidth / 8.3);
	final int ARROW_HEIGHT = (int) (panelHeight / 15.625);
	final int ARROW_WIDTH = (int) (panelWidth / 9);
	final int BUTTON_HEIGHT = (int) (panelHeight / 13.3);
	final int BUTTON_WIDTH = (int) (panelWidth / 10.7);
	final int KEYS_HEIGHT = (int) (panelHeight / 3.53);
	final int KEYS_WIDTH = (int) (panelWidth / 2);

	// Object information
	private String name = "";
	private Player player1;
	private int[] obstaclesX = new int[10];
	private int[] obstaclesY = { panelHeight + panelHeight / 20, panelHeight + 1 * panelHeight / 10 + panelHeight / 20,
			panelHeight + 2 * panelHeight / 10 + panelHeight / 20,
			panelHeight + 3 * panelHeight / 10 + panelHeight / 20,
			panelHeight + 4 * panelHeight / 10 + panelHeight / 20,
			panelHeight + 5 * panelHeight / 10 + panelHeight / 20,
			panelHeight + 6 * panelHeight / 10 + panelHeight / 20,
			panelHeight + 7 * panelHeight / 10 + panelHeight / 20,
			panelHeight + 8 * panelHeight / 10 + panelHeight / 20,
			panelHeight + 9 * panelHeight / 10 + panelHeight / 20 };
	private int moneyX = -100;
	private int moneyY = -100;
	private int gateX = -100;
	private int gateY = -100;
	private int robberX = panelWidth / 2;
	private int robberY = 0;
	private int itemNo = 0;
	private boolean up = false;
	private boolean down = false;
	private boolean right = false;
	private boolean left = false;

	// Create high scores array for scores and names
	private String[] highNames = new String[5];
	private int[] highScores = new int[5];

	private int screen = 0; // 0-main, 1-enter name, 2-play, 3-instructions,
							// 4-instructions,
							// 5-high scores, 6-credits
	private boolean paused;

	// Define Rectangular areas are hot spots for graphical buttons
	final Rectangle PLAY_BUTTON = new Rectangle(panelWidth / 16, (int) (panelHeight / 1.96), panelWidth / 5,
			(int) (panelHeight / 11.1));
	final Rectangle INSTRUCTIONS_BUTTON = new Rectangle(panelWidth / 16, (int) (panelHeight / 1.57),
			(int) (panelWidth / 1.6), (int) (panelHeight / 11.1));
	final Rectangle INSTRUCTIONS_PLAY_BUTTON = new Rectangle((int) (panelWidth / 1.23), (int) (panelHeight / 1.14),
			(int) (panelWidth / 10.67), (int) (panelHeight / 22.2));
	final Rectangle SCORES_BUTTON = new Rectangle(panelWidth / 16, (int) (panelHeight / 1.32), (int) (panelWidth / 1.8),
			(int) (panelHeight / 11.1));
	final Rectangle CREDITS_BUTTON = new Rectangle(panelWidth / 16, (int) (panelHeight / 1.13),
			(int) (panelWidth / 2.86), (int) (panelHeight / 11.1));
	final Rectangle TRY_AGAIN_BUTTON = new Rectangle((int) (panelWidth / 1.86), (int) (panelHeight / 1.85),
			(int) (panelWidth / 4.7), (int) (panelHeight / 33.3));
	final Rectangle GAME_MENU_BUTTON = new Rectangle((int) (panelWidth / 3.48), (int) (panelHeight / 1.85),
			(int) (panelWidth / 8), (int) (panelHeight / 33.3));
	final Rectangle PAUSE_BUTTON = new Rectangle((int) (panelWidth / 80), (int) (panelHeight / 100),
			(int) (panelWidth / 10.67), (int) (panelHeight / 13.3));
	final Rectangle MAIN_MENU_BUTTON = new Rectangle((int) (panelWidth / 2.29), (int) (panelHeight / 1.14),
			(int) (panelWidth / 10), (int) (panelHeight / 33.3));
	final Rectangle RIGHT_ARROW = new Rectangle((int) (panelWidth / 1.23), (int) (panelHeight / 1.15),
			(int) (panelWidth / 9), (int) (panelHeight / 15.625));
	final Rectangle LEFT_ARROW = new Rectangle((int) (panelWidth / 13.1), (int) (panelHeight / 1.15),
			(int) (panelWidth / 9), (int) (panelHeight / 15.625));

	public SkiGame() throws FileNotFoundException {
		// Sets up the board area, loads in piece images and starts a new game
		setPreferredSize(panelSize);
		setBackground(new Color(250, 250, 250));

		// Create an array for the obstacles and load them up
		// Also load up all other images to be used in the program
		mainMenu = new ImageIcon("main menu.fw.png").getImage().getScaledInstance(panelWidth, panelHeight,
				Image.SCALE_DEFAULT);
		obstacleImages = new Image[10];
		imageBackground = new ImageIcon("map mountain.png").getImage().getScaledInstance(panelWidth, panelHeight,
				Image.SCALE_DEFAULT);
		obstacleImages[0] = new ImageIcon("tree.fw.png").getImage().getScaledInstance(TREE_WIDTH, TREE_HEIGHT,
				Image.SCALE_DEFAULT); // https://s-media-cache-ak0.pinimg.com/736x/1d/eb/02/1deb024cd57d262eafce40b8712c9e4b.jpg
		obstacleImages[1] = new ImageIcon("boulder.fw.png").getImage().getScaledInstance(ROCK_WIDTH, ROCK_HEIGHT,
				Image.SCALE_DEFAULT); // http://www.diversifiedpondsupplies.com/sites/www.diversifiedpondsupplies.com/files/imagecache/product_full/realrock_boulder_riverbed-dlb2r_0.jpg
		obstacleImages[2] = new ImageIcon("tree.fw.png").getImage().getScaledInstance(TREE_WIDTH, TREE_HEIGHT,
				Image.SCALE_DEFAULT);
		obstacleImages[3] = new ImageIcon("boulder.fw.png").getImage().getScaledInstance(ROCK_WIDTH, ROCK_HEIGHT,
				Image.SCALE_DEFAULT);
		obstacleImages[4] = new ImageIcon("tree.fw.png").getImage().getScaledInstance(TREE_WIDTH, TREE_HEIGHT,
				Image.SCALE_DEFAULT);
		obstacleImages[5] = new ImageIcon("boulder.fw.png").getImage().getScaledInstance(ROCK_WIDTH, ROCK_HEIGHT,
				Image.SCALE_DEFAULT);
		obstacleImages[6] = new ImageIcon("tree.fw.png").getImage().getScaledInstance(TREE_WIDTH, TREE_HEIGHT,
				Image.SCALE_DEFAULT);
		obstacleImages[7] = new ImageIcon("boulder.fw.png").getImage().getScaledInstance(ROCK_WIDTH, ROCK_HEIGHT,
				Image.SCALE_DEFAULT);
		obstacleImages[8] = new ImageIcon("tree.fw.png").getImage().getScaledInstance(TREE_WIDTH, TREE_HEIGHT,
				Image.SCALE_DEFAULT);
		obstacleImages[9] = new ImageIcon("boulder.fw.png").getImage().getScaledInstance(ROCK_WIDTH, ROCK_HEIGHT,
				Image.SCALE_DEFAULT);
		money = new ImageIcon("cash.png").getImage().getScaledInstance(MONEY_WIDTH, MONEY_HEIGHT, Image.SCALE_DEFAULT);
		gate = new ImageIcon("gate.png").getImage().getScaledInstance(GATE_WIDTH, GATE_HEIGHT, Image.SCALE_DEFAULT);
		player1Image = new ImageIcon("skiier.png").getImage().getScaledInstance(PLAYER_WIDTH, PLAYER_HEIGHT,
				Image.SCALE_DEFAULT);
		robber = new ImageIcon("robber.png").getImage().getScaledInstance(PLAYER_WIDTH, PLAYER_HEIGHT,
				Image.SCALE_DEFAULT);
		rightArrow = new ImageIcon("right arrow.png").getImage().getScaledInstance(ARROW_WIDTH, ARROW_HEIGHT,
				Image.SCALE_DEFAULT);
		leftArrow = new ImageIcon("left arrow.png").getImage().getScaledInstance(ARROW_WIDTH, ARROW_HEIGHT,
				Image.SCALE_DEFAULT);
		arrowKeys = new ImageIcon("arrow_keys.png").getImage().getScaledInstance(KEYS_WIDTH, KEYS_HEIGHT,
				Image.SCALE_DEFAULT); // http://what-dah-flip.weebly.com/uploads/2/1/7/6/21762700/arrow_keys.png
		pauseButton = new ImageIcon("pause.png").getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT,
				Image.SCALE_DEFAULT); // http://cdn.mysitemyway.com/etc-mysitemyway/icons/legacy-previews/icons/simple-black-square-icons-media/127166-simple-black-square-icon-media-a-media27-pause-sign.png
		playButton = new ImageIcon("play.png").getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT,
				Image.SCALE_DEFAULT); // http://cdn.mysitemyway.com/etc-mysitemyway/icons/legacy-previews/icons/simple-black-square-icons-media/127161-simple-black-square-icon-media-a-media22-arrow-forward1.png

		// Add mouse listeners and Key Listeners to the game board
		addMouseListener(this);
		setFocusable(true);
		addKeyListener(this);
		this.addMouseMotionListener(new MouseMotionHandler());
		requestFocusInWindow();

		// Define the various timers
		timer = new Timer(TIME_INTERVAL, new TimerEventHandler());
		moneyTimer = new Timer(8000, new TimerEventHandler());
		playTimer = new Timer(1000, new TimerEventHandler());
		gateTimer = new Timer(30000, new TimerEventHandler());
		bonusTimer = new Timer(10000, new TimerEventHandler());
		movementTimer = new Timer(40, new TimerEventHandler());
		robberTimer = new Timer(30, new TimerEventHandler());

		// Populate the highscores array from the highscores file
		Scanner highScoresIn = new Scanner(new File("highscores.txt"));
		Scanner highNamesIn = new Scanner(new File("highnames.txt"));
		for (int score = 0; score < highScores.length; score++) {
			highScores[score] = highScoresIn.nextInt();
			highNames[score] = highNamesIn.nextLine();
		}
	}

	/**
	 * Start timers to prepare for game and show robber animation
	 */
	public void startGame() {
		player1 = new Player(name, panelSize);
		robberTime = 0;
		robberTimer.restart();
		timer.setInitialDelay(3000 + TIME_INTERVAL);
		moneyTimer.setInitialDelay(3000 + 8000);
		gateTimer.setInitialDelay(3000 + 30000);
		playTimer.setInitialDelay(3000 + 1000);
		movementTimer.setInitialDelay(3000 + 40);
		timer.restart();
		moneyTimer.restart();
		gateTimer.restart();
		playTimer.restart();
		movementTimer.restart();
	}

	/**
	 * Reset positions and timers for game
	 */
	public void reset() {
		player1.start();
		up = false;
		down = false;
		right = false;
		left = false;
		time = 0;
		TIME_INTERVAL = 100;
		itemNo = 0;
		obstaclesX = new int[10];
		for (int obs = 0; obs < obstaclesY.length; obs++) {
			obstaclesY[obs] = panelHeight + panelHeight / 20 + obs * panelHeight / 10;
		}
		moneyX = -100;
		moneyY = -100;
		screen = 2;
		gateY = -100;
		gateX = -100;
		robberY = 0;
		bonus = false;
	}

	/**
	 * Add an item to the map
	 */
	public void addItem() {
		if (itemNo < obstacleImages.length) {
			int randomX = (int) (Math.random() * (panelWidth - panelWidth / 3) + panelWidth / 8);
			obstaclesX[itemNo] = randomX;
		}
		itemNo++;
	}

	/**
	 * Check to see if the player is collided with environment
	 * 
	 * @return true if the player has collided
	 */
	public boolean isCollision() {
		// Check left/right of map
		if (player1.showXPos() > panelWidth - panelWidth / 4.88 || player1.showXPos() < panelWidth / 8) {
			return true;
		}

		// Check Obstacles
		for (int item = 0; item < obstacleImages.length; item++) {
			Rectangle playerBox = new Rectangle(player1.showXPos(), player1.showYPos(), PLAYER_WIDTH - panelWidth / 40,
					PLAYER_HEIGHT - panelHeight / 50);
			Rectangle rockBox = new Rectangle(obstaclesX[item], obstaclesY[item], ROCK_WIDTH - panelWidth / 40,
					ROCK_HEIGHT - (int) (panelHeight / 33.3));
			Rectangle treeBox = new Rectangle(obstaclesX[item], obstaclesY[item], TREE_WIDTH - panelWidth / 40,
					TREE_HEIGHT - (int) (panelHeight / 33.3));

			// Tree check
			if (item % 2 == 0) {
				if (playerBox.intersects(treeBox)) {
					return true;
				}
			} else
			// Boulder check
			{
				if (playerBox.intersects(rockBox)) {
					return true;
				}
			}
		}
		return false;

	}

	/**
	 * Checks if player is on money
	 * 
	 * @return whether or not the player is on money
	 */
	public boolean isMoneyHere() {
		if (Math.abs(player1.showXPos() - moneyX) < panelWidth / 12.5
				&& Math.abs(player1.showYPos() - moneyY) < panelHeight / 15.625)
			return true;
		return false;
	}

	/**
	 * Checks if player is in gate
	 * 
	 * @return true if player is in gate, false if player is not
	 */
	public boolean isGateHere() {
		Rectangle playerBox = new Rectangle(player1.showXPos() + panelWidth / 160, player1.showYPos(),
				PLAYER_WIDTH - panelWidth / 80, PLAYER_HEIGHT - panelHeight / 50);
		Rectangle gateBox = new Rectangle(gateX + (int) (panelWidth / 16.67), gateY, 1, panelHeight / 100);

		if (playerBox.intersects(gateBox))
			return true;
		return false;
	}

	/**
	 * Adds money to the map
	 */
	public void addMoney() {
		moneyX = (int) (Math.random() * (panelWidth - panelWidth / 2.67) + panelWidth / 8);
		moneyY = panelHeight + panelHeight / 20;
	}

	/**
	 * Sets position and adds gate to map
	 */
	public void addGate() {
		gateX = (int) (Math.random() * (panelWidth - panelWidth / 2.67) + panelWidth / 8);
		gateY = panelHeight + panelHeight / 20;
	}

	/**
	 * Stops timers and pauses the game
	 */
	public void pause() {
		// Stop all timers
		timer.stop();
		moneyTimer.stop();
		playTimer.stop();
		gateTimer.stop();
		bonusTimer.stop();
		movementTimer.stop();

		paused = true;
	}

	/**
	 * Restarts timers and unpause the game
	 */
	public void unpause() {
		// Reset initial delay of timers back to normal
		timer.setInitialDelay(TIME_INTERVAL);
		moneyTimer.setInitialDelay(8000);
		gateTimer.setInitialDelay(30000);
		playTimer.setInitialDelay(1000);
		movementTimer.setInitialDelay(40);

		// Restart all timers
		timer.start();
		moneyTimer.start();
		playTimer.start();
		gateTimer.start();
		bonusTimer.start();
		movementTimer.start();
		paused = false;
	}

	/**
	 * Updates the high scores and names of corresponding players after a new
	 * score is given
	 * 
	 * @param highScores
	 *            array of high scores
	 * @param score
	 *            score of new player
	 * @throws IOException
	 *             if fail to print write
	 */
	public void updateHighscores(int[] highScores, int score) throws IOException {
		// Compare new player's score with last player on top 5, only update if
		// new player makes it

		// Find the position of the player's score in the high scores
		if (score > highScores[highScores.length - 1]) {
			int playerRank = 0;
			while (score < highScores[playerRank]) {
				playerRank++;
			}
			for (int topPlayer = highScores.length - 1; topPlayer > playerRank; topPlayer--) {
				highScores[topPlayer] = highScores[topPlayer - 1];
				highNames[topPlayer] = highNames[topPlayer - 1];
			}

			// Update the high scores arrays and text files.
			highScores[playerRank] = score;
			highNames[playerRank] = name;
			PrintWriter scoreOut = new PrintWriter("highscores.txt");
			PrintWriter nameOut = new PrintWriter("highnames.txt");
			for (int highScore = 0; highScore < highScores.length; highScore++) {
				nameOut.println(highNames[highScore]);
				scoreOut.println(highScores[highScore]);
			}
			scoreOut.close();
			nameOut.close();
		}
	}

	/**
	 * Repaint the drawing panel
	 * 
	 * @param g
	 *            The Graphics context
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw the main menu
		if (screen == 0) {
			g.setFont(LARGE_MENU_FONT);
			g.drawImage(mainMenu, 0, 0, this);
			g.drawString("ULTIMATE DOWNHILL", panelWidth / 16, panelHeight / 5);
			g.drawString("SKI CHASE", (int) (panelWidth / 4.35), (int) (panelHeight / 3.3));
			g.drawString("PLAY", panelWidth / 16, (int) (panelHeight / 1.67));
			g.drawString("INSTRUCTIONS", panelWidth / 16, (int) (panelHeight / 1.38));
			g.drawString("HIGH SCORES", panelWidth / 16, (int) (panelHeight / 1.18));
			g.drawString("CREDITS", panelWidth / 16, (int) (panelHeight / 1.03));
		}

		// Play game
		else if (screen == 2) {
			// Draw snow
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, panelWidth, panelHeight);

			if (robberTime < 3000)
				g.drawImage(robber, robberX, robberY, this);
			else
				// Draw the moving player on top of the map
				g.drawImage(player1Image, player1.showXPos(), player1.showYPos(), this);

			g.drawImage(money, moneyX, moneyY, this);
			g.drawImage(gate, gateX, gateY, this);

			// Update obstacles
			for (int i = 0; i < obstacleImages.length; i++) {
				// Player drawn first (obstacle in front of player)
				g.drawImage(obstacleImages[i], obstaclesX[i], obstaclesY[i], this);
				// Obstacle behind player
				if (obstaclesY[i] < player1.showYPos() + 30)
					g.drawImage(player1Image, player1.showXPos(), player1.showYPos(), this);

			}
			
			// Draw side trees
			g.drawImage(imageBackground, 0, 0, this);

			// Pause button
			if (!paused) {
				g.drawImage(pauseButton, panelWidth / 80, panelHeight / 100, this);
			}
			// Play button
			else {
				g.drawImage(playButton, panelWidth / 80, panelHeight / 100, this);
			}

			// Time and score
			g.setFont(TIME_FONT);
			g.setColor(Color.RED);
			g.drawString("Time: " + time, (int) (panelWidth / 5.3), panelHeight / 10);
			g.drawString("Score: " + (int) player1.getScore(), (int) (panelWidth / 1.6), panelHeight / 10);

			if (bonus)
				g.drawString("!!DOUBLE POINTS!!", panelWidth / 4, panelHeight / 5);

			if (isCollision()) {
				// Game over screen
				g.setColor(Color.WHITE);
				g.fillRect(panelWidth / 4, (int) (panelHeight / 2.5), panelWidth / 2, panelHeight / 5);
				g.setColor(Color.BLUE);
				g.drawRect(panelWidth / 4, (int) (panelHeight / 2.5), panelWidth / 2, panelHeight / 5);
				g.setColor(Color.BLACK);
				g.drawString("" + (int) player1.getScore(), (int) (panelWidth / 2.2), panelHeight / 2);
				g.setFont(MED_MENU_FONT);
				g.drawString("YOUR SCORE", (int) (panelWidth / 2.67), (int) (panelHeight / 2.2));
				g.drawString("MENU", (int) (panelWidth / 3.48), (int) (panelHeight / 1.75));
				g.drawString("TRY AGAIN", (int) (panelWidth / 1.86), (int) (panelHeight / 1.75));
			}
		}

		// Player name
		else if (screen == 1) {
			g.drawImage(mainMenu, 0, 0, this);
			g.setFont(MED_MENU_FONT);
			g.setColor(Color.BLACK);
			g.drawString("Menu", (int) (panelWidth / 2.29), (int) (panelHeight / 1.1));
			g.drawString("Please enter your name: ", panelWidth / 16, (int) (panelHeight / 3.3));
			g.drawString("(max 15 characters)", panelWidth / 16, (int) (panelHeight / 3));
			g.drawString("Press enter to continue", panelWidth / 16, (int) (panelHeight / 2.2));
			g.drawRect(panelWidth / 16, (int) (panelHeight / 2.86), panelWidth / 2, panelHeight / 20);
			g.drawString(name, (int) (panelWidth / 13.3), (int) (panelHeight / 2.6));
		}

		// Instructions page 1
		else if (screen == 3) {
			g.setFont(LARGE_MENU_FONT);
			g.drawImage(mainMenu, 0, 0, this);
			g.drawString("Instructions", (int) (panelWidth / 4.57), (int) (panelHeight / 3.3));
			g.drawImage(rightArrow, (int) (panelWidth / 1.23), (int) (panelHeight / 1.15), this);
			g.setFont(MED_MENU_FONT);
			g.drawString("You are a professional downhill skier", panelWidth / 80,
					(int) (panelHeight / 3.3) + panelHeight / 20);
			g.drawString("who is chasing a bank robber to ", panelWidth / 80,
					(int) (panelHeight / 3.3) + 2 * panelHeight / 20);
			g.drawString("serve vigilante justice. To do so, you", panelWidth / 80,
					(int) (panelHeight / 3.3) + 3 * panelHeight / 20);
			g.drawString("must ski down a mountain full of obstacles,", panelWidth / 80,
					(int) (panelHeight / 3.3) + 4 * panelHeight / 20);
			g.drawString("collecting as many points as possible.", panelWidth / 80,
					(int) (panelHeight / 3.3) + 5 * panelHeight / 20);
			g.drawString("Use the arrow keys to move.", panelWidth / 20,
					(int) (panelHeight / 3.3) + 7 * panelHeight / 20);
			g.drawImage(arrowKeys, (int) (panelWidth / 3.2), (int) (panelHeight / 1.9), this);
			g.drawString("Do not collide with trees or rocks.", panelWidth / 20, (int) (panelHeight / 1.25));
			g.drawImage(obstacleImages[0], panelWidth / 8, (int) (panelHeight / 1.18), this);
			g.drawImage(obstacleImages[1], panelWidth / 4, (int) (panelHeight / 1.14), this);
			g.drawString("Menu", (int) (panelWidth / 2.29), (int) (panelHeight / 1.1));
		}

		// Instructions page 2
		else if (screen == 4) {
			g.setFont(LARGE_MENU_FONT);
			g.drawImage(mainMenu, 0, 0, this);
			g.drawString("Instructions", (int) (panelWidth / 4.57), (int) (panelHeight / 3.3));
			g.setFont(MED_MENU_FONT);
			g.drawImage(leftArrow, (int) (panelWidth / 13.11), (int) (panelHeight / 1.15), this);
			g.drawString("Pick up the robber's dropped cash to ", panelWidth / 80, (int) (panelHeight / 2.5));
			g.drawString("get more points.", panelWidth / 80, (int) (panelHeight / 2.2));
			g.drawImage(money, panelWidth / 8, panelHeight / 2, this);
			g.drawImage(money, panelWidth / 4, panelHeight / 2, this);
			g.drawImage(money, panelWidth / 8 + panelWidth / 4, panelHeight / 2, this);
			g.drawString("Ski through gates to get double points ", panelWidth / 80, (int) (panelHeight / 1.6));
			g.drawString("for ten seconds.", panelWidth / 80, (int) (panelHeight / 1.6) + panelHeight / 20);
			g.drawImage(gate, (int) (panelWidth / 3.48), (int) (panelHeight / 1.39), this);
			g.drawString("Menu", (int) (panelWidth / 2.29), (int) (panelHeight / 1.1));
			g.drawString("Play", (int) (panelWidth / 1.23), (int) (panelHeight / 1.1));
		}

		// High scores
		else if (screen == 5) {
			g.drawImage(mainMenu, 0, 0, this);
			g.setFont(LARGE_MENU_FONT);
			g.drawString("Highscores", panelWidth / 4, (int) (panelHeight / 3.3));
			g.setFont(MED_MENU_FONT);
			g.drawString("Menu", (int) (panelWidth / 2.29), (int) (panelHeight / 1.1));
			int position = (int) (panelHeight / 2.5);
			for (int score = 0; score < highScores.length; score++) {
				g.drawString(highNames[score], panelWidth / 80, position);
				g.drawString(Integer.toString(highScores[score]), (int) (panelWidth / 1.78), position);
				position += panelHeight / 10;
			}
		}

		// Credits
		else if (screen == 6) {
			g.drawImage(mainMenu, 0, 0, this);
			g.setFont(MED_MENU_FONT);
			g.drawString("Menu", (int) (panelWidth / 2.29), (int) (panelHeight / 1.1));
			g.setFont(LARGE_MENU_FONT);
			g.drawString("Game made by: ", (int) (panelWidth / 5.3), (int) (panelHeight / 3.5));
			g.drawString("Frank Long", (int) (panelWidth / 16), (int) (panelHeight / 2.2));
			g.drawString("and", (int) (panelWidth / 3.2), (int) (panelHeight / 1.8));
			g.drawString("Daniel Rawana", (int) (panelWidth / 5.3), (int) (panelHeight / 1.54));
		}
	} // paint component method

	/**
	 * Responds to a keyPressed event
	 * 
	 * @param event
	 *            information about the key pressed event
	 */
	public void keyPressed(KeyEvent event) {
		// Move the player if there is no collision and in the map (left/right)
		if (screen == 2) {
			if (!isCollision()) {
				if (event.getKeyCode() == KeyEvent.VK_LEFT && !paused) {
					left = true;
				} else if (event.getKeyCode() == KeyEvent.VK_RIGHT && !paused) {
					right = true;
				} else if (event.getKeyCode() == KeyEvent.VK_UP && player1.showYPos() > 0 && !paused) {
					up = true;
				} else if (event.getKeyCode() == KeyEvent.VK_DOWN && player1.showYPos() < panelHeight - 50 && !paused) {
					down = true;
				}
			}
			repaint();
		}

	}

	/**
	 * Action performed when key is released
	 * 
	 * @param event
	 *            information about the key released event
	 */
	public void keyReleased(KeyEvent event) {
		if (screen == 2) {
			if (!isCollision()) {
				if (event.getKeyCode() == KeyEvent.VK_LEFT && !paused) {
					left = false;
				} else if (event.getKeyCode() == KeyEvent.VK_RIGHT && !paused) {
					right = false;
				} else if (event.getKeyCode() == KeyEvent.VK_UP && !paused) {
					up = false;
				} else if (event.getKeyCode() == KeyEvent.VK_DOWN && !paused) {
					down = false;
				}
			}
			repaint();
		}
	}

	/**
	 * Allows the player to enter their name
	 * 
	 * @param event
	 *            information about the key typed event
	 */
	public void keyTyped(KeyEvent event) {
		// Only applies in enter name screen
		if (screen == 1) {
			char key = event.getKeyChar();

			// Start game when player presses enter
			if (key == KeyEvent.VK_ENTER && name.length() > 0) {
				screen = 2;
				startGame();
			}

			// Make sure the key is a letter and enter it into the name
			else if ((Character.isLetter(key) || Character.isSpace(key)) && name.length() < 15)
				name += key;

			// Handle backspaces
			else if (key == KeyEvent.VK_BACK_SPACE && name.length() > 0)
				name = name.substring(0, name.length() - 1);
			repaint();
		}
	}

	/**
	 * Responds to a mousePressed event
	 * 
	 * @param event
	 *            information about the mouse pressed event
	 */
	public void mousePressed(MouseEvent event) {
		Point pressed = event.getPoint();

		// Determine main menu choice
		if (screen == 0) {
			if (PLAY_BUTTON.contains(pressed)) {
				// Repaint the panel with help screen #1 showing
				screen = 1;
			} else if (INSTRUCTIONS_BUTTON.contains(pressed)) {
				// Show the first instructions screen
				screen = 3;
			} else if (SCORES_BUTTON.contains(pressed)) {
				// Show the high scores screen
				screen = 5;
			} else if (CREDITS_BUTTON.contains(pressed)) {
				// Show the credits screen
				screen = 6;
			}
		}

		// Interactions in the game screen

		// Try again clicked in collision menu
		if (TRY_AGAIN_BUTTON.contains(pressed)) {
			if (screen == 2 && isCollision()) {
				reset();
				startGame();
			}
		}

		// Main menu clicked in collision menu
		if (GAME_MENU_BUTTON.contains(pressed)) {
			if (screen == 2 && isCollision()) {
				reset();
				screen = 0;
			}
		}

		// Pause button clicked during game
		if (PAUSE_BUTTON.contains(pressed)) {
			if (screen == 2 && !paused)
				pause();
			else
				unpause();
		}

		// Instructions movement
		if (screen == 3) {
			if (RIGHT_ARROW.contains(pressed)) {
				screen = 4;
			}
		} else if (screen == 4) {
			if (LEFT_ARROW.contains(pressed)) {
				screen = 3;
			} else if (INSTRUCTIONS_PLAY_BUTTON.contains(pressed)) {
				screen = 1;
			}
		}

		// Back to main menu button
		if (screen == 1 || screen == 3 || screen == 4 || screen == 5 || screen == 6) {
			if (MAIN_MENU_BUTTON.contains(pressed)) {
				screen = 0;
			}
		}
		repaint();
	}

	// Extra methods needed since this game board is a MouseListener
	public void mouseReleased(MouseEvent event) {
	}

	public void mouseClicked(MouseEvent event) {
	}

	public void mouseEntered(MouseEvent event) {
	}

	public void mouseExited(MouseEvent event) {
	}

	/**
	 * Inner class to deal with mouse motion
	 */
	private class MouseMotionHandler extends MouseMotionAdapter {
		/**
		 * Responds to a mouse moved event
		 * 
		 * @param event
		 *            information about the mouse moved event
		 */
		public void mouseMoved(MouseEvent event) {
			// Get the position of the mouse
			Point pos = event.getPoint();

			// Change mouse icon to hand when over options
			if (screen == 0) {
				if (PLAY_BUTTON.contains(pos) || INSTRUCTIONS_BUTTON.contains(pos) || SCORES_BUTTON.contains(pos)
						|| CREDITS_BUTTON.contains(pos)) {

					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else
					setCursor(Cursor.getDefaultCursor());
			}

			// Set hand cursor on options in the game screen
			if (screen == 2) {
				if (TRY_AGAIN_BUTTON.contains(pos) || GAME_MENU_BUTTON.contains(pos)) {
					if (isCollision()) {
						setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					}
				}

				else if (PAUSE_BUTTON.contains(pos)) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else
					setCursor(Cursor.getDefaultCursor());
			}

			// Set hand mouse on instructions movement buttons-
			if (screen == 3) {
				if (RIGHT_ARROW.contains(pos) || MAIN_MENU_BUTTON.contains(pos))
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				else
					setCursor(Cursor.getDefaultCursor());
			}
			if (screen == 4) {
				if (LEFT_ARROW.contains(pos) || INSTRUCTIONS_PLAY_BUTTON.contains(pos)
						|| MAIN_MENU_BUTTON.contains(pos))
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				else
					setCursor(Cursor.getDefaultCursor());
			}

			// Set hand cursor for the back to main menu button
			if (screen == 1 || screen == 5 || screen == 6) {
				if (MAIN_MENU_BUTTON.contains(pos)) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else
					setCursor(Cursor.getDefaultCursor());
			}

		}
	}

	/**
	 * An inner class to deal with the timer events
	 */
	private class TimerEventHandler implements ActionListener {

		/**
		 * Handles all timer events
		 * 
		 * @param event
		 *            the Timer event
		 */
		public void actionPerformed(ActionEvent event) {
			// Control the movement of the robber
			if (screen == 2) {
				if (event.getSource() == robberTimer) {
					robberTime += 30;
					robberY += panelHeight / 50;
					repaint();
				}

				// Movement of the player
				if (!isCollision()) {
					if (event.getSource() == movementTimer) {
						// Movement
						if (left)
							player1.moveLeft();
						if (right)
							player1.moveRight();
						if (up && player1.showYPos() > 0) {
							 player1.moveUp();
						}
						if (down && player1.showYPos() < panelHeight - 50) {
							 player1.moveDown();
							// for (int item = 0; item < obstacleImages.length;
							// item++) {
							// // Reset item if out of map
							// if (obstaclesY[item] < 0) {
							// obstaclesY[item] = panelHeight + panelHeight /
							// 10;
							// obstaclesX[item] = (int) (Math.random() *
							// (panelWidth - panelWidth / 4)
							// + panelWidth / 8);
							// }
							// obstaclesY[item] -= panelHeight / 200;
							// }
							// moneyY -= panelHeight / 200;
							// gateY -= panelHeight / 200;

						}
					}

					// Move items up and stuff every time interval
					if (event.getSource() == timer) {
						// Add items
						addItem();

						// Move items and money up
						for (int item = 0; item < obstacleImages.length; item++) {
							// Reset item if out of map
							if (obstaclesY[item] < 0) {
								obstaclesY[item] = panelHeight + panelHeight / 10;
								obstaclesX[item] = (int) (Math.random() * (panelWidth - panelWidth / 4)
										+ panelWidth / 8);
							}
//							if (up)
//								obstaclesY[item] -= panelHeight / 150;
//							else
								obstaclesY[item] -= panelHeight / 100;
						}
//						if (up) {
//							moneyY -= panelHeight / 150;
//							gateY -= panelHeight / 150;
//						} else {
							moneyY -= panelHeight / 100;
							gateY -= panelHeight / 100;
//						}
						// Remove money
						if (isMoneyHere()) {
							if (!bonus)
								player1.pickUpMoney();
							else {
								player1.pickUpMoney();
								player1.pickUpMoney();
							}
							moneyX = -100;
							moneyY = -100;
						}

						if (isGateHere()) {
							// Double points for 10 seconds
							bonus = true;
							bonusTimer.start();
						}
					}

					// Every 8 seconds
					if (event.getSource() == moneyTimer)
						addMoney();

					// Every second
					if (event.getSource() == playTimer) {
						// Add time
						time++;
						if (!bonus)
							player1.addTimePoints();
						else {
							player1.addTimePoints();
							player1.addTimePoints();
						}

						// Decrease time interval (make game faster)
						if (TIME_INTERVAL > 10) {
							TIME_INTERVAL--;
							timer.setDelay(TIME_INTERVAL);
						}
					}

					// Every 30 seconds
					if (event.getSource() == gateTimer && !isCollision())
						addGate();

					// Every 10 seconds
					if (event.getSource() == bonusTimer && !isCollision()) {
						bonusTimer.stop();
						bonus = false;
					}
				}

				// If the player has collided, stop the timers and update high
				// scores
				else {
					moneyTimer.stop();
					timer.stop();
					playTimer.stop();
					gateTimer.stop();
					bonusTimer.stop();
					movementTimer.stop();
					robberTimer.stop();
					try {
						updateHighscores(highScores, (int) player1.getScore());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				repaint();
			}
		}
	}
}
