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
import ssc0103.coup.game.Coup;
import ssc0103.coup.game.Deck;

/**
 * Classe Board, caracteriza-se por ser o servidor local do jogo, gerenciando as
 * conexões com os jogadores da partida.
 * 
 * @author Bruno M.
 *
 */
public class Board extends Coup {
    // CONSTANTES
    private static final int CONNECTIONS_LIMIT = 100;
    private static final int MAX_PLAYER_NAME = 16;
    private static final int MIN_PLAYER_NAME = 1;

    // ATRIBUTOS
    private int port;
    private int numPlayers;
    private ServerSocket board;
    private Map<String, Socket> players;
    private List<String> gameLog;

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

	/* Inicializa o log da partida. */
	gameLog = new ArrayList<String>();
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

    /**
     * Obtém o número de players da partida e verifica a sua validade.
     */
    private void getNumPlayers() {
	String msg = "Informe o número de jogadores da partida:\nMínimo 2.\nMáximo " + CONNECTIONS_LIMIT + ".";
	try {
	    msg = JOptionPane.showInputDialog(msg);

	    if (msg == null) {
		board.close();
		System.exit(0);
	    }

	    this.numPlayers = Integer.parseInt(msg);

	    if (this.numPlayers < 2 || this.numPlayers > CONNECTIONS_LIMIT)
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
     * Obtém a porta de conexão com o servidor e verifica a sua validade.
     */
    private void serverUp() {
	String msg = "Informe a porta de conexão com o jogo:";

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
     * Método responsável por gerenciar as conexões do servidor e da partida.
     * 
     */
    public void execute() {
	try {
	    /* Obtém todos os jogadores da partida. */
	    getPlayersSockets();

	    /* Inicializa a mecânica do jogo. */
	    instanceGame(numPlayers, players.keySet().toArray(new String[players.size()]));

	    /* Inicia o jogo em todos os players. */
	    startGame();

	    /* Roda o Jogo até que reste apenas 1 player. */
	    for (Iterator<String> iterator = players.keySet().iterator(); players
		    .size() > 1; iterator = !iterator.hasNext() ? players.keySet().iterator() : iterator)
		coupHandler(iterator);

	    /* Finaliza o servidor e fecha as conexões restantes. */
	    board.close();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (PException e) {
	    e.printStackTrace();
	}
    }

    /**
     * 
     * @param coup
     * @param iterator
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws PException
     */
    private void coupHandler(Iterator<String> iterator) throws IOException, ClassNotFoundException, PException {
	Actions actions;
	ObjectInputStream input;
	ObjectOutputStream output;
	String playerName;

	/* Nome do jogador do turno. */
	playerName = iterator.next();

	/* Verifica se o jogador foi retirado do jogo. */
	if (!super.getPlayers().containsKey(iterator)) {
	    iterator.remove();
	    return;
	}

	/* Obtém a conexão do jogador do turno. */
	Socket player = players.get(playerName);
	System.out.println("Turno do Jogador " + playerName + ".");

	/* Envia ao jogador as suas ações. */
	output = new ObjectOutputStream(player.getOutputStream());
	actions = new Actions();
	actions.setId(Actions.LOAD_PLAYER_ACTIONS);
	actions.setPlayers(super.getPlayers());
	actions.setDead(super.getDead());
	actions.setFrom(playerName);
	output.writeObject(actions);
	output.flush();

	/* Recebe a resposta do jogador. */
	input = new ObjectInputStream(player.getInputStream());
	actions = (Actions) input.readObject();

	/* Executa a ação do jogador. */
	switch (actions.getId()) {

	case Actions.LEFT:
	    iterator.remove();
	    break;

	case Actions.INCOME:
	    income(actions);
	    break;

	case Actions.FOREIGN:
	    foreign(actions, playerName);
	    break;

	case Actions.COUP:
	    coup(actions);
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

    /**
     * 
     */
    private void swap() {
	System.out.println("Swap");
    }

    /**
     * 
     */
    private void steal() {
	System.out.println("Steal");
    }

    /**
     * 
     */
    private void assassinate() {
	System.out.println("Assassinate");
    }

    /**
     * 
     */
    private void taxes() {
	System.out.println("Taxes");
    }

    /**
     * @param actions
     * @throws PException
     * @throws IOException
     */
    private void coup(Actions actions) throws PException, IOException {
	String msg;
	super.play(Actions.COUP, actions.getFrom(), actions.getTo(), actions.isContest(), actions.isBlock());
	/* REMOVER DO ITERATOR O PLAYER QUE LEVOU O GOLPE E FICOU SEM CARTAS. */

	/* Atualiza o jogo de todos os players. */
	msg = "O jogador " + actions.getFrom() + " deu um golpe de estado no jogador " + actions.getTo() + ".";
	actions.setId(Actions.UPDATE_ALL_INTERFACE);
	actions.setPlayers(super.getPlayers());
	actions.setDead(super.getDead());
	actions.setLog(msg);
	gameLog.add(msg);

	/* Envia ação para todos os jogadores. */
	spreadAction(actions);
    }

    /**
     * @param actions 
     * @param playerName 
     * 
     */
    private void foreign(Actions actions, String playerName) {
	actions.setId(Actions.FOREIGN);
	actions.setFrom(playerName);
	
	
	System.out.println("FOREIGN");
	// coup.play(Actions.FOREIGN, actions.getFrom(),
	// actions.getTo(), contest, block)
    }

    /**
     * @param actions
     * @throws PException
     * @throws IOException
     */
    private void income(Actions actions) throws PException, IOException {
	String msg;
	super.play(Actions.INCOME, actions.getFrom(), actions.getTo(), actions.isContest(), actions.isBlock());

	/* Atualiza o jogo de todos os players. */
	msg = "O jogador " + actions.getFrom() + " recebeu 1 moeda.";
	actions.setId(Actions.UPDATE_ALL_INTERFACE);
	actions.setPlayers(super.getPlayers());
	actions.setDead(super.getDead());
	actions.setLog(msg);
	gameLog.add(msg);

	/* Envia ação para todos os jogadores. */
	spreadAction(actions);
    }

    /**
     * Envia a ação para todos os players.
     * 
     * @param actions
     * @throws IOException
     */
    private void spreadAction(Actions actions) throws IOException {
	ObjectOutputStream output;

	for (Socket p : players.values()) {
	    output = new ObjectOutputStream(p.getOutputStream());
	    output.writeObject(actions);
	    output.flush();
	}
    }

    /**
     * Notifica todos os players para iniciar o jogo.
     * 
     * @param coup
     */
    private void startGame() {
	for (String player : players.keySet()) {
	    new Thread(() -> {
		try {
		    ObjectOutputStream output = new ObjectOutputStream(players.get(player).getOutputStream());
		    Actions action = new Actions();

		    action.setPlayers(super.getPlayers());
		    action.setDead(super.getDead());
		    action.setLog("Início do Jogo.");
		    gameLog.add(action.getLog());
		    action.setId(Actions.LOAD_INTERFACE);

		    output.writeObject(action);
		    output.flush();
		} catch (IOException e) {
		    players.remove(player);
		    e.printStackTrace();
		}
	    }).start();

	}

	/* Aguarda Threads adiconais serem finalizadas para prosseguir. */
	waitThreads();
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
	    Socket player = board.accept();
	    System.out.println("Player: " + player.getInetAddress().getHostAddress() + " conectado.");

	    /* Obtém o nome do player. */
	    new Thread(() -> {
		try {
		    getPlayerName(player);
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
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void getPlayerName(Socket player) throws IOException, ClassNotFoundException {
	/* Canal de comunicação do cliente para o servidor. */
	ObjectInputStream input = new ObjectInputStream(player.getInputStream());

	/* Canal de comunicação do servidor para o cliente. */
	ObjectOutputStream output = new ObjectOutputStream(player.getOutputStream());

	/* Objeto que serve como meio de comunicação. */
	Actions actions = new Actions();

	/* Mensagem que será enviada. */
	String msg;

	try {
	    /* Solicita ao jogador um nickname. */
	    actions.setId(Actions.GET_NAME);
	    output.writeObject(actions);
	    output.flush();

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
		    System.out.println("Jogador " + actions.getFrom() + " se juntou a mesa.");

		    /* Envia mensagem de aguardando demais jogadores. */
		    actions = new Actions();
		    actions.setId(Actions.SERVER_MESSAGE);
		    actions.setMessage(msg);
		    output.writeObject(actions);
		    output.flush();
		    return;
		}

		throw new PException(msg);
	    }
	} catch (PException f) {
	    /* Envia uma notificação ao jogador. */
	    actions = new Actions();
	    actions.setId(Actions.SERVER_MESSAGE);
	    actions.setMessage(f.getMessage());
	    output.writeObject(actions);
	    output.flush();

	    /* Solicita novamente ao player um nickanme. */
	    getPlayerName(player);
	}
    }

    /**
     * Espera até que todas as Threads adicionais tenham sido finalizadas.
     */
    private void waitThreads() {
	System.out.println("Threads ativas no momento: " + Thread.activeCount() + ".");
	if (Thread.activeCount() > 1)
	    waitThreads();
    }

    @Override
    public String[] getInput(Deck hand, Socket player) {
	ObjectOutputStream output;
	ObjectInputStream input;
	Actions action;

	try {
	    /* Envia requisição de carta ao jogador. */
	    output = new ObjectOutputStream(player.getOutputStream());
	    action = new Actions();
	    action.setId(Actions.GET_INPUT);
	    output.flush();

	    /* Obtém cartas selecionadas pelo jogador. */
	    input = new ObjectInputStream(player.getInputStream());
	    action = (Actions) input.readObject();
	    return action.getCards();
	} catch (IOException | ClassNotFoundException e) {
	    e.printStackTrace();
	}
	return null;
    }
}
