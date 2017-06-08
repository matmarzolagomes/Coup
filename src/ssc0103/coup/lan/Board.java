package ssc0103.coup.lan;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import ssc0103.coup.exception.PException;

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
				if (msg == null)
					System.exit(0);

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
					System.exit(0);
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

	public void execute() throws IOException, ClassNotFoundException, PException {
		Actions actions;
		ObjectInputStream input;
		ObjectOutputStream output;
		String playerName;
		List<String> log = new ArrayList<>();		

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
						while (true) {
							/* Objeto que o cliente enviou para o servidor. */
							ObjectInputStream ois = new ObjectInputStream(player.getInputStream());
							/* Objeto que ser� enviado pelo servidor. */
							ObjectOutputStream oos = new ObjectOutputStream(player.getOutputStream());

							Actions act = (Actions) ois.readObject();
							String msg;

							if (act.getId() == Actions.GET_NAME) {
								/* Verifica se o nome � v�lido. */
								if (act.getFrom() == null || act.getFrom().isEmpty()) {
									msg = "Nomes nulos n�o s�o permitidos.";
								} else if (players.containsKey(act.getFrom())) {
									msg = "J� existe um jogador com este nome.";
								} else if (act.getFrom().length() > 16) {
									msg = "O nome do jogador � muito extenso." + "\nTamanho M�ximo = 16 caracteres.";
								} else {
									msg = "Aguadando demais jogadores.";
									/* Insere na lista de conex�es de players. */
									players.put(act.getFrom(), player);
									System.out.println("Jogador " + act.getFrom() + " se juntou a mesa.");

									/* Envia mensagem de aguardando demais jogadores. */									
									act = new Actions();
									act.setId(Actions.SERVER_MESSAGE);
									act.setMessage(msg);
									oos.writeObject(act);
									oos.flush();
									break;
								}

								/* Envia uma notifica��o ao jogador. */								
								act = new Actions();
								act.setId(Actions.SERVER_MESSAGE);
								act.setMessage(msg);
								oos.writeObject(act);
								oos.flush();

								/* Solicita novamente ao player um nickanme. */
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
			
			System.out.println(Thread.activeCount());			

			/* Aguarda at� que todas as Threads tenham sido finalizadas. */
			while (Thread.activeCount() > 1);
			
			System.out.println(Thread.activeCount());
						
			/* Inicializa a mec�nica do jogo. */
			CoupLan coup = new CoupLan(this.numPlayers, players.keySet().toArray(new String[players.size()]));			
												
			/* Notifica todos os players para iniciar o jogo. */
			for (Socket player : players.values()) {
				new Thread(() -> {
					try {
						ObjectOutputStream oos = new ObjectOutputStream(player.getOutputStream());
						Actions action = new Actions();
						action.setCoup(coup);
						action.setLog("In�cio do Jogo.");
						log.add(action.getLog());												
						action.setId(Actions.LOAD_INTERFACE);						
						oos.writeObject(action);
						oos.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}).start();
			}						
						
			/* Aguarda at� que todas as Threads tenham sido finalizadas. */
			while (Thread.activeCount() > 1);

			/* Roda o Jogo at� que reste apenas 1 player. */
			for (Iterator<String> iterator = players.keySet().iterator(); players.size() > 1; 
					iterator = !iterator.hasNext() ? players.keySet().iterator() : iterator) {

				/* Obt�m a conex�o do player do turno. */
				playerName = (String) iterator.next();
				Socket player = players.get(playerName);
				System.out.println("Turno do Jogador " + playerName + ".");
				
				/* Envia ao player as suas a��es. */
				output = new ObjectOutputStream(player.getOutputStream());
				actions = new Actions();
				actions.setId(Actions.LOAD_PLAYER_ACTIONS);
				actions.setCoup(coup);
				actions.setFrom(playerName);
				output.writeObject(actions);
				output.flush();
				
				/* Recebe a resposta do jogador. */
				input = new ObjectInputStream(player.getInputStream());				
				actions = (Actions) input.readObject();
												
				if(actions.getId() == Actions.INCOME) {
					coup.play(Actions.INCOME, actions.getFrom(), actions.getTo(), actions.isContest(), actions.isBlock());
					
					/* Atualiza o jogo de todos os players. */
					String msg = "O jogador " + actions.getFrom() + " recebeu 1 moeda.";
					actions.setId(Actions.UPDATE_ALL_INTERFACE);
					actions.setCoup(coup);
					actions.setLog(msg);
					log.add(msg);
					
					/* Envia a a��o para todos os players. */
					for (Socket p : players.values()) {						
						output = new ObjectOutputStream(p.getOutputStream());
						output.writeObject(actions);
						output.flush();
					}														
				} else if(actions.getId() == Actions.FOREIGN) {
					//coup.play(Actions.FOREIGN, actions.getFrom(), actions.getTo(), contest, block)
					
				} else if(actions.getId() == Actions.COUP) {
					coup.play(Actions.COUP, actions.getFrom(), actions.getTo(), actions.isContest(), actions.isBlock());					
					/* REMOVER DO ITERATOR O PLAYER QUE LEVOU O GOLPE. */
					
					/* Atualiza o jogo de todos os players. */
					String msg = "O jogador " + actions.getFrom() + " deu um golpe de estado no jogador " + actions.getTo() + ".";
					actions.setId(Actions.UPDATE_ALL_INTERFACE);
					actions.setCoup(coup);
					actions.setLog(msg);
					log.add(msg);
					
					/* Envia a a��o para todos os players. */
					for (Socket p : players.values()) {						
						output = new ObjectOutputStream(p.getOutputStream());
						output.writeObject(actions);
						output.flush();
					}							
				} else if(actions.getId() == Actions.TAXES) {
					
				} else if(actions.getId() == Actions.ASSASSINATE) {
					
				} else if(actions.getId() == Actions.STEAL) {
					
				} else if(actions.getId() == Actions.SWAP) {
					
				}										

				// M�todo para remover.
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
			// JOptionPane.showMessageDialog(null, "O jogador contestou a a��o
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
