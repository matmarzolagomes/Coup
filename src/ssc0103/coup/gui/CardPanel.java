package ssc0103.coup.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ssc0103.coup.exception.GUIException;

/**
 * JPanel com uma imagem escalada.
 * @author Rodrigo Geurgas Zavarizz 9791080
 */
@SuppressWarnings("serial")
public class CardPanel extends JPanel {
    private final String card;
    
    /**
     * Construtor da classe.
     * @param card nome da imagem.
     * @param w largura da imagem escalada.
     * @param h altura da imagem ecalada.
     * @throws GUIException
     */
    public CardPanel(String card, int w, int h) throws GUIException {
        super();
        this.card = card;
        add(new JLabel(new CardGUI(card, w, h), SwingConstants.CENTER));
    }
    
    /**
     * Devolve a imagem.
     * @return imagem do JPanel.
     */
    public String getCard() {
        return card;
    }
}