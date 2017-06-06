package scc0103.coup.lan;

import java.io.Serializable;
import java.util.List;

import ssc0103.coup.game.Coup;

public class Actions implements Serializable {
	// CONSTANTES
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
	public static final int ASSASSINATE_RESPOND = 12;
		
	// ATRIBUTOS
	private int id;	
	private String from;
	private String to;
	private boolean allow;
	private boolean contest;
	private boolean block;
	private List<String> log;
	private Coup coup;
	private boolean playerResponse;

	public Actions() {
		// TODO Auto-generated constructor stub
	}

	public boolean isAllow() {
		return allow;
	}

	public void setAllow(boolean allow) {
		this.allow = allow;
	}

	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
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

	public List<String> getLog() {
		return log;
	}

	public Coup getCoup() {
		return coup;
	}

	public boolean isPlayerResponse() {
		return playerResponse;
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

	public void setLog(List<String> log) {
		this.log = log;
	}

	public void setCoup(Coup coup) {
		this.coup = coup;
	}

	public void setPlayerResponse(boolean playerResponse) {
		this.playerResponse = playerResponse;
	}
	
	
}