package ssc0103.coup.gui;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ssc0103.coup.exception.GUIException;
import ssc0103.coup.game.Deck;
/**
 * Classe do Panel com a mão do jogador
 * @author Matheus Marzola Gomes
 *
 */
@SuppressWarnings("serial")
public class HandGUI extends JPanel{
	/**
	 * Construtor da classe
	 */
    public HandGUI() {
        super();
        setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
    }
    /**
     * Mostra as cartas do jogador
     * @param hand Deck do jogador
     * @throws GUIException
     */
    public void showCards(Deck hand) throws GUIException {
        removeAll();
        setAlignmentY(BOTTOM_ALIGNMENT);
        
        for (String string : hand)
            add(new JLabel(new CardGUI(string, 300, 500)));
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