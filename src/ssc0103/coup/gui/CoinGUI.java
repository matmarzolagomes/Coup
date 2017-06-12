package ssc0103.coup.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
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
		
		setLayout(new GridBagLayout());
		
		image = new ImageIcon("images/coins.png");
		image.setImage(image.getImage().getScaledInstance(50, 50, 100));
		
		numCoins = new JLabel(image);
		numCoins.setFont(new Font("Serif", Font.BOLD, 25));
		numCoins.setText(String.format(" x %02d", player.getMoney()));
		
		time = new ImageIcon("images/ampulheta.png");
		time.setImage(time.getImage().getScaledInstance(50, 50, 100));
		
		text = new JLabel(time);
		text.setFont(new Font("Serif", Font.BOLD, 25));
		
		addComponents();		
		runCounter();
	}
	
	private void addComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.7;
		add(numCoins, c);

		c.gridy = 1;
		c.weighty = 0.3;
		add(text, c);
		
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
			}
		};
		
		tm.scheduleAtFixedRate(task, 1000, 1000);
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		JFrame game = new JFrame();
		game.setSize(290, 200);
		game.setVisible(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Player p = new Player();
		
		CoinGUI coin = new CoinGUI(p);
		
		game.add(coin);
		
		System.out.println("APERTA ENTER PRA INCREMENTAR A MOEDA (s� interface top rogerinho)");
		
		while (true)
			if (s.hasNextLine()) {
				String ae = s.nextLine();
				if (ae == "stop") break;
				p.income();
				coin.attCoins(p);
				s.reset();
			}
			
		s.close();
		
	}

}
