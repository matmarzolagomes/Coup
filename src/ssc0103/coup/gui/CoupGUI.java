package ssc0103.coup.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ssc0103.coup.exception.GUIException;
import ssc0103.coup.game.Coup;
import ssc0103.coup.game.Deck;
import ssc0103.coup.game.Player;

public class CoupGUI extends Coup {
    public CoupGUI(Player me, int nplayers, String[] order, Deck dead, JFrame frame, HashMap<String, Player> players, Deck deck) {
        super(nplayers, order);
        
        JPanel window = new JPanel(new GridBagLayout());
        
        GridBagConstraints cons = new GridBagConstraints();
        
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.FIRST_LINE_START;
        cons.gridheight = 1;
        cons.gridwidth = 1;

        cons.gridx = 0;
        cons.gridy = 0;
        cons.weightx = 0.2;
        cons.weighty = 1;
        
        JPanel pleft = new JPanel(new GridBagLayout());
        window.add(pleft, cons);
        
        cons.gridx = 0;
        cons.gridy = 0;
        cons.weightx = 0.2;
        cons.weighty = 0.8;
        
        try {
            DeadGUI deadg = new DeadGUI(dead, nplayers);
            pleft.add(deadg, cons);
        } catch (GUIException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        
        cons.gridy = 1;
        cons.weighty = 0.2;
        
        CoinGUI coing = new CoinGUI(me);
        pleft.add(coing, cons);
        
        cons.gridx = 1;
        cons.gridy = 0;
        cons.weightx = 0.6;
        cons.weighty = 1;
        
        JPanel pcenter = new JPanel(new GridBagLayout());
        window.add(pcenter, cons);
        
        cons.gridx = 0;
        cons.weightx = 1;
        cons.weighty = 0.4;
        
        LogGUI logg = new LogGUI();
        pcenter.add(logg, cons);
        
        cons.gridy = 1;
        cons.weighty = 0.6;
        
        HandGUI handg = new HandGUI();
        pcenter.add(handg, cons);
        
        try {
            handg.showCards(deck);
        } catch (GUIException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        
        cons.gridx = 2;
        cons.gridy = 0;
        cons.weightx = 0.2;
        cons.weighty = 1;
        
        PlayerGUI playerg = new PlayerGUI(players);
        window.add(playerg, cons);
        
        frame.add(window);
    }

    @Override
    public String[] getInput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(String[] args) {
        JFrame game = new JFrame("Game");
        game.setExtendedState(JFrame.MAXIMIZED_BOTH);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        String[] order = new String[3];
        order[0] = "Rodrigo";
        order[1] = "Matheus";
        order[2] = "Victor";
        
        Deck dead = new Deck();
        dead.add("Capitao");
        dead.add("Duque");
        dead.add("Condessa");
        dead.add("Inquisidor");
        dead.add("Embaixador");
        dead.add("Assassino");
        
        HashMap<String, Player> players = new HashMap<>();
        players.put("Rodrigo", new Player("Rodrigo"));
        players.put("Matheus", new Player("Matheus"));
        players.put("Victor", new Player("Victor"));
        
        Deck deck = new Deck();
        deck.add("Capitao");
        deck.add("Duque");
        
        CoupGUI coup = new CoupGUI(new Player(order[0]), 3, order, dead, game, players, deck);
        
        game.setVisible(true);
    }
}
