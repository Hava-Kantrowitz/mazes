import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.util.*;
import javax.swing.*;
 
public class MazeMaker extends JPanel {
    enum Dir {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);//the directions
        final int bit;//given cell
        final int dx;//the movement in the x direction
        final int dy;//the movement in the y directions
        Dir opposite;//the direction opposite the given direction
 
        // initializes all opposite values
        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }
 
        Dir(int bit, int dx, int dy) {//the direction enum
            this.bit = bit;//the given cell
            this.dx = dx;//the movement in x
            this.dy = dy;//the movement in y
        }
    };
    final int nCols;//number of columns in maze
    final int nRows;//number of rows in maze
    final int cellSize = 25;//pixel size of each cells
    final int margin = 25;//pixel size of the margin
    final int[][] maze;//the maze
    LinkedList<Integer> solution;//the list of the solution points
 
    /**
     * Creates the maze generator and maze solver
     * @param size the size of the board, how many rows/columns it would be
     */
    public MazeMaker(int size) {
        setPreferredSize(new Dimension(650, 650));//set the size
        setBackground(Color.white);//set the background color
        nCols = size;//set the number of rows and cols to the given size
        nRows = size;//making it a square
        maze = new int[nRows][nCols];//initialize the maze with the correct number of rows and columns
        solution = new LinkedList<>();//initialize the solution list
        generateMaze(0, 0);//generate the maze starting at coordinate (0,0)
 
        addMouseListener(new MouseAdapter() {//listen for mouse input
            @Override
            public void mousePressed(MouseEvent e) {//if a mouse is clicked
                new Thread(() -> {//start a thread to solve the maze
                    solve(0);//solve it, zero seed
                }).start();
            }
        });
    }
 
    @Override
    /**
     * Paints the maze and the solution
     * @param g the graphics input
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);//super it up to the component
        Graphics2D g2d = (Graphics2D) g;//make it a 2d drawing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);//turn on rendering hints
 
        g2d.setStroke(new BasicStroke(5));//set the size of the stroke
        g2d.setColor(Color.black);//set the color to black
 
        // draw maze
        for (int r = 0; r < nRows; r++) {//for each row and columns
            for (int c = 0; c < nCols; c++) {
 
                int x = margin + c * cellSize;//the x is the column number adjusted for cell size and margin
                int y = margin + r * cellSize;//the y is the row number adjusted for cell size and margin
 
                if ((maze[r][c] & 1) == 0) // Draws north
                    g2d.drawLine(x, y, x + cellSize, y);
 
                if ((maze[r][c] & 2) == 0) // Draws south
                    g2d.drawLine(x, y + cellSize, x + cellSize, y + cellSize);
 
                if ((maze[r][c] & 4) == 0) // Draws east
                    g2d.drawLine(x + cellSize, y, x + cellSize, y + cellSize);
 
                if ((maze[r][c] & 8) == 0) // Draws west
                    g2d.drawLine(x, y, x, y + cellSize);
            }
        }
 
        // draw pathfinding animation
        int offset = margin + cellSize / 2;//create the animation offset
 
        Path2D path = new Path2D.Float();//create a path
        path.moveTo(offset, offset);//move it depending on the opposite
 
        for (int pos : solution) {//for each coordinate in the solution
            int x = pos % nCols * cellSize + offset;//set the x
            int y = pos / nCols * cellSize + offset;//set the y
            path.lineTo(x, y);//draw a line from the current position to the x,y
        }
 
        g2d.setColor(Color.orange);//set the path color to orange
        g2d.draw(path);//draw it
 
        g2d.setColor(Color.blue);//set the start dot color to blue
        g2d.fillOval(offset - 5, offset - 5, 10, 10);//draw it
 
        g2d.setColor(Color.green);//set the end dot color to green
        int x = offset + (nCols - 1) * cellSize;//set the x value
        int y = offset + (nRows - 1) * cellSize;//set the y value
        g2d.fillOval(x - 5, y - 5, 10, 10);//draw it
 
    }
 
    /**
     * Generates a maze using the recursive backtracking algorithm
     * @param r the row the current cell is in
     * @param c the column the current cell is in
     */
    void generateMaze(int r, int c) {
        Dir[] dirs = Dir.values();//create a new list of directions
        Collections.shuffle(Arrays.asList(dirs));//shuffle the list of directions
        for (Dir dir : dirs) {//for each direction in the directions list
            int nc = c + dir.dx;//adjust the new column
            int nr = r + dir.dy;//adjust the new row
            if (withinBounds(nr, nc) && maze[nr][nc] == 0) {//if it is within the bounds and not equal to 0
                maze[r][c] |= dir.bit;//set the row and column
                maze[nr][nc] |= dir.opposite.bit;//set the new row and column
                generateMaze(nr, nc);//recursively generate the maze 
            }
        }
    }
 
    /**
     * Determines if the cell at the given row and column is within the bounds of the board
     * @param r the row of the current cell
     * @param c the column of the current cell
     * @return true if within bounds, false otherwise
     */
    boolean withinBounds(int r, int c) {
        return c >= 0 && c < nCols && r >= 0 && r < nRows;//returns true if it is within the rounds of the board, false otherwise
    }
 
    /**
     * Determines whether the maze is solved
     * @param pos the position of the cell
     * @return true if the current solution has solved the maze, false otherwise
     */
    boolean solve(int pos) {
        if (pos == nCols * nRows - 1)//if the position is equal to the final position
            return true;//it is solved
 
        int c = pos % nCols;//get the column
        int r = pos / nCols;//get the row
 
        for (Dir dir : Dir.values()) {//for each direction in the dir list
            int nc = c + dir.dx;//adjust for the change in x
            int nr = r + dir.dy;//adjust for the change in y
            if (withinBounds(nr, nc) && (maze[r][c] & dir.bit) != 0
                    && (maze[nr][nc] & 16) == 0) {//if the cell is within the bounds and isn't at an edge
 
                int newPos = nr * nCols + nc;//create the new position
 
                solution.add(newPos);//add it to the solutions list
                maze[nr][nc] |= 16;//bit adjustment
 
                animate();//run the solve animation
 
                if (solve(newPos))//if the maze is solved with that new position
                    return true;//it is solved
 
                animate();//rerun the solve animation
 
                solution.removeLast();//remove the failed solution from the solutions list
                maze[nr][nc] &= ~16;//bit readjustment
            }
        }
 
        return false;//not solved
    }
 
    /**
     * Runs the animation
     */
    void animate() {
        try {//try to run the animation
            Thread.sleep(50L);
        } catch (InterruptedException ignored) {
        }
        repaint();//repaint the board
    }
 
    /**
     * Main function to set up the board and maze
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();//create the new frame
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//set the close operation
            f.setTitle("Maze Generator");//set the title
            f.setResizable(false);//not resizable
            f.add(new MazeMaker(24), BorderLayout.CENTER);//add the maze to the board
            f.pack();//pack size
            f.setLocationRelativeTo(null);//set location
            f.setVisible(true);//set the maze to visible
        });
    }
}
