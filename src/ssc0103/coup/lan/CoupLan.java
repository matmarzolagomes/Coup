package ssc0103.coup.lan;

import java.io.Serializable;

import ssc0103.coup.game.Coup;
import ssc0103.coup.game.Deck;

public class CoupLan extends Coup implements Serializable {

	public CoupLan(int numPlayers, String[] order) {
		super(numPlayers, order);
	}

	@Override
	public String[] getInput(Deck hand) {
	    // TODO Auto-generated method stub
	    return null;
	}

}
