package projects.games.naval_battle.tests;

import projects.games.naval_battle.*;
import junit.framework.TestCase;
import org.junit.Test;

public class TestNavalBattle extends TestCase {

	@Test
	public void test_smoke() {
		Unit testSubmarine = new Unit("Sea Wolf", 1);
		Unit testDestroyer = new Unit("Ajax", 2);
		Unit testCruiser = new Unit("Essex", 3);
		Unit testBattleship = new Unit("Vanguard", 4);

		assertNotNull("Submarine created sucssesful", testSubmarine);
		assertNotNull("Destroyer created sucssesful", testDestroyer);
		assertNotNull("Cruiser created sucssesful", testCruiser);
		assertNotNull("Battleship created sucssesful", testBattleship);

		assertEquals("Sea Wolf", testSubmarine.getName());
		assertEquals("Ajax", testDestroyer.getName());
		assertEquals("Essex", testCruiser.getName());
		assertEquals("Vanguard", testBattleship.getName());

		assertEquals(Type.submarine, testSubmarine.getTypeOfShip());
		assertEquals(Type.destroyer, testDestroyer.getTypeOfShip());
		assertEquals(Type.cruiser, testCruiser.getTypeOfShip());
		assertEquals(Type.battleship, testBattleship.getTypeOfShip());

		assertEquals(1, testSubmarine.getHitsToSunk());
		assertEquals(2, testDestroyer.getHitsToSunk());
		assertEquals(3, testCruiser.getHitsToSunk());
		assertEquals(4, testBattleship.getHitsToSunk());

		assertNotNull(testSubmarine.getLocationCells());
		assertNotNull(testDestroyer.getLocationCells());
		assertNotNull(testCruiser.getLocationCells());
		assertNotNull(testBattleship.getLocationCells());

	}

	@Test
	public void testingMethodTakeCoordinateX() {
		PlayerInterface instantseForTest = new PlayerInterface();
		String coordX = "a";
		assertEquals(0, instantseForTest.takeCoordinateX(coordX));
		String[] arrayOfLetters = { "a", "b", "c", "d", "e", "f", "g", "h",
				"i", "j" };
		for (int i = 0; i <= 9; i++) {
			assertEquals(i, instantseForTest.takeCoordinateX(arrayOfLetters[i]));
			assertEquals(-1, instantseForTest.takeCoordinateX(arrayOfLetters[i]
					.toUpperCase()));
		}
	}

	@Test
	public void testingMethodTakeCoordinateY() {
		PlayerInterface instantseForTest = new PlayerInterface();
		String coordY = "10";
		assertEquals(90, instantseForTest.takeCoordinateY(coordY));
		for (int i = 0; i <= 9; i++) {
			String coordinateY = ((Integer) (i + 1)).toString();
			assertEquals(i * 10, instantseForTest.takeCoordinateY(coordinateY));
		}
	}

	@Test
	public void testingMethodGetCoordinate() {
		PlayerInterface instantseForTest = new PlayerInterface();
		String coordJ10 = "J10";
		assertEquals(99, instantseForTest.getCoordinate(coordJ10));
		String coordA1 = "A1";
		assertEquals(0, instantseForTest.getCoordinate(coordA1));
		String coord10B = "10B";
		assertEquals(91, instantseForTest.getCoordinate(coord10B));
	}

	@Test
	public void testingMethodCheckForHit() {
		GameLogic instantseForTest = new GameLogic();
		Admiral testAdmiral = instantseForTest.getAdmiralRed();
		Admiral testOpponentAdmiral = instantseForTest.getAdmiralGreen();
		Unit testShip1 = new Unit("Aurora", 2);
		testShip1.getLocationCells().add(1);
		testShip1.getLocationCells().add(2);
		Unit testShip2 = new Unit("Polaris", 2);
		testShip2.getLocationCells().add(3);
		testShip2.getLocationCells().add(4);
		testOpponentAdmiral.addShipInFleet(testShip1);
		testOpponentAdmiral.addShipInFleet(testShip2);
		assertEquals("Hit", instantseForTest.checkForHit(testAdmiral, 1));
		assertEquals("Miss", instantseForTest.checkForHit(testAdmiral, 10));
		assertEquals(
				"Sunk " + testShip1.getTypeOfShip() + " " + testShip1.getName(),
				instantseForTest.checkForHit(testAdmiral, 2));

	}
	
	@Test
	public void testingReturnOpponent(){
		GameLogic gameLogicForTest = new GameLogic( "TestNameOne", "TestNameTwo");
		Admiral testAmdiralRed = gameLogicForTest.getAdmiralRed();
		Admiral testAdmiralGreen = gameLogicForTest.getAdmiralGreen();
		assertEquals("TestNameTwo", gameLogicForTest.returnOpponent(testAmdiralRed).getAdmiralName());
		assertEquals("TestNameOne", gameLogicForTest.returnOpponent(testAdmiralGreen).getAdmiralName());
		assertNotSame(testAdmiralGreen, gameLogicForTest.returnOpponent(testAdmiralGreen));
		assertNotSame(testAmdiralRed, gameLogicForTest.returnOpponent(testAmdiralRed));

	}
	
	@Test
	public void testingCellInSameRow(){
		AdmiralComputer testAmdiralComp = new AdmiralComputer();
		
		assertEquals(true, testAmdiralComp.checkerCellInSameRow(67,68));
		assertEquals(false, testAmdiralComp.checkerCellInSameRow(69,70));
		assertEquals(false, testAmdiralComp.checkerCellInSameRow(50,49));
	}

}
