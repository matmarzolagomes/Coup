package ssc0103.coup.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ssc0103.coup.exception.GUIException;
import ssc0103.coup.game.Coup;
import ssc0103.coup.game.Deck;
import ssc0103.coup.game.Player;

public class CoupGUI extends JPanel {
    private DeadGUI deadg;
    private final CoinGUI coing;
    private final HandGUI handg;
    private final LogGUI logg;
    private final PlayerGUI playerg;
    private final Player me;
    
    public CoupGUI(String me, String[] order, HashMap<String, Player> players, JFrame frame) {
        super(players.size(), order);
        
        this.me = players.get(me);
        
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
            deadg = new DeadGUI(players.size());
            pleft.add(deadg, cons);
        } catch (GUIException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        
        cons.gridy = 1;
        cons.weighty = 0.2;
        
        coing = new CoinGUI(this.me);
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
        
        logg = new LogGUI();
        pcenter.add(logg, cons);
        
        cons.gridy = 1;
        cons.weighty = 0.6;
        
        handg = new HandGUI();
        pcenter.add(handg, cons);
        
        cons.gridx = 2;
        cons.gridy = 0;
        cons.weightx = 0.2;
        cons.weighty = 1;
        
        playerg = new PlayerGUI(players);
        window.add(playerg, cons);
        
        frame.add(window);
    }
    
    public void updateAll(Deck dead, Deck hand, String log) {
        try {
            updateCoin();
            updateDead(dead);
            updateLog(log);
            updatePlayer();
        } catch (GUIException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
    }
    
    public void updateCoin() {
        coing.attCoins(me);
    }
    
    public void updateDead(Deck dead) throws GUIException {
        deadg.update(dead);
    }
    
    public void updateLog(String log) {
        logg.insertLog(log);
    }
    
    public void updatePlayer() {
        playerg.attTable();
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        
        String[] order = new String[3];
        order[0] = "Rodrigo";
        order[1] = "Tico Liro";
        order[2] = "Victor";

        HashMap<String, Player> players = new HashMap<>();
        players.put("Rodrigo", new Player("Rodrigo"));
        players.put("Tico Liro", new Player("Tico Liro"));
        players.put("Victor", new Player("Victor"));
        
        CoupGUI game = new CoupGUI("Rodrigo", order, players, frame);
        
        Deck dead = new Deck();
        dead.add("Capitao");
        
        Deck hand = new Deck();
        hand.add("Condessa");
        hand.add("Duque");
        
        game.updateAll(dead, hand, "Testando.");
        
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            System.out.println("Deu bosta.");
            System.exit(-1);
        }
        
        hand.remove("Condessa");
        dead.add("Condessa");
        
        game.updateAll(dead, hand, "Condessa is ded.");

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            System.out.println("Deu bosta.");
            System.exit(-1);
        }
        
        players.get("Rodrigo").income();
        game.updateAll(dead, hand, "Here comes the money.");
    }
}
