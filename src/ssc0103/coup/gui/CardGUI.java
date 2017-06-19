package ssc0103.coup.gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import ssc0103.coup.exception.GUIException;

/**
 * ImageIcon escalado para a resolução escolhida.
 * @author Rodrigo Geurgas Zavarizz 9791080
 */
@SuppressWarnings("serial")
public class CardGUI extends ImageIcon {
	/**
	 * Construtor da classe.
	 * @param card nome da imagem a ser carregada.
	 * @param w largura da imagem escalada.
	 * @param h altura da imagem escalada.
	 * @throws GUIException
	 */
    public CardGUI(String card, int w, int h) throws GUIException {
        super("images/" + card + ".png");
        
        if(getImageLoadStatus() != MediaTracker.COMPLETE) throw new GUIException("Failed to load image.");
        this.setImage(scaleImage(getImage(), w, h));
    }
    
    /**
     * Escala uma imagem para a resolução escolhida.
     * @param srcImg imagem a ser escalada.
     * @param w largura da imagem escalada.
     * @param h altura da imagem escalada.
     * @return imagem escalada.
     */
    private Image scaleImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        
        return resizedImg;
    }
    
    /**
     * Função para debug.
     * @param args
     * @throws GUIException
     */
    public static void main(String[] args) throws GUIException {
    }
}
