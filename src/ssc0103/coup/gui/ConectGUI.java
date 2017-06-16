package ssc0103.coup.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ssc0103.coup.lan.Player;

@SuppressWarnings("serial")
public class ConectGUI extends JPanel {
	private boolean conected = false;
	private String ipAdress;	
	private String porta;
	private String playerName;
	private JButton bt;
	private JTextFieldWithLimit ip;
	private JTextFieldWithLimit port;
	private JTextFieldWithLimit name;

	public ConectGUI() {
		super(new GridBagLayout());
								
		GridBagConstraints cons = new GridBagConstraints();

		cons.anchor = GridBagConstraints.FIRST_LINE_START;
		cons.fill = GridBagConstraints.BOTH;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridheight = 1;
		cons.gridwidth = 3;
		cons.weightx = 1;
		cons.weighty = 0.45;

		add(new JPanel(), cons);

		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.weightx = 0.45;
		cons.weighty = 0.1;

		add(new JPanel(), cons);

		cons.gridx = 1;
		cons.weightx = 0.1;

		JPanel center = new JPanel(new GridLayout(4, 1));
		center.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(center, cons);

		cons.gridx = 2;
		cons.weightx = 0.45;

		add(new JPanel(), cons);

		cons.gridx = 0;
		cons.gridwidth = 3;
		cons.gridy = 2;
		cons.weighty = 0.45;

		add(new JPanel(), cons);

		ip = new JTextFieldWithLimit("IP Address: ", 15);
		port = new JTextFieldWithLimit("Host Port: ", 5);
		name = new JTextFieldWithLimit("Nickname: ", 16);
		

		JPanel btp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bt = new JButton("Connect");
		
		ip.disable();
		port.disable();
		name.disable();
		bt.setEnabled(false);
		bt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!conected) {					
					ip.disable();					
					port.disable();
					ipAdress = ip.getText();
					porta = port.getText();					
					bt.setEnabled(false);
					bt.setText("Connecting...");
					Player.setReadConnectGUI(true);
				} else {
					name.disable();
					playerName = name.getText();					
					bt.setEnabled(false);
					bt.setText("Waiting for game to start");
					Player.setReadConnectGUI(true);
				}
			}
		});

		btp.add(bt);
		center.add(ip);
		center.add(port);
		center.add(name);
		center.add(btp);
	}
	
	public void frameAdd(JFrame frame, ConectGUI conectGUI) {		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.NORMAL);	
		frame.add(conectGUI);
		frame.pack();		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);		
	}
	
	public String getIpAdress() {
		return ipAdress;
	}

	public String getPorta() {
		return porta;
	}

	public String getPlayerName() {
		return playerName;
	}	

	public void setConected(boolean conected) {
		this.conected = conected;
	}	
	
	public void activeButton1() {
		ip.enable();
		port.enable();
		bt.setText("Connect");
		bt.setEnabled(true);
		Player.setReadConnectGUI(false);
	}
	
	public void activeButton2() {
		bt.setText("Choose name");
		bt.setEnabled(true);
		name.enable();
		Player.setReadConnectGUI(false);
	}
}
