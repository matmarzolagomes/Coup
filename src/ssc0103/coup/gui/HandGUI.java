package ssc0103.coup.gui;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ssc0103.coup.game.Deck;

@SuppressWarnings("serial")
public class HandGUI extends JPanel{
	Deck hand;
	
	public HandGUI() {
		super();
	}
	
	public void showCards(Deck hand) {
		ImageIcon icon;
		this.setLayout(new GridLayout(1,hand.size()));
		
		for (String string : hand) {
			icon = new ImageIcon("images/" + string + ".jpeg");
			this.add(new JLabel(icon));
		}
		
	}

	public static void main(String[] args) {
		JFrame game = new JFrame();
		game.setSize(1000, 600);
		game.setVisible(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Deck deck = new Deck();
		deck.add("Assassino");
		deck.add("Duque");
		
		HandGUI hand = new HandGUI();
		hand.showCards(deck);
		
		game.add(hand);		

	}

}
