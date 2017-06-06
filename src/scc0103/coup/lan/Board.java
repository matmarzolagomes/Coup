package scc0103.coup.lan;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Board {
	private int port;
	private List<Socket> players;

	public Board(int port) {
		this.port = port;
		this.players = new ArrayList<Socket>();
	}

	public void execute(int numPlayers) {
		try {
			ServerSocket board = new ServerSocket(this.port);
			System.out.println("Mesa Ativada.");
			System.out.println("Porta " + this.port + " aberta!");

			for (int i = 0; i < numPlayers; ++i) {
				/* Aceita uma conexao com um player. */
				Socket player = board.accept();

				System.out.println("Nova conexao com o player " + player.getInetAddress().getHostAddress());

				/* Adiciona player a lista a lista de players. */
				this.players.add(player);

				/* Cria Tratador de Cliente em uma nova thread. */
				// TrataCliente tc = new TrataCliente(player.getInputStream(),
				// this);
				// new Thread(tc).start();
			}

			Actions actions;

			ObjectInputStream input;
			ObjectOutputStream output;

			/* Solicita a todos os jogadores o nome. */
			for (Socket player : players) {
				/* Objeto que o cliente enviou para o servidor. */
				//input = new ObjectInputStream(player.getInputStream());

				/* Objeto que o servidor vai enviar para o cliente. */
				output = new ObjectOutputStream(player.getOutputStream());
				
				actions = new Actions();
				actions.setId(Actions.GET_NAME);
				output.writeObject(actions);
				output.flush();
				// output.reset();
				// output.close();

				// new PrintStream(player.getOutputStream()).println("Informe o
				// seu nome:");
			}

			/* Recebe o nome de todos os jogadores. */
			for (Socket player : players) {
				/* Objeto que o cliente enviou para o servidor. */
				input = new ObjectInputStream(player.getInputStream());

				/* Objeto que o servidor vai enviar para o cliente. */
				//output = new ObjectOutputStream(player.getOutputStream());

				Object object;

				while (!((object = input.readObject()) instanceof Actions));

				actions = (Actions) object;

				if (actions.getId() == Actions.GET_NAME) {
					JOptionPane.showMessageDialog(null, "O nome do jogador é: " + actions.getFrom());
				}
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		/* Inicia o Servidor. */
		int port = Integer.parseInt(JOptionPane.showInputDialog("Informe a porta de conexao."));
		int numPlayers = Integer.parseInt(JOptionPane.showInputDialog("Informe o número de jogadores."));
		new Board(port).execute(numPlayers);
	}
}
