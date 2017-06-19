package ssc0103.coup.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ssc0103.coup.exception.GUIException;

import ssc0103.coup.game.Deck;

@SuppressWarnings("serial")
public class PopUp extends JDialog {
    private final LinkedList<String> ret;
    private final int removen;
    
    public PopUp(Deck cards, int removen) throws GUIException {
        super();
        this.removen = removen;
        ret = new LinkedList<>();
        
        toFront();
        setModalityType(DEFAULT_MODALITY_TYPE);
        
        JPanel content = new JPanel(new GridBagLayout());
        
        GridBagConstraints cons = new GridBagConstraints();
        
        cons.anchor = GridBagConstraints.FIRST_LINE_START;
        cons.fill = GridBagConstraints.BOTH;
        cons.gridy = 0;
        cons.weightx = 1/cards.size();
        cons.weighty = 0.7;
        
        CardPanel[] panel = new CardPanel[cards.size()];
        PanelListener listener = new PanelListener();
        
        for(int i = 0; i < panel.length; i++) {
            cons.gridx = i;
            panel[i] = new CardPanel(cards.get(i), 300, 500);
            panel[i].addMouseListener(listener);
            content.add(panel[i]);
        }
        
        cons.gridx = 0;
        cons.gridy = 1;
        cons.weightx = 1;
        cons.weighty = 0.3;
        cons.gridwidth = cards.size();
        
        content.add(new JLabel("Escolha " + removen + " cartas para retirar de sua mão."), cons);

        add(content);
        pack();
        setResizable(false);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getSize().width/2, dim.height/2 - getSize().height/2);
    }
    
    private class PanelListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent event) {
            Object source = event.getSource();
            if(source instanceof CardPanel){
                CardPanel panelPressed = (CardPanel) source;
                if(panelPressed.getBackground() == Color.BLUE) {
                    panelPressed.setBackground(Color.WHITE);
                    ret.remove(panelPressed.getCard());
                } else {
                    panelPressed.setBackground(Color.BLUE);
                    ret.add(panelPressed.getCard());
                }
            }
            
            if(ret.size() == removen)
                dispose();
        }
        
        @Override
        public void mouseEntered(MouseEvent arg0) {}
        @Override
        public void mouseExited(MouseEvent arg0) {}
        @Override
        public void mousePressed(MouseEvent arg0) {}
        @Override
        public void mouseReleased(MouseEvent arg0) {}
    }
    
    public LinkedList<String> showPopUp() {
        setVisible(true);
        return ret;
    }
    
    public static void main(String[] args) throws GUIException {
    }
}
