package ssc0103.coup.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;

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
        logo.setImage(logo.getImage().getScaledInstance(300, 70, 100));
        
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.FIRST_LINE_START;
        cons.gridheight = 1;
        cons.gridwidth = 1;

        cons.gridx = 0;
        cons.gridy = 0;
        cons.weightx = 0.1;
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
        
        cons.gridx = 1;
        cons.gridy = 0;
        cons.weightx = 0.8;
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
        cons.weighty = 0.5;
        
        playerg = new PlayerGUI(players);
        pright.add(playerg, cons);
        
        cons.gridy = 1;
        cons.weighty = 0.1;
        
        coing = new CoinGUI(players.get(me));
        pright.add(coing, cons);
        
        cons.gridy = 2;
        cons.weighty = 0.4;
        
        cons.ipady = 0;
        
        pright.add(new JLabel(logo), cons);
    }
    
    public void updateAll(Actions data) {
        try {
            updateCoin(data.getPlayers().get(me));
            updateDead(data.getDead());
            updateHand(data.getPlayers().get(me).getHand());
            updateLog(data.getLog());
            updatePlayer(data.getPlayers());
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
    
    public void updatePlayer(HashMap<String,Player> players) {
        playerg.attTable(players);
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
        players.put("Victor1", new Player("Victor1"));
        players.put("Victor2", new Player("Victor2"));
        players.put("Victor3", new Player("Victor3"));
        players.put("Victor4", new Player("Victor4"));
        players.put("Victor5", new Player("Victor5"));
        players.put("Victor6", new Player("Victor6"));
        players.put("Victor7", new Player("Victor7"));
        players.put("Victor8", new Player("Victor8"));
        players.put("Victor9", new Player("Victor9"));
        players.put("Victor0", new Player("Victor0"));
        players.put("Victor11", new Player("Victor11"));
        players.put("Victor12", new Player("Victor12"));
        players.put("Victor31", new Player("Victor31"));
        players.put("Victor25", new Player("Victor25"));
        players.put("aVictor3", new Player("aVictor3"));
        players.put("aVictor4", new Player("aVictor4"));
        players.put("aVictor5", new Player("aVictor5"));
        players.put("aVictor6", new Player("aVictor6"));
        players.put("aVictor7", new Player("aVictor7"));
        players.put("aVictor8", new Player("aVictor8"));
        players.put("aVictor9", new Player("aVictor9"));
        players.put("aVictor0", new Player("aVictor0"));
        players.put("aVictor11", new Player("aVictor11"));
        players.put("aVictor12", new Player("aVictor12"));
        players.put("aVictor31", new Player("aVictor31"));
        players.put("aVictor25", new Player("aVictor25"));
        players.put("bVictor3", new Player("bVictor3"));
        players.put("bVictor4", new Player("bVictor4"));
        players.put("bVictor5", new Player("bVictor5"));
        players.put("bVictor6", new Player("bVictor6"));
        players.put("bVictor7", new Player("bVictor7"));
        players.put("bVictor8", new Player("bVictor8"));
        players.put("bVictor9", new Player("bVictor9"));
        players.put("bVictor0", new Player("bVictor0"));
        players.put("bVictor11", new Player("bVictor11"));
        players.put("bVictor12", new Player("bVictor12"));
        players.put("bVictor31", new Player("bVictor31"));
        players.put("bVictor25", new Player("bVictor25"));
        players.put("bVictor11", new Player("bVictor11"));
        players.put("bVictor12", new Player("bVictor12"));
        players.put("bVictor31", new Player("bVictor31"));
        players.put("bVictor25", new Player("bVictor25"));
        players.put("bVictor25", new Player("bVictor25"));
        players.put("bVictor11", new Player("bVictor11"));
        players.put("bVictor12", new Player("bVictor12"));
        players.put("bVictor31", new Player("bVictor31"));
        players.put("bVictor25", new Player("bVictor25"));
        
        CoupGUI game = new CoupGUI("Rodrigo", players);
        frame.add(game);
        
        Deck dead = new Deck();
        dead.add("Condessa");
        dead.add("Capitao");

        Deck board = new Deck();
        board.add("Assassino");
        board.add("Capitao");
        
        players.get("Rodrigo").draw(board, 2);
        players.get("Rodrigo").income();
        
        Actions act = new Actions();
        act.setPlayers(players);
        act.setDead(dead);
        act.setLog("Update all.");
        
        game.updateAll(act);
        frame.pack();
    }
}
