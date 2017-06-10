package ssc0103.coup.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import ssc0103.coup.exception.GUIException;

public class CardPanel extends JPanel {
    private final String card;
    
    public CardPanel(String card, int w, int h) throws GUIException {
        super();
        this.card = card;
        add(new JLabel(new CardGUI(card, w, h), SwingConstants.CENTER));
    }
    
    public String getCard() {
        return card;
    }
}