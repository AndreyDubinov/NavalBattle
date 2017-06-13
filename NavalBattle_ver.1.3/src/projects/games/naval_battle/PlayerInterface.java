package projects.games.naval_battle;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerInterface {

	public int getPlayerShot() {
		int result = -1;
		String shot = null;
		@SuppressWarnings("resource")
		Scanner scannerPlayerShot = new Scanner(System.in);
		System.out.println("Make your shot:");
		if (scannerPlayerShot.hasNextLine()) {
			shot = scannerPlayerShot.nextLine();
		}
		if (!shot.equals(null)) {
			try {
				result = getCoordinate(shot);
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
				System.out
						.println("Shot must contain only latin letter and number, like G5");
				result = getPlayerShot();
			}
		}
		return result;
	}

	public int getCoordinate(String inputString)
			throws IllegalArgumentException {
		int result = -1;
		String inputStringLower = inputString.toLowerCase();
		Pattern startFromLetter = Pattern.compile("[a-j][0-9]+");
		Pattern startFromNumber = Pattern.compile("[0-9][0-9]?[a-j]");
		Matcher matcherLetter = startFromLetter.matcher(inputStringLower);
		Matcher matcherNumber = startFromNumber.matcher(inputStringLower);
		String coordinateStringX = null;
		String coordinateStringY = null;
		if (matcherLetter.matches()) {
			coordinateStringX = inputStringLower.substring(0, 1);
			coordinateStringY = inputStringLower.substring(1);
		} else if (matcherNumber.matches()) {
			if (inputString.length() == 2) {
				coordinateStringX = inputStringLower.substring(1);
				coordinateStringY = inputStringLower.substring(0, 1);
			} else {
				coordinateStringX = inputStringLower.substring(2);
				coordinateStringY = inputStringLower.substring(0, 2);
			}
		} else {
			throw new IllegalArgumentException("This is not available shot");
		}
		result = takeCoordinateX(coordinateStringX)
				+ takeCoordinateY(coordinateStringY);

		return result;
	}

	public int takeCoordinateX(String coordinateStringX) {
		int result = -1;
		switch (coordinateStringX) {
		case "a":
			result = 0;
			break;
		case "b":
			result = 1;
			break;
		case "c":
			result = 2;
			break;
		case "d":
			result = 3;
			break;
		case "e":
			result = 4;
			break;
		case "f":
			result = 5;
			break;
		case "g":
			result = 6;
			break;
		case "h":
			result = 7;
			break;
		case "i":
			result = 8;
			break;
		case "j":
			result = 9;
			break;
		}
		return result;
	}

	public int takeCoordinateY(String coordinateStringY) {
		int result = -1;
		int coordinateY = Integer.parseInt(coordinateStringY);
		switch (coordinateY) {
		case 1:
			result = 0;
			break;
		case 2:
			result = 10;
			break;
		case 3:
			result = 20;
			break;
		case 4:
			result = 30;
			break;
		case 5:
			result = 40;
			break;
		case 6:
			result = 50;
			break;
		case 7:
			result = 60;
			break;
		case 8:
			result = 70;
			break;
		case 9:
			result = 80;
			break;
		case 10:
			result = 90;
			break;
		}
		return result;
	}

	public static String convertCellCoordinate(int cell) {
		String result = "";
		int row = (cell / Constants.ROW_LENGTH) + 1;
		int column = cell % Constants.ROW_LENGTH;
		switch (column) {
		case 0:
			result = "A" + row;
			break;
		case 1:
			result = "B" + row;
			break;
		case 2:
			result = "C" + row;
			break;
		case 3:
			result = "D" + row;
			break;
		case 4:
			result = "E" + row;
			break;
		case 5:
			result = "F" + row;
			break;
		case 6:
			result = "G" + row;
			break;
		case 7:
			result = "H" + row;
			break;
		case 8:
			result = "I" + row;
			break;
		case 9:
			result = "J" + row;
			break;
		}
		return result;
	}
}