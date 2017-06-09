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
	JLabel numCoins;
	JLabel text;
	JLabel time;
	Timer tm;
	TimerTask task;
	private int counter, seconds, minutes, hours;
	
	public CoinGUI(Player player) {
		super();
		counter = 0;
		
		setLayout(new GridBagLayout());
		image = new ImageIcon("images/coin.png");
		
		numCoins = new JLabel();
		numCoins.setFont(new Font("Serif", Font.BOLD, 40));
		numCoins.setText(String.format("x %02d", player.getMoney()));
		
		text = new JLabel("Time: ");
		text.setFont(new Font("Serif", Font.BOLD, 30));
		
		
		time = new JLabel();
		time.setFont(new Font("Serif", Font.BOLD, 30));
		
		addComponents();		
		runCounter();
	}
	
	private void addComponents() {
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.3;
		c.weighty = 0.7;
		
		add(new JLabel(image), c);
		
		c.gridx = 1;
		c.weightx = 0.7;
		c.anchor = GridBagConstraints.WEST;
		
		add(numCoins, c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 1;
		
		add(text, c);
		
		c.gridx = 1;
		c.weighty = 0.3;
		c.anchor = GridBagConstraints.CENTER;
		
		add(time, c);
		
	}
	
	public void attCoins(Player player) {
		numCoins.setText(String.format("x %02d", player.getMoney()));
	}
	
	private void runCounter() {
		tm = new Timer();
		
		task = new TimerTask() {
			@Override
			public void run() {
				counter += 1;
				seconds = counter % 60;
				minutes = counter / 60;
				hours = minutes / 60;
				
				minutes = minutes % 60;
				
				time.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
			}
		};
		
		tm.scheduleAtFixedRate(task, 1000, 1000);
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		JFrame game = new JFrame();
		game.setSize(270, 200);
		game.setVisible(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Player p = new Player();
		
		CoinGUI coin = new CoinGUI(p);
		
		game.add(coin);
		
		System.out.println("APERTA ENTER PRA INCREMENTAR A MOEDA (só interface top rogerinho)");
		
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
