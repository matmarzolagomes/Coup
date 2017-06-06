package scc0103.coup.lan;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Player {
	private String host;
	private int port;

	public Player(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void execute() {
		Socket player = null;
		ObjectInputStream input;
		ObjectOutputStream output;

		try {
			player = new Socket(this.host, this.port);
			System.out.println("Player Conectado.");
			System.out.println("O player se juntou a mesa!");

			while (true) {

				input = new ObjectInputStream(player.getInputStream());

				Actions actions = (Actions) input.readObject();
				output = new ObjectOutputStream(player.getOutputStream());

				if (actions.getId() == Actions.GET_NAME) {
					String name = JOptionPane.showInputDialog("Informe o seu nick no jogo.:");
					actions.setFrom(name);
					output.writeObject(actions);
					output.flush();					
				}

				else if (actions.getId() == Actions.ASSASSINATE) {
					int op = JOptionPane.showConfirmDialog(null,
							"Vc está sendo Assassinado pelo Player " + actions.getFrom() + ". Informe a sua ação",
							"Assassinate", JOptionPane.YES_NO_CANCEL_OPTION);
					switch (op) {
					/* Permitiu. */
					case 0:
						actions = new Actions();
						actions.setId(Actions.ASSASSINATE_RESPOND);
						actions.setAllow(true);
						output.writeObject(actions);
						output.flush();
						//output.reset();
						break;

					/* Contestou. */
					case 1:
						actions = new Actions();
						actions.setId(Actions.ASSASSINATE_RESPOND);
						actions.setContest(true);
						output.writeObject(actions);
						output.flush();
						//output.reset();
						break;

					/* Bloqueou. */
					case 2:
						actions = new Actions();
						actions.setId(Actions.ASSASSINATE_RESPOND);
						actions.setBlock(true);
						output.writeObject(actions);
						output.flush();
						//output.reset();
						break;
					default:
						break;
					}
				}

				else if (actions.getId() == Actions.COUP) {

				}

				else if (actions.getId() == Actions.FOREIGN) {

				}

				else if (actions.getId() == Actions.INCOME) {

				}

				else if (actions.getId() == Actions.LOAD_INTERFACE) {

				}

				else if (actions.getId() == Actions.LOAD_PLAYER_ACTIONS) {

				}

				else if (actions.getId() == Actions.STEAL) {

				}

				else if (actions.getId() == Actions.SWAP) {

				}

				else if (actions.getId() == Actions.TAXES) {

				}

				else if (actions.getId() == Actions.UPDATE_ALL_INTERFACE
						|| actions.getId() == Actions.UPDATE_INTERFACE) {

				}

			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {

		}

	}

	public static void main(String[] args) {
		String host = JOptionPane.showInputDialog("Informe o ip de conexao. Exemplo: 127.0.0.1");
		int port = Integer.parseInt(JOptionPane.showInputDialog("Informe a porta que deseja se conectar."));
		new Player(host, port).execute();
	}

}
