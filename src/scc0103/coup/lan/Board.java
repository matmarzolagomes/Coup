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
		Actions actions;
		ObjectInputStream input;
		ObjectOutputStream output;

		try {
			ServerSocket board = new ServerSocket(this.port);
			System.out.println("Mesa Ativada.");
			System.out.println("Porta " + this.port + " aberta!");

			/* Espera todos os players se conectarem. */
			for (int i = 0; i < numPlayers; ++i) {
				/* Aceita uma conexao com um player. */
				Socket player = board.accept();

				System.out.println("Nova conexao com o player " + player.getInetAddress().getHostAddress());

				/* Adiciona player a lista a lista de players. */
				this.players.add(player);

				/* Solicita ao jogador um nome. */
				output = new ObjectOutputStream(player.getOutputStream());

				/*
				 * Envia ao player uma solicitação de ação para inserir o nome.
				 */
				actions = new Actions();
				actions.setId(Actions.GET_NAME);
				output.writeObject(actions);
				output.flush();
				output.reset();
			}

			/* Recebe o nome de todos os jogadores. */
			for (Socket player : players) {
				new Thread(() -> {
					try {
						/* Objeto que o cliente enviou para o servidor. */
						ObjectInputStream ois = new ObjectInputStream(player.getInputStream());
						Actions act = (Actions) ois.readObject();

						if (act.getId() == Actions.GET_NAME) {
							JOptionPane.showMessageDialog(null, "O nome do jogador eh: " + act.getFrom());
						}

					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		/* Inicia o Servidor. */
		int port = Integer.parseInt(JOptionPane.showInputDialog("Informe a porta de conexao."));
		int numPlayers = Integer.parseInt(JOptionPane.showInputDialog("Informe o numero de jogadores."));
		new Board(port).execute(numPlayers);
	}
}
