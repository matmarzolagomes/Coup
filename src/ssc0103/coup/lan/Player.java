package ssc0103.coup.lan;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ssc0103.coup.exception.GUIException;
import ssc0103.coup.game.Deck;
import ssc0103.coup.gui.PopUp;
import ssc0103.coup.gui.PopUpPlayer;

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
	private String playerName;
	private Socket player;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Actions actions;
	private PopUpPlayer popup;

	/**
	 * Construtor do Player, responsável por conectar o jogador com o servidor.
	 */
	public Player() {
		/* Conecta ao servidor do jogo. */
		connectHost();

		/* Inicializa o objeto de PopUps. */
		popup = new PopUpPlayer();

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
			System.out.println("3");
			this.output = new ObjectOutputStream(player.getOutputStream());
			System.out.println("4");
			this.input = new ObjectInputStream(player.getInputStream());
			System.out.println("5");
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
		try {
			System.out.println("1");
			/* Recebe um objeto do servidor. */
			actions = getObject();
			System.out.println("2");
			
			switch (actions.getId()) {
			case Actions.GET_NAME:
				getName();
				break;

			case Actions.SERVER_MESSAGE:
				getMessage();
				break;

			case Actions.ASSASSINATE:
				assassinate();
				break;

			case Actions.COUP:
				coup();
				return;

			case Actions.FOREIGN:
				foreign();
				break;

			case Actions.STEAL:
				steal();
				break;

			case Actions.LOAD_INTERFACE:
				// CARREGA PELA PRIMEIRA VEZ A INTERFACE GRÁFICA
				loadInterface();
				break;

			case Actions.LOAD_PLAYER_ACTIONS:
				loadPlayerActions();
				break;

			case Actions.TAXES:
				taxes();
				break;

			case Actions.SWAP:
				swap();
				break;

			case Actions.UPDATE_ALL_INTERFACE:
			case Actions.UPDATE_INTERFACE:
				// ATUALIZA A INTERFACE GRÀFICA
				break;

			case Actions.GET_INPUT:
				getInput();
				break;
			}
			execute();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void getInput() throws IOException {
		/* Solicitar ao jogador para remover 2 cartas. */
		if (actions.getPlayer().getHand().size() > 2) {
			popUp(actions.getPlayer().getHand());
			/* Solicitar ao jogador para remover 1 carta. */
		} else {
			popUp(actions.getPlayer().getHand());
		}

		/* Envia as cartas a serem removidas para o servidor. */
		flushObject();
	}

	private void swap() throws IOException {
		if (popup.popUpTroca(actions.getFrom()) == 1) {
			actions.setTo(playerName);
			actions.setContest(true);
		}

		/* Envia objeto de resposta ao servidor. */
		flushObject();
	}

	private void taxes() throws IOException {
		if (popup.popUpTaxas(actions.getFrom()) == 1) {
			actions.setTo(playerName);
			actions.setContest(true);
		}

		/* Envia objeto de resposta ao servidor. */
		flushObject();
	}

	private void foreign() throws IOException {
		/* Verifica se jogador bloqueou ação. */
		if (actions.isBlock()) {
			/* Verifica se jogador deseja contestar. */
			if (popup.popUpBloqueioAjuda(actions.getTo()) == 1)
				actions.setContest(true);
		} else {
			/* Verifica se o jogador deseja bloquear. */
			if (popup.popUpAjudaExterna(actions.getFrom()) == 1) {
				actions.setTo(playerName);
				actions.setBlock(true);
			}
		}

		/* Envia resposta ao servidor. */
		flushObject();
	}

	private void coup() {
		popup.popUpGolpe(actions.getFrom());
	}

	private void steal() throws IOException {
		int action;

		if (actions.isBlock()) {
			action = popup.popUpBloqueioExtorcao(actions.getTo(), actions.getCards()[0]);

			if (action == 1)
				actions.setContest(true);

		} else {
			action = popup.popUpExtorcao(actions.getFrom());

			switch (action) {
			/* Bloqueou com Embaixador. */
			case 1:
				actions.setCards(new String[] { "Embaixador" });

				/* Bloqueou com Capitão. */
			case 2:
				if (actions.getCards() == null)
					actions.setCards(new String[] { "Capitao" });
				actions.setBlock(true);
				break;

			/* Contestou. */
			case 3:
				actions.setContest(true);
				break;
			}
		}

		flushObject();
	}

	private void loadPlayerActions() throws IOException {
		ArrayList<String> loadActions = new ArrayList<String>();
		ArrayList<String> playersName = new ArrayList<String>();

		int money = actions.getPlayers().get(this.playerName).getMoney();

		/* Verifica quais ações serão apresentadas ao jogador. */
		for (ssc0103.coup.game.Player player : actions.getPlayers().values()) {
			if (!player.getName().equals(this.playerName)) {
				/* Verifica se algum jogador pode ser extorquido. */
				if (player.getMoney() > 0) {
					/* Obtém o nome desse jogador. */
					playersName.add(player.getName());
					if (!loadActions.contains("Extorquir"))
						loadActions.add("Extorquir");
				}
			}
		}

		if (money >= 3) {
			loadActions.add("Assassinar");
			if (money >= 7) {
				loadActions.add("Golpe");
			}
		}

		/* Recebe a ação do jogador. */
		actions.setId(popup.popUpAcoes(loadActions, playerName));

		switch (actions.getId()) {
		case Actions.COUP:
		case Actions.ASSASSINATE:
			actions.setTo(popup
					.popUpJogadores(actions.getPlayers().keySet().toArray(new String[actions.getPlayers().size()])));
			break;

		case Actions.STEAL:
			actions.setTo(popup.popUpJogadores(playersName.toArray(new String[playersName.size()])));
			break;
		}

		/* Envia a ação do jogador ao servidor. */
		flushObject();
	}

	/**
	 * Envia ao servidor a resposta do jogador sobre estar sendo assassinado.
	 * 
	 * @param output
	 * @param actions
	 * @return
	 * @throws IOException
	 */
	private void assassinate() throws IOException {
		if (actions.isBlock()) {
			if (popup.popUpCondessa(actions.getTo()) == 1)
				actions.setContest(true);

		} else {
			switch (popup.popUpAssassino(actions.getFrom())) {
			/* Bloquear. */
			case 1:
				actions.setBlock(true);
				break;

			/* Contestar. */
			case 2:
				actions.setContest(true);
				break;
			}
		}

		/* Envia objeto ao servidor. */
		flushObject();
	}

	/**
	 * Carrega a interface gráfica do jogador.
	 * 
	 * @param output
	 * @throws IOException
	 */
	private void loadInterface() throws IOException {
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
	private void getMessage() throws IOException {
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
	private void getName() throws IOException {
		String name = "Informe o seu nickname no jogo:";
		name = JOptionPane.showInputDialog(name);
		actions.setFrom(name);
		flushObject();
	}

	public String[] popUp(Deck hand) {
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

	// ############# MÉTODOS AUXILIARES DO CLIENTE #############//
	/**
	 * Envia o objeto Actions para o servidor.
	 * 
	 * @throws IOException
	 */
	private void flushObject() throws IOException {
		/* Canal de comunicação do cliente para o servidor. */
		output.reset();
		/* Escreve o objeto no canal. */
		output.writeObject(actions);
		/* Envia o objeto para o servidor. */
		output.flush();
	}

	/**
	 * Retorna o objeto Actions do servidor.
	 * 
	 * @param player
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Actions getObject() throws IOException, ClassNotFoundException {
		/* Canal de comunicação do servidor para o cliente. */
		/* Retorna o objeto enviado pelo servidor. */
		return (Actions) input.readObject();
	}
}
