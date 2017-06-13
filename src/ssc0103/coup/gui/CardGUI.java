package ssc0103.coup.gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import ssc0103.coup.exception.GUIException;

@SuppressWarnings("serial")
public class CardGUI extends ImageIcon {
    public CardGUI(String card, int w, int h) throws GUIException {
        super("images/" + card + ".png");
        
        if(getImageLoadStatus() != MediaTracker.COMPLETE) throw new GUIException("Failed to load image.");
        this.setImage(scaleImage(getImage(), w, h));
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
