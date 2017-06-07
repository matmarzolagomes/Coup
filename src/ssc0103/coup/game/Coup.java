package ssc0103.coup.game;

import java.util.HashMap;

import ssc0103.coup.exception.PException;

public abstract class Coup {
	private HashMap<String,Player> players;
	private Deck board;
	private Deck dead;
	
	public Coup(int nplayers, String[] order) {
		players = new HashMap<String,Player>();
		for(int i = 0; i < nplayers; i++) 
			players.put(order[i], new Player());
		dead = new Deck();
		board = new Deck();
		board.startGame(nplayers);
	}

	abstract public String[] getInput();
	
	public Deck getBoard() {
		return board;
	}
	
	public Deck getDead() {
		return dead;
	}
	
	private void isDead(String index) {
		if(players.get(index).getHand().size() == 0) players.remove(index);
	}
	
	public boolean play(int action, String from, String to, boolean contest, boolean block) throws PException {
		boolean ret = false;
		
		switch (action) {
		case(0):
			// Income
			ret = players.get(from).income();
		
			break;
		case(1):
			// Foreign
			if(block && contest) {
				// to == blocker
				if(players.get(to).checkCard("Duke")) {
					while(!ret)
						ret = players.get(from).removeCard(getInput(), dead);
				} else { 
					while(!ret)
						ret = players.get(to).removeCard(getInput(), dead);
					ret = players.get(from).foreign();
				}
			} else if(!block) ret = players.get(from).foreign();
		
			break;
		case(2):
			// Coup
			if(players.get(from).coup())
				while(!ret)
					ret = players.get(to).removeCard(getInput(), dead);
			break;
		case(3):
			// Taxes
			if(contest) {
				// to == blocker
				if(players.get(from).checkCard("Duke")) {
					while(!ret)
						ret = players.get(to).removeCard(getInput(), dead);
				} else {
					while(!ret)
						ret = players.get(from).removeCard(getInput(), dead);
					ret = players.get(from).taxes();
				}
			} else ret = players.get(from).taxes();
			break;
		case(4):
			// Assassinate
			if (players.get(from).assassinate()) {
				if (contest && !block) {
					// Se contestar
					if (players.get(from).checkCard("Assassino")) {
						while(!ret)ret = players.get(to).removeCard(getInput(), dead);
						ret = false;
						while(!ret) ret = players.get(to).removeCard(getInput(), dead);
					} else
						while (!ret) ret = players.get(from).removeCard(getInput(), dead);
				} else if (block) {
					// se bloquear com a condessa
					if (contest) {
						// se contestar que o cara tem a condessa
						if (players.get(to).checkCard("Condessa")) {
							while(!ret) ret = players.get(from).removeCard(getInput(), dead);
						} else {
							while(!ret) ret = players.get(to).removeCard(getInput(), dead);
							ret = false;
							while(!ret) ret = players.get(to).removeCard(getInput(), dead);
						}
					} else ret = true;
				} else 
					while(!ret) ret = players.get(to).removeCard(getInput(), dead);
			}
			
			break;
		case(5):
			// Steal
			if (contest && !block) {
				// se o cara que for roubado contestar o que tentou roubar
				if (players.get(from).checkCard("Capitao")) {
					while(!ret) ret = players.get(to).removeCard(getInput(), dead);
					ret = players.get(from).steal(players.get(to));
				} else 
					while(!ret) ret = players.get(from).removeCard(getInput(), dead);
				
			} else if (block){
				// se o segundo cara tentar bloquear com o capitao ou embaixador
				if (contest) {
					// se o primeiro contestar o bloqueio do segundo
					if (players.get(to).checkCard("Embaixador") || players.get(to).checkCard("Capitao"))
						while(!ret) ret = players.get(from).removeCard(getInput(), dead);
					 else {
						 while(!ret) ret = players.get(to).removeCard(getInput(), dead);
						 ret = players.get(from).steal(players.get(to));
					 }
				} else ret = true;
					
			} else ret = players.get(from).steal(players.get(to));
			
			break;
		case(6):
			// Swap
			if (contest) {
				if (players.get(from).checkCard("Embaixador")) {
					while(!ret) ret = players.get(to).removeCard(getInput(), dead);
				} else {
					while(!ret) ret = players.get(from).removeCard(getInput(), dead);
				}
			} else {
				players.get(from).draw(board);
				while(!ret) ret = players.get(from).removeCard(getInput(), dead);				
			}
			
			break;
		default:
			throw new PException("Invalid play.");
		
		}
		
		isDead(from);
		isDead(to);
		
		return ret;
	}
	
	public static void main(String[] args) {
	}
}