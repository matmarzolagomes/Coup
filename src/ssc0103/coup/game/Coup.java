package ssc0103.coup.game;

import java.util.HashMap;

import ssc0103.coup.exception.PException;
import ssc0103.coup.lan.Actions;

public abstract class Coup {
	private HashMap<String, Player> players;
	private Deck board;
	private Deck dead;

	/**
	 * Inicializa os atributos do jogo.
	 * 
	 * @param nplayers
	 * @param order
	 */
	public void instanceGame(int nplayers, String[] order) {
		players = new HashMap<String, Player>();
		for (int i = 0; i < nplayers; i++)
			players.put(order[i], new Player(order[i]));
		dead = new Deck();
		board = new Deck();
		board.startGame(players, nplayers);
	}

	abstract public String[] getInput(Player player);

	public Deck getBoard() {
		return board;
	}

	public Deck getDead() {
		return dead;
	}

	private void isDead(String from, String to) {
		if (from != null)
			if (players.get(from).getHand().size() == 0)
				players.remove(from);
		if (to != null)
			if (players.get(to).getHand().size() == 0)
				players.remove(to);
	}

	public HashMap<String, Player> getPlayers() {
		return players;
	}

	public String play(int action, String from, String to, boolean contest, boolean block) throws PException {
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
						ret = players.get(to).removeCard(new String[] { "Duque" }, dead);
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
						ret = players.get(from).removeCard(new String[] { "Duque" }, dead);
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
							ret = players.get(from).removeCard(new String[] { "Assassino" }, dead);
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
								ret = players.get(to).removeCard(new String[] { "Condessa" }, dead);
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
						ret = players.get(from).removeCard(new String[] { "Capitao" }, dead);
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

						// PRECISA VERIFICAR COM QUAL CARTA ESPECIFICAMENTE O
						// JOGADOR BLOQUEOU PARA RETIRÁ-LA DO BARALHO.
						while (!ret)
							ret = players.get(to).removeCard(getInput(players.get(to)), dead);
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
						ret = players.get(from).removeCard(getInput(players.get(from)), dead);

				} else {
					while (!ret)
						ret = players.get(from).removeCard(getInput(players.get(from)), dead);
					winner = to;
				}
			} else {
				players.get(from).draw(board, 2);
				while (!ret)
					ret = players.get(from).removeCard(getInput(players.get(from)), dead);
			}
			break;

		default:
			throw new PException("Invalid play.");
		}

		isDead(from, to);

		return winner;
	}
}