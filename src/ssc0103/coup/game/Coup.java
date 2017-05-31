package ssc0103.coup.game;

import java.util.LinkedList;

import ssc0103.coup.exception.PException;

public abstract class Coup {
	private LinkedList<Player> players;
	private Deck board;
	private Deck dead;
	
	public Coup(int nplayers) {
		players = new LinkedList<Player>();
		for(int i = 0; i < nplayers; i++) 
			players.add(new Player());
		dead = new Deck();
		board = new Deck();
		board.startGame(nplayers);
	}

	abstract public String getInput();
	
	public Deck getBoard() {
		return board;
	}
	
	public Deck getDead() {
		return dead;
	}
	
	private void isDead(int index) {
		if(players.get(index).getHand().size() == 0) players.remove(index);
	}
	
	public boolean play(int action, int from, int to, boolean contest, boolean block) throws PException {
		boolean ret = false;
		
		switch (action) {
		case(0):
			// Income
			ret = players.get(from).Income();
		
			break;
		case(1):
			// Foreign
			if(block && contest) {
				// to == blocker
				if(players.get(to).checkCard("Duke")) {
					while(!ret)
						ret = players.get(from).removeCard(getInput());
				} else { 
					while(!ret)
						ret = players.get(to).removeCard(getInput());
					ret = players.get(from).Foreign();
				}
			} else if(!block) ret = players.get(from).Foreign();
		
			break;
		case(2):
			// Coup
			if(players.get(from).Coup())
				while(!ret)
					ret = players.get(to).removeCard(getInput());
				
			break;
		case(3):
			// Taxes
			if(contest) {
				// to == blocker
				if(players.get(from).checkCard("Duke")) {
					while(!ret)
						ret = players.get(to).removeCard(getInput());
				} else {
					while(!ret)
						ret = players.get(from).removeCard(getInput());
					ret = players.get(from).Taxes();
				}
			} else ret = players.get(from).Taxes();
		
			break;
		case(4):
			// Assassinate
			if (contest) {
				
			} else ret = player.get(to).a
			
			break;
		case(5):
			// Steal
			
			break;
		case(6):
			// Swap
			
			break;
		default:
			throw new PException("Invalid play.");
			
			break;
		
		}
		
		
		isDead(from);
		isDead(to);
		
		return ret;
	}
	
	public static void main(String[] args) {
	}
}