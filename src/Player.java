import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Keeps track of Player object's information including points and position
 * @author Frank Long and Daniel Rawana
 * @version June 13, 2015
 */
public class Player
{
	private double points = 0;
	private int xPos, yPos;
	private Dimension dim;

	/**
	 * Constructs a player class
	 * @param name the player's name
	 */
	public Player(String name, Dimension size)
	{
		dim = size;
		xPos = (int) (dim.getWidth()/2);
		yPos = 25;
	}

	/**
	 * Places player back at start
	 */
	public void start()
	{
		xPos = (int) (dim.getWidth()/2);
		yPos = 25;
		points = 0; 
	}
	/**
	 * Return x position
	 */
	public int showXPos()
	{
		return this.xPos;
	}

	/**
	 * Return y position
	 */
	public int showYPos()
	{
		return this.yPos;
	}

	/**
	 * Outputs the player's score
	 * @return the player's score
	 */
	public double getScore()
	{
		return this.points;
	}

	/**
	 * Moves the player right
	 */
	public void moveRight()
	{
		xPos += (int) (dim.getWidth()/80);
	}

	/**
	 * Moves the player left
	 */
	public void moveLeft()
	{
		xPos -= (int) (dim.getWidth()/80);
	}

	/**
	 * Moves to player up
	 */
	public void moveUp()
	{
		yPos -= (int) (dim.getHeight()/100);
	}

	/**
	 * Moves the player down
	 */
	public void moveDown()
	{
		yPos += (int) (dim.getHeight()/100);
	}

	/**
	 * Add points to player's score for picking up money
	 */
	public void pickUpMoney()
	{
		points += 100;
	}

	/**
	 * Add points to player's score for time
	 */
	public void addTimePoints()
	{
		points++;
	}
}
