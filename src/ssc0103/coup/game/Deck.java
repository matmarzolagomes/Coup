package ssc0103.coup.game;

import java.util.HashMap;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class Deck extends LinkedList<String> {
	public Deck() {
		super();
	}

	public void startGame(HashMap<String,Player> players, int nplayers) {
		createDeck(nplayers);
		
		for (Player player : players.values()) {
			player.draw(this, 2);
		}
	}

	public void createDeck(int numPlayers) {
		int nCards = 3;

		if (numPlayers > 6) {
			nCards = (int) Math.ceil(numPlayers / 2.0);
		}

		for (int i = 0; i < nCards; ++i) {
			this.add("Duque");
			this.add("Capitao");
			this.add("Embaixador");
			this.add("Condessa");
			this.add("Assassino");
		}
	}
}
