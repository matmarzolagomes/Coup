package ssc0103.coup.gui;

//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
//import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

//import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import ssc0103.coup.exception.GUIException;

import ssc0103.coup.game.Deck;

@SuppressWarnings("serial")
public class PopUp extends JDialog /*implements ActionListener*/ {
    private LinkedList<String> ret;
    private int removen;
    
    public PopUp(Deck cards, int removen) throws GUIException {
        super();
        this.removen = removen;
        ret = new LinkedList<String>();
        
        toFront();
        setModal(true);
        
        JPanel content = new JPanel(new GridBagLayout());
        
        GridBagConstraints cons = new GridBagConstraints();
        
        cons.anchor = GridBagConstraints.FIRST_LINE_START;
        cons.fill = GridBagConstraints.BOTH;
        cons.gridy = 0;
        cons.weightx = 1/cards.size();
        cons.weighty = 0.7;
        
        CardGUI[] panel = new CardGUI[cards.size()];
        PanelListener listener = new PanelListener();
        
        for(int i = 0; i < panel.length; i++) {
            cons.gridx = i;
            panel[i] = new CardGUI(cards.get(i), 300, 500);
            panel[i].addMouseListener(listener);
            content.add(panel[i], cons);
        }
        
//        cons.gridx = 0;
//        cons.gridy = 1;
//        cons.gridwidth = panel.length;
//        cons.weightx = 1;
//        cons.weighty = 0.3;
//
//        JPanel btp = new JPanel(new BorderLayout());
//        btp.setPreferredSize(new Dimension(0, 100));
//        JButton bt = new JButton("Remove");
//        bt.setFont(new Font("Arial", Font.PLAIN, 40));
//
//        bt.addActionListener(this);
//
//        btp.add(bt, BorderLayout.CENTER);
//        content.add(btp, cons);

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
            if(source instanceof CardGUI){
                CardGUI panelPressed = (CardGUI) source;
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
    
//	@Override
//	public void actionPerformed(ActionEvent arg0) {
//		dispose();
//	}
    
    public LinkedList<String> showPopUp() {
        setVisible(true);
        return ret;
    }
    
    public static void main(String[] args) throws GUIException {
        Deck cards = new Deck();
        
        cards.add("Duque");
        cards.add("Assassino");
        cards.add("Capitao");
        
        PopUp pu = new PopUp(cards, 2);
        System.out.println(pu.showPopUp());
    }
}