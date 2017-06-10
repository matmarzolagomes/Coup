package ssc0103.coup.gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ssc0103.coup.exception.GUIException;

@SuppressWarnings("serial")
public class CardGUI extends JPanel {
    private String card;
    
    public CardGUI(String card, int w, int h) throws GUIException {
        super();
        this.card = card;
        ImageIcon icon = new ImageIcon("images/" + card + ".jpeg");
        if(icon.getImageLoadStatus() != MediaTracker.COMPLETE) throw new GUIException("Failed to load image.");
        icon = new ImageIcon(scaleImage(icon.getImage(), w, h));
        
        add(new JLabel(icon));
    }
    
    private Image scaleImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        
        return resizedImg;
    }
    
    public String getCard() {
        return card;
    }
    
    public static void main(String[] args) throws GUIException {
        CardGUI c = new CardGUI("asodi", 100, 100);
    }
}
