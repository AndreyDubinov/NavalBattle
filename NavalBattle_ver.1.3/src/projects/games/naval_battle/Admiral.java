package projects.games.naval_battle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import projects.games.naval_battle.Constants.ShotResult;



public class Admiral {
	private final String admiralName;
	private ArrayList<Unit> fleet;
	private LinkedList<String> shipNames;
	private File nameForShip;
	private int countOfPlayerShots = 0;
	private int[] mapGrid = new int[100];
	protected ShotResult lastShot = ShotResult.Miss;	
	

	public Admiral(String name) {
		this.admiralName = name;
		fleet = new ArrayList<Unit>();
		shipNames = new LinkedList<String>();
		nameForShip = new File("Names for Ships.txt");
		loadFile();
		generateShipForFleet();
		placeShipsRandom();
	}

	public void loadFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					nameForShip));
			String[] tempString = reader.readLine().split("/");
			for (String element : tempString) {
				shipNames.add(element);
			}
			reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("Can`t load file with names for ship");
		}
	}

	/**
	 * This method is placing ships on the map in rows and in columns, and left
	 * free space between ships.
	 */
	private void placeShipsRandom() {
		boolean rotation = false;
		for (int i = 0; i < fleet.size(); i++) {
			Unit ship = fleet.get(i);
			int cells = ship.getHitsToSunk();
			while (ship.getLocationCells().isEmpty()) {
				int startCell = (int) (Math.random() * mapGrid.length);
				int numberOfRow = (startCell / Constants.ROW_LENGTH) + 1;
				int currentRowOutOfLength = numberOfRow * Constants.ROW_LENGTH;
				int[] tempCells = new int[ship.getHitsToSunk()];
				// Checking the start cell for free
				if (mapGrid[startCell] == 0) {
					// Trying put ship in row if rotation is false, change
					// rotation to the true.
					if (!rotation) {
						rotation = true;
						for (int j = 0; j < cells; j++) {
							int currentCell = startCell + j;
							if (currentCell < mapGrid.length
									&& mapGrid[currentCell] == 0
									&& currentCell < currentRowOutOfLength) {
								tempCells[j] = currentCell;
							} else {
								break;
							}
						}
						// Trying to put ship in column if rotation is true.
						// change rotation to the false.
					} else {
						rotation = false;
						// Check all cells from start cell to
						for (int j = 0; j < cells; j++) {
							int currentCell = startCell + Constants.ROW_LENGTH * j;
							if (currentCell < mapGrid.length
									&& mapGrid[currentCell] == 0) {
								tempCells[j] = currentCell;
							} else {
								break;
							}
						}
					}
				}
				if (tempCells[(tempCells.length - 1)] != 0) {
					for (int element : tempCells) {
						mapGrid[element] = 1;
						markSurrondCells(element);
						ship.getLocationCells().add(element);
					}
				}
			}
		}
	}

	/**
	 * This method is using for mark the surround cells around current cell. It
	 * is using to put ships not near each other.
	 * 
	 * @param argument
	 *            cell, this is the cell around each marking cells as an not
	 *            free.
	 */

	private void markSurrondCells(int cell) {
		int currentCell;
		int row = (cell / Constants.ROW_LENGTH);
		int column = (cell % Constants.ROW_LENGTH);
		for (int i = 0; i < Constants.SURROND_LENGTH; i++) {
			for (int j = 0; j < Constants.SURROND_LENGTH; j++) {
				currentCell = (row - 1 + i) * Constants.ROW_LENGTH + (column - 1 + j);
				if (currentCell >= 0 && currentCell < mapGrid.length) {
					mapGrid[currentCell] = 1;
				}
			}
		}

	}

	/**
	 * This method create 10 objects class Unite - ships of the fleet. Each ship
	 * is from one of 4 type, the type of the ship based on the numbers of hits
	 * to sunk them.
	 */

	private void generateShipForFleet() {
		for (int i = 0; i < Constants.FLEET_HITS.length; i++) {
			Unit ship = new Unit(takeRandomName(), Constants.FLEET_HITS[i]);
			fleet.add(ship);
		}
	}

	/**
	 * This method take the random name for the ship from the loaded file.
	 * 
	 * @return name of the ship, type String.
	 */

	private String takeRandomName() {
		int index = (int) (Math.random() * (shipNames.size()));
		String result = shipNames.get(index);
		shipNames.remove(index);
		return result;
	}

	public String getAdmiralName() {
		return admiralName;
	}

	public ArrayList<Unit> getFleet() {
		return fleet;
	}

	public LinkedList<String> getShipNames() {
		return shipNames;
	}

	public File getNameForShip() {
		return nameForShip;
	}

	public int getCountOfPlayerShots() {
		return countOfPlayerShots;
	}

	public void setCountOfPlayerShots(int countOfPlayerShots) {
		this.countOfPlayerShots = countOfPlayerShots;
	}

	public int[] getMapGrid() {
		return mapGrid;
	}

	public void addShipInFleet(Unit ship) {
		fleet.add(ship);
	}

	public ShotResult getLastShot() {
		return lastShot;
	}

	public void setLastShot(ShotResult lastShot) {
		this.lastShot = lastShot;
	}

	public void showFleet() {
		System.out.println(fleet.size() + " ship in Fleet");
		for (int i = 0; i < fleet.size(); i++) {
			Unit ship = fleet.get(i);
			System.out.print(ship.getName() + " " + ship.getTypeOfShip()
					+ " " + ship.getLocationCells()+ "| ");
		}
	}
	
	public void showShortFleet() {
		System.out.println(fleet.size() + " ship in Fleet");
		for (int i = 0; i < fleet.size(); i++) {
			Unit ship = fleet.get(i);
			System.out.print(ship.getLocationCells()+ " ");
		}
		System.out.println();
	}

}
