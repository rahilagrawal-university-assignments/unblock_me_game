import java.awt.*;
import java.awt.geom.Point2D;

/**
 * class that holds information about a vehicle's shape
 */
public class VehicleShape {
    private Rectangle rectangle;
    private Vehicle v;
    private GameState gameState;
    private Color colour;

	/**
	 * standard constructor
	 * @param vehicle vehicle shape is for
	 * @param gameState gameState
	 */
	public VehicleShape(Vehicle vehicle, GameState gameState) {
        this.v = vehicle;
        this.gameState = gameState;
        colour = vehicle.getColour();
    }

	/**
	 * method to draw vehicle shape on grid
	 * @param g2d Graphics
	 */
    public void draw(Graphics2D g2d) {
        int cellWidth = 0;
        int cellHeight = 0;
        int thickness = 100;
        int padding = 7;

        Rectangle cell = null;

        if (v.isHorizontal() && v.isCar()) {
            cellWidth = 200;
            cellHeight = 100;
            cell = new Rectangle(((v.getCoordinate().getX()-1) * thickness),
                    ((v.getCoordinate().getY()) * thickness), cellWidth-padding, cellHeight-padding);
        } else if (v.isVertical() && v.isCar()) {
            cellWidth = 100;
            cellHeight = 200;
            cell = new Rectangle(((v.getCoordinate().getX()) * thickness),
                    ((v.getCoordinate().getY()-1) * thickness), cellWidth-padding, cellHeight-padding);
        } else if (v.isHorizontal() && v.isTruck()) {
            cellWidth = 300;
            cellHeight = 100;
            cell = new Rectangle(((v.getCoordinate().getX()-2) * thickness),
                    ((v.getCoordinate().getY()) * thickness), cellWidth-padding, cellHeight-padding);
        } else if (v.isVertical() && v.isTruck()) {
            cellWidth = 100;
            cellHeight = 300;
            cell = new Rectangle(((v.getCoordinate().getX()) * thickness),
                    ((v.getCoordinate().getY()-2) * thickness), cellWidth-padding, cellHeight-padding);
        }

        this.rectangle = cell;

        g2d.setColor(colour);
        g2d.draw(cell);
        g2d.fill(cell);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(0));
    }

	/**
	 * method to check if vehicle contains a point
	 * @param p point to check for
	 * @return true if point in vehicle shape, false otherwise
	 */
	public boolean contains(Point2D p) {
        return rectangle.x <= p.getX() && p.getX() <= rectangle.x + rectangle.width
                && rectangle.y <= p.getY() && p.getY() <= rectangle.y + rectangle.height;
    }

	/**
	 * method to translate vehicle on grid (and in GUI)
	 * @param dx change in x
	 * @param dy change in y
	 */
    public void translate(int dx, int dy) {
        int currentX = v.getCoordinate().getX();
        int currentY = v.getCoordinate().getY();

        int newX = 0;
        int newY = 0;
        if (v.isHorizontal()) {
            newX = (int) Math.floor(dx/100);
            if (currentX - newX >= 1) gameState.updateGameState(v.getId(), -1);
            else if (currentX - newX <= -1) gameState.updateGameState(v.getId(), 1);
        } else {
            newY = (int) Math.floor(dy/100);
            if (currentY - newY >= 1) gameState.updateGameState(v.getId(), -1);
            else if (currentY - newY <= -1) gameState.updateGameState(v.getId(), 1);
        }
    }
}
