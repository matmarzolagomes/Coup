package ssc0103.coup.lan;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.DelayQueue;

import javax.swing.JOptionPane;

import ssc0103.coup.game.Coup;

public class Board {
	private static final int LIMITE = 100;
	private int port;
	private int numPlayers;
	private ServerSocket board;
	private Map<String, Socket> players;

	public Board() throws IOException {
		/* Obtém pota de conexão e verifica validade. */
		while (true) {
			try {
				String msg = JOptionPane.showInputDialog("Informe a porta de conexão com o jogo:");
				if (msg == null)
					System.exit(0);				

				this.port = Integer.parseInt(msg);
				board = new ServerSocket(this.port);

				System.out.println("Mesa Ativada.");
				System.out.println("Porta " + this.port + " aberta!");
				break;
			} catch (IOException | IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, "Porta de conexão inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}

		/* Obtém número de jogadores e verifica validade. */
		while (true) {
			try {
				String msg = JOptionPane.showInputDialog("Informe o número de jogadores da partida:\nMínimo 2. \nMáximo " + LIMITE + ".");

				if (msg == null) {
					board.close();
					System.exit(0);					
				}

				this.numPlayers = Integer.parseInt(msg);
				if (this.numPlayers < 2 || this.numPlayers > LIMITE)
					throw new NumberFormatException();
				break;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "O número de jogares informado é inválido.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		this.players = new HashMap<String, Socket>(this.numPlayers);
	}

	public void execute() throws IOException {
		Actions actions;
		ObjectInputStream input;
		ObjectOutputStream output;		
		String playerName;

		try {
			/* Aguarda até que todos os players tenham se conectado. */
			for (int i = 0; i < this.numPlayers; ++i) {

				/* Aceita uma conexão com um player. */
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
						while (true) {
							/* Objeto que o cliente enviou para o servidor. */
							ObjectInputStream ois = new ObjectInputStream(player.getInputStream());

							Actions act = (Actions) ois.readObject();
							String msg;

							if (act.getId() == Actions.GET_NAME) {
								/* Verifica se o nome é válido. */
								if (act.getFrom() == null || act.getFrom().isEmpty()) {
									msg = "Nomes nulos não são permitidos.";
								} else if (players.containsKey(act.getFrom())) {
									msg = "Já existe um jogador com este nome.";
								} else if (act.getFrom().length() > 16) {
									msg = "O nome do jogador é muito extenso." + "\nTamanho Máximo = 16 caracteres.";
								} else {
									msg = "Aguadando demais jogadores.";
									/* Insere na lista de conexões de players. */
									players.put(act.getFrom(), player);
									System.out.println("Jogador " + act.getFrom() + " se juntou a mesa.");

									/*Envia mensagem de aguardando demais jogadores. */
									ObjectOutputStream oos = new ObjectOutputStream(player.getOutputStream());
									act = new Actions();
									act.setId(Actions.SERVER_MESSAGE);
									act.setMessage(msg);
									oos.writeObject(act);
									oos.flush();
									break;
								}

								/* Envia uma notificação ao jogador. */
								ObjectOutputStream oos = new ObjectOutputStream(player.getOutputStream());
								act = new Actions();
								act.setId(Actions.SERVER_MESSAGE);
								act.setMessage(msg);
								oos.writeObject(act);
								oos.flush();

								/* Solicita novamente ao player um nickanme. */
								oos = new ObjectOutputStream(player.getOutputStream());
								act.setId(Actions.GET_NAME);
								oos.writeObject(act);
								oos.flush();
							}
						}
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}).start();
			}

			/* Aguarda até que todas as Threads tenham sido finalizadas. */
			while (Thread.activeCount() > 1);
			
			/* Inicializa a mecânica do jogo.*/
			//Coup coup = new Coup(this.numPlayers, players.keySet());
						
			/* Roda o Jogo até que reste apenas 1 player. */
			for (Iterator<String> iterator = players.keySet().iterator(); players.size() > 1; 
					iterator = !iterator.hasNext() ? players.keySet().iterator() : iterator) {
				
				/* Obtém a conexão do player do turno. */
				playerName = (String) iterator.next();
				Socket player = players.get(playerName);				
				System.out.println("Turno do Jogador " + playerName + ".");
				
				/*
				 * PROGRAMAR AÇÕES ENTRE O SERVIDOR E O CLIENTE AQUI. 
				 */

				// Método para remover.
				// iterator.remove();
			}

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
			// JOptionPane.showMessageDialog(null, "O jogador contestou a ação
			// de Assassinato.");
			// }
			// }
			// }

			/* Mantem o Servidor Rodando. */
			while (true);

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
