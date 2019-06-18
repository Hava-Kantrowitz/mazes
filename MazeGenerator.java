
import java.awt.EventQueue;

import javax.swing.JFrame;

public class MazeGenerator extends JFrame{
	
	public MazeGenerator() {
		initUI();//creates the UI
	}
	
	private void initUI() {
		add(new MazeSolver());//entry point of game
		setResizable(false);
		
		pack();//puts the board in center of JFrame container
		
		setTitle("Maze");//sets title of frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exits the screen when closed
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {//makes code visible onscreen
		EventQueue.invokeLater(() ->{
			MazeGenerator ex = new MazeGenerator();
			ex.setVisible(true);
		});
	}

}
