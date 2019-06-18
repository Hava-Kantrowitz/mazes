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
public class MazeBoard extends JPanel implements ActionListener{
	
	final int sizeX = 400;//board width
	final int sizeY = 400;//board height
	int pixelSize = 10;
	Timer timer;//the timer to run the animation
	final int DELAY = 120;//the speed of the animation in ms
	Cell[][] maze;
	final int NORTH = 1;
	final int EAST = 2;
	final int SOUTH = 3;
	final int WEST = 4;
	
	
	
	/**
	 * Constructor
	 * When the snake game starts, the board is initialized
	 */
	public MazeBoard() {
		maze = new Cell[sizeX][sizeY];
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
		
		setPreferredSize(new Dimension(sizeX, sizeY));
		initGame();
	}
	
	/**
	 * Initializes the game
	 */
	private void initGame() {
		Random randy = new Random();
		int startX = randy.nextInt(sizeX);
		int startY = randy.nextInt(sizeY);
		
		initMaze();
		
		maze[100][100].setVisited();
		maze[100][100].setRecent(true);
		
		timer = new Timer(DELAY, this);//initializes the timer
		timer.start();//starts the animation
	}
	
	private void initMaze() {
		for (int row = 0; row < maze.length; row++) {
			for(int col = 0; col < maze.length; col++) {
				maze[row][col] = new Cell(row, col);
			}
		}
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
		
		int topHeight = getHeight();
		int topWidth = getWidth();
		
		g.setColor(Color.black);
		
		//the columns in the grid
		for (int i = 0; i < maze.length; i++) {
			g.drawLine(i*pixelSize, 0, i*pixelSize, topHeight);
		}
		
		//the rows in the grid
		for(int j = 0; j < maze.length; j++) {
			g.drawLine(0, j*pixelSize, topWidth, j*pixelSize);
		}
		
		for (int row = 0; row < maze.length; row++) {
			for(int col = 0; col < maze.length; col++) {
				if (maze[row][col].getVisited()) {
					g.setColor(Color.yellow);
					g.fillRect(row, col, pixelSize, pixelSize);
				}
			}
		}
		
		Toolkit.getDefaultToolkit().sync();//smooth out the animation
		
	}
	
	public Cell findCurrentCell() {
		Cell cell = null;
		for (int row = 0; row < maze.length; row++) {
			for(int col = 0; col < maze.length; col++) {
				if(maze[row][col].getRecent()) {
					cell = maze[row][col];
				}
			}
		}
		
		return cell;
	}
	
	public void aldousBroder() {
		/*
		Cell currCell = findCurrentCell();
		maze[currCell.xCoord][currCell.yCoord+1].setVisited();
		maze[currCell.xCoord][currCell.yCoord+1].setRecent(true);
		
		maze[currCell.xCoord][currCell.yCoord].setRecent(false);
		*/
	}
	
	
	/**
	 * The action performed every delay miliseconds of the timed animation
	 * @param arg0 the input to action performed
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		aldousBroder();
		repaint();//repaint the board
		
	}
	
	
	
}

