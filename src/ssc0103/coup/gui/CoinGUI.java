package ssc0103.coup.gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ssc0103.coup.game.Player;

@SuppressWarnings("serial")
public class CoinGUI extends JPanel{
	ImageIcon image;
	ImageIcon time;
	JLabel numCoins;
	JLabel text;
	Timer tm;
	TimerTask task;
	private int counter, seconds, minutes;
	
	public CoinGUI(Player player) {
		super();
		counter = 121;
		
		setLayout(new GridLayout(1, 2));
		
		image = new ImageIcon("images/coins.png");
		image.setImage(image.getImage().getScaledInstance(50, 50, 100));
		
		numCoins = new JLabel(image);
		numCoins.setFont(new Font("Serif", Font.BOLD, 25));
		numCoins.setText(String.format(" x %02d", player.getMoney()));
		
		time = new ImageIcon("images/ampulheta.png");
		time.setImage(time.getImage().getScaledInstance(50, 50, 100));
		
		text = new JLabel(time);
		text.setFont(new Font("Serif", Font.BOLD, 25));
		
		add(numCoins);
		add(text);

		runCounter();
	}

	public void attCoins(Player player) {
		numCoins.setText(String.format(" x %02d", player.getMoney()));
	}
	
	private void runCounter() {
		tm = new Timer();
		
		task = new TimerTask() {
			@Override
			public void run() {
				counter -= 1;
				seconds = counter % 60;
				minutes = counter / 60;
				
				text.setText(String.format("%02d:%02d", minutes, seconds));
				
				if (counter == 0) {
					counter = 121;
				}
			}
		};
		
		tm.scheduleAtFixedRate(task, 1000, 1000);
	}

	public static void main(String[] args) {
	}
}
