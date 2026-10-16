package designbook.solution;

import designbook.chapter.Deck;
class GameModel {

	private static final GameModel INSTANCE = new GameModel();

	private final Deck aDeck = new Deck();

	public static GameModel instance() {
		return INSTANCE;
	}

	private GameModel() {}
	
	public void newGame() {
		aDeck.shuffle();
	}
}