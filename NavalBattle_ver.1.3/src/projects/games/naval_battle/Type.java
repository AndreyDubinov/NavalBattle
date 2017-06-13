package projects.games.naval_battle;

/**
 * Type of the ship is based on the number of hits for sunk this ship.
 * For example your need 4 hits for sunk ship type - battleship.
 * @author Andrew
 *
 */

public enum Type {
	submarine, destroyer, cruiser, battleship;

	public static Type getType(int numberOfHits) {
		Type result = null; 
		switch (numberOfHits) {
		case 1:
			result = Type.submarine;
			break;
		case 2:
			result = Type.destroyer;
			break;
		case 3:
			result = Type.cruiser;
			break;
		case 4:
			result = Type.battleship;
			break;
		}
		return result;
	}
}
