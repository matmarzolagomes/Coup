package ssc0103.coup.lan;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import ssc0103.coup.exception.PException;
import ssc0103.coup.game.Coup;

/**
 * Classe Board, caracteriza-se por ser o servidor local do jogo, gerenciando as
 * conexões com os jogadores da partida.
 * 
 * @author Bruno M.
 *
 */
public class Board extends Coup {
	// CONSTANTES
	private static final int CONNECTIONS_LIMIT = 50;
	private static final int MAX_PLAYER_NAME = 16;
	private static final int MIN_PLAYER_NAME = 1;
	private static final int ACTIVE_THREADS = 1;

	// ATRIBUTOS
	private int port;
	private int numPlayers;
	private ServerSocket board;
	private Map<String, Socket> players;
	private Map<String, ObjectInputStream> inputs;
	private Map<String, ObjectOutputStream> outputs;
	private List<String> gameLog;
	private String playerName;
	private Socket player;
	private String msg;
	private Actions actions;

	/**
	 * Construtor da Classe Board, responsável por iniciliazar o servidor do
	 * jogo.
	 */
	public Board() {
		/* Ativa o servidor na rede. */
		serverUp();

		/* Obtém número de jogadores da partida. */
		getNumPlayers();

		/* Indica status do servidor em tempo real. */
		serverStatus();

		/* Inicializa o HashMap de jogadores conectados. */
		this.players = new HashMap<String, Socket>(this.numPlayers);
		/* Inicializa o HashMap de conexões de entrada. */
		this.inputs = new HashMap<String, ObjectInputStream>(this.numPlayers);
		/* Inicializa o HashMap de conexões de saída. */
		this.outputs = new HashMap<String, ObjectOutputStream>(this.numPlayers);

		/* Inicializa o log da partida. */
		gameLog = new ArrayList<String>();
	}

	/**
	 * Obtém a porta de conexão com o servidor e verifica a sua validade.
	 */
	private void serverUp() {
		msg = "Informe a porta de conexão com o jogo:";

		try {
			msg = JOptionPane.showInputDialog(msg);

			if (msg == null)
				System.exit(0);

			this.port = Integer.parseInt(msg);
			board = new ServerSocket(this.port);

		} catch (IOException | IllegalArgumentException e) {
			msg = "Porta de conexão inválida.";
			JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
			serverUp();
		}
	}

	/**
	 * Obtém o número de players da partida e verifica a sua validade.
	 */
	private void getNumPlayers() {
		msg = "Informe o número de jogadores da partida:\nMínimo 2.\nMáximo " + CONNECTIONS_LIMIT + ".";
		try {
			msg = JOptionPane.showInputDialog(msg);

			if (msg == null) {
				board.close();
				System.exit(0);
			}

			this.numPlayers = Integer.parseInt(msg);

			if (this.numPlayers < 1 || this.numPlayers > CONNECTIONS_LIMIT)
				throw new IllegalArgumentException();

		} catch (IllegalArgumentException e) {
			msg = "O número de jogares informado é inválido.";
			JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
			getNumPlayers();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Indica em tempo real as informações e o status do servidor.
	 */
	private void serverStatus() {
		new Thread(() -> {
			System.out.println("Mesa Ativada.");
			System.out.println("Porta " + this.port + " aberta!");
		}).start();
	}

	@Override
	public String[] getInput(ssc0103.coup.game.Player player) {
		try {
			/* Envia requisição de carta ao jogador. */
			actions = new Actions();
			actions.setId(Actions.GET_INPUT);
			flushObject(actions, player.getName());

			/* Obtém cartas selecionadas pelo jogador. */
			actions = getObject(player.getName());

			return actions.getCards();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Método responsável por gerenciar as conexões do servidor e da partida.
	 * 
	 */
	public void execute() {
		try {
			/* Obtém todos os jogadores da partida. */
			getPlayersSockets();

			/* Inicializa a mecânica do jogo. */
			instanceGame(this.numPlayers, this.players.keySet().toArray(new String[this.players.size()]));

			/* Inicia o jogo em todos os players. */
			startGame();

			/* Roda o Jogo até que reste apenas 1 player. */
			for (Iterator<String> iterator = players.keySet().iterator(); players
					.size() > 0; iterator = !iterator.hasNext() ? players.keySet().iterator() : iterator)
				coupHandler(iterator);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (PException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} finally {
			try {
				/* Finaliza o servidor e fecha as conexões restantes. */
				if (player != null)
					player.close();
				if (board != null)
					board.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Recebe a conexão de todos os players com a partida.
	 * 
	 * @throws IOException
	 */
	private void getPlayersSockets() throws IOException {
		/* Aguarda até que todos os players tenham se conectado. */
		for (int i = 0; i < this.numPlayers; ++i) {

			/* Aceita uma conexão com um player. */
			this.player = board.accept();
			System.out.println("Player: " + player.getInetAddress().getHostAddress() + " conectado.");

			/* Obtém o nome do player. */
			new Thread(() -> {
				try {
					ObjectOutputStream output = new ObjectOutputStream(this.player.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(this.player.getInputStream());
					getPlayerName(this.player, input, output);
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}).start();
		}

		/* Aguarda Threads adiconais serem finalizadas para prosseguir. */
		waitThreads();
	}

	/**
	 * Recebe o nome do jogador e o insere em um HashMap.
	 * 
	 * @param player
	 * @param output
	 * @param input
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void getPlayerName(Socket player, ObjectInputStream input, ObjectOutputStream output)
			throws IOException, ClassNotFoundException {
		/* Objeto que serve como meio de comunicação. */
		Actions actions = new Actions();

		/* Mensagem que será enviada ao jogador. */
		String msg;

		try {
			/* Solicita ao jogador um nickname. */
			actions.setId(Actions.GET_NAME);
			output.writeObject(actions);
			output.flush();
			output.reset();

			/* Garante que o objeto utilizado será o do input. */
			actions = null;

			/* Recebe a resposta do jogador. */
			actions = (Actions) input.readObject();

			if (actions.getId() == Actions.GET_NAME) {
				/* Verifica se o nome é válido. */
				if (actions.getFrom() == null || actions.getFrom().isEmpty()) {
					msg = "Nomes nulos não são permitidos.";

				} else if (players.containsKey(actions.getFrom())) {
					msg = "Já existe um jogador com este nome.";

				} else if (actions.getFrom().length() < MIN_PLAYER_NAME) {
					msg = "O nome do jogador é muito curto.\nTamanho Mínimo = " + MIN_PLAYER_NAME + " caracteres.";

				} else if (actions.getFrom().length() > MAX_PLAYER_NAME) {
					msg = "O nome do jogador é muito extenso.\nTamanho Máximo = " + MAX_PLAYER_NAME + " caracteres.";

				} else {
					msg = "Aguardando demais jogadores.";

					/* Insere na lista de conexões de players. */
					players.put(actions.getFrom(), player);
					inputs.put(actions.getFrom(), input);
					outputs.put(actions.getFrom(), output);
					System.out.println("Jogador " + actions.getFrom() + " se juntou a mesa.");

					/* Envia mensagem de aguardando demais jogadores. */
					actions.setId(Actions.SERVER_MESSAGE);
					actions.setMessage(msg);
					flushObject(actions, actions.getFrom());
					return;
				}

				throw new PException(msg);
			} else {
				throw new NullPointerException("Objeto Actions vazio.");
			}
		} catch (PException f) {
			/* Envia uma notificação ao jogador. */
			actions = new Actions();
			actions.setId(Actions.SERVER_MESSAGE);
			actions.setMessage(f.getMessage());
			output.writeObject(actions);
			output.flush();
			output.reset();

			/* Solicita novamente ao player um nickanme. */
			getPlayerName(player, input, output);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Objeto actions vazio:" + e.getMessage());
		}
	}

	/**
	 * Espera até que todas as Threads adicionais tenham sido finalizadas.
	 */
	private void waitThreads() {
		System.out.println("Threads ativas no momento: " + Thread.activeCount() + ".");
		while (Thread.activeCount() > ACTIVE_THREADS)
			continue;
	}

	/**
	 * Notifica todos os players para iniciar o jogo.
	 * 
	 * @param coup
	 */
	private void startGame() {
		for (Iterator<String> playersList = players.keySet().iterator(); playersList.hasNext();) {
			this.playerName = (String) playersList.next();
			new Thread(() -> {
				try {
					Actions actions = new Actions();					
					actions.setId(Actions.LOAD_INTERFACE);
					actions.setFrom(this.playerName);
					actions.setPlayers(super.getPlayers());
					actions.setDead(super.getDead());
					actions.setLog("Início do Jogo.");
					gameLog.add(actions.getLog());
					flushObject(actions, this.playerName);
				} catch (IOException e) {
					//players.remove(player);
					e.printStackTrace();
				}
			}).start();
		}

		/* Aguarda Threads adiconais serem finalizadas para prosseguir. */
		waitThreads();
	}

	/**
	 * Gerencia as ações feitas pelos jogadores.
	 * 
	 * @param coup
	 * @param iterator
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws PException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	private void coupHandler(Iterator<String> iterator)
			throws IOException, ClassNotFoundException, PException, NoSuchMethodException, SecurityException {
		/* Nome do jogador do turno. */
		this.playerName = iterator.next();

		/* Verifica se o jogador foi retirado do jogo. */
		// if (!super.getPlayers().containsKey(playerName)) {
		// players.get(iterator).close();
		// iterator.remove();
		// return;
		// }

		/* Obtém a conexão do jogador do turno. */
		//player = players.get(playerName);
		System.out.println("Turno do Jogador " + this.playerName + ".");

		/* Envia ao jogador as suas ações. */
		actions = new Actions();
		actions.setId(Actions.LOAD_PLAYER_ACTIONS);
		actions.setFrom(this.playerName);
		actions.setPlayers(super.getPlayers());
		actions.setDead(super.getDead());		
		flushObject(actions, this.playerName);

		/* Recebe a resposta do jogador. */
		actions = getObject(this.playerName);

		/* Executa a ação do jogador. */
		switch (actions.getId()) {

		case Actions.LEFT:
			//players.get(iterator).close();
			//iterator.remove();
			break;

		case Actions.INCOME:
			income();
			break;

		case Actions.FOREIGN:
			foreign();
			break;

		case Actions.COUP:
			coup();
			break;

		case Actions.TAXES:
			taxes();
			break;

		case Actions.ASSASSINATE:
			assassinate();
			break;

		case Actions.STEAL:
			steal();
			break;

		case Actions.SWAP:
			swap();
			break;
		}
	}

	// ############# AÇÕES DO JOGADOR #############//

	private void income() throws PException, IOException {
		/* Executa a ação no servidor. */
		super.play(Actions.INCOME, actions.getFrom(), actions.getTo(), actions.isContest(), actions.isBlock());

		/* Atualiza o jogo de todos os players. */
		updateAllPlayers("O jogador " + actions.getFrom() + " recebeu 1 moeda.");
	}

	private void foreign()
			throws IOException, PException, ClassNotFoundException, NoSuchMethodException, SecurityException {
		updateAllPlayers("O jogador " + actions.getFrom() + " solicitou ajuda externa.");

		/* Pergunta a todos os jogadores se alguém deseja bloquear a ação. */
		spreadActions(actions, actions.getFrom());

		/* Obtém o bloqueio mais rápido, se houver. */
		getFastAction(Actions.class.getDeclaredMethod("isBlock", (Class<?>[]) null));
		if (actions.isBlock()) {
			updateAllPlayers(
					"O jogador " + actions.getTo() + " diz ser o " + actions.getCards()[0] + " e tenta bloquear.");

			/* Pergunta ao jogador se ele deseja contestar bloqueio. */
			flushObject(actions, actions.getFrom());
			actions = getObject(actions.getFrom());

			if (actions.isContest())
				updateAllPlayers("O jogador " + actions.getFrom() + " contestou o bloqueio.");
		}

		/* Executa a ação no servidor. */
		super.play(Actions.FOREIGN, actions.getFrom(), actions.getTo(), actions.isContest(), actions.isBlock());

		// updateAllPlayers("Ação bem sucedida do jogador X.");
	}

	private void coup() throws PException, IOException {
		/* Envia notificação ao player de que recebeu um golpe de estado. */
		flushObject(actions, actions.getTo());

		super.play(Actions.COUP, actions.getFrom(), actions.getTo(), actions.isContest(), actions.isBlock());

		/* Atualiza o jogo de todos os players. */
		updateAllPlayers(
				"O jogador " + actions.getFrom() + " deu um golpe de estado no jogador " + actions.getTo() + ".");
	}

	private void taxes() throws IOException, PException, NoSuchMethodException, SecurityException {
		updateAllPlayers("O jogador " + actions.getFrom() + " diz ser o Duque e solicitou taxas.");

		/* Pergunta a todos os jogadores se alguém deseja contestar a ação. */
		spreadActions(actions, actions.getFrom());

		/* Obtém a contestação mais rápida, se houver. */
		getFastAction(Actions.class.getDeclaredMethod("isContest", (Class<?>[]) null));

		if (actions.isContest())
			updateAllPlayers("O jogador " + actions.getTo() + " contestou.");

		super.play(Actions.TAXES, actions.getFrom(), actions.getTo(), actions.isContest(), actions.isBlock());

		// updateAllPlayers("Ação bem sucedida do jogador X.");
	}

	private void assassinate() throws IOException, ClassNotFoundException, PException {
		updateAllPlayers("O jogador " + actions.getFrom() + " diz ser o Assassino e pretende assassinar o jogador "
				+ actions.getTo());

		/* Pergunta ao jogador se ele deseja contestar, permitir ou bloquear. */
		flushObject(actions, actions.getTo());

		/* Obtém a resposta do jogador. */
		actions = getObject(actions.getTo());

		/* Se tentou bloquear, pergunta ao atacante se deseja contestar. */
		if (actions.isBlock()) {
			updateAllPlayers("O jogador " + actions.getTo() + " diz ser a Condessa e deseja bloquear.");
			flushObject(actions, actions.getFrom());
			actions = getObject(actions.getFrom());

			if (actions.isContest())
				updateAllPlayers("O jogador " + actions.getFrom() + " contesta.");
		}

		super.play(Actions.ASSASSINATE, actions.getFrom(), actions.getTo(), actions.isContest(), actions.isBlock());

		// updateAllPlayers("Ação bem sucedida do jogador X.");
	}

	private void steal() throws IOException, ClassNotFoundException, PException {
		updateAllPlayers("O jogador " + actions.getFrom() + " diz ser o Capitão e deseja roubar o jogador "
				+ actions.getTo() + ".");

		/* Notifica o oponente que está sendo roubado. */
		flushObject(actions, actions.getTo());

		/* Obtém resposta do oponente. */
		actions = getObject(actions.getTo());

		if (actions.isBlock()) {
			updateAllPlayers("O jogador " + actions.getTo() + " diz ser o " + actions.getCards()[0] + " e bloqueia.");

			flushObject(actions, actions.getFrom());
			actions = getObject(actions.getFrom());
		} else if (actions.isContest()) {
			updateAllPlayers("O jogador " + actions.getTo() + " contesta.");
		}

		super.play(Actions.STEAL, actions.getFrom(), actions.getTo(), actions.isContest(), actions.isBlock());

		// updateAllPlayers("Ação bem sucedida do jogador X.");
	}

	private void swap() throws IOException, PException, NoSuchMethodException, SecurityException {
		updateAllPlayers("O jogador " + actions.getFrom() + " diz ser o embaixador e deseja trocar cartas.");

		/* Notifica todos os oponentes. */
		spreadActions(actions, actions.getFrom());

		/* Obtém a contestação mais rápida, se houver. */
		getFastAction(Actions.class.getDeclaredMethod("isContest", (Class<?>[]) null));

		if (actions.isContest())
			updateAllPlayers("O jogador " + actions.getTo() + "contesta.");

		/* Executa a ação. */
		super.play(Actions.SWAP, actions.getFrom(), actions.getTo(), actions.isContest(), actions.isBlock());

		// updateAllPlayers("Ação bem sucedida do jogador X.");
	}

	// ############# MÈTODOS AUXILIARES DO SERVIDOR #############//

	/**
	 * Envia ação para todos os players menos o do parâmetro.
	 * 
	 * @throws IOException
	 */
	private void spreadActions(Actions actions, String playerName) {
		for (String player : players.keySet()) {
			if (!player.equals(playerName)) {
				try {
					flushObject(actions, player);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Obtém a ação de resposta mais rápida entre os jogadores oponentes. A ação
	 * pode ser um bloqueio ou um contestamento.
	 */
	private void getFastAction(Method method) {
		for (String player : players.keySet()) {
			if (!player.equals(playerName)) {
				new Thread(() -> {
					try {
						ObjectInputStream input = new ObjectInputStream(players.get(player).getInputStream());
						Actions action = (Actions) input.readObject();
						if (method.invoke(actions).equals(false) && method.invoke(action).equals(true))
							actions = action;
					} catch (IOException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
				}).start();
			}
		}

		/* Aguarda até que todas as threads tenham terminado. */
		waitThreads();
	}

	/**
	 * Envia o objeto Actions para um jogador.
	 * 
	 * @throws IOException
	 */
	private void flushObject(Actions actions, String player) throws IOException {
		/* Canal de comunicação do servidor para o cliente. */
		ObjectOutputStream output = outputs.get(player);
		/* Escreve o objeto no canal. */
		output.writeObject(actions);
		/* Envia o objeto para o cliente. */
		output.flush();
		/* Reset no output. */
		output.reset();
	}

	/**
	 * Retorna o objeto Actions de um jogador.
	 * 
	 * @param player
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Actions getObject(String player) throws IOException, ClassNotFoundException {
		/* Canal de comunicação do cliente para o servidor. */
		ObjectInputStream input = inputs.get(player);
		/* Retorna o objeto enviado pelo cliente. */
		Actions act = (Actions) input.readObject();
		return act;
	}

	/**
	 * Atualiza o jogo de todos os players.
	 */
	private void updateAllPlayers(String msg) {
		/* Realiza um backup do objeto Actions. */
		Actions actionsBackup = (Actions) actions.clone();

		gameLog.add(msg);
		actionsBackup.setId(Actions.UPDATE_ALL_INTERFACE);
		actionsBackup.setPlayers(super.getPlayers());
		actionsBackup.setDead(super.getDead());
		actionsBackup.setLog(msg);

		/* Envia ação para todos os jogadores. */
		spreadActions(actionsBackup, null);

		/* Restaura o objeto Actions. */
		// actions = actionsBackup;
	}
}