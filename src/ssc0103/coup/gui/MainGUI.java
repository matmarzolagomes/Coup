package ssc0103.coup.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import ssc0103.coup.game.Deck;
import ssc0103.coup.game.Player;
import ssc0103.coup.lan.Actions;

public class MainGUI {
	private static JFrame game;
	private static ConectGUI c;
	private static CoupGUI g;
	
	public static void main(String[] args) {
		game = new JFrame("Coup");
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setExtendedState(JFrame.MAXIMIZED_BOTH);
        game.setVisible(true);
		
		JMenuBar menubar = new JMenuBar();
		
		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_A);
		help.getAccessibleContext().setAccessibleDescription("Help related to the game.");
		menubar.add(help);

		JMenuItem play = new JMenuItem("How to play.", KeyEvent.VK_H);
		play.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
		play.getAccessibleContext().setAccessibleDescription("How to play the game.");
		help.add(play);
		
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked");
			}
		});
		
		c = new ConectGUI();
		game.add(c);
		
		game.setJMenuBar(menubar);
		game.pack();
	}
	
	public static void startGame(String me, HashMap<String, Player> players) {
		game.remove(c);
		
		g = new CoupGUI(me, players);
		game.add(g);
		
		Deck dead = new Deck();
        Actions act = new Actions();
        act.setPlayers(players);
        act.setDead(dead);
        act.setLog("Game started");
        
        g.updateAll(act);
	}
}
