
public class Drawing {
	
	public void fillCell(int[][] maze, int x, int y) {
		maze[y][x] = 1;
		for(int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[0].length; col++) {
				if(row % 2 == 0 || col % 2 == 0) {
					maze[row][col] = 9;
				}
				else {
					maze[row][col] = 1;
				}
				
			}
		}
	}
	
	

}
