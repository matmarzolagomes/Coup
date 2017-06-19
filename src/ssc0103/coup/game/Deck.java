package ssc0103.coup.game;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Classe criada para representar as cartas
 * 
 * @author Rodrigo Geurgas Zavarizz 9791080
 *
 */
@SuppressWarnings("serial")
public class Deck extends LinkedList<String> {

	/**
	 * Construtor que cria uma linked list de string
	 */
	public Deck() {
		super();
	}
	
	/**
	 * Método que inicia o jogo distribuindo duas cartas para cada
	 * @param players (HashMap com os jogadores que estão na partida)
	 * @param nplayers (Número de jogadores)
	 */
	public void startGame(HashMap<String, Player> players, int nplayers) {
		createDeck(nplayers);

		for (Player player : players.values()) {
			player.draw(this, 2);
		}
	}
	
	/**
	 * Método que gera o baralho de acordo com o número de jogadores
	 * @param numPlayers (número de jogadores)
	 */
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
