package projects.games.naval_battle;

public class Constants {
	/**
	 * COLUMNS_TITLES - This is name for map grid columns from A to J. 
	 * FLEET_HITS - Classic game fleet contains 10 ship of 4 different types, all of this
	 * ships has its own numbers of hit for sunk, from 1 hit to 4 hits.
	 * ROW_LENGTH - Length of the classic battlefield map contains 10 cells in one row.
	 * SURROND_LENGTH - This variable is using when.
	 * NUMBER_AIMED_CELLS - This variable is showing number of maximum cell when computer.
	 * ShotResult - 3 different results of the shot.
	 */
	public final static String[] COLUMNS_TITLES = { "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J" };	
	public final static int[] FLEET_HITS = { 4, 3, 3, 2, 2, 2, 1, 1, 1, 1 };
	public final static int ROW_LENGTH = 10;
	public final static int SURROND_LENGTH = 3;	
	public final static int NUMBER_AIMED_CELLS = 4;
	public static enum ShotResult {
		Miss, Hit, Sunk
	};
	public static enum Direction {NO, NORTH, SOUTH, EAST, WEST, ROW, COLUMN };
}
