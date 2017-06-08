package ssc0103.coup.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ssc0103.coup.game.Deck;
import ssc0103.coup.game.Player;

@SuppressWarnings("serial")
public class PlayerGUI extends JPanel{
	LinkedList<Player> players;
	
	public PlayerGUI(LinkedList<Player> players) {
		super();
		this.players = players;
		this.setLayout(new GridBagLayout());
	}
	
	public void showPlayers() {
		GridBagConstraints c = new GridBagConstraints();
		
		c.weighty = 0.1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		
		c.gridx = 0;
		c.weightx = 0.8;
		this.add(new JLabel("JOGADORES"), c);
		c.gridx = 1;
		c.weightx = 0.1;
		this.add(new JLabel("MOEDAS"), c);
		c.gridx = 2;
		c.weightx = 0.1;
		this.add(new JLabel("CARTAS"), c);
		c.gridy++;
		
		for (Player player : players) {
			c.gridx = 0;
			c.weightx = 0.8;
			this.add(new JLabel(player.getName()), c);
			c.gridx = 1;
			c.weightx = 0.1;
			this.add(new JLabel(player.getMoney() + ""), c);
			c.gridx = 2;
			c.weightx = 0.1;
			this.add(new JLabel(player.getHand().size() + ""), c);
			c.gridy++;
		}
		
		if(players.size() <= 10) {
			c.weighty = 1 - (players.size() * 0.1);
			this.add(new JPanel(), c);
		} else {
			//this.setLayout(new GridLayout(12, 3));
			JScrollPane scroll = new JScrollPane();
			//scroll.setVisible(true);
			this.add(scroll);
		}
	}
	
	public static void main(String[] args) {
		JFrame game = new JFrame();
		game.setSize(300, 400);
		game.setVisible(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LinkedList<Player> p = new LinkedList<Player>();
		Player p1;
		
		Deck deck = new Deck();
		deck.createDeck(4);
		
		p1 = new Player("Matheus");
		p1.income();
		p1.draw(deck);
		p.add(p1);
		
		p1 = new Player("Rodrigo");
		p1.foreign();
		p.add(p1);
		
		p1 = new Player("Ola");
		p1.foreign();
		p.add(p1);
		
		p1 = new Player("teste");
		p1.income();
		p1.draw(deck);
		p.add(p1);
		
		p1 = new Player("hey");
		p1.foreign();
		p.add(p1);
		
		p1 = new Player("soul");
		p1.foreign();
		p.add(p1);
		
		p1 = new Player("Matheus");
		p1.income();
		p1.draw(deck);
		p.add(p1);
		
		p1 = new Player("abc");
		p1.foreign();
		p.add(p1);
		
		p1 = new Player("aeeee");
		p1.foreign();
		p.add(p1);
		
		p1 = new Player("Matheus");
		p1.income();
		p1.draw(deck);
		p.add(p1);
		
		p1 = new Player("Rodrigo");
		p1.foreign();
		p.add(p1);
		
		p1 = new Player("Ola");
		p1.foreign();
		p.add(p1);
		
		PlayerGUI list = new PlayerGUI(p);
		list.showPlayers();
		game.add(list);
	}

}
