package ssc0103.coup.gui;

import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ssc0103.coup.game.Deck;

public class DeadGUI extends JPanel {
    private String maxcards;
    
    public DeadGUI(Deck dead, int nplayers) {
        super(new GridLayout(6, 2));
        if(nplayers > 6){
            maxcards = Integer.toString((int) Math.ceil(nplayers/2.0));
        } else {
            maxcards = "3";
        }
        update(dead);
    }
    
    private void update(Deck dead) {
        removeAll();

        HashMap<String, JLabel> count = new HashMap<>();
        JLabel aux;
        for(String card : dead) {
            if(count.containsKey(card)) {
                aux = count.get(card);
                int c = Character.getNumericValue(aux.getText().charAt(aux.getText().length()-(maxcards.length()+2)))+1;
                aux.setText("x" + c + "/" + maxcards);
            } else {
                add(new CardGUI(card, 30, 50));
                
                aux = new JLabel("x1" + "/" + maxcards);
                add(aux);
                
                count.put(card, aux);
            }
        }
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        Deck ded = new Deck();
        ded.add("Duque");
        ded.add("Condessa");
        ded.add("Assassino");
        ded.add("Capitao");
        ded.add("Embaixador");
        ded.add("Capitao");
        ded.add("Inquisidor");
        
        DeadGUI dg = new DeadGUI(ded, 3);
        
        ded.add("Duque");
        
        dg.update(ded);
        
        frame.add(dg);
        frame.pack();
    }
}
