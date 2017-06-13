package ssc0103.coup.game;

import java.net.Socket;
import java.util.HashMap;

import ssc0103.coup.exception.PException;
import ssc0103.coup.lan.Actions;

public abstract class Coup  {
    private HashMap<String,Player> players;
    private Deck board;
    private Deck dead;
    protected Socket playerTurn;
    
    /**
     * Inicializa os atributos do jogo.
     * @param nplayers
     * @param order
     */
    public void instanceGame(int nplayers, String[] order) {
	players = new HashMap<String,Player>();
        for(int i = 0; i < nplayers; i++)
            players.put(order[i], new Player());
        dead = new Deck();
        board = new Deck();
        board.startGame(nplayers);
    }
    
    abstract public String[] getInput(Player player);
    
    public Deck getBoard() {
        return board;
    }
    
    public Deck getDead() {
        return dead;
    }
    
    private void isDead(String index) {
    	if(index != null) {
    		if(players.get(index).getHand().size() == 0) players.remove(index);
    	}
    }
    
    public HashMap<String, Player> getPlayers() {
        return players;
    }
    
    public String play(int action, String from, String to, boolean contest, boolean block) throws PException {
        boolean ret = false;
        String winner = from;
        
        switch (action) {
            case(Actions.INCOME):
                // Income
                ret = players.get(from).income();
                
                break;
            case(Actions.FOREIGN):
                // Foreign
                if(block && contest) {
                    // to == blocker
                    if(players.get(to).checkCard("Duke")) {
                        while(!ret)
                            ret = players.get(from).removeCard(getInput(players.get(from)), dead);
                        ret = false;
                        
                        while(!ret)
                            ret = players.get(to).removeCard(getInput(players.get(to)), dead);
                        players.get(to).draw(board, 1);
                        winner = to;
                        
                        
                    } else {
                        while(!ret)
                            ret = players.get(to).removeCard(getInput(players.get(to)), dead);
                        ret = players.get(from).foreign();
                    }
                } else if(!block) {
                	ret = players.get(from).foreign();
                }
                
                break;
            case(Actions.COUP):
                // Coup
                if(players.get(from).coup())
                    while(!ret)
                        ret = players.get(to).removeCard(getInput(players.get(to)), dead);
                break;
            case(Actions.TAXES):
                // Taxes
                if(contest) {
                    // to == blocker
                    if(players.get(from).checkCard("Duke")) {
                        while(!ret)
                            ret = players.get(to).removeCard(getInput(players.get(to)), dead);
                        ret = false;
                        
                        while(!ret)
                            ret = players.get(from).removeCard(getInput(players.get(from)), dead);
                        players.get(from).draw(board, 1);
                        
                        ret = players.get(from).taxes();
                    } else {
                        while(!ret)
                            ret = players.get(from).removeCard(getInput(players.get(from)), dead);
                        winner = to;
                    }
                } else ret = players.get(from).taxes();
            
                break;
            case(Actions.ASSASSINATE):
                // Assassinate
                if (players.get(from).assassinate()) {
                    if (contest && !block) {
                        // Se contestar
                        if (players.get(from).checkCard("Assassino")) {
                            while(!ret)ret = players.get(to).removeCard(getInput(players.get(to)), dead);
                            ret = false;
                            while(!ret) ret = players.get(to).removeCard(getInput(players.get(to)), dead);
                            ret = false;
                            
                            while(!ret)ret = players.get(from).removeCard(getInput(players.get(from)), dead);
                            players.get(from).draw(board, 1);
                            
                        } else {
                            while (!ret) ret = players.get(from).removeCard(getInput(players.get(from)), dead);
                            winner = to;
                        }
                    } else if (block) {
                        // se bloquear com a condessa
                        if (contest) {
                            // se contestar que o cara tem a condessa
                            if (players.get(to).checkCard("Condessa")) {
                                while(!ret) ret = players.get(from).removeCard(getInput(players.get(from)), dead);
                                ret = false;
                                
                                while(!ret) ret = players.get(to).removeCard(getInput(players.get(to)), dead);
                                ret = false;
                                players.get(to).draw(board, 1);
                                
                                winner = to;
                            
                            } else {
                                while(!ret) ret = players.get(to).removeCard(getInput(players.get(to)), dead);
                                ret = false;
                                while(!ret) ret = players.get(to).removeCard(getInput(players.get(to)), dead);
                            }
                        } else {
                        	ret = true;
                        	winner = to;
                        }
                    } else
                        while(!ret) ret = players.get(to).removeCard(getInput(players.get(to)), dead);
                }
                
                break;
            case(Actions.STEAL):
                // Steal
                if (contest && !block) {
                    // se o cara que for roubado contestar o que tentou roubar
                    if (players.get(from).checkCard("Capitao")) {
                        while(!ret) ret = players.get(to).removeCard(getInput(players.get(to)), dead);
                        ret = false;
                        
                        while(!ret) ret = players.get(from).removeCard(getInput(players.get(from)), dead);
                        players.get(from).draw(board, 1);
                        
                        ret = players.get(from).steal(players.get(to));
                    } else {
                        while(!ret) ret = players.get(from).removeCard(getInput(players.get(from)), dead);
                        winner = to;
                    }
                    
                } else if (block){
                    // se o segundo cara tentar bloquear com o capitao ou embaixador
                    if (contest) {
                        // se o primeiro contestar o bloqueio do segundo
                        if (players.get(to).checkCard("Embaixador") || players.get(to).checkCard("Capitao")) {
                        	while(!ret) ret = players.get(from).removeCard(getInput(players.get(from)), dead);
                        	ret = false;
                        	
                        	while(!ret) ret = players.get(to).removeCard(getInput(players.get(to)), dead);
                        	players.get(to).draw(board, 1);
                        	winner = to;
                        
                        }
                        else {
                            while(!ret) ret = players.get(to).removeCard(getInput(players.get(to)), dead);
                            ret = players.get(from).steal(players.get(to));
                        }
                    } else {
                    	ret = true;
                    	winner = to;
                    }
                    
                } else ret = players.get(from).steal(players.get(to));
                
                break;
            case(Actions.SWAP):
                // Swap
                if (contest) {
                    if (players.get(from).checkCard("Embaixador")) {
                        while(!ret) ret = players.get(to).removeCard(getInput(players.get(to)), dead);
                        ret = false;
                        
                        players.get(from).draw(board, 2);
                        while(!ret) ret = players.get(from).removeCard(getInput(players.get(from)), dead);
                        ret = false;
                        while(!ret) ret = players.get(from).removeCard(getInput(players.get(from)), dead);
                        
                    } else {
                        while(!ret) ret = players.get(from).removeCard(getInput(players.get(from)), dead);
                        winner = to;
                    }
                } else {
                    players.get(from).draw(board, 2);
                    while(!ret) ret = players.get(from).removeCard(getInput(players.get(from)), dead);
                    ret = false;
                    while(!ret) ret = players.get(from).removeCard(getInput(players.get(from)), dead);
                    
                }
                
                break;
            default:
                throw new PException("Invalid play.");
                
        }
        
        isDead(from);
        isDead(to);
        
        return winner;
    }
    
    public static void main(String[] args) {
    }
}