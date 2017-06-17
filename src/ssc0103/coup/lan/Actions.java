package ssc0103.coup.lan;

import java.io.Serializable;
import java.util.HashMap;

import ssc0103.coup.game.Deck;
import ssc0103.coup.game.Player;

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

	public Actions() {
	}

	public int getId() {
		return id;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public boolean isContest() {
		return contest;
	}

	public boolean isBlock() {
		return block;
	}

	public String getLog() {
		return log;
	}

	public HashMap<String, Player> getPlayers() {
		return players;
	}

	public Player getPlayer() {
		return player;
	}

	public Deck getDead() {
		return dead;
	}

	public String getMessage() {
		return message;
	}

	public String[] getCards() {
		return cards;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setContest(boolean contest) {
		this.contest = contest;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public void setPlayers(HashMap<String, Player> players) {
		this.players = players;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setDead(Deck dead) {
		this.dead = dead;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCards(String[] cards) {
		this.cards = cards;
	}

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