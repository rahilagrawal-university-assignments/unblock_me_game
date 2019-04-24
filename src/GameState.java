import java.util.ArrayList;

/**
 * class that holds game state
 */
public class GameState {
    private Grid grid;
    private ArrayList<Vehicle> vehicles;

	/**
	 * standard constructor
	 * @param grid grid
	 * @param vehicles list of vehicles
	 */
	public GameState(Grid grid, ArrayList<Vehicle> vehicles) {
        this.grid = grid;
        this.vehicles = vehicles;
    }

	/**
	 * method to update the game state
	 * @param vehicleId ID of vehicle to update
	 * @param direction direction
	 */
	public void updateGameState(int vehicleId, int direction) {
        Vehicle vehicle = getVehicleById(vehicleId);
        if (direction == 1 && vehicle.isHorizontal()) {
            if (isValidMove(new Coordinate(vehicle.getCoordinate().getX()+1, vehicle.getCoordinate().getY()))) {
                grid.moveVehicleForwardHorizontal(vehicle);
            }
        }
        else if (direction == 1 && vehicle.isVertical()) {
            if (isValidMove(new Coordinate(vehicle.getCoordinate().getX(), vehicle.getCoordinate().getY()+1))) {
                grid.moveVehicleForwardVertical(vehicle);
            }
        }
        else if (direction == -1 && vehicle.isHorizontal()) {
            if (vehicle.isCar()) {
                if (isValidMove(new Coordinate(vehicle.getCoordinate().getX()-2, vehicle.getCoordinate().getY()))) {
                    grid.moveVehicleBackwardHorizontal(vehicle);
                }
            }
            else if (vehicle.isTruck()) {
                if (isValidMove(new Coordinate(vehicle.getCoordinate().getX()-3, vehicle.getCoordinate().getY()))) {
                    grid.moveVehicleBackwardHorizontal(vehicle);
                }
            }
        }
        else if (direction == -1 && vehicle.isVertical()) {
            if (vehicle.isCar()) {
                if (isValidMove(new Coordinate(vehicle.getCoordinate().getX(), vehicle.getCoordinate().getY()-2))) {
                    grid.moveVehicleBackwardVertical(vehicle);
                }
            }
            else if (vehicle.isTruck()) {
                if (isValidMove(new Coordinate(vehicle.getCoordinate().getX(), vehicle.getCoordinate().getY()-3))) {
                    grid.moveVehicleBackwardVertical(vehicle);
                }
            }
        }
    }

	/**
	 * method to check if a move is valid (no vehicle at new coordinate)
	 * @param coordinate coordinate to check if vehicle exists at
	 * @return true if no vehicle at new position, false otherwise
	 */
	private boolean isValidMove(Coordinate coordinate) {
        try {
            if (grid.getBlockContentsAt(coordinate) == null) {
                return true;
            }

            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

	/**
	 * method to get vehicle with specified ID
	 * @param id id of vehicle
	 * @return Vehicle with specified ID
	 */
	private Vehicle getVehicleById(int id) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == id) {
                return vehicle;
            }
        }

        return null;
    }

	/**
	 * method to check if grid is in a solved state
	 * @return true if solved, false otherwise
	 */
	public boolean isSolved() {
        return (getVehicleById(0).getCoordinate().getX() == grid.getnBlocks()-1 &&
                getVehicleById(0).getCoordinate().getY() == grid.getnBlocks()/2 - 1);
    }

	/**
	 * Getter for grid
	 * @return grid
	 */
	public Grid getGrid() {
        return grid;
    }

	/**
	 * getter for vehicle list
	 * @return vehicles
	 */
	public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }
}