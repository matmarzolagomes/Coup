package ssc0103.coup.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ssc0103.coup.exception.GUIException;
import ssc0103.coup.game.Deck;
import ssc0103.coup.game.Player;
import ssc0103.coup.lan.Actions;

@SuppressWarnings("serial")
public class CoupGUI extends JPanel {
    private DeadGUI deadg;
    private ImageIcon logo;
    private final CoinGUI coing;
    private final HandGUI handg;
    private final LogGUI logg;
    private final PlayerGUI playerg;
    private final String me;
    
    public CoupGUI(String me, HashMap<String, Player> players) {
        super(new GridBagLayout());
        this.me = me;
        
        GridBagConstraints cons = new GridBagConstraints();
        
        logo = new ImageIcon("images/coup_logo.png");
        logo.setImage(logo.getImage().getScaledInstance(350, 90, 50));
        
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.FIRST_LINE_START;
        cons.gridheight = 1;
        cons.gridwidth = 1;

        cons.gridx = 0;
        cons.gridy = 0;
        cons.weightx = 0.3;
        cons.weighty = 1;
        
        JPanel pleft = new JPanel(new GridBagLayout());
        add(pleft, cons);
        
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
        
        coing = new CoinGUI(players.get(me));
        pleft.add(coing, cons);
        
        cons.gridx = 1;
        cons.gridy = 0;
        cons.weightx = 0.6;
        cons.weighty = 1;
        
        JPanel pcenter = new JPanel(new GridBagLayout());
        add(pcenter, cons);
        
        cons.gridx = 0;
        cons.weightx = 1;
        cons.weighty = 0.2;
        
        logg = new LogGUI();
        pcenter.add(logg, cons);
        
        cons.gridy = 1;
        cons.weighty = 0.8;
        
        handg = new HandGUI();
        pcenter.add(handg, cons);
        
        cons.gridx = 2;
        cons.gridy = 0;
        cons.weightx = 0.1;
        cons.weighty = 1;
        
        JPanel pright = new JPanel(new GridBagLayout());
        add(pright, cons);
        
        cons.gridx = 0;
        cons.gridy = 0;
        cons.weightx = 1;
        cons.weighty = 0.3;
        
        playerg = new PlayerGUI(players);
        pright.add(playerg, cons);
        
        cons.gridy = 1;
        cons.weighty = 0.7;
        
        pright.add(new JLabel(logo), cons);
    }
    
    public void updateAll(Actions data) {
        try {
            updateCoin(data.getPlayers().get(me));
            updateDead(data.getDead());
            updateHand(data.getPlayers().get(me).getHand());
            updateLog(data.getLog());
            updatePlayer();
        } catch (GUIException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
    }
    
    public void updateCoin(Player me) {
        coing.attCoins(me);
    }
    
    public void updateHand(Deck hand) throws GUIException {
        handg.showCards(hand);
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
        
        HashMap<String, Player> players = new HashMap<>();
        players.put("Rodrigo", new Player("Rodrigo"));
        players.put("Tico Liro", new Player("Tico Liro"));
        players.put("Victor", new Player("Victor"));
        
        CoupGUI game = new CoupGUI("Rodrigo", players);
        frame.add(game);
        
        Deck dead = new Deck();
        dead.add("Capitao");
        
        Deck hand = new Deck();
        hand.add("Condessa");
        hand.add("Duque");
      
        
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            System.out.println("Deu bosta.");
            System.exit(-1);
        }
        
        hand.remove("Condessa");
        dead.add("Condessa");
        
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            System.out.println("Deu bosta.");
            System.exit(-1);
        }
        
        players.get("Rodrigo").income();
    }
}
