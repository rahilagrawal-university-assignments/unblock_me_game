import java.util.*;

/**
 * Class to generate levels
 */
public class LevelGenerator {

	/**
	 * method to generate level based on number of blocks and difficulty
	 * @param nBlocks number of blocks (size of one dimension of grid)
	 * @param difficulty difficulty of game to generate
	 * @return generated GameState with specified parameters
	 */
    public GameState generateLevel(int nBlocks, Difficulty difficulty) {
    	if (difficulty.equals(Difficulty.Hard)) {
    		return generateHardLevel(nBlocks);
		}

        Grid grid = new Grid(nBlocks);
        
        Random r = new Random();
        int truck = r.nextInt(2) + 1;
        int allVehicle;
        
        if (difficulty == Difficulty.Easy) {
        	allVehicle = 9;
        }
        else if (difficulty == Difficulty.Medium) {
        	allVehicle = 12;
        }
        else {
            allVehicle = 12;
        }
        
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle(0, Type.Car, new Coordinate(5, 2), Orientation.Horizontal));
        grid.addVehicle(vehicles.get(0));
        
        // create new vehicles onto the grid
        for (int i = 1; i < truck+1; i++) {
        	
        	Orientation orientation = Orientation.values()[new Random().nextInt(Orientation.values().length)];
        	Coordinate coordinate;
        	
        	if (orientation == Orientation.Horizontal) {
            	coordinate = new Coordinate(r.nextInt(3)+2, r.nextInt(6));
        	}
        	else {
        		coordinate = new Coordinate(r.nextInt(6), r.nextInt(3)+2);
        	}
        	if (orientation == Orientation.Horizontal && coordinate.getY() == 2) {
        		i--;
        	}
        	else if (orientation == Orientation.Vertical && tooManyVert(coordinate, Type.Truck, grid) == true) {
        		i--;
        	}
        	else if (collision(coordinate, Type.Truck, orientation, grid) == false) {
            	vehicles.add(new Vehicle(i, Type.Truck, coordinate, orientation));
            	grid.addVehicle(vehicles.get(i));
        	}
			else {
				i--;
			}
        }
        
        for (int i = truck+1; i < allVehicle ; i++) {
        	Orientation orientation = Orientation.values()[new Random().nextInt(Orientation.values().length)];
        	Coordinate coordinate = new Coordinate(0, 0);
        	
        	if (orientation == Orientation.Horizontal) {
            	coordinate = new Coordinate(r.nextInt(4)+1, r.nextInt(6));
        	}
        	else{
            	coordinate = new Coordinate(r.nextInt(6), r.nextInt(4)+1);
        	}
        	
        	if (orientation == Orientation.Horizontal && coordinate.getY() == 2) {
        		i--;
        	}
        	else if (orientation == Orientation.Vertical && tooManyVert(coordinate, Type.Car, grid) == true) {
        		i--;
        	}
        	else if (collision(coordinate, Type.Car, orientation, grid) == false) {
            	vehicles.add(new Vehicle(i, Type.Car, coordinate, orientation));
            	grid.addVehicle(vehicles.get(i));
        	}
			else {
				i--;
			}
        	
        }
        
        GameState thisGameState = new GameState (grid, vehicles);
        
        for (int i = 0; i < 5000; i++) {
        	int thisId = r.nextInt(vehicles.size());
        	int direction = r.nextBoolean() ? 1 : -1;
        	thisGameState.updateGameState(thisId, direction);
        	thisGameState.updateGameState(0, -1);
        }
        
	    if (solvedByStart(thisGameState) == true) {
	    	thisGameState = generateLevel(nBlocks, difficulty);
	    }
	    
        return thisGameState;
    }
    
	/**
	 * method to test if the generated game is already solved by start
	 * @param gameState the starting gamestate
	 * @return true if the generated game is already solved by start, false otherwise
	 */
	public Boolean solvedByStart(GameState gameState) {
		int redX = 0;
		for (int i = 0; i < 6; i++) {
			Coordinate newCoord = new Coordinate(i, 2);
			if (gameState.getGrid().getBlockContentsAt(newCoord) == null) {
				continue;
			}
			Vehicle thisVehicle = gameState.getGrid().getBlockContentsAt(newCoord);
			if (thisVehicle.getId() == 0) {
				redX = i;
				break;
			}
		}
		for (int i = redX+2; i < 6; i++) {
			Coordinate newCoord = new Coordinate(i, 2);
			if (gameState.getGrid().getBlockContentsAt(newCoord) != null) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * method to test if a certain column has too many vertical vehicles
	 * (a truck and a car with a truck on the top)
	 * such that the red car will not be allowed to move
	 * @param coordinate the coordinate of the vehicle
	 * @param type the type of the vehicle
	 * @param grid grid
	 * @return true if a certain column has too many vertical vehicles, false otherwise
	 */
    public Boolean tooManyVert(Coordinate coordinate, Type type, Grid grid) {
    	if (type == Type.Truck && coordinate.getY() > 3) {
    		return false;
    	}
    	if (type == Type.Car && coordinate.getY() <= 3) {
    		return false;
    	}
    	int x = coordinate.getX();
    	if (type == Type.Truck) {
    		for (int i = 3; i < 6; i++) {
    			Coordinate newCoord = new Coordinate(x,i);
    			if (grid.getBlockContentsAt(newCoord) == null) {
    				continue;
    			}
    			Vehicle thisVehicle = grid.getBlockContentsAt(newCoord);
    			if (thisVehicle.getOrientation() == Orientation.Vertical) {
    				return true;
    			}
    		}
    	}
    	else {
    		for (int i = 2; i < 4; i++) {
    			Coordinate newCoord = new Coordinate(x,i);
    			if (grid.getBlockContentsAt(newCoord) == null) {
    				continue;
    			}
    			Vehicle thisVehicle = grid.getBlockContentsAt(newCoord);
    			if (thisVehicle.getOrientation() == Orientation.Vertical && thisVehicle.getType() == Type.Truck) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
	/**
	 * method to test if there is a collision
	 * @param coordinate coordinate of vehicle
	 * @param type type of vehicle
	 * @param orientation orientation of vehicle
	 * @param grid grid
	 * @return true if collision at vehicles position, false otherwise
	 */
    public Boolean collision(Coordinate coordinate, Type type, Orientation orientation, Grid grid) {
    	int x = coordinate.getX();
    	int y = coordinate.getY();
    	if (type == Type.Car) {
    		for (int i = 0; i < 2; i++) {
    			if (orientation == Orientation.Horizontal) {
    				Coordinate newCoord = new Coordinate (x-i, y);
    				if (grid.getBlockContentsAt(newCoord) != null) {
    					return true;
    				}
    			}
    			else {
    				Coordinate newCoord = new Coordinate (x, y-i);
    				if (grid.getBlockContentsAt(newCoord) != null) {
    					return true;
    				}
    			}
    		}
    	}
    	else {
    		for (int i = 0; i < 3; i++) {
    			if (orientation == Orientation.Horizontal) {
    				Coordinate newCoord = new Coordinate (x-i, y);
    				if (grid.getBlockContentsAt(newCoord) != null) {
    					return true;
    				}
    			}
    			else {
    				Coordinate newCoord = new Coordinate (x, y-i);
    				if (grid.getBlockContentsAt(newCoord) != null) {
    					return true;
    				}
    			}
    		}
    		
    	}
    	return false;
    }

    /**
     * This function generates hard configurations
     * @param nBlocks
     * @return a hard to solve gameState
     */
	public GameState generateHardLevel(int nBlocks) {
		ArrayList<GameState> hardLevels= new ArrayList<>();

        ArrayList<Vehicle> vehicles1 = new ArrayList<>();

        vehicles1.add(new Vehicle(0, Type.Car, new Coordinate(1,2), Orientation.Horizontal));
        vehicles1.add(new Vehicle(1, Type.Car, new Coordinate(1,3), Orientation.Horizontal));
        vehicles1.add(new Vehicle(2, Type.Car, new Coordinate(3,3), Orientation.Horizontal));
        vehicles1.add(new Vehicle(3, Type.Car, new Coordinate(2,4), Orientation.Horizontal));
        vehicles1.add(new Vehicle(4, Type.Car, new Coordinate(3,5), Orientation.Vertical));
        vehicles1.add(new Vehicle(5, Type.Car, new Coordinate(5,5), Orientation.Horizontal));
        vehicles1.add(new Vehicle(6, Type.Truck, new Coordinate(4,4), Orientation.Vertical));
        vehicles1.add(new Vehicle(7, Type.Truck, new Coordinate(5,4), Orientation.Vertical));
        vehicles1.add(new Vehicle(8, Type.Car, new Coordinate(5,1), Orientation.Horizontal));
        vehicles1.add(new Vehicle(9, Type.Car, new Coordinate(5,0), Orientation.Horizontal));
        vehicles1.add(new Vehicle(10, Type.Car, new Coordinate(3,1), Orientation.Horizontal));

        Grid grid1 = new Grid(nBlocks);

        for (Vehicle vehicle : vehicles1) {
            grid1.addVehicle(vehicle);
        }

        GameState gameState1 = new GameState(grid1, vehicles1);

        hardLevels.add(gameState1);

        ArrayList<Vehicle> vehicles2 = new ArrayList<>();

        vehicles2.add(new Vehicle(0, Type.Car, new Coordinate(3, 2), Orientation.Horizontal));
        vehicles2.add(new Vehicle(1, Type.Car, new Coordinate(0, 2), Orientation.Vertical));
        vehicles2.add(new Vehicle(2, Type.Car, new Coordinate(1, 5), Orientation.Vertical));
        vehicles2.add(new Vehicle(3, Type.Car, new Coordinate(2, 4), Orientation.Vertical));
        vehicles2.add(new Vehicle(4, Type.Car, new Coordinate(3, 1), Orientation.Vertical));
        vehicles2.add(new Vehicle(5, Type.Truck, new Coordinate(4, 2), Orientation.Vertical));
        vehicles2.add(new Vehicle(6, Type.Truck, new Coordinate(5, 2), Orientation.Vertical));
        vehicles2.add(new Vehicle(7, Type.Car, new Coordinate(5, 4), Orientation.Horizontal));
        vehicles2.add(new Vehicle(8, Type.Car, new Coordinate(3, 5), Orientation.Horizontal));
        vehicles2.add(new Vehicle(9, Type.Car, new Coordinate(5, 5), Orientation.Horizontal));
        vehicles2.add(new Vehicle(10, Type.Truck, new Coordinate(2, 0), Orientation.Horizontal));
        vehicles2.add(new Vehicle(11, Type.Car, new Coordinate(2, 1), Orientation.Horizontal));
        vehicles2.add(new Vehicle(12, Type.Car, new Coordinate(1, 3), Orientation.Horizontal));

        Grid grid2 = new Grid(nBlocks);

        for (Vehicle vehicle : vehicles2) {
            grid2.addVehicle(vehicle);
        }

        GameState gameState2 = new GameState(grid2, vehicles2);

        hardLevels.add(gameState2);

        ArrayList<Vehicle> vehicles3 = new ArrayList<>();

        vehicles3.add(new Vehicle(0, Type.Car, new Coordinate(2, 2), Orientation.Horizontal));
        vehicles3.add(new Vehicle(1, Type.Car, new Coordinate(0, 2), Orientation.Vertical));
        vehicles3.add(new Vehicle(2, Type.Car, new Coordinate(2, 1), Orientation.Vertical));
        vehicles3.add(new Vehicle(3, Type.Car, new Coordinate(3, 2), Orientation.Vertical));
        vehicles3.add(new Vehicle(4, Type.Car, new Coordinate(1, 5), Orientation.Vertical));
        vehicles3.add(new Vehicle(5, Type.Truck, new Coordinate(5, 5), Orientation.Vertical));
        vehicles3.add(new Vehicle(6, Type.Truck, new Coordinate(4, 4), Orientation.Vertical));
        vehicles3.add(new Vehicle(7, Type.Car, new Coordinate(1, 3), Orientation.Horizontal));
        vehicles3.add(new Vehicle(8, Type.Car, new Coordinate(3, 3), Orientation.Horizontal));
        vehicles3.add(new Vehicle(9, Type.Car, new Coordinate(5, 0), Orientation.Horizontal));
        vehicles3.add(new Vehicle(10, Type.Truck, new Coordinate(4, 5), Orientation.Horizontal));

        Grid grid3 = new Grid(nBlocks);

        for (Vehicle vehicle : vehicles3) {
            grid3.addVehicle(vehicle);
        }

        GameState gameState3 = new GameState(grid3, vehicles3);

        hardLevels.add(gameState3);

        ArrayList<Vehicle> vehicles4 = new ArrayList<>();

        vehicles4.add(new Vehicle(0, Type.Car, new Coordinate(1, 2), Orientation.Horizontal));
        vehicles4.add(new Vehicle(1, Type.Car, new Coordinate(0, 1), Orientation.Vertical));
        vehicles4.add(new Vehicle(2, Type.Car, new Coordinate(2, 0), Orientation.Horizontal));
        vehicles4.add(new Vehicle(3, Type.Car, new Coordinate(3, 1), Orientation.Vertical));
        vehicles4.add(new Vehicle(4, Type.Car, new Coordinate(5, 0), Orientation.Horizontal));
        vehicles4.add(new Vehicle(5, Type.Truck, new Coordinate(5, 3), Orientation.Vertical));
        vehicles4.add(new Vehicle(6, Type.Car, new Coordinate(4, 3), Orientation.Horizontal));
        vehicles4.add(new Vehicle(7, Type.Car, new Coordinate(3, 5), Orientation.Vertical));
        vehicles4.add(new Vehicle(8, Type.Car, new Coordinate(5, 5), Orientation.Horizontal));
        vehicles4.add(new Vehicle(9, Type.Truck, new Coordinate(2, 4), Orientation.Vertical));
        vehicles4.add(new Vehicle(10, Type.Car, new Coordinate(1, 3), Orientation.Horizontal));

        Grid grid4 = new Grid(nBlocks);

        for (Vehicle vehicle : vehicles4) {
            grid4.addVehicle(vehicle);
        }

        GameState gameState4 = new GameState(grid4, vehicles4);

        hardLevels.add(gameState4);

        ArrayList<Vehicle> vehicles5 = new ArrayList<>();

        vehicles5.add(new Vehicle(0, Type.Car, new Coordinate(2, 2), Orientation.Horizontal));
        vehicles5.add(new Vehicle(1, Type.Truck, new Coordinate(0, 2), Orientation.Vertical));
        vehicles5.add(new Vehicle(2, Type.Car, new Coordinate(2, 1), Orientation.Vertical));
        vehicles5.add(new Vehicle(3, Type.Truck, new Coordinate(5, 0), Orientation.Horizontal));
        vehicles5.add(new Vehicle(4, Type.Truck, new Coordinate(3, 3), Orientation.Vertical));
        vehicles5.add(new Vehicle(5, Type.Car, new Coordinate(5, 2), Orientation.Vertical));
        vehicles5.add(new Vehicle(6, Type.Car, new Coordinate(5, 4), Orientation.Vertical));
        vehicles5.add(new Vehicle(7, Type.Truck, new Coordinate(4, 4), Orientation.Vertical));
        vehicles5.add(new Vehicle(8, Type.Car, new Coordinate(3, 4), Orientation.Horizontal));
        vehicles5.add(new Vehicle(9, Type.Car, new Coordinate(1, 4), Orientation.Vertical));
        vehicles5.add(new Vehicle(10, Type.Truck, new Coordinate(2, 5), Orientation.Horizontal));

        Grid grid5 = new Grid(nBlocks);

        for (Vehicle vehicle : vehicles5) {
            grid5.addVehicle(vehicle);
        }

        GameState gameState5 = new GameState(grid5, vehicles5);

        hardLevels.add(gameState5);

        ArrayList<Vehicle> vehicles6 = new ArrayList<>();

        vehicles6.add(new Vehicle(0, Type.Car, new Coordinate(4, 2), Orientation.Horizontal));
        vehicles6.add(new Vehicle(1, Type.Truck, new Coordinate(4, 0), Orientation.Horizontal));
        vehicles6.add(new Vehicle(2, Type.Truck, new Coordinate(4, 5), Orientation.Horizontal));
        vehicles6.add(new Vehicle(3, Type.Truck, new Coordinate(5, 2), Orientation.Vertical));
        vehicles6.add(new Vehicle(4, Type.Car, new Coordinate(4, 1), Orientation.Horizontal));
        vehicles6.add(new Vehicle(5, Type.Car, new Coordinate(5, 3), Orientation.Horizontal));
        vehicles6.add(new Vehicle(6, Type.Car, new Coordinate(5, 4), Orientation.Horizontal));
        vehicles6.add(new Vehicle(7, Type.Car, new Coordinate(2, 2), Orientation.Vertical));
        vehicles6.add(new Vehicle(8, Type.Car, new Coordinate(2, 4), Orientation.Vertical));
        vehicles6.add(new Vehicle(9, Type.Car, new Coordinate(3, 4), Orientation.Vertical));

        Grid grid6 = new Grid(nBlocks);

        for (Vehicle vehicle : vehicles6) {
            grid6.addVehicle(vehicle);
        }

        GameState gameState6 = new GameState(grid6, vehicles6);

        hardLevels.add(gameState6);


        ArrayList<Vehicle> vehicles7 = new ArrayList<>();

        vehicles7.add(new Vehicle(0, Type.Car, new Coordinate(2, 2), Orientation.Horizontal));
        vehicles7.add(new Vehicle(1, Type.Truck, new Coordinate(5, 0), Orientation.Horizontal));
        vehicles7.add(new Vehicle(2, Type.Truck, new Coordinate(5, 5), Orientation.Horizontal));
        vehicles7.add(new Vehicle(3, Type.Truck, new Coordinate(2, 5), Orientation.Vertical));
        vehicles7.add(new Vehicle(4, Type.Truck, new Coordinate(5, 4), Orientation.Vertical));
        vehicles7.add(new Vehicle(5, Type.Car, new Coordinate(1, 0), Orientation.Horizontal));
        vehicles7.add(new Vehicle(6, Type.Car, new Coordinate(5, 1), Orientation.Horizontal));
        vehicles7.add(new Vehicle(7, Type.Car, new Coordinate(1, 4), Orientation.Horizontal));
        vehicles7.add(new Vehicle(8, Type.Car, new Coordinate(4, 3), Orientation.Horizontal));
        vehicles7.add(new Vehicle(9, Type.Car, new Coordinate(0, 3), Orientation.Vertical));
        vehicles7.add(new Vehicle(10, Type.Car, new Coordinate(3, 2), Orientation.Vertical));

        Grid grid7 = new Grid(nBlocks);

        for (Vehicle vehicle : vehicles7) {
            grid7.addVehicle(vehicle);
        }

        GameState gameState7 = new GameState(grid7, vehicles7);

        hardLevels.add(gameState7);


        ArrayList<Vehicle> vehicles8 = new ArrayList<>();

        vehicles8.add(new Vehicle(0, Type.Car, new Coordinate(3, 2), Orientation.Horizontal));
        vehicles8.add(new Vehicle(1, Type.Truck, new Coordinate(2, 4), Orientation.Horizontal));
        vehicles8.add(new Vehicle(2, Type.Car, new Coordinate(4, 0), Orientation.Horizontal));
        vehicles8.add(new Vehicle(3, Type.Car, new Coordinate(2, 3), Orientation.Horizontal));
        vehicles8.add(new Vehicle(4, Type.Car, new Coordinate(1, 5), Orientation.Horizontal));
        vehicles8.add(new Vehicle(5, Type.Car, new Coordinate(2, 1), Orientation.Vertical));
        vehicles8.add(new Vehicle(6, Type.Car, new Coordinate(1, 2), Orientation.Vertical));
        vehicles8.add(new Vehicle(7, Type.Car, new Coordinate(0, 3), Orientation.Vertical));
        vehicles8.add(new Vehicle(8, Type.Car, new Coordinate(4, 3), Orientation.Vertical));
        vehicles8.add(new Vehicle(9, Type.Car, new Coordinate(4, 5), Orientation.Vertical));

        Grid grid8 = new Grid(nBlocks);

        for (Vehicle vehicle : vehicles8) {
            grid8.addVehicle(vehicle);
        }

        GameState gameState8 = new GameState(grid8, vehicles8);

        hardLevels.add(gameState8);


        ArrayList<Vehicle> vehicles9 = new ArrayList<>();

        vehicles9.add(new Vehicle(0, Type.Car, new Coordinate(2, 2), Orientation.Horizontal));
        vehicles9.add(new Vehicle(1, Type.Truck, new Coordinate(3, 3), Orientation.Horizontal));
        vehicles9.add(new Vehicle(2, Type.Truck, new Coordinate(5, 3), Orientation.Vertical));
        vehicles9.add(new Vehicle(3, Type.Truck, new Coordinate(0, 4), Orientation.Vertical));
        vehicles9.add(new Vehicle(4, Type.Car, new Coordinate(1, 0), Orientation.Horizontal));
        vehicles9.add(new Vehicle(5, Type.Car, new Coordinate(1, 1), Orientation.Horizontal));
        vehicles9.add(new Vehicle(6, Type.Car, new Coordinate(5, 4), Orientation.Horizontal));
        vehicles9.add(new Vehicle(7, Type.Car, new Coordinate(5, 5), Orientation.Horizontal));
        vehicles9.add(new Vehicle(8, Type.Car, new Coordinate(5, 0), Orientation.Horizontal));
        vehicles9.add(new Vehicle(9, Type.Car, new Coordinate(2, 1), Orientation.Vertical));
        vehicles9.add(new Vehicle(10, Type.Car, new Coordinate(4, 3), Orientation.Vertical));
        vehicles9.add(new Vehicle(11, Type.Car, new Coordinate(1, 5), Orientation.Vertical));
        vehicles9.add(new Vehicle(12, Type.Car, new Coordinate(3, 5), Orientation.Vertical));

        Grid grid9 = new Grid(nBlocks);

        for (Vehicle vehicle : vehicles9) {
            grid9.addVehicle(vehicle);
        }

        GameState gameState9 = new GameState(grid9, vehicles9);

        hardLevels.add(gameState9);


        ArrayList<Vehicle> vehicles10 = new ArrayList<>();

        vehicles10.add(new Vehicle(0, Type.Car, new Coordinate(1, 2), Orientation.Horizontal));
        vehicles10.add(new Vehicle(1, Type.Truck, new Coordinate(5, 5), Orientation.Horizontal));
        vehicles10.add(new Vehicle(2, Type.Truck, new Coordinate(3, 2), Orientation.Vertical));
        vehicles10.add(new Vehicle(3, Type.Truck, new Coordinate(5, 4), Orientation.Vertical));
        vehicles10.add(new Vehicle(4, Type.Car, new Coordinate(2, 0), Orientation.Horizontal));
        vehicles10.add(new Vehicle(5, Type.Car, new Coordinate(2, 1), Orientation.Horizontal));
        vehicles10.add(new Vehicle(6, Type.Car, new Coordinate(4, 3), Orientation.Horizontal));
        vehicles10.add(new Vehicle(7, Type.Car, new Coordinate(0, 1), Orientation.Vertical));
        vehicles10.add(new Vehicle(8, Type.Car, new Coordinate(2, 3), Orientation.Vertical));
        vehicles10.add(new Vehicle(9, Type.Car, new Coordinate(2, 5), Orientation.Vertical));

        Grid grid10 = new Grid(nBlocks);

        for (Vehicle vehicle : vehicles10) {
            grid10.addVehicle(vehicle);
        }

        GameState gameState10 = new GameState(grid10, vehicles10);

        hardLevels.add(gameState10);


        ArrayList<Vehicle> vehicles11 = new ArrayList<>();

        vehicles11.add(new Vehicle(0, Type.Car, new Coordinate(1, 2), Orientation.Horizontal));
        vehicles11.add(new Vehicle(1, Type.Truck, new Coordinate(2, 0), Orientation.Horizontal));
        vehicles11.add(new Vehicle(2, Type.Truck, new Coordinate(4, 4), Orientation.Horizontal));
        vehicles11.add(new Vehicle(3, Type.Truck, new Coordinate(2, 3), Orientation.Vertical));
        vehicles11.add(new Vehicle(4, Type.Truck, new Coordinate(5, 5), Orientation.Vertical));
        vehicles11.add(new Vehicle(5, Type.Car, new Coordinate(5, 1), Orientation.Horizontal));
        vehicles11.add(new Vehicle(6, Type.Car, new Coordinate(4, 3), Orientation.Horizontal));
        vehicles11.add(new Vehicle(7, Type.Car, new Coordinate(1, 5), Orientation.Horizontal));
        vehicles11.add(new Vehicle(8, Type.Car, new Coordinate(3, 5), Orientation.Horizontal));
        vehicles11.add(new Vehicle(9, Type.Car, new Coordinate(0, 4), Orientation.Vertical));
        vehicles11.add(new Vehicle(10, Type.Car, new Coordinate(1, 4), Orientation.Vertical));
        vehicles11.add(new Vehicle(11, Type.Car, new Coordinate(3, 1), Orientation.Vertical));

        Grid grid11 = new Grid(nBlocks);

        for (Vehicle vehicle : vehicles11) {
            grid11.addVehicle(vehicle);
        }

        GameState gameState11 = new GameState(grid11, vehicles11);

        hardLevels.add(gameState11);

        Random rand = new Random();
        return hardLevels.get(rand.nextInt(hardLevels.size()));
	}
}