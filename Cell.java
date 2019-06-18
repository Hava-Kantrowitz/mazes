
public class Cell {
	
	private boolean visited = false;
	private boolean mostRecentlyVisited = false;
	int xCoord;
	int yCoord;
	
	public Cell(int xCoord, int yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}
	
	/**
	 * Gets the visited value
	 * @return whether the cell is visited or not
	 */
	public boolean getVisited() {
		return visited;
	}
	
	/**
	 * Sets the visited value to true
	 */
	public void setVisited() {
		visited = true;
	}
	
	public boolean getRecent() {
		return mostRecentlyVisited;
	}
	
	public void setRecent(boolean isRecent) {
		visited = isRecent;
	}

}
