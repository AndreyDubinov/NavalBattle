package projects.games.naval_battle;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import projects.games.naval_battle.Constants.*;


public class AdmiralComputer extends Admiral {
	private static String[] namesForAdmiral;
	private static File fileAdmiralName = new File("Admiral Name.txt");
	private static String nameComputerAdmiral;
	private LinkedList<Integer> cellsForShooting;
	private LinkedList<Integer> cellsForAimedShooting = new LinkedList<>();
	private ArrayList<Integer> succesShot = new ArrayList<>();
	private int lastShotCoordinate;	

	{
		loadFileAdmiralNames();
		nameComputerAdmiral = generateAdmiralName();
		cellsForShooting = new LinkedList<Integer>();
		for (int i = 0; i < 100; i++) {
			cellsForShooting.add(i);
		}
	}

	public AdmiralComputer() {
		super("Computer");
	}

	public static void loadFileAdmiralNames() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					fileAdmiralName));
			namesForAdmiral = reader.readLine().split("/");
			reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.print(ex.getMessage());
		}
	}

	public static String generateAdmiralName() {
		int random = (int) (Math.random() * namesForAdmiral.length);
		return namesForAdmiral[random];
	}

	/**
	 * When computer shooting, it can choose between two variants of shooting.
	 * First of all, if computer hit in the ship, it trying to sunk this ship.
	 * So in this situation computer use method makingAimedShot(). In second
	 * situation if it last shot was "Miss" or it sunk the ship it shooting in
	 * the random cell from general list
	 * 
	 * @return
	 */
	public int makingShot() {
		if (lastShot.equals(ShotResult.Hit)) {
			succesShot.add(lastShotCoordinate);
		} else if (lastShot.equals(ShotResult.Sunk)) {
			cellsForAimedShooting.clear();
			succesShot.clear();
		}

		if (lastShot.equals(ShotResult.Hit) || !(cellsForAimedShooting.isEmpty())
				|| !(succesShot.isEmpty())) {
			return prepaireAndMakingAimedShot();
		} else {
			return makingRandomShot();
		}
	}

	private int makingRandomShot() {
		super.setCountOfPlayerShots(super.getCountOfPlayerShots() + 1);
		int index = (int) (Math.random() * cellsForShooting.size());
		Integer result = cellsForShooting.get(index);
		lastShotCoordinate = result;
		cellsForShooting.remove(index);
		return result;
	}

	private int prepaireAndMakingAimedShot() {
		if (cellsForAimedShooting.isEmpty()) {
			genereteCellsForAimedShot();
			return makingAimedShot();
		} else if (lastShot.equals(ShotResult.Hit)) {
			updateCellsForAimedShooting();
			return makingAimedShot();
		} else {
			return makingAimedShot();
		}

	}

	private int makingAimedShot() {
		super.setCountOfPlayerShots(super.getCountOfPlayerShots() + 1);
		int index = (int) (Math.random() * cellsForAimedShooting.size());
		Integer result = cellsForAimedShooting.get(index);		
		lastShotCoordinate = result;
		cellsForAimedShooting.remove(index);		
		cellsForShooting.remove(cellsForShooting.indexOf(result));
		return result;
	}

	private void genereteCellsForAimedShot() {
		Integer tempCell = -1;
		for (int i = 1; i <= Constants.NUMBER_AIMED_CELLS; i++) {
			switch (i) {
			case 1:
				tempCell = lastShotCoordinate - Constants.ROW_LENGTH;
				break;
			case 2:
				if (checkerCellInSameRow(lastShotCoordinate,
						lastShotCoordinate - 1)) {
					tempCell = lastShotCoordinate - 1;
				}
				break;
			case 3:
				if (checkerCellInSameRow(lastShotCoordinate,
						lastShotCoordinate + 1)) {
					tempCell = lastShotCoordinate + 1;
				}
				break;
			case 4:
				tempCell = lastShotCoordinate + Constants.ROW_LENGTH;
				break;
			}
			if (checkerCellInGrid(tempCell)
					&& cellsForShooting.contains(tempCell)) {
				cellsForAimedShooting.add(tempCell);
			}
		}
	}

	private void updateCellsForAimedShooting() {
		Integer firstShot = succesShot.get(0);
		Direction direction = directionOfHits();
		try {
			if (direction.equals(Direction.EAST) || direction.equals(Direction.WEST)) {
				if (cellsForAimedShooting
						.contains((Integer) (firstShot - Constants.ROW_LENGTH))) {
					cellsForAimedShooting
							.remove((Integer) (firstShot - Constants.ROW_LENGTH));
				}
				if (cellsForAimedShooting
						.contains((Integer) (firstShot + Constants.ROW_LENGTH))) {
					cellsForAimedShooting
							.remove((Integer) (firstShot + Constants.ROW_LENGTH));
				}
				if (cellsForShooting
						.contains((Integer) (firstShot - Constants.ROW_LENGTH))) {
					cellsForShooting.remove((Integer) (firstShot - Constants.ROW_LENGTH));
				}
				if (cellsForShooting
						.contains((Integer) (firstShot + Constants.ROW_LENGTH))) {
					cellsForShooting.remove((Integer) (firstShot + Constants.ROW_LENGTH));
				}
			}
			if (direction.equals(Direction.NORTH) || direction.equals(Direction.SOUTH)) {
				if (cellsForAimedShooting.contains((Integer) (firstShot - 1))) {
					cellsForAimedShooting.remove((Integer) (firstShot - 1));
				}
				if (cellsForAimedShooting.contains((Integer) (firstShot + 1))) {
					cellsForAimedShooting.remove((Integer) (firstShot + 1));
				}
				if (cellsForShooting.contains((Integer) (firstShot - 1))) {
					cellsForShooting.remove((Integer) (firstShot - 1));
				}
				if (cellsForShooting.contains((Integer) (firstShot + 1))) {
					cellsForShooting.remove((Integer) (firstShot + 1));
				}
			}
		} catch (NoSuchElementException ex) {
		}
		Integer cellForAdd;
		switch (direction) {
		case EAST:
			cellForAdd = lastShotCoordinate + 1;
			if (checkerCellInGrid(cellForAdd)
					&& checkerCellInSameRow(cellForAdd, lastShotCoordinate)) {
				cellsForAimedShooting.add(cellForAdd);
			}
			break;
		case WEST:
			cellForAdd = lastShotCoordinate - 1;
			if (checkerCellInGrid(cellForAdd)
					&& checkerCellInSameRow(cellForAdd, lastShotCoordinate)) {
				cellsForAimedShooting.add(cellForAdd);
			}
			break;
		case NORTH:
			cellForAdd = lastShotCoordinate - Constants.ROW_LENGTH;
			if (checkerCellInGrid(cellForAdd)) {
				cellsForAimedShooting.add(cellForAdd);
			}
			break;
		case SOUTH:
			cellForAdd = lastShotCoordinate + Constants.ROW_LENGTH;
			if (checkerCellInGrid(cellForAdd)) {
				cellsForAimedShooting.add(cellForAdd);
			}
			break;
		case ROW:
			for (int i = -1; i < 2; i += 2) {
				cellForAdd = lastShotCoordinate + i;
				if (checkerCellInGrid(cellForAdd)
						&& checkerCellInSameRow(cellForAdd, lastShotCoordinate)
						&& succesShot.contains(cellForAdd)) {
					cellsForAimedShooting.add(cellForAdd);
				}
			}
			break;
		case COLUMN:
			for (int i = -10; i < 20; i += 20) {
				cellForAdd = lastShotCoordinate + i;
				if (checkerCellInGrid(cellForAdd)
						&& succesShot.contains(cellForAdd)) {
					cellsForAimedShooting.add(cellForAdd);
				}
			}
			break;
		}
	}

	private boolean checkerCellInGrid(int cell) {
		if (cell >= 0 && cell <= 99) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkerCellInSameRow(int mainCell, int cellForMatch) {
		if ((mainCell / Constants.ROW_LENGTH) == (cellForMatch / Constants.ROW_LENGTH)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * north: (same column but less row), east: (same row, but bigger column),
	 * west: (same row but less column), south: (same column, but bigger row).
	 * Also check is the ship locate in row or in column
	 * 
	 * @return one of the direction: north, south, east, west or row, column
	 */
	private Direction directionOfHits() {
		int lastHit = succesShot.get(succesShot.size() - 1);
		int previousHit = succesShot.get(succesShot.size() - 2);
		int result = previousHit - lastHit;
		Direction direction = Direction.NO;
		switch (result) {
		case 1:
			direction = Direction.WEST;
			break;
		case 10:
			direction = Direction.NORTH;
			break;
		case -1:
			direction = Direction.EAST;
			break;
		case -10:
			direction = Direction.SOUTH;
			break;
		}
		if (direction.equals(Direction.NO)) {
			if (isShipInRow()) {
				direction = Direction.ROW;
			} else {
				direction = Direction.COLUMN;
			}
		}
		return direction;
	}

	/**
	 * Method check position of the ship in row or in column.
	 * 
	 * @return true if ship in row, false - if in column
	 */
	private boolean isShipInRow() {
			int lastShot = succesShot.get(succesShot.size() - 1);
			int preLatShot = succesShot.get(succesShot.size() - 2);
			if ((lastShot / Constants.ROW_LENGTH) == (preLatShot / Constants.ROW_LENGTH)) {
				return true;
			} else {
				return false;
			}		
	}

	/**
	 * This method not necessary, it can be used only for testing
	 */
	public void printNames() {
		System.out.print("\n");
		for (String element : namesForAdmiral) {
			System.out.print(element + ", ");
		}
		System.out.print("\n");
	}

	public static String getNameComputerAdmiral() {
		return nameComputerAdmiral;
	}

	public static String[] getNamesForAdmiral() {
		return namesForAdmiral;
	}

	public static File getFileAdmiralName() {
		return fileAdmiralName;
	}

	public int getLastShotCoordinate() {
		return lastShotCoordinate;
	}

	public void setLastShotCoordinate(int lastShotCoordinate) {
		this.lastShotCoordinate = lastShotCoordinate;
	}

	public LinkedList<Integer> getCellsForShooting() {
		return cellsForShooting;
	}

	public LinkedList<Integer> getCellsForAimedShooting() {
		return cellsForAimedShooting;
	}

	public ArrayList<Integer> getSuccesShot() {
		return succesShot;
	}

}
