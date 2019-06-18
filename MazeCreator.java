import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Generates mazes
 * @author havak
 *
 */
public class MazeCreator extends JPanel implements ActionListener{
	
	Timer timer;//the timer to run the animation
	final int DELAY = 300;//the speed of the animation in ms
	private int[][] maze =
		{ {1,1,1,1,1,1,1,1,1,1,1,1,1},
		  {1,0,1,0,1,0,1,0,0,0,0,0,1},
		  {1,0,1,0,0,0,1,0,1,1,1,0,1},
		  {1,0,0,0,1,1,1,0,0,0,0,0,1},
		  {1,0,1,0,0,0,0,0,1,1,1,0,1},
		  {1,0,1,0,1,1,1,0,1,0,0,0,1},
		  {1,0,1,0,1,0,0,0,1,1,1,0,1},
		  {1,0,1,0,1,1,1,0,1,0,1,0,1},
		  {1,0,0,0,0,0,0,0,0,0,1,9,1},
		  {1,1,1,1,1,1,1,1,1,1,1,1,1},
		  {0,0,0,0,0,0,0,0,0,0,0,0,0}
		};
	
	Drawing algo;
	
	/**
	 * Constructor
	 * When the snake game starts, the board is initialized
	 */
	public MazeCreator() {
		initBoard();
	}
	
	/**
	 * Initializes the board
	 * Sets the focus, background color, and size
	 * Loads images
	 */
	public void initBoard() {
		setFocusable(true);
		setBackground(Color.RED);
		
		setPreferredSize(new Dimension(640, 480));
		initGame();
	}
	
	/**
	 * Initializes the game
	 */
	private void initGame() {
		algo = new Drawing();
		timer = new Timer(DELAY, this);//initializes the timer
		timer.start();//starts the animation
	}
	
	/**
	 * Paints the screen
	 * @param g the input of the graphics class
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
	
	/**
	 * Performs the actual drawing
	 * @param g the input of the graphics class
	 */
	private void doDrawing(Graphics g) {
		
		g.translate(50, 60);
		for(int row = 0; row < maze.length; row++) {
			for(int col = 0; col < maze[0].length; col++) {
				Color color;
				switch(maze[row][col]) {
					case 1: color = Color.BLACK;
					break;
					case 9: color = Color.RED;
					break;
					default: color = Color.WHITE;
				}
				
				g.setColor(color);
				g.fillRect(30*col, 30*row, 30, 30);
				g.setColor(Color.BLACK);
				g.drawRect(30*col, 30*row, 30, 30);
			}
		}
		
		
		
		Toolkit.getDefaultToolkit().sync();//smooth out the animation
		
	}
	
	/**
	 * The action performed every delay miliseconds of the timed animation
	 * @param arg0 the input to action performed
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int currX = 0;
		int currY = 0;
		algo.fillCell(maze, currX, currY);
		repaint();
		
	}
	
	
	
}