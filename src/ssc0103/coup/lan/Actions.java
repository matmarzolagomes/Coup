package ssc0103.coup.lan;

import java.io.Serializable;
import java.util.HashMap;

import ssc0103.coup.game.Deck;
import ssc0103.coup.game.Player;

@SuppressWarnings("serial")
public class Actions implements Serializable {
    // CONSTANTES
    public static final int LEFT = 99;
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
    public static final int ASSASSINATE_CONTEST = 12;
    public static final int SERVER_MESSAGE = 13;
    public static final int PLAYER_RESPONSE = 14;
    public static final int ASSASSINATE_BLOCK = 15;
    public static final int STEAL_BLOCK = 16;
    public static final int STEAL_CONTEST = 17;
    public static final int ALLOW = 18;
    public static final int GET_INPUT = 19;

    // ATRIBUTOS
    private int id;
    private String from;
    private String to;
    private boolean allow;
    private boolean contest;
    private boolean block;
    private String log;
    private HashMap<String,Player> players;
    private Deck dead;    
    private boolean playerResponse;
    private String message;
    private String[] cards;

    public Actions() {
	// TODO Auto-generated constructor stub
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

    public boolean isAllow() {
        return allow;
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

    public Deck getDead() {
        return dead;
    }

    public boolean isPlayerResponse() {
        return playerResponse;
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

    public void setAllow(boolean allow) {
        this.allow = allow;
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

    public void setDead(Deck dead) {
        this.dead = dead;
    }

    public void setPlayerResponse(boolean playerResponse) {
        this.playerResponse = playerResponse;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCards(String[] cards) {
        this.cards = cards;
    } 
}