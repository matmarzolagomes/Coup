package ssc0103.coup.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ssc0103.coup.lan.Player;

/**
 * Classe criada para a criação do JPanel de conexão.
 * @author Rodrigo Geurgas Zavarizz 9791080
 */
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
	private Image backgroundImage = null;

	/**
	 * Construtor da classe.
	 */
	public ConectGUI() {
		super(new GridBagLayout());
		
		try {
			backgroundImage = ImageIO.read(new File("images/background.png")).getScaledInstance((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 50, 100);
		} catch (HeadlessException | IOException e) {
			System.out.println("Failed to load background.");
			System.exit(-1);
		}
		
		GridBagConstraints cons = new GridBagConstraints();

		cons.anchor = GridBagConstraints.FIRST_LINE_START;
		cons.fill = GridBagConstraints.BOTH;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridheight = 1;
		cons.gridwidth = 3;
		cons.weightx = 1;
		cons.weighty = 0.45;

		JPanel aux = new JPanel();
		aux.setOpaque(false);
		
		add(aux, cons);

		cons.gridy = 1;
		cons.gridwidth = 1;
		cons.weightx = 0.45;
		cons.weighty = 0.1;

		aux = new JPanel();
		aux.setOpaque(false);
		
		add(aux, cons);

		cons.gridx = 1;
		cons.weightx = 0.1;

		JPanel center = new JPanel(new GridLayout(4, 1));
		center.setOpaque(false);
		center.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(center, cons);

		cons.gridx = 2;
		cons.weightx = 0.45;

		aux = new JPanel();
		aux.setOpaque(false);
		
		add(aux, cons);

		cons.gridx = 0;
		cons.gridwidth = 3;
		cons.gridy = 2;
		cons.weighty = 0.45;

		aux = new JPanel();
		aux.setOpaque(false);
		
		add(aux, cons);

		ip = new JTextFieldWithLimit("IP Address: ", 16);
		port = new JTextFieldWithLimit("Host Port: ", 5);
		name = new JTextFieldWithLimit("Nickname: ", 16);
		
		JPanel btp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btp.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		
		bt = new JButton("Connect");
		
		Random random = new Random(System.currentTimeMillis());
		int n = Math.abs(random.nextInt()) % 100;
		
		ip.setText("127.0.0.1");
		port.setText("12345");
		name.setText("Player" + n);		
		
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
	
	/**
     * Insere background do jogo
     */
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this);
	}
	
	/**
	 * Adiciona o JPanel a um JFrame.
	 * @param frame JFrame a ser adicionado.
	 */
	public void frameAdd(JFrame frame) {	
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);	
		frame.add(this);
		frame.pack();		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);		
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		getRootPane().setDefaultButton(bt);
	}

	/**
	 * Retorna o IP digitado.
	 * @return IP digitado no JTextField.
	 */
	public String getIpAdress() {
		return ipAdress;
	}

	/**
	 * Retorna a Porta digitada.
	 * @return Porta digitada no JTextField.
	 */
	public String getPorta() {
		return porta;
	}

	/**
	 * Retorna o nome digitado.
	 * @return nome digitado no JTextField.
	 */
	public String getPlayerName() {
		return playerName;
	}	

	/**
	 * Atribui um boolean à variável conected.
	 * @param conected valor a ser atribuido.
	 */
	public void setConected(boolean conected) {
		this.conected = conected;
	}	
	
	/**
	 * Ativa o primeiro botão.
	 */
	public void activeButton1() {
		ip.enable();
		port.enable();
		bt.setText("Connect");
		bt.setEnabled(true);
		Player.setReadConnectGUI(false);
	}
	
	/**
	 * Ativa o segundo botão.
	 */
	public void activeButton2() {
		bt.setText("Choose name");
		bt.setEnabled(true);
		name.enable();
		Player.setReadConnectGUI(false);
	}
}
