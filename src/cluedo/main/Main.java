package cluedo.main;

import cluedo.controller.CluedoController;

public class Main {
	/**
	 * Responsible for initializing the controller which will then create all other objects required
	 * for the game
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CluedoController controller = new CluedoController();
		controller.setupGame();
	}
}