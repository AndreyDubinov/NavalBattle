package projects.games.naval_battle;

import java.util.*;

import projects.games.naval_battle.Constants.ShotResult;

public class GameLogic {
	private Admiral admiralRed;
	private Admiral admiralGreen;
	private Admiral winner;
	private boolean gameWin;

	public GameLogic() {
		this("Player", "Computer");

	}

	public GameLogic(String firstPlayer, String secondPlayer) {
		admiralRed = new Admiral(firstPlayer);
		if (secondPlayer.equals("Computer")) {
			admiralGreen = new AdmiralComputer();
		} else {
			admiralGreen = new Admiral(secondPlayer);
		}
	}

	public boolean checkIsPlayerComputer() {
		if (admiralGreen instanceof AdmiralComputer) {
			return true;
		} else {
			return false;
		}
	}

	public Admiral returnOpponent(Admiral admiral) {
		if (admiral.getAdmiralName().equals(admiralRed.getAdmiralName())) {
			return admiralGreen;
		} else {
			return admiralRed;
		}
	}

	/**
	 * Map legend: Fog of war: index 0, on map 0; Hidden ship: index 1, on map
	 * 0; Miss: index -1, on map *; Hit or sunk: index 2, on map X; Shown ship:
	 * index 1, on map #;
	 */

	// Fog of war: index 0, on map 0;
	// Hidden ship: index 1, on map 0;
	// Miss: index -1, on map *;
	// Hit or sunk: index 2, on map X;
	// Shown ship: index 1, on map #;

	public void showOpponentMap(Admiral admiral) {
		int[] mapGrid = returnOpponent(admiral).getMapGrid();
		System.out.println();
		System.out.println("The map of admiral: "
				+ returnOpponent(admiral).getAdmiralName());
		System.out.print("  ");
		for (int i = 0; i < Constants.COLUMNS_TITLES.length; i++) {
			System.out.print(" " + Constants.COLUMNS_TITLES[i]);
		}
		System.out.print("\n");
		for (int j = 0; j < Constants.ROW_LENGTH; j++) {
			if (j < 9) {
				System.out.print(j + 1 + " ");
			} else {
				System.out.print(j + 1);
			}
			for (int k = 0; k < Constants.ROW_LENGTH; k++) {
				String cell = null;
				if (mapGrid[j * Constants.ROW_LENGTH + k] == 0
						|| mapGrid[j * Constants.ROW_LENGTH + k] == 1) {
					cell = " " + "0";
				} else if (mapGrid[j * Constants.ROW_LENGTH + k] == -1) {
					cell = " " + "*";
				} else if (mapGrid[j * Constants.ROW_LENGTH + k] == 2) {
					cell = " " + "X";
				}
				System.out.print(cell);
			}
			System.out.print("\n");
		}
	}

	public void showPlayerMap(Admiral admiral) {
		int[] mapGrid = admiral.getMapGrid();
		ArrayList<Unit> fleet = admiral.getFleet();
		System.out.println();
		System.out.println("The map of admiral: " + admiral.getAdmiralName());
		System.out.print("  ");
		for (int i = 0; i < Constants.COLUMNS_TITLES.length; i++) {
			System.out.print(" " + Constants.COLUMNS_TITLES[i]);
		}
		System.out.print("\n");
		for (int j = 0; j < Constants.ROW_LENGTH; j++) {
			if (j < 9) {
				System.out.print(j + 1 + " ");
			} else {
				System.out.print(j + 1);
			}
			for (int k = 0; k < Constants.ROW_LENGTH; k++) {
				String cell = null;
				if (mapGrid[j * Constants.ROW_LENGTH + k] == 0
						|| mapGrid[j * Constants.ROW_LENGTH + k] == 1) {
					cell = " " + "0";
				} else if (mapGrid[j * Constants.ROW_LENGTH + k] == -1) {
					cell = " " + "*";
				} else if (mapGrid[j * Constants.ROW_LENGTH + k] == 2) {
					cell = " " + "X";
				}

				for (Unit ship : fleet) {
					for (Integer locationCell : ship.getLocationCells()) {
						if (locationCell == (j * Constants.ROW_LENGTH + k)) {
							cell = " " + "#";
						}
					}
				}

				System.out.print(cell);
			}
			System.out.print("\n");
		}
	}

	public ShotResult checkForHit(Admiral admiral, int playerShot) {
		/**
		 * REMIDER This is doing with iterators, because you can't delete
		 * element from collection in the loop. Every time when you trying to do
		 * such thing, the size of the collection is changing, so changing
		 * indexes of elements.
		 */
		Admiral opponentAdmiral = returnOpponent(admiral);
		int[] mapGrid = opponentAdmiral.getMapGrid();
		admiral.setCountOfPlayerShots((admiral.getCountOfPlayerShots()) + 1);
		ShotResult result = ShotResult.Miss;
		Iterator<Unit> iteratorForShips = opponentAdmiral.getFleet().iterator();
		while (iteratorForShips.hasNext()) {
			Unit ship = iteratorForShips.next();
			ArrayList<Integer> location = ship.getLocationCells();
			Iterator<Integer> iteratorFofCells = location.iterator();
			while (iteratorFofCells.hasNext()) {
				Integer cell = iteratorFofCells.next();
				if (cell == playerShot) {
					mapGrid[cell] = 2;
					iteratorFofCells.remove();
					int countOfHits = ship.getHitsToSunk();
					ship.setHitsToSunk(--countOfHits);
					if (ship.getHitsToSunk() == 0) {
						result = ShotResult.Sunk;						
						System.out.println("Sunk " + ship.getTypeOfShip() + " "+ ship.getName());
						iteratorForShips.remove();
					} else {
						result = ShotResult.Hit;
					}

				}
			}
		}
		if (result.equals(ShotResult.Miss)) {
			mapGrid[playerShot] = -1;
		}
		if (returnOpponent(admiral).getFleet().isEmpty()) {
			gameWin = true;
			winner = admiral;
		}
		return result;
	}

	public int makeComputerShot() {
		return ((AdmiralComputer) admiralGreen).makingShot();

	}

	public Admiral getAdmiralRed() {
		return admiralRed;
	}

	public Admiral getAdmiralGreen() {
		return admiralGreen;
	}

	public Admiral getWinner() {
		return winner;
	}

	public boolean isGameWin() {
		return gameWin;
	}

}