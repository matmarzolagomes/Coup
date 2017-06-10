package ssc0103.coup.lan;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;
import ssc0103.coup.exception.GUIException;
import ssc0103.coup.game.Deck;
import ssc0103.coup.gui.PopUp;

/**
 * Classe Player, caracteriza-se por executar as ações recebidas pelo servidor e
 * enviar uma resposta.
 * 
 * @author Bruno M.
 *
 */
public class Player {
    private String host;
    private int port;
    private Socket player;

    /**
     * Construtor do Player, responsável por conectar o jogador com o servidor.
     */
    public Player() {
	/* Conecta ao servidor do jogo. */
	connectHost();

	System.out.println("Player Conectado.");
	System.out.println("O player se juntou a mesa!");
    }

    /**
     * Realiza uma conexão com o servidor do jogo.
     */
    private void connectHost() {
	String msg = "Informe o IP de conexão com o servidor do jogo:\nDefault: 127.0.0.1";
	try {
	    this.host = JOptionPane.showInputDialog(msg);

	    if (host == null)
		System.exit(0);
	    else if (host.isEmpty())
		host = "127.0.0.1";

	    msg = "Informe a porta de conexão com o servidor do jogo:";
	    msg = JOptionPane.showInputDialog(msg);

	    if (msg == null)
		System.exit(0);

	    this.port = Integer.parseInt(msg);
	    player = new Socket(this.host, this.port);

	} catch (IOException | IllegalArgumentException e) {
	    msg = "Não foi possível realizar a conexão no host e porta informados.\nTente outra conexão.";
	    JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
	    connectHost();
	}
    }

    /**
     * Executa o jogo.
     */
    public void execute() {
	ObjectInputStream input;
	ObjectOutputStream output;

	try {
	    /* Recebe um objeto do servidor. */
	    input = new ObjectInputStream(player.getInputStream());

	    Actions actions = (Actions) input.readObject();

	    /* Objeto que será enviado ao servidor. */
	    output = new ObjectOutputStream(player.getOutputStream());

	    switch (actions.getId()) {
	    case Actions.GET_NAME:
		getName(output, actions);
		break;

	    case Actions.SERVER_MESSAGE:
		getMessage(output, actions);
		break;

	    case Actions.ASSASSINATE:
		if (assassinate(output, actions))
		    return;
		break;

	    case Actions.ASSASSINATE_BLOCK:
		// PEGA AÇÂO DO JOGADOR
		// SE ELE PERMITE O BLOQUEIO OU CONTESTA
		// ENVIA RESPOSTA AO SERVIDOR
		break;

	    case Actions.COUP:
		// CARREGA POPUP DIZENDO QUE LEVOU UM GOLPE DE ESTADO
		// ESCOLHE CARTA A RETIRAR
		// ENVIA CARTA AO SERVIDOR
		return;

	    case Actions.FOREIGN:
		// ALGUM JOGADOR SOLICITOU AJUDA EXTERNA
		// CARREGA RESPOSTA DO JOGADOR
		// ENVIA AO SERVIDOR
		break;

	    case Actions.STEAL:
		// TENTA CONTESTAR OU BLOQUEAR
		// ENVIA REPOSTA AO SERVIDOR
		break;

	    case Actions.STEAL_BLOCK:
		// PEGA AÇÂO DO JOGADOR
		// SE ELE PERMITE O BLOQUEIO OU CONTESTA
		// ENVIA RESPOSTA AO SERVIDOR
		break;

	    case Actions.LOAD_INTERFACE:
		// CARREGA PELA PRIMEIRA VEZ A INTERFACE GRÁFICA
		loadInterface(output, actions);
		break;

	    case Actions.LOAD_PLAYER_ACTIONS:
		// CARREGA AS AÇÕES DO JOGADOR E OBTÈM UMA RESPOSTA
		// ENVIA AO SERVIDOR A RESPOSTA
		break;

	    case Actions.TAXES:
		// ALGUM JOGADOR SE PROCLAMOU DUQUE
		// CARREGA A AÇÃO DO JOGADOR
		// ENVIA RESPOSTA AS SERVIDOR
		break;

	    case Actions.SWAP:
		// ALGUM JOGADOR SE PROCLAMOU EMBAIXADOR
		// CARREGA AÇÃO DO JOGADOR
		// ENVIA RESPOSTA AO SERVIDOR
		break;

	    case Actions.UPDATE_ALL_INTERFACE:
	    case Actions.UPDATE_INTERFACE:
		// ATUALIZA A INTERFACE GRÀFICA
		break;
	    }
	    execute();
	} catch (IOException | ClassNotFoundException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Envia ao servidor a resposta do jogador sobre estar sendo assassinado.
     * 
     * @param output
     * @param actions
     * @return
     * @throws IOException
     */
    private boolean assassinate(ObjectOutputStream output, Actions actions) throws IOException {
	int op = JOptionPane.showConfirmDialog(null,
		"Vc está sendo Assassinado pelo Player " + actions.getFrom() + ". Informe a sua ação", "Assassinate",
		JOptionPane.YES_NO_CANCEL_OPTION);
	switch (op) {
	/* Permitiu. */
	case 0:
	    actions = new Actions();
	    actions.setId(Actions.ALLOW);
	    actions.setAllow(true);
	    output.writeObject(actions);
	    output.flush();
	    return true;

	/* Contestou. */
	case 1:
	    actions = new Actions();
	    actions.setId(Actions.ASSASSINATE_CONTEST);
	    actions.setContest(true);
	    output.writeObject(actions);
	    output.flush();
	    break;

	/* Bloqueou. */
	case 2:
	    actions = new Actions();
	    actions.setId(Actions.ASSASSINATE_BLOCK);
	    actions.setBlock(true);
	    output.writeObject(actions);
	    output.flush();
	    break;
	default:
	    break;
	}
	return false;
    }

    /**
     * Carrega a interface gráfica do jogador.
     * 
     * @param output
     * @throws IOException
     */
    private void loadInterface(ObjectOutputStream output, Actions actions) throws IOException {
	JOptionPane.showMessageDialog(null, "Interface Carregada.");
	// actions = new Actions();
	// actions.setId(Actions.PLAYER_RESPONSE);
	// actions.setPlayerResponse(true);
	// output.writeObject(actions);
	// output.flush();
    }

    /**
     * Recebe uma mensagem do servidor e exibe na tela.
     * 
     * @param output
     * @param actions
     * @throws IOException
     */
    private void getMessage(ObjectOutputStream output, Actions actions) throws IOException {
	JOptionPane.showMessageDialog(null, actions.getMessage(), "Mensagem", JOptionPane.WARNING_MESSAGE);
	// actions.setPlayerResponse(true);
	// output.writeObject(actions);
	// output.flush();
    }

    /**
     * Envia o nome do jogador ao servidor.
     * 
     * @param output
     * @param actions
     * @throws IOException
     */
    private void getName(ObjectOutputStream output, Actions actions) throws IOException {
	String name = "Informe o seu nickname no jogo:";
	name = JOptionPane.showInputDialog(name);
	actions.setFrom(name);
	output.writeObject(actions);
	output.flush();
    }

    public String[] PopUp(Deck hand) {
	String[] ret = null;

	try {
	    boolean cont = true;
	    while (cont) {
		PopUp pop = new PopUp(hand, hand.size() / 2);
		ret = (String[]) pop.showPopUp().toArray();
		if (ret.length == hand.size() / 2)
		    cont = false;
	    }
	} catch (GUIException ex) {
	    System.out.println(ex.getMessage());
	    System.exit(-1);
	}

	return ret;
    }
}
