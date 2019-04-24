import java.awt.*;
import java.util.Random;

/**
 * class that holds information about each vehicle
 */
public class Vehicle {
    public static int PLAYER = 0;

    private int id;
    private Type type;
    private Coordinate coordinate;
    private Orientation orientation;
    private Color colour;

	/**
	 * standard constructor
	 * @param id id of vehicle
	 * @param type type of vehicle
	 * @param coordinate coordinate of vehicle
	 * @param orientation orientation of vehicle
	 */
    public Vehicle(int id, Type type, Coordinate coordinate, Orientation orientation) {
        this.id = id;
        this.coordinate = coordinate;
        this.orientation = orientation;
        this.type = type;
        setColour();
    }

	/**
	 * method to randomly set colour of vehicle
	 */
	private void setColour() {
        if (getId() == Vehicle.PLAYER) {
            colour = Color.RED;
        } else {
            Random rand = new Random();
            int randNum = rand.nextInt(7);
            switch (randNum) {
				case 0 : colour = Color.PINK; break;
				case 1 : colour = Color.CYAN; break;
                case 2 : colour = Color.GREEN; break;
                case 3 : colour = Color.MAGENTA; break;
                case 4 : colour = Color.CYAN; break;
                case 5 : colour = Color.GRAY; break;
                default: colour = Color.ORANGE; break;
            }
        }
    }

	/**
	 * getter for id
	 * @return ID
	 */
	public int getId() {
        return id;
    }

	/**
	 * getter for coordinate
	 * @return coordinate
	 */
    public Coordinate getCoordinate() {
        return coordinate;
    }

	/**
	 * getter for orientation
	 * @return orientation
	 */
	public Orientation getOrientation() {
        return orientation;
    }

	/**
	 * getter for type
	 * @return type
	 */
	public Type getType() {
        return type;
    }

	/**
	 * setter for coordinate
	 * @param coordinate new coordinate to set to
	 */
	public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

	/**
	 * method to check if vehicle is horizontal
	 * @return true if horizontal, false otherwise
	 */
    public boolean isHorizontal() {
        return this.getOrientation() == Orientation.Horizontal;
    }

	/**
	 * method to check if vehicle is vertical
	 * @return true if vertical, false otherwise
	 */
    public boolean isVertical() {
        return this.getOrientation() == Orientation.Vertical;
    }

	/**
	 * method to check if vehicle is car
	 * @return true if car, false otherwise
	 */
    public boolean isCar() {
        return this.getType() == Type.Car;
    }

	/**
	 * method to check if vehicle is truck
	 * @return true if truck, false otherwise
	 */
    public boolean isTruck() {
        return this.getType() == Type.Truck;
    }

	/**
	 * getter for vehicle colour
	 * @return colour
	 */
	public Color getColour() { return colour; }
}