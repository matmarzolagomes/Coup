package ssc0103.coup.gui;

import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ssc0103.coup.exception.GUIException;
import ssc0103.coup.game.Deck;

@SuppressWarnings("serial")
public class HandGUI extends JPanel{
	private Deck hand;
	
	public HandGUI() {
		super();
		setLayout(new GridBagLayout());
	}
	
	public void showCards(Deck hand) throws GUIException {	
		
		for (String string : hand) {
			this.add(new CardGUI(string, 300, 500));
		}
		
	}

	public static void main(String[] args) throws GUIException {
		JFrame game = new JFrame();
		game.setSize(650, 550);
		game.setVisible(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Deck deck = new Deck();
		deck.add("Assassino");
		deck.add("Duque");
		
		HandGUI hand = new HandGUI();
		hand.showCards(deck);
		
		game.add(hand);		
		game.pack();

	}

}
