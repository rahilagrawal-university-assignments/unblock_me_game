/**
 * Class to hold the x and y coordinates of the vehicles
 */
public class Coordinate {
    private int x;
    private int y;

	/**
	 * standard constructor
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

	/**
	 * getter for X coordinate
	 * @return X coordinate
	 */
	public int getX() {
        return x;
    }

	/**
	 * getter for Y coordinate
	 * @return Y coordinate
	 */
    public int getY() {
        return y;
    }
}