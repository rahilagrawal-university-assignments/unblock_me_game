/**
 * Class to hold game grid
 */
public class Grid {
    private int nBlocks;
    private Vehicle[][] matrix;

	/**
	 * standard constructor for grid
	 * @param nBlocks size of grid
	 */
	public Grid(int nBlocks) {
        this.nBlocks = nBlocks;
        this.matrix = new Vehicle[nBlocks][nBlocks];

        for (int i = 0; i < nBlocks; i++) {
            for (int j = 0; j < nBlocks; j++) {
                matrix[i][j] = null;
            }
        }
    }

	/**
	 * method to add vehicle to grid
	 * @param vehicle vehicle to add
	 */
	public void addVehicle(Vehicle vehicle) {
        if (vehicle.getOrientation() == Orientation.Horizontal) {
            if (vehicle.getType() == Type.Car) {
                this.matrix[vehicle.getCoordinate().getX()][vehicle.getCoordinate().getY()] = vehicle;
                this.matrix[vehicle.getCoordinate().getX()-1][vehicle.getCoordinate().getY()] = vehicle;
            } else if (vehicle.getType() == Type.Truck) {
                this.matrix[vehicle.getCoordinate().getX()][vehicle.getCoordinate().getY()] = vehicle;
                this.matrix[vehicle.getCoordinate().getX()-1][vehicle.getCoordinate().getY()] = vehicle;
                this.matrix[vehicle.getCoordinate().getX()-2][vehicle.getCoordinate().getY()] = vehicle;
            }
        } else if (vehicle.getOrientation() == Orientation.Vertical) {
            if (vehicle.getType() == Type.Car) {
                this.matrix[vehicle.getCoordinate().getX()][vehicle.getCoordinate().getY()] = vehicle;
                this.matrix[vehicle.getCoordinate().getX()][vehicle.getCoordinate().getY()-1] = vehicle;
            } else if (vehicle.getType() == Type.Truck) {
                this.matrix[vehicle.getCoordinate().getX()][vehicle.getCoordinate().getY()] = vehicle;
                this.matrix[vehicle.getCoordinate().getX()][vehicle.getCoordinate().getY()-1] = vehicle;
                this.matrix[vehicle.getCoordinate().getX()][vehicle.getCoordinate().getY()-2] = vehicle;
            }
        }
    }

	/**
	 * method to move vehicle in a forward horizontal direction
	 * @param vehicle vehicle to move
	 */
	public void moveVehicleForwardHorizontal(Vehicle vehicle) {
        try {
            this.matrix[vehicle.getCoordinate().getX() + 1][vehicle.getCoordinate().getY()] = vehicle;
            if (vehicle.getType() == Type.Car) {
                this.matrix[vehicle.getCoordinate().getX() - 1][vehicle.getCoordinate().getY()] = null;
            } else if (vehicle.getType() == Type.Truck) {
                this.matrix[vehicle.getCoordinate().getX() - 2][vehicle.getCoordinate().getY()] = null;
            }
            Coordinate newCoordinate = new Coordinate(vehicle.getCoordinate().getX() + 1, vehicle.getCoordinate().getY());
            vehicle.setCoordinate(newCoordinate);
        } catch (ArrayIndexOutOfBoundsException e) { }
    }

	/**
	 * method to move vehicle in a forward vertical direction
	 * @param vehicle vehicle to move
	 */
    public void moveVehicleForwardVertical(Vehicle vehicle) {
        try {
            this.matrix[vehicle.getCoordinate().getX()][vehicle.getCoordinate().getY() + 1] = vehicle;
            if (vehicle.getType() == Type.Car) {
                this.matrix[vehicle.getCoordinate().getX()][vehicle.getCoordinate().getY() - 1] = null;
            } else if (vehicle.getType() == Type.Truck) {
                this.matrix[vehicle.getCoordinate().getX()][vehicle.getCoordinate().getY() - 2] = null;
            }
            Coordinate newCoordinate = new Coordinate(vehicle.getCoordinate().getX(), vehicle.getCoordinate().getY() + 1);
            vehicle.setCoordinate(newCoordinate);
        } catch (ArrayIndexOutOfBoundsException e) { }
    }

	/**
	 * method to move vehicle in a backward horizontal direction
	 * @param vehicle vehicle to move
	 */
    public void moveVehicleBackwardHorizontal(Vehicle vehicle) {
        try {
            this.matrix[vehicle.getCoordinate().getX()][vehicle.getCoordinate().getY()] = null;
            if (vehicle.getType() == Type.Car) {
                this.matrix[vehicle.getCoordinate().getX() - 2][vehicle.getCoordinate().getY()] = vehicle;
            } else if (vehicle.getType() == Type.Truck) {
                this.matrix[vehicle.getCoordinate().getX() - 3][vehicle.getCoordinate().getY()] = vehicle;
            }
            Coordinate newCoordinate = new Coordinate(vehicle.getCoordinate().getX() - 1, vehicle.getCoordinate().getY());
            vehicle.setCoordinate(newCoordinate);
        } catch (ArrayIndexOutOfBoundsException e) { }
    }

	/**
	 * method to move vehicle in a backward vertical direction
	 * @param vehicle vehicle to move
	 */
    public void moveVehicleBackwardVertical(Vehicle vehicle) {
        try {
            this.matrix[vehicle.getCoordinate().getX()][vehicle.getCoordinate().getY()] = null;
            if (vehicle.getType() == Type.Car) {
                this.matrix[vehicle.getCoordinate().getX()][vehicle.getCoordinate().getY() - 2] = vehicle;
            } else if (vehicle.getType() == Type.Truck) {
                this.matrix[vehicle.getCoordinate().getX()][vehicle.getCoordinate().getY() - 3] = vehicle;
            }
            Coordinate newCoordinate = new Coordinate(vehicle.getCoordinate().getX(), vehicle.getCoordinate().getY() - 1);
            vehicle.setCoordinate(newCoordinate);
        } catch (ArrayIndexOutOfBoundsException e) { }
    }

	/**
	 * method to print grid in terminal
	 */
	public void printGrid() {
        for (int i = 0; i < nBlocks * 3; i++) {
            System.out.print("-");
        }

        System.out.println();

        for (int i = 0; i < nBlocks; i++) {
            System.out.print("|");
            for (int j = 0; j < nBlocks; j++) {
                if (matrix[j][i] == null) {
                    System.out.print(" " + "|");
                } else {
                    System.out.print(matrix[j][i].getId() + "|");
                }
            }

            System.out.println();

            for (int k = 0; k < nBlocks * 3; k++) {
                System.out.print("-");
            }

            System.out.println();
        }
    }

	/**
	 * method that returns vehicle at specified coordinate
	 * @param coordinate coordinate where vehicle is at
	 * @return Vehicle at position of coordinate
	 */
	public Vehicle getBlockContentsAt(Coordinate coordinate) {
        return matrix[coordinate.getX()][coordinate.getY()];
    }

	/**
	 * getter for number of blocks
	 * @return nBlockss
	 */
	public int getnBlocks() {
        return nBlocks;
    }
}