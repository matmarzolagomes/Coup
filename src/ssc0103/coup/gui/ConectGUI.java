package ssc0103.coup.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ConectGUI extends JPanel {
	private boolean conected = false;

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

		JTextFieldWithLimit ip = new JTextFieldWithLimit("IP", 15);
		JTextFieldWithLimit port = new JTextFieldWithLimit("Port", 6);
		JTextFieldWithLimit name = new JTextFieldWithLimit("Name", 15);

		name.disable();

		JPanel btp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton bt = new JButton("Connect");

		bt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!conected) {
					
					connectHost(ip.getText(), port.getText());
					
					System.out.println("Conected.");

					ip.disable();
					port.disable();
					name.enable();

					bt.setText("Choose name");
					conected = true;
				} else {
					// Implement set name
					System.out.println("Chosen.");

					name.disable();
					bt.setEnabled(false);
					bt.setText("Waiting for game to start");
				}
			}
		});

		btp.add(bt);
		center.add(ip);
		center.add(port);
		center.add(name);
		center.add(btp);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);

		ConectGUI c = new ConectGUI();

		frame.add(c);
		frame.pack();
	}

	/**
	 * Realiza uma conexão com o servidor do jogo.
	 */
	private boolean connectHost(String host, String porta) {
		try {

			if (host == null)
				System.exit(0);
			else if (host.isEmpty())
				host = "127.0.0.1";

			int port = Integer.parseInt(porta);

			/* Obtém o Socket de comunicação entre o servidor e o jogador. */
			Socket player = new Socket(host, port);

			/* Fluxo de dados do jogador para o servidor. */
			ObjectInputStream input = new ObjectInputStream(player.getInputStream());

			/* Fluxo de dados do servidor para o jogador. */
			ObjectOutputStream output = new ObjectOutputStream(player.getOutputStream());

			return true;
		} catch (IOException | IllegalArgumentException e) {
			String msg = "Não foi possível realizar a conexão no host e porta informados.\nTente outra conexão.";
			JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
}
