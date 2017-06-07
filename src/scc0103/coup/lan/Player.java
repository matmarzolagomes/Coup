package scc0103.coup.lan;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Player {
	private String host;
	private int port;
	private Socket player;

	public Player() {
		while (true) {
			try {
				this.host = JOptionPane
						.showInputDialog("Informe o IP de conexão com o servidor do jogo: \nDefault: 127.0.0.1");

				if (host == null) {
					System.exit(0);
				} else if (host.isEmpty()) {
					host = "127.0.0.1";
				}

				String msg = JOptionPane.showInputDialog("Informe a porta de conexão com o servidor do jogo:");

				if (msg == null)
					System.exit(0);

				this.port = Integer.parseInt(msg);

				player = new Socket(this.host, this.port);
				System.out.println("Player Conectado.");
				System.out.println("O player se juntou a mesa!");
				break;
			} catch (IOException | IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null,
						"Não foi possível realizar a conexão no host e portas informados.\nTente outra conexão.",
						"Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void execute() {
		ObjectInputStream input;
		ObjectOutputStream output;

		try {
			/* Recebe um objeto do servidor. */
			input = new ObjectInputStream(player.getInputStream());

			Actions actions = (Actions) input.readObject();
			
			/* Objeto que será enviado ao servidor. */
			output = new ObjectOutputStream(player.getOutputStream());

			if (actions.getId() == Actions.GET_NAME) {
				String name = JOptionPane.showInputDialog("Informe o seu nickname no jogo:");
				actions.setFrom(name);
				output.writeObject(actions);
				output.flush();
			}
			
			else if(actions.getId() == Actions.SERVER_MESSAGE) {				
				JOptionPane.showMessageDialog(null, actions.getMessage(), "Mensagem", JOptionPane.WARNING_MESSAGE);
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
					// output.reset();
					break;

				/* Contestou. */
				case 1:
					actions = new Actions();
					actions.setId(Actions.ASSASSINATE_RESPOND);
					actions.setContest(true);
					output.writeObject(actions);
					output.flush();
					// output.reset();
					break;

				/* Bloqueou. */
				case 2:
					actions = new Actions();
					actions.setId(Actions.ASSASSINATE_RESPOND);
					actions.setBlock(true);
					output.writeObject(actions);
					output.flush();
					// output.reset();
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

			else if (actions.getId() == Actions.UPDATE_ALL_INTERFACE || actions.getId() == Actions.UPDATE_INTERFACE) {

			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {

		}

	}

	public static void main(String[] args) {
		new Player().execute();
	}

}
