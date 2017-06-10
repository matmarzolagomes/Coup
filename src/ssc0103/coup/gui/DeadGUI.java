package ssc0103.coup.gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ssc0103.coup.exception.GUIException;
import ssc0103.coup.game.Deck;

@SuppressWarnings("serial")
public class DeadGUI extends JPanel {
    private final String maxcards;
    
    public DeadGUI(int nplayers) throws GUIException {
        super(new GridLayout(5, 1));
        
        if(nplayers > 6){
            maxcards = Integer.toString((int) Math.ceil(nplayers/2.0));
        } else {
            maxcards = "3";
        }
    }
    
    public void update(Deck dead) throws GUIException {
        removeAll();

        HashMap<String, JLabel> count = new HashMap<>();
        JLabel aux;
        Pattern r = Pattern.compile("x([0-9]+)/.*");
        
        aux = new JLabel("x0" + "/" + maxcards, new CardGUI("Assassino", 60, 100), SwingConstants.LEFT);
        add(aux);
        count.put("Assassino", aux);
        
        aux = new JLabel("x0" + "/" + maxcards, new CardGUI("Capitao", 60, 100), SwingConstants.LEFT);
        add(aux);
        count.put("Capitao", aux);
        
        aux = new JLabel("x0" + "/" + maxcards, new CardGUI("Condessa", 60, 100), SwingConstants.LEFT);
        add(aux);
        count.put("Condessa", aux);
        
        aux = new JLabel("x0" + "/" + maxcards, new CardGUI("Duque", 60, 100), SwingConstants.LEFT);
        add(aux);
        count.put("Duque", aux);
        
        aux = new JLabel("x0" + "/" + maxcards, new CardGUI("Embaixador", 60, 100), SwingConstants.LEFT);
        add(aux);
        count.put("Embaixador", aux);
        
        for(String card : dead) {
            aux = count.get(card);
                
            Matcher m = r.matcher(aux.getText());
            if(!m.find()) throw new GUIException("Inválid dead.");
                
            int c = Integer.parseInt(m.group(1))+1;
            aux.setText("x" + c + "/" + maxcards);
        }
        
        count.values().forEach((JLabel label) -> {
            label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 32));
        });

    }
    
    public static void main(String[] args) throws GUIException {
    }
}
