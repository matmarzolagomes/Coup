package ssc0103.coup.game;

import java.util.HashMap;

import ssc0103.coup.exception.PException;
import ssc0103.coup.lan.Actions;

/**
 * Classe com o objetivo de controlar a mecânica do jogo
 * @author Matheus Marzola Gomes
 * @author Rodrigo Geurgas
 *
 */
public abstract class Coup {
	private HashMap<String, Player> players;
	private Deck board;
	private Deck dead;

	/**
	 * Inicializa os atributos do jogo.
	 * 
	 * @param nplayers (numero de jogadores)
	 * @param order (string com os jogadores em ordem)
	 */
	public void instanceGame(int nplayers, String[] order) {
		players = new HashMap<String, Player>();
		for (int i = 0; i < nplayers; i++)
			players.put(order[i], new Player(order[i]));
		dead = new Deck();
		board = new Deck();
		board.startGame(players, nplayers);
	}
	
	/**
	 * Método abstrato para pegar a entrada
	 * @param player (classe para representar um jogador)
	 * @return array de strings
	 */
	abstract public String[] getInput(Player player);
	
	/**
	 * Método para retornar as cartas do baralho
	 * @return cartas do baralho
	 */
	public Deck getBoard() {
		return board;
	}
	
	/**
	 * Método para retornar as cartas do cemitério
	 * @return cartas do cemitério
	 */
	public Deck getDead() {
		return dead;
	}
	
	/**
	 * Método que remove um jogador caso ele esteja eliminado
	 * @param from jogador que fez a ação
	 * @param to jogador que recebeu a ação
	 */
	private void isDead(String from, String to) {
		if (from != null)
			if (players.get(from).getHand().size() == 0)
				players.remove(from);
		if (to != null)
			if (players.get(to).getHand().size() == 0)
				players.remove(to);
	}
	
	/**
	 * Método que retorna o hashmap com os jogadores que estão no jogo
	 * @return jogadores que estão no jogo
	 */
	public HashMap<String, Player> getPlayers() {
		return players;
	}
	
	/**
	 * Método que realiza as ações do jogo
	 * @param action - código da ação
	 * @param from - quem fez a ação
	 * @param to - quem sofreu a ação
	 * @param contest - se foi contestada
	 * @param block - se foi bloqueada
	 * @return se a execução foi bem sucedida
	 * @throws PException - exceção
	 */
	public String play(int action, String from, String to, boolean contest, boolean block, String[] cards) throws PException {
		boolean ret = false;
		String winner = from;

		switch (action) {
		case (Actions.INCOME):
			ret = players.get(from).income();
			break;

		case (Actions.FOREIGN):
			if (block && contest) {
				// to == blocker
				if (players.get(to).checkCard("Duque")) {
					while (!ret)
						ret = players.get(from).removeCard(getInput(players.get(from)), dead);
					ret = false;

					while (!ret)
						ret = players.get(to).removeCard(new String[] { "Duque" }, board);
					players.get(to).draw(board, 1);
					winner = to;

				} else {
					while (!ret)
						ret = players.get(to).removeCard(getInput(players.get(to)), dead);
					ret = players.get(from).foreign();
				}
			} else if (!block) {
				ret = players.get(from).foreign();
			}

			break;

		case (Actions.COUP):
			if (players.get(from).coup())
				while (!ret)
					ret = players.get(to).removeCard(getInput(players.get(to)), dead);
			break;

		case (Actions.TAXES):
			if (contest) {
				// to == blocker
				if (players.get(from).checkCard("Duque")) {
					while (!ret)
						ret = players.get(to).removeCard(getInput(players.get(to)), dead);
					ret = false;

					while (!ret)
						ret = players.get(from).removeCard(new String[] { "Duque" }, board);
					players.get(from).draw(board, 1);

					ret = players.get(from).taxes();
				} else {
					while (!ret)
						ret = players.get(from).removeCard(getInput(players.get(from)), dead);
					winner = to;
				}
			} else
				ret = players.get(from).taxes();
			break;

		case (Actions.ASSASSINATE):
			if (players.get(from).assassinate()) {
				if (contest && !block) {
					// Se contestar
					if (players.get(from).checkCard("Assassino")) {
						while (!ret)
							ret = players.get(to).removeCard(
									players.get(to).getHand().toArray(new String[players.get(to).getHand().size()]),
									dead);
						ret = false;

						while (!ret)
							ret = players.get(from).removeCard(new String[] { "Assassino" }, board);
						players.get(from).draw(board, 1);

					} else {
						while (!ret)
							ret = players.get(from).removeCard(getInput(players.get(from)), dead);
						winner = to;
					}
				} else if (block) {
					// se bloquear com a condessa
					if (contest) {
						// se contestar que o cara tem a condessa
						if (players.get(to).checkCard("Condessa")) {
							while (!ret)
								ret = players.get(from).removeCard(getInput(players.get(from)), dead);
							ret = false;

							while (!ret)
								ret = players.get(to).removeCard(new String[] { "Condessa" }, board);
							ret = false;
							players.get(to).draw(board, 1);

							winner = to;

						} else {
							while (!ret)
								ret = players.get(to).removeCard(
										players.get(to).getHand().toArray(new String[players.get(to).getHand().size()]),
										dead);
						}
					} else {
						ret = true;
						winner = to;
					}
				} else
					while (!ret)
						ret = players.get(to).removeCard(getInput(players.get(to)), dead);
			}
			break;

		case (Actions.STEAL):
			if (contest && !block) {
				// se o cara que for roubado contestar o que tentou roubar
				if (players.get(from).checkCard("Capitao")) {
					while (!ret)
						ret = players.get(to).removeCard(getInput(players.get(to)), dead);
					ret = false;

					while (!ret)
						ret = players.get(from).removeCard(new String[] { "Capitao" }, board);
					players.get(from).draw(board, 1);

					ret = players.get(from).steal(players.get(to));
				} else {
					while (!ret)
						ret = players.get(from).removeCard(getInput(players.get(from)), dead);
					winner = to;
				}

			} else if (block) {
				// se o segundo cara tentar bloquear com o capitao ou embaixador
				if (contest) {
					// se o primeiro contestar o bloqueio do segundo
					if (players.get(to).checkCard("Embaixador") || players.get(to).checkCard("Capitao")) {
						while (!ret)
							ret = players.get(from).removeCard(getInput(players.get(from)), dead);
						ret = false;

						while (!ret)
							ret = players.get(to).removeCard(cards, board);
						players.get(to).draw(board, 1);
						winner = to;

					} else {
						while (!ret)
							ret = players.get(to).removeCard(getInput(players.get(to)), dead);
						ret = players.get(from).steal(players.get(to));
					}
				} else {
					ret = true;
					winner = to;
				}

			} else
				ret = players.get(from).steal(players.get(to));
			break;

		case (Actions.SWAP):
			if (contest) {
				if (players.get(from).checkCard("Embaixador")) {
					while (!ret)
						ret = players.get(to).removeCard(getInput(players.get(to)), dead);
					ret = false;

					players.get(from).draw(board, 2);
					while (!ret)
						ret = players.get(from).removeCard(getInput(players.get(from)), board);

				} else {
					while (!ret)
						ret = players.get(from).removeCard(getInput(players.get(from)), dead);
					winner = to;
				}
			} else {
				players.get(from).draw(board, 2);
				while (!ret)
					ret = players.get(from).removeCard(getInput(players.get(from)), board);
			}
			break;

		default:
			throw new PException("Invalid play.");
		}

		isDead(from, to);

		return winner;
	}
}