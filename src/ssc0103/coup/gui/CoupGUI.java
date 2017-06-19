package ssc0103.coup.gui;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ssc0103.coup.exception.GUIException;
import ssc0103.coup.game.Deck;
import ssc0103.coup.game.Player;
import ssc0103.coup.lan.Actions;
/**
 * JPanel maior que contém todos os outros panels
 * @author Matheus Marzola Gomes
 * @author Rodrigo Geurgas
 *
 */
@SuppressWarnings("serial")
public class CoupGUI extends JPanel {
    private DeadGUI deadg;
    private ImageIcon logo;
    private final CoinGUI coing;
    private final HandGUI handg;
    private final LogGUI logg;
    private final PlayerGUI playerg;
    private final String me;
    private Image backgroundImage = null;
    /**
     * Construtor da classe
     * @param me Nome do jogador
     * @param players HashMap dos Jogadores
     */
    public CoupGUI(String me, HashMap<String, Player> players) {
        super(new GridBagLayout());
        this.me = me;
        
        GridBagConstraints cons = new GridBagConstraints();
        
        logo = new ImageIcon("images/coup_logo.png");
        logo.setImage(logo.getImage().getScaledInstance(300, 70, 100));
        
        try {
			backgroundImage = ImageIO.read(new File("images/background.jpg")).getScaledInstance((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(), 100);
		} catch (HeadlessException | IOException e) {
			System.out.println("Failed to load background.");
			System.exit(-1);
		}
        
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.FIRST_LINE_START;
        cons.gridheight = 1;
        cons.gridwidth = 1;

        cons.gridx = 0;
        cons.gridy = 0;
        cons.weightx = 0.2;
        cons.weighty = 1;
        
        JPanel pleft = new JPanel(new GridBagLayout());
        pleft.setOpaque(false);
        add(pleft, cons);
        
        cons.gridx = 0;
        cons.gridy = 0;
        cons.weightx = 0.2;
        cons.weighty = 0.8;
        
        try {
            deadg = new DeadGUI(players.size());
            deadg.setOpaque(false);
            pleft.add(deadg, cons);
        } catch (GUIException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        
        cons.gridx = 1;
        cons.gridy = 0;
        cons.weightx = 0.7;
        cons.weighty = 1;
        
        JPanel pcenter = new JPanel(new GridBagLayout());
        pcenter.setOpaque(false);
        add(pcenter, cons);
        
        cons.gridx = 0;
        cons.weightx = 1;
        cons.weighty = 0.2;
        
        logg = new LogGUI();
        logg.setOpaque(false);
        pcenter.add(logg, cons);
        
        cons.gridy = 1;
        cons.weighty = 0.8;
        
        handg = new HandGUI();
        handg.setOpaque(false);
        pcenter.add(handg, cons);
        
        cons.gridx = 2;
        cons.gridy = 0;
        cons.weightx = 0.1;
        cons.weighty = 1;
        
        JPanel pright = new JPanel(new GridBagLayout());
        pright.setOpaque(false);
        add(pright, cons);
        
        cons.gridx = 0;
        cons.gridy = 0;
        cons.weightx = 1;
        cons.weighty = 0.8;
        
        playerg = new PlayerGUI(players);
        playerg.setOpaque(false);
        pright.add(playerg, cons);
        
        cons.gridy = 1;
        cons.weighty = 0.1;
        
        coing = new CoinGUI(players.get(me));
        coing.setOpaque(false);
        pright.add(coing, cons);
        
        cons.gridy = 2;
        cons.weighty = 0.1;
        
        cons.ipady = 0;
        
        pright.add(new JLabel(logo), cons);
    }
    /**
     * Insere background do jogo
     */
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this);
	}
    /**
     * Atualiza todos os panels internos
     * @param data Variável actions verifica o que vai ser atualizado
     */
    public void updateAll(Actions data) {
        try {
            if(data.getPlayers() != null) {
            	updateCoin(data.getPlayers().get(me));
            	updateHand(data.getPlayers().get(me).getHand());
            	updatePlayer(data.getPlayers());
            }
            if(data.getDead() != null) updateDead(data.getDead());
            if(data.getLog() != null) updateLog(data.getLog());
        } catch (GUIException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
    }
    /**
     * Atualiza o Panel de moedas e tempo
     * @param me Jogador 
     */
    public void updateCoin(Player me) {
        coing.attCoins(me);
    }
    /**
     * Atualiza a mao do jogador
     * @param hand Mão do jogador
     * @throws GUIException Exceção
     */
    public void updateHand(Deck hand) throws GUIException {
        handg.showCards(hand);
    }
    /**
     * Atualiza o Deck de cartas "mortas"
     * @param dead Deck de cartas "mortas"
     * @throws GUIException
     */
    public void updateDead(Deck dead) throws GUIException {
        deadg.update(dead);
    }
    /**
     * Atualiza o Log com a string inserida
     * @param log String inserida
     */
    public void updateLog(String log) {
        logg.insertLog(log);
    }
    /**
     * Atualiza a tabela de jogadores
     * @param players HashMap com os jogadores
     */
    public void updatePlayer(HashMap<String,Player> players) {
        playerg.attTable(players);
    }
    /**
     * Começa a contagem do panel de tempo
     */
    public void startCount() {
    	coing.start();
    }
    /**
     * Para a contagem do panel de tempo
     */
    public void stopCount() {
    	coing.stop();
    }
    /**
     * Verifica se o tempo do jogador acabou
     * @return
     */
    public boolean isConnected() {
    	return !coing.disconnect;
    }
    /**
     * Verifica se o tempo está correndo no panel
     * @return
     */
    public boolean isTimeRunning() {
    	return coing.start;
    }
    
    public static void main(String[] args) {
    }
}