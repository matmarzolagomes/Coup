package ssc0103.coup.lan;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import ssc0103.coup.exception.GUIException;
import ssc0103.coup.exception.LANExcpetion;
import ssc0103.coup.game.Deck;
import ssc0103.coup.gui.ConectGUI;
import ssc0103.coup.gui.CoupGUI;
import ssc0103.coup.gui.MenuGUI;
import ssc0103.coup.gui.PopUp;
import ssc0103.coup.gui.PopUpAjuda;
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
	private Socket player = null;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Actions actions;
	private PopUpPlayer popup;
	private JFrame jframe;
	private CoupGUI coupgui;
	private MenuGUI menu;
	private ConectGUI conectGUI;
	private static boolean readConnectGUI = false;

	public static boolean readConnectGUI() {
		return readConnectGUI;
	}

	public static void setReadConnectGUI(boolean button) {
		Player.readConnectGUI = button;
	}

	/**
	 * Construtor do Player, responsável por conectar o jogador com o servidor.
	 */
	public Player() {
		/* Tela de login. */
		jframe = new JFrame("Coup Login");
		conectGUI = new ConectGUI();
		conectGUI.frameAdd(jframe, conectGUI);

		jframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				PopUpPlayer quit = new PopUpPlayer();
				if(quit.popUpQuit() == 1) {
					if(player != null && player.isConnected()) closeConnection();
			        jframe.dispose();
			        System.exit(0);
				}
		    }
		});
		
		/* Conecta ao servidor do jogo. */
		connectHost();

		/* Inicializa o objeto de PopUps. */
		popup = new PopUpPlayer();
	}
	
	/**
	 * Realiza uma conexão com o servidor do jogo.
	 */
	private void connectHost() {
		conectGUI.activeButton1();
		String msg; // = "Informe o IP de conexão com o servidor do
					// jogo:\nDefault: 127.0.0.1";
		try {
			// this.host = JOptionPane.showInputDialog(msg);

			while (Thread.currentThread().isAlive() && !Player.readConnectGUI)
				continue;
			host = conectGUI.getIpAdress();
			msg = conectGUI.getPorta();

			if (host == null)
				System.exit(0);
			else if (host.isEmpty())
				host = "127.0.0.1";

			// msg = "Informe a porta de conexão com o servidor do jogo:";
			// msg = JOptionPane.showInputDialog(msg);

			// if (msg == null)
			// System.exit(0);

			this.port = Integer.parseInt(msg);

			/* Obtém o Socket de comunicação entre o servidor e o jogador. */
			this.player = new Socket(this.host, this.port);

			/* Fluxo de dados do jogador para o servidor. */
			this.input = new ObjectInputStream(player.getInputStream());

			/* Fluxo de dados do servidor para o jogador. */
			this.output = new ObjectOutputStream(player.getOutputStream());

			conectGUI.setConected(true);
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
			while (true) {
				/* Recebe um objeto do servidor. */
				actions = getObject();

				switch (actions.getId()) {
				case Actions.LEFT:
					left();
					break;

				case Actions.WINNER:
					winner();
					break;

				case Actions.GET_NAME:
					getName();
					break;
					
				case Actions.ON_HOLD:
					onHold();
					break;

				case Actions.SERVER_MESSAGE:
					getMessage();
					break;

				case Actions.LOAD_INTERFACE:
					// CARREGA PELA PRIMEIRA VEZ A INTERFACE GRÁFICA
					System.out.println(playerName);
					jframe.setVisible(false);
					loadInterface();
					break;

				case Actions.UPDATE_ALL_INTERFACE:
				case Actions.UPDATE_INTERFACE:
					// ATUALIZA A INTERFACE GRÀFICA
					if (actions.getPlayers().containsKey(playerName))
						updateInterface();
					break;

				case Actions.GET_INPUT:
					getInput();
					break;

				case Actions.LOAD_PLAYER_ACTIONS:
					loadPlayerActions();
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
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (LANExcpetion e) {
			System.out.println(e.getMessage());
		} finally {
			closeConnection();
			System.exit(0);
		}
	}

	private void left() throws LANExcpetion {
		jframe.setVisible(false);
		popup.popUpDerrota();
		throw new LANExcpetion("Derrota do Jogador " + playerName + ".");
	}

	private void winner() throws LANExcpetion {
		jframe.setVisible(false);
		popup.popUpVitoria();
		throw new LANExcpetion("Vitória do Jogador " + playerName + ".");
	}

	/**
	 * Envia o nome do jogador ao servidor.
	 * 
	 * @param output
	 * @param actions
	 * @throws IOException
	 * @throws LANExcpetion
	 */
	private void getName() throws IOException, LANExcpetion {
		// this.playerName = "Informe o seu nickname no jogo:";
		// this.playerName = JOptionPane.showInputDialog(this.playerName);
		conectGUI.activeButton2();
		while (Thread.currentThread().isAlive() && !Player.readConnectGUI)
			continue;
		this.playerName = conectGUI.getPlayerName();
		actions.setFrom(this.playerName);
		flushObject();
	}
	
	private void onHold() {
		popup.PopUpHistoria();
		new PopUpAjuda().popUpGeral();		
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
	}

	/**
	 * Carrega a interface gráfica do jogador.
	 * 
	 * @param output
	 * @throws IOException
	 */
	private void loadInterface() {
		jframe = new JFrame(playerName);
		jframe.setExtendedState(JFrame.NORMAL);

		coupgui = new CoupGUI(playerName, actions.getPlayers());
		menu = new MenuGUI();

		jframe.setJMenuBar(menu);

		jframe.add(coupgui);
		coupgui.updateAll(actions);

		jframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		jframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				PopUpPlayer quit = new PopUpPlayer();
				if(quit.popUpQuit() == 1) {
					if(player != null && player.isConnected()) closeConnection();
			        jframe.dispose();
			        System.exit(0);
				}
		    }
		});
		
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);
	}

	/**
	 * Atualiza a interface gráfica do jogador.
	 */
	private void updateInterface() {
		coupgui.updateAll(actions);
	}

	/**
	 * Retira cartas da mão do jogador.
	 * 
	 * @throws IOException
	 * @throws LANExcpetion
	 */
	private void getInput() throws IOException, LANExcpetion {
		String[] cards;
		/* Solicitar ao jogador para remover 1 ou 2 cartas. */
		cards = popUp(actions.getPlayer().getHand(), actions.getPlayer().getHand().size() > 2 ? 2 : 1);
		actions.setCards(cards);

		/* Envia as cartas a serem removidas para o servidor. */
		flushObject();
	}

	/**
	 * Carrega todas as ações que o jogador pode realizar.
	 * 
	 * @throws IOException
	 * @throws LANExcpetion
	 */
	private void loadPlayerActions() throws IOException, LANExcpetion {
		ArrayList<String> loadActions = new ArrayList<String>();
		ArrayList<String> playersName = new ArrayList<String>();

		coupgui.startCount();
		new Thread(() -> {
			while (coupgui.isConnected() && coupgui.isTimeRunning())
				continue;
			if (!coupgui.isConnected()) {
				try {
					left();
				} catch (LANExcpetion e) {
					e.printStackTrace();
				}
				// RODRIGO, MEXE AQUI
			}
				
		}).start();

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
		case Actions.STEAL:
			actions.setTo(popup.popUpJogadores(new ArrayList<String>(actions.getPlayers().keySet()), this.playerName));
			break;
		}

		coupgui.stopCount();

		/* Envia a ação do jogador ao servidor. */
		flushObject();
	}

	// ############# AÇÕES DE RESPOSTA DO JOGADOR ############# //

	private void foreign() throws IOException, LANExcpetion {
		/* Verifica se jogador bloqueou ação. */
		if (actions.isBlock()) {
			/* Verifica se jogador deseja contestar. */
			if (popup.popUpBloqueioAjuda(actions.getTo()) == 1)
				actions.setContest(true);
		} else {
			/* Verifica se o jogador deseja bloquear. */
			if (popup.popUpAjudaExterna(actions.getFrom()) == 1) {
				actions.setTo(playerName);
				actions.setCards(new String[] { "Duque" });
				actions.setBlock(true);
			}
		}

		/* Envia resposta ao servidor. */
		flushObject();
	}

	private void coup() {
		popup.popUpGolpe(actions.getFrom());
	}

	private void taxes() throws IOException, LANExcpetion {
		if (popup.popUpTaxas(actions.getFrom()) == 1) {
			actions.setTo(playerName);
			actions.setContest(true);
		}

		/* Envia objeto de resposta ao servidor. */
		flushObject();
	}

	/**
	 * Envia ao servidor a resposta do jogador sobre estar sendo assassinado.
	 * 
	 * @param output
	 * @param actions
	 * @return
	 * @throws IOException
	 * @throws LANExcpetion
	 */
	private void assassinate() throws IOException, LANExcpetion {
		if (actions.isBlock()) {
			if (popup.popUpCondessa(actions.getTo()) == 1)
				actions.setContest(true);

		} else {
			switch (popup.popUpAssassino(actions.getFrom())) {
			/* Bloquear. */
			case 1:
				actions.setCards(new String[] { "Condessa" });
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

	private void steal() throws IOException, LANExcpetion {
		if (actions.isBlock()) {
			if (popup.popUpBloqueioExtorcao(actions.getTo(), actions.getCards()[0]) == 1)
				actions.setContest(true);

		} else {
			switch (popup.popUpExtorcao(actions.getFrom())) {
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

	private void swap() throws IOException, LANExcpetion {
		if (popup.popUpTroca(actions.getFrom()) == 1) {
			actions.setTo(playerName);
			actions.setContest(true);
		}

		/* Envia objeto de resposta ao servidor. */
		flushObject();
	}

	// ############# MÉTODOS AUXILIARES DO CLIENTE ############# //

	/**
	 * Envia o objeto Actions para o servidor.
	 * 
	 * @throws IOException
	 * @throws LANExcpetion
	 */
	private void flushObject() throws IOException, LANExcpetion {
		if (player.isInputShutdown() || player.isOutputShutdown() || player.isClosed())
			throw new LANExcpetion("Conexão finalizada.");
		/* Escreve o objeto no fluxo de dados. */
		output.writeObject(actions);
		/* Envia o objeto para o servidor. */
		output.flush();
		/* Limpa o fluxo de dados. */
		output.reset();
	}

	/**
	 * Retorna o objeto Actions do servidor.
	 * 
	 * @param player
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws LANExcpetion
	 */
	private Actions getObject() throws IOException, ClassNotFoundException, LANExcpetion {
		if (player.isInputShutdown() || player.isOutputShutdown() || player.isClosed())
			throw new LANExcpetion("Conexão finalizada.");
		/* Retorna o objeto enviado pelo servidor. */
		return (Actions) input.readObject();
	}

	// ############# MÈTODOS DA INTERFACE GRÀFICA ############# //

	/**
	 * Remove cartas da mão do jogador.
	 * 
	 * @param hand
	 * @param numCards
	 *            Número de cartas que será removido.
	 * @return
	 */
	public String[] popUp(Deck hand, int numCards) {
		String[] ret = null;

		try {
			boolean cont = true;
			while (cont) {
				PopUp pop = new PopUp(hand, numCards);
				ret = pop.showPopUp().toArray(new String[numCards]);
				if (ret.length == numCards)
					cont = false;
			}
		} catch (GUIException ex) {
			System.out.println(ex.getMessage());
			System.exit(-1);
		}

		return ret;
	}

	private void closeConnection() {
		try {
			if (player != null && !player.isClosed()) {
				if (output != null && !player.isOutputShutdown())
					output.close();
				if (input != null && !player.isInputShutdown())
					input.close();
				player.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Player().execute();
	}
}
