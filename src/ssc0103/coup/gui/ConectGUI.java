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

import ssc0103.coup.lan.Actions;

@SuppressWarnings("serial")
public class ConectGUI extends JPanel {
	private Socket player;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Actions actions;
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

					try {
						while (true) {
							actions = getObject();

							switch (actions.getId()) {
							case Actions.GET_NAME:
								actions.setFrom(name.getText());
								flushObject();
								break;

							case Actions.LOAD_INTERFACE:
								JOptionPane.showMessageDialog(null, "Interface Gráfica Carregada.");
								System.out.println("Chosen.");

								name.disable();
								bt.setEnabled(false);
								bt.setText("Waiting for game to start");
								return;

							case Actions.SERVER_MESSAGE:
								JOptionPane.showMessageDialog(null, name.getText() + ": " + actions.getMessage(),
										"Mensagem", JOptionPane.WARNING_MESSAGE);
								break;
							}
						}
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}

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
		frame.setExtendedState(JFrame.NORMAL);
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
			this.player = new Socket(host, port);

			/* Fluxo de dados do jogador para o servidor. */
			this.input = new ObjectInputStream(this.player.getInputStream());

			/* Fluxo de dados do servidor para o jogador. */
			this.output = new ObjectOutputStream(this.player.getOutputStream());

			return true;
		} catch (IOException | IllegalArgumentException e) {
			String msg = "Não foi possível realizar a conexão no host e porta informados.\nTente outra conexão.";
			JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	/**
	 * Envia o objeto Actions para o servidor.
	 * 
	 * @throws IOException
	 */
	private void flushObject() throws IOException {
		/* Escreve o objeto no fluxo de dados. */
		output.writeObject(actions);
		/* Envia o objeto para o servidor. */
		output.flush();
		/* Limpa o fluxo de dados. */
		output.reset();
	}

	/**
	 * Retorna o objeto Actions do servidor.
	 * 
	 * @param player
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Actions getObject() throws IOException, ClassNotFoundException {
		/* Retorna o objeto enviado pelo servidor. */
		return (Actions) input.readObject();
	}
}
