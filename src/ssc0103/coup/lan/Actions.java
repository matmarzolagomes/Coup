package ssc0103.coup.lan;

import java.io.Serializable;
import java.util.HashMap;

import ssc0103.coup.game.Deck;
import ssc0103.coup.game.Player;

/**
 * Objeto utilizado como meio de comunicação entre o servidor (Board) e o
 * jogador (Player) na rede.
 * 
 * @author Bruno Mendes da Costa - Nº USP 9779433
 *
 */
@SuppressWarnings("serial")
public class Actions implements Serializable, Cloneable {
	// CONSTANTES
	public static final int LEFT = 99;
	public static final int WINNER = 100;
	public static final int INCOME = 0;
	public static final int FOREIGN = 1;
	public static final int COUP = 2;
	public static final int TAXES = 3;
	public static final int ASSASSINATE = 4;
	public static final int STEAL = 5;
	public static final int SWAP = 6;
	public static final int LOAD_INTERFACE = 7;
	public static final int UPDATE_INTERFACE = 8;
	public static final int UPDATE_ALL_INTERFACE = 9;
	public static final int LOAD_PLAYER_ACTIONS = 10;
	public static final int GET_NAME = 11;
	public static final int SERVER_MESSAGE = 12;
	public static final int GET_INPUT = 13;
	public static final int ON_HOLD = 14;

	// ATRIBUTOS
	private int id;
	private String from;
	private String to;
	private boolean contest;
	private boolean block;
	private String log;
	private HashMap<String, Player> players;
	private Player player;
	private Deck dead;
	private String message;
	private String[] cards;

	/**
	 * Construtor vazio da classe Actions.
	 */
	public Actions() {
	}

	/**
	 * 
	 * @return Retorna o id da ação.
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @return Retorna o nome do jogador que fez a ação.
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * 
	 * @return Retorna o nome do jogador que está recebendo a ação.
	 */
	public String getTo() {
		return to;
	}

	/**
	 * 
	 * @return Retorna true se uma ação foi contestada e false caso não tenha
	 *         sido.
	 */
	public boolean isContest() {
		return contest;
	}

	/**
	 * 
	 * @return Retorna true se uma ação foi bloqueada e false caso não tenha
	 *         sido.
	 */
	public boolean isBlock() {
		return block;
	}

	/**
	 * 
	 * @return Retorna uma mensagem do log do jogo.
	 */
	public String getLog() {
		return log;
	}

	/**
	 * 
	 * @return Retorna um HashMap que associa o nome do jogador ao seu Objeto.
	 */
	public HashMap<String, Player> getPlayers() {
		return players;
	}

	/**
	 * 
	 * @return Retorna o Objeto de um jogador.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * 
	 * @return Retorna o cemitério do jogo.
	 */
	public Deck getDead() {
		return dead;
	}

	/**
	 * 
	 * @return Retorna uma mensagem de caracteres.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @return Retorna um vetor de cartas.
	 */
	public String[] getCards() {
		return cards;
	}

	/**
	 * Atribui uma ação para o ID do Objeto Actions.
	 * 
	 * @param id
	 *            Ação que será atribuida.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Atribui uma String ao nome do jogador que está fazendo a ação.
	 * 
	 * @param from
	 *            Nome do jogador.
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * Atribui uma string ao nome do jogador que está recebendo a ação.
	 * 
	 * @param to
	 *            Nome do jogador.
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * Atribui um estado a variável contest que indica se uma ação foi
	 * contestada ou não.
	 * 
	 * @param contest
	 *            True ou False para contestação.
	 */
	public void setContest(boolean contest) {
		this.contest = contest;
	}

	/**
	 * Atribui um estado a variável block que indica se uma ação foi bloqueada
	 * ou não.
	 * 
	 * @param block
	 *            True ou False para bloqueio.
	 */
	public void setBlock(boolean block) {
		this.block = block;
	}

	/**
	 * Atribui uma mensagem ao log do jogo.
	 * 
	 * @param log
	 *            Mensagem de log.
	 */
	public void setLog(String log) {
		this.log = log;
	}

	/**
	 * Atribui um HashMap de jogadores a sua variável.
	 * 
	 * @param players
	 *            HashMap de jogadores.
	 */
	public void setPlayers(HashMap<String, Player> players) {
		this.players = players;
	}

	/**
	 * Atribui um Objeto Player a sua variável.
	 * 
	 * @param player
	 *            Objeto que representa o jogador.
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Atribui a variável de cemitério as cartas do cemitério.
	 * 
	 * @param dead
	 *            Cemitério do jogo.
	 */
	public void setDead(Deck dead) {
		this.dead = dead;
	}

	/**
	 * Atribui a variável mensagem uma mensagem.
	 * 
	 * @param message
	 *            Mensagem passada por parâmetro.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Atribui a variável cartas um vetor de nomes correspondente as cartas.
	 * 
	 * @param cards
	 *            Vetor de cartas.
	 */
	public void setCards(String[] cards) {
		this.cards = cards;
	}

	/**
	 * Implementação da interface Cloneable.
	 */
	@Override
	protected Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}