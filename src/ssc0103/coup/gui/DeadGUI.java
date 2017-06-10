package ssc0103.coup.gui;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import ssc0103.coup.exception.GUIException;
import ssc0103.coup.game.Deck;

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
        
        Image img = new ImageIcon("images/Assassino.jpeg").getImage();
        img = scaleImage(img, 60, 100);
        aux = new JLabel("x0" + "/" + maxcards, new ImageIcon(img), SwingConstants.LEFT);
        
        add(aux);
        
        count.put("Assassino", aux);
        
        img = new ImageIcon("images/Capitao.jpeg").getImage();
        img = scaleImage(img, 60, 100);
        aux = new JLabel("x0" + "/" + maxcards, new ImageIcon(img), SwingConstants.LEFT);
        
        add(aux);
        
        count.put("Capitao", aux);
        
        img = new ImageIcon("images/Condessa.jpeg").getImage();
        img = scaleImage(img, 60, 100);
        aux = new JLabel("x0" + "/" + maxcards, new ImageIcon(img), SwingConstants.LEFT);
        
        add(aux);
        
        count.put("Condessa", aux);
        
        img = new ImageIcon("images/Duque.jpeg").getImage();
        img = scaleImage(img, 60, 100);
        aux = new JLabel("x0" + "/" + maxcards, new ImageIcon(img), SwingConstants.LEFT);
        
        add(aux);
        
        count.put("Duque", aux);
        
        img = new ImageIcon("images/Embaixador.jpeg").getImage();
        img = scaleImage(img, 60, 100);
        aux = new JLabel("x0" + "/" + maxcards, new ImageIcon(img), SwingConstants.LEFT);
        
        add(aux);
        
        count.put("Embaixador", aux);
        
        for(String card : dead) {
            aux = count.get(card);
                
            Matcher m = r.matcher(aux.getText());
            if(!m.find()) throw new GUIException("Inv�lid dead.");
                
            int c = Integer.parseInt(m.group(1))+1;
            aux.setText("x" + c + "/" + maxcards);
        }
        
        count.values().forEach((JLabel label) -> {
            label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 32));
        });
    }
    
    private Image scaleImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        
        return resizedImg;
    }
    
    public static void main(String[] args) throws GUIException {
    }
}