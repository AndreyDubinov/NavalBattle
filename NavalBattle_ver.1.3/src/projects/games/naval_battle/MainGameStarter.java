package projects.games.naval_battle;

import java.util.Scanner;

import projects.games.naval_battle.Constants.ShotResult;

public class MainGameStarter {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Welcome on-board admiral");
		System.out.println("Enemy fleet on horizon");
		System.out.println("You must sink them all");
		GameLogic gameLogic = new GameLogic("Player", "Computer");
		PlayerInterface playInterface = new PlayerInterface();
		gameLogic.showPlayerMap(gameLogic.getAdmiralRed());
		gameLogic.showOpponentMap(gameLogic.getAdmiralRed());

		while (!gameLogic.isGameWin()) {
			ShotResult playerResult;
			ShotResult computerResult;
			do {
				System.out.println("Player turn");
				int playerShot = playInterface.getPlayerShot();
				playerResult = gameLogic.checkForHit(gameLogic.getAdmiralRed(),
						playerShot);
				gameLogic.showPlayerMap(gameLogic.getAdmiralRed());
				gameLogic.showOpponentMap(gameLogic.getAdmiralRed());
				System.out.println(gameLogic.getAdmiralRed().getAdmiralName()
						+ ": " + playerResult + " at "
						+ PlayerInterface.convertCellCoordinate(playerShot));			

			} while (!playerResult.equals(ShotResult.Miss));

			do {
				System.out.println("Computer turn");
				int computerShot = gameLogic.makeComputerShot();
				computerResult = gameLogic.checkForHit(
						gameLogic.getAdmiralGreen(), computerShot);
				gameLogic.getAdmiralGreen().setLastShot(computerResult);
				gameLogic.showPlayerMap(gameLogic.getAdmiralRed());
				gameLogic.showOpponentMap(gameLogic.getAdmiralRed());
				System.out.println(gameLogic.getAdmiralGreen().getAdmiralName()
						+ ": " + computerResult + " at "
						+ PlayerInterface.convertCellCoordinate(computerShot));				
				
				// This part of code use only for holding the result of
				// computer "hit" or "sunk" shot
				if (computerResult.equals(ShotResult.Hit)
						|| computerResult.equals(ShotResult.Sunk)) {
					Thread.sleep(3000);
				}

			} while (!computerResult.equals(ShotResult.Miss));
		}
		System.out.println("Congratulation admiral "
				+ gameLogic.getWinner().getAdmiralName());
		System.out.println("You sink all ship in fleet!");
		System.out.println("Win with "
				+ gameLogic.getWinner().getCountOfPlayerShots() + " shots");

	}
}
