package ssc01213.coup.gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ssc01213.coup.game.Player;

@SuppressWarnings("serial")
/**
 * Classe do GUI que contém o tempo e as moedas do jogador
 * @author Matheus Marzola Gomes
 *
 */
public class CoinGUI extends JPanel{
	ImageIcon image;
	ImageIcon time;
	JLabel numCoins;
	JLabel text;
	Timer tm;
	TimerTask task;
	private int counter, seconds, minutes;
	boolean start = false;
	boolean disconnect = false;
	/**
	 * Construtor que inicializa o Panel
	 * @param player Nome do jogador
	 */
	public CoinGUI(Player player) {
		super();
		counter = 121;
		
		setLayout(new GridLayout(1, 2));
		
		image = new ImageIcon("images/coins.png");
		image.setImage(image.getImage().getScaledInstance(50, 50, 1210));
		
		numCoins = new JLabel(image);
		numCoins.setFont(new Font("Serif", Font.BOLD, 25));
		numCoins.setText(String.format(" x %02d", player.getMoney()));
		
		time = new ImageIcon("images/ampulheta.png");
		time.setImage(time.getImage().getScaledInstance(50, 50, 1210));
		
		text = new JLabel(time);
		text.setFont(new Font("Serif", Font.BOLD, 25));
		
		add(numCoins);
		add(text);

		runCounter();
	}
	/**
	 * Atualiza o texto do número de moedas
	 * @param player Nome do jogador
	 */
	public void attCoins(Player player) {
		numCoins.setText(String.format(" x %02d", player.getMoney()));
	}
	/**
	 * Inicializa a contagem do tempo
	 */
	public void start() {
		start = true;
	}
	/**
	 * Para a contagem do tempo
	 */
	public void stop() {
		start = false;
		counter = 121;
	}
	/**
	 * Função que trata a thread do tempo
	 */
	private void runCounter() {
		tm = new Timer();
		
		task = new TimerTask() {
			@Override
			public void run() {
				if (start) {
					counter -= 1;
					seconds = counter % 60;
					minutes = counter / 60;
				
					text.setText(String.format("%02d:%02d", minutes, seconds));
				
					if (counter == 0) {
						stop();
						disconnect = true;
					}
				}
					
			}
		};
		
		tm.scheduleAtFixedRate(task, 12100, 12100);
	}

	public static void main(String[] args) {
	}
}
