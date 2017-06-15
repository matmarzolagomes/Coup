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
import java.util.Set;

import javax.swing.JOptionPane;

import ssc0103.coup.exception.LANExcpetion;
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
	private static final int MIN_PLAYER_NAME = 2;
	private static final int LOOP_BREAK = 1;
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
			actions.setPlayer(player);
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
			for (Iterator<String> it = it(); players.size() > LOOP_BREAK; it = !it.hasNext() ? it() : it)
				coupHandler(it);

		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			/* Finaliza o servidor e fecha as conexões restantes. */
			shutdownBoard();
		}
	}

	/**
	 * @return Retorna uma lista de chaves para o iterator.
	 */
	private Iterator<String> it() {
		return players.keySet().iterator();
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

				throw new LANExcpetion(msg);
			} else {
				throw new NullPointerException("Objeto Actions vazio.");
			}
		} catch (LANExcpetion f) {
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
	 * Notifica todos os players para iniciar o jogo.
	 * 
	 * @param coup
	 * @throws IOException
	 */
	private void startGame() throws IOException {
		Actions actions = new Actions();
		actions.setId(Actions.LOAD_INTERFACE);
		actions.setPlayers(super.getPlayers());
		actions.setDead(super.getDead());
		spreadActions(actions, null);
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
	private void coupHandler(Iterator<String> iterator) {
		try {
			/* Nome do jogador do turno. */
			this.playerName = iterator.next();

			/* Verifica se o jogador foi retirado do jogo. */
			if (!super.getPlayers().containsKey(this.playerName)) {
				closeConnections(this.playerName);
				iterator.remove();
				return;
			}

			/* Obtém a conexão do jogador do turno. */
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
				closeConnections(this.playerName);
				iterator.remove();
				return;

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
		} catch (NoSuchMethodException | SecurityException | IOException | PException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	// ############# AÇÕES DO JOGADOR ############# //

	private void income() throws PException, IOException {
		/* Executa ação e atualiza o jogo de todos os jogadores. */
		updateAllPlayers("O jogador " + playCoup(Actions.INCOME) + " recebeu 1 moeda.");
	}

	private void foreign()
			throws IOException, PException, ClassNotFoundException, NoSuchMethodException, SecurityException {
		updateAllPlayers("O jogador " + actions.getFrom() + " solicitou ajuda externa.");

		/* Pergunta a todos os jogadores se alguém deseja bloquear a ação. */
		spreadActions(actions, actions.getFrom());

		/* Obtém o bloqueio mais rápido, se houver. */
		getFastAction(Actions.class.getDeclaredMethod("isBlock", (Class<?>[]) null));
		if (actions.isBlock()) {
			msg = "O jogador " + actions.getTo() + " diz ser o " + actions.getCards()[0] + " e tenta bloquear.";
			updateAllPlayers(msg);

			/* Pergunta ao jogador se ele deseja contestar bloqueio. */
			flushObject(actions, actions.getFrom());
			actions = getObject(actions.getFrom());

			if (actions.isContest())
				updateAllPlayers("O jogador " + actions.getFrom() + " contestou o bloqueio.");
		}

		/* Executa a ação e notifica a todos. */
		updateAllPlayers("Ação bem sucedida do jogador " + playCoup(Actions.FOREIGN) + ".");
	}

	private void coup() throws PException, IOException {
		/* Envia notificação ao player de que recebeu um golpe de estado. */
		flushObject(actions, actions.getTo());

		/* Executa ação e atualiza o jogo de todos os jogadores. */
		msg = "O jogador " + playCoup(Actions.COUP) + " deu um golpe de estado no jogador " + actions.getTo() + ".";
		updateAllPlayers(msg);
	}

	private void taxes() throws IOException, PException, NoSuchMethodException, SecurityException {
		updateAllPlayers("O jogador " + actions.getFrom() + " diz ser o Duque e solicitou taxas.");

		/* Pergunta a todos os jogadores se alguém deseja contestar a ação. */
		spreadActions(actions, actions.getFrom());

		/* Obtém a contestação mais rápida, se houver. */
		getFastAction(Actions.class.getDeclaredMethod("isContest", (Class<?>[]) null));

		if (actions.isContest())
			updateAllPlayers("O jogador " + actions.getTo() + " contestou.");

		/* Executa a ação e notifica a todos. */
		updateAllPlayers("Ação bem sucedida do jogador " + playCoup(Actions.TAXES) + ".");
	}

	private void assassinate() throws IOException, ClassNotFoundException, PException {
		updateAllPlayers("O jogador " + actions.getFrom() + " diz ser o Assassino e pretende assassinar o jogador "
				+ actions.getTo() + ".");

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

		/* Executa a ação e notifica a todos. */
		updateAllPlayers("Ação bem sucedida do jogador " + playCoup(Actions.ASSASSINATE) + ".");
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

		/* Executa a ação e notifica a todos. */
		updateAllPlayers("Ação bem sucedida do jogador " + playCoup(Actions.STEAL) + ".");
	}

	private void swap() throws IOException, PException, NoSuchMethodException, SecurityException {
		updateAllPlayers("O jogador " + actions.getFrom() + " diz ser o embaixador e deseja trocar cartas.");

		/* Notifica todos os oponentes. */
		spreadActions(actions, actions.getFrom());

		/* Obtém a contestação mais rápida, se houver. */
		getFastAction(Actions.class.getDeclaredMethod("isContest", (Class<?>[]) null));

		if (actions.isContest())
			updateAllPlayers("O jogador " + actions.getTo() + "contesta.");

		/* Executa a ação e notifica a todos. */
		updateAllPlayers("Ação bem sucedida do jogador " + playCoup(Actions.SWAP) + ".");
	}

	// ############# MÈTODOS AUXILIARES DO SERVIDOR ############# //

	/**
	 * Espera até que todas as Threads adicionais tenham sido finalizadas.
	 */
	private void waitThreads() {
		System.out.println("Threads ativas no momento: " + Thread.activeCount() + ".");
		while (Thread.activeCount() > ACTIVE_THREADS)
			continue;
		System.out.println("Threads ativas no momento: " + Thread.activeCount() + ".");
	}

	/**
	 * Executa uma ação no jogo.
	 * 
	 * @throws PException
	 */
	private String playCoup(int action) throws PException {
		return super.play(action, actions.getFrom(), actions.getTo(), actions.isContest(), actions.isBlock());
	}

	/**
	 * Envia ação para todos os players menos o do parâmetro.
	 * 
	 * @throws IOException
	 */
	private void spreadActions(Actions actions, String playerName) throws IOException {
		for (String player : players.keySet()) {
			if (!player.equals(playerName))
				flushObject(actions, player);
		}
	}

	/**
	 * Obtém a ação de resposta mais rápida entre os jogadores oponentes. A ação
	 * pode ser um bloqueio ou um contestamento.
	 */
	private void getFastAction(Method method) {
		for (String player : players.keySet()) {
			if (!player.equals(this.playerName)) {
				new Thread(() -> {
					try {
						Actions action;
						action = getObject(player);
						if (method.invoke(actions).equals(false) && method.invoke(action).equals(true))
							actions = action;
					} catch (ClassNotFoundException | IOException | IllegalAccessException | IllegalArgumentException
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
		/* Fluxo de dados do servidor para o cliente. */
		ObjectOutputStream output = outputs.get(player);
		/* Escreve o objeto no fluxo. */
		output.writeObject(actions);
		/* Envia o objeto para o cliente. */
		output.flush();
		/* Limpa o fluxo de dados. */
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
		/* Fluexo de dados do cliente para o servidor. */
		ObjectInputStream input = inputs.get(player);
		/* Retorna o objeto enviado pelo cliente. */
		return (Actions) input.readObject();
	}

	/**
	 * Atualiza o jogo de todos os players.
	 * 
	 * @throws IOException
	 */
	private void updateAllPlayers(String msg) throws IOException {
		/* Realiza um cópia do objeto Actions. */
		Actions actionsCopy = (Actions) actions.clone();

		gameLog.add(msg);
		actionsCopy.setId(Actions.UPDATE_ALL_INTERFACE);
		actionsCopy.setPlayers(super.getPlayers());
		actionsCopy.setDead(super.getDead());
		actionsCopy.setLog(msg);

		/* Envia ação para todos os jogadores. */
		spreadActions(actionsCopy, null);
	}

	/**
	 * Encerra todas as conexões com um jogador.
	 * 
	 * @throws IOException
	 */
	private void closeConnections(String playerName) {
		/* Fecha todas conexões com o jogador. */
		try {
			if (inputs.get(playerName) != null)
				inputs.get(playerName).close();
			if (outputs.get(playerName) != null)
				outputs.get(playerName).close();
			if (players.get(playerName) != null)
				players.get(playerName).close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Finaliza a partida e fecha todas conexões restantes.
	 */
	private void shutdownBoard() {
		Set<String> playersName = players.keySet();
		for (String playerConnection : playersName)
			closeConnections(playerConnection);

		try {
			if (board != null)
				board.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		/* Inicia o Servidor. */
		new Board().execute();
	}
}