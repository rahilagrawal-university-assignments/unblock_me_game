import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * class that converts back end gameState to front-end panel
 */
public class GridPanel extends JPanel {
    private int size;
    private GameState gameState;
    private ArrayList<VehicleShape> vehicleShapes = new ArrayList<VehicleShape>();

	/**
	 * standard constructor
	 * @param size size of grid
	 * @param gameState gameState
	 */
	public GridPanel(int size, GameState gameState) {
        this.size = size;
        this.gameState = gameState;
    }

	/**
	 * method to draw grid with vehicles
	 * @param g Graphics
	 */
	public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        ArrayList<Vehicle> vehicles = gameState.getVehicles();
        vehicleShapes.clear();

        for (Vehicle v : vehicles) {
            VehicleShape shape = new VehicleShape(v, gameState);
            shape.draw(g2d);
            vehicleShapes.add(shape);
        }

        g2d.dispose();
    }

	/**
	 * getter for vehicle shape list
	 * @return vehicleShapes
	 */
	public ArrayList<VehicleShape> getVehicleShapes() {
        return vehicleShapes;
    }
}
