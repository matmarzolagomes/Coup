package scc0103.coup.lan;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class Board {
	private static final int LIMITE = 100;
	private int port;
	private int numPlayers;
	private ServerSocket board;
	private Map<String, Socket> players;

	public Board() throws IOException {
		/* Obt�m pota de conex�o e verifica validade. */
		while (true) {
			try {
				String msg = JOptionPane.showInputDialog("Informe a porta de conex�o com o jogo:");
				if (msg == null) System.exit(0);;					

				this.port = Integer.parseInt(msg);
				board = new ServerSocket(this.port);

				System.out.println("Mesa Ativada.");
				System.out.println("Porta " + this.port + " aberta!");
				break;
			} catch (IOException | IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, "Porta de conex�o inv�lida.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}

		/* Obt�m n�mero de jogadores e verifica validade. */
		while (true) {
			try {
				String msg = JOptionPane.showInputDialog(
						"Informe o n�mero de jogadores da partida:\nM�nimo 2. \nM�ximo " + LIMITE + ".");
				
				if (msg == null) {
					board.close();
					System.exit(0);;
				}

				this.numPlayers = Integer.parseInt(msg);
				if (this.numPlayers < 2 || this.numPlayers > LIMITE)
					throw new NumberFormatException();
				break;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "O n�mero de jogares informado � inv�lido.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}
				
		this.players = new HashMap<String, Socket>(this.numPlayers);
	}

	public void execute() throws IOException {
		Actions actions;
		ObjectInputStream input;
		ObjectOutputStream output;

		try {
			/* Aguarda at� que todos os players tenham se conectado. */
			for (int i = 0; i < this.numPlayers; ++i) {

				/* Aceita uma conex�o com um player. */
				Socket player = board.accept();
				System.out.println("Player: " + player.getInetAddress().getHostAddress() + " conectado.");

				/* Solicita ao jogador um nickname. */
				output = new ObjectOutputStream(player.getOutputStream());
				actions = new Actions();
				actions.setId(Actions.GET_NAME);
				output.writeObject(actions);
				output.flush();

				/* Recebe o nome do jogador e o insere em um HashMap. */
				new Thread(() -> {
					try {
						/* Objeto que o cliente enviou para o servidor. */
						ObjectInputStream ois = new ObjectInputStream(player.getInputStream());
						Actions act = (Actions) ois.readObject();

						if (act.getId() == Actions.GET_NAME) {
							/*
							 * Adiciona player a lista de conex�es de players.
							 */
							players.put(act.getFrom(), player);
							System.out.println("Jogador " + act.getFrom() + " se juntou a mesa.");
						}
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}).start();
			}
							
			/* Aguarda at� que todas as Threads tenham sido finalizadas. */
			while(Thread.activeCount() > 1);
			
			// for (Socket socket : players.values()) {
			// actions = new Actions();
			// actions.setId(Actions.ASSASSINATE);
			// actions.setFrom(socket.getInetAddress().getHostAddress());
			//
			// output = new ObjectOutputStream(socket.getOutputStream());
			// output.writeObject(actions);
			// output.flush();
			//
			// input = new ObjectInputStream(socket.getInputStream());
			// actions = (Actions) input.readObject();
			//
			// if (actions.getId() == Actions.ASSASSINATE_RESPOND) {
			// if (actions.isAllow()) {
			// JOptionPane.showMessageDialog(null, "O jogador permitiu o
			// Assassinato.");
			//
			// } else if (actions.isBlock()) {
			// JOptionPane.showMessageDialog(null, "O jogador tentou bloquear o
			// Assassinato.");
			//
			// } else if (actions.isContest()) {
			// JOptionPane.showMessageDialog(null, "O jogador contestou a a��o
			// de Assassinato.");
			// }
			// }
			// }

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			board.close();
		}
	}

	public static void main(String[] args) throws IOException {
		try {
			/* Inicia o Servidor. */
			new Board().execute();
		} catch (Exception e) {
			System.exit(0);
		}
	}
}
