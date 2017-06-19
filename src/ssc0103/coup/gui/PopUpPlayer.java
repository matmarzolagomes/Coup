package ssc0103.coup.gui;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import ssc0103.coup.lan.Actions;

/**
 * Classe que exibe janelas com as ações do jogo na tela do jogador.
 * 
 * @author Victor Henrique de Souza Rodrigues (9791027)
 *
 */
public class PopUpPlayer {

	private int ret = -1;
	private ImageIcon duque, assassino, embaixador, capitao, condessa, coins, golpe, coup, icon, derrota, vitoria,
			historia, quit;
	private String opcoes1[] = new String[] { "Permitir", "Bloquear", "Contestar" };
	private String opcoes2[] = new String[] { "Permitir", "Contestar" };
	private String opcoes3[] = new String[] { "Permitir", "Bloquear" };
	private String opcoes4[] = new String[] { "Permitir", "Bloquear (Embaixador)", "Bloquear (Capitão)", "Contestar" };
	private String opcoes5[] = new String[] { "Continuar no jogo", "Sair do jogo" };
	private ArrayList<String> acoes;

	/**
	 * Construtor que abre e redimensionada todas as imagens que serão usadas
	 * nas janelas.
	 */
	public PopUpPlayer() {

		vitoria = new ImageIcon("images/historia.png");
		coins = new ImageIcon("images/coins.png");
		duque = new ImageIcon("images/Duque.png");
		assassino = new ImageIcon("images/Assassino.png");
		embaixador = new ImageIcon("images/Embaixador.png");
		capitao = new ImageIcon("images/Capitao.png");
		condessa = new ImageIcon("images/Condessa.png");
		golpe = new ImageIcon("images/golpe.png");
		coup = new ImageIcon("images/action.png");
		icon = new ImageIcon("images/icon.png");
		derrota = new ImageIcon("images/derrota.jpg");
		vitoria = new ImageIcon("images/vitoria.jpg");
		historia = new ImageIcon("images/historia.png");
		quit = new ImageIcon("images/quit.gif");

		golpe.setImage(golpe.getImage().getScaledInstance(150, 180, 100));
		coins.setImage(coins.getImage().getScaledInstance(128, 128, 100));
		duque.setImage(duque.getImage().getScaledInstance(150, 250, 100));
		assassino.setImage(assassino.getImage().getScaledInstance(150, 250, 100));
		embaixador.setImage(embaixador.getImage().getScaledInstance(150, 250, 100));
		capitao.setImage(capitao.getImage().getScaledInstance(150, 250, 100));
		condessa.setImage(condessa.getImage().getScaledInstance(150, 250, 100));
		coup.setImage(coup.getImage().getScaledInstance(182, 207, 100));
		icon.setImage(icon.getImage().getScaledInstance(128, 128, 100));
		derrota.setImage(derrota.getImage().getScaledInstance(640, 360, 100));
		vitoria.setImage(vitoria.getImage().getScaledInstance(640, 360, 100));

	}

	/**
	 * Método que exibe uma janela para um jogador se alguém bloquear a ajuda
	 * externa.
	 * 
	 * @param nome
	 *            do jogador que bloqueou.
	 * @return 0 se o jogador aceitou ou 1 se contestou o bloqueio.
	 */
	public int popUpBloqueioAjuda(String nome) {

		ret = -1;
		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null, nome + " bloqueou o pedido de ajuda externa!", "Bloqueio",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, duque, opcoes2, opcoes2[0]);

		return ret;
	}

	/**
	 * Método que exibe uma janela com a história base do jogo.
	 */
	public void PopUpHistoria() {
		JOptionPane.showMessageDialog(null, null, "História do Jogo", JOptionPane.DEFAULT_OPTION, historia);
	}

	/**
	 * Método que exibe uma janela de confirmação caso o jogador queira deixar o
	 * jogo.
	 * 
	 * @return 0 se ele quer continuar no jogo, 1 se ele quer sair
	 */
	public int popUpQuit() {

		ret = -1;
		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null, null, "Sair", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE, quit, opcoes5, opcoes5[0]);

		return ret;

	}

	/**
	 * Método que exibe uma janela mostrando que o jogador foi eliminado do
	 * jogo.
	 */
	public void popUpDerrota() {
		JOptionPane.showMessageDialog(null, null, "Derrota", JOptionPane.DEFAULT_OPTION, derrota);
	}

	/**
	 * Método que exibe uma janela mostrando que o jogador venceu o jogo.
	 */
	public void popUpVitoria() {
		JOptionPane.showMessageDialog(null, null, "Vitória", JOptionPane.DEFAULT_OPTION, vitoria);

	}

	/**
	 * Método que exibe para um jogador que outro jogador está tentando
	 * assassinar ele.
	 * 
	 * @param nome
	 *            do jogador que está tentando assassinar.
	 * @return 0 se ele permite, 1 se bloqueia, 2 se contesta o assassinato.
	 */
	public int popUpAssassino(String nome) {

		ret = -1;
		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null, nome + " está tentando te assassinar!", "Assassinato",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, assassino, opcoes1, opcoes1[0]);

		return ret;
	}

	/**
	 * Método que exibe uma janela para os jogadores, dizendo que um dos
	 * jogadores está reinvindicando taxas.
	 * 
	 * @param nome
	 *            do jogador que está reinvindicando taxas.
	 * @return 0 se ele permite, 1 se ele contesta.
	 */
	public int popUpTaxas(String nome) {

		ret = -1;
		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null, nome + " está reinvindicando taxas!", "Taxas",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, duque, opcoes2, opcoes2[0]);

		return ret;

	}

	/**
	 * Método que exibe para um jogador uma janela na qual um jogador bloqueou o
	 * assassinato.
	 * 
	 * @param nome
	 *            do jogador que bloqueou o assassinato.
	 * @return 0 se ele aceita o bloqueio, 1 se ele contesta.
	 */
	public int popUpCondessa(String nome) {

		ret = -1;
		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null, nome + " bloqueou o seu assassinato!", "Bloqueio",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, condessa, opcoes2, opcoes2[0]);

		return ret;
	}

	/**
	 * Método que exibe uma janela dizendo que um jogador está pedindo uma
	 * troca.
	 * 
	 * @param nome
	 *            do jogador que está reinvindicando a troca.
	 * @return 0 se ele permite, 1 se ele contesta.
	 */
	public int popUpTroca(String nome) {

		ret = -1;
		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null, nome + " está reinvindicando uma troca de cartas!", "Troca",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, embaixador, opcoes2, opcoes2[0]);

		return ret;
	}

	/**
	 * Método que exibe uma janela dizendo que o jogador está sendo extorquido.
	 * 
	 * @param nome
	 *            do jogador que está extorquindo.
	 * @return 0 se ele perimite, 1 se ele bloqueia com embaixador, 2 se ele
	 *         bloqueia com capitão, 3 se ele contesta.
	 */
	public int popUpExtorcao(String nome) {

		ret = -1;
		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null,
					"                              " + nome + " está tentando te extorquir!", "Extorsão",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, capitao, opcoes4, opcoes4[0]);

		return ret;
	}

	/**
	 * Método que exibe uma janela na qual um jogador está pedindo ajuda
	 * externa.
	 * 
	 * @param nome
	 *            do jogador que está pedindo ajuda externa.
	 * @return 0 se ele perimite, 1 se ele bloqueia.
	 */
	public int popUpAjudaExterna(String nome) {

		ret = -1;
		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null, nome + " está pedindo ajuda externa!", "Ajuda Externa",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, coins, opcoes3, opcoes3[0]);

		return ret;
	}

	/**
	 * Método que exibe uma janela na qual um jogador bloqueou a extorção.
	 * 
	 * @param nome
	 *            do jogador que bloqueou.
	 * @param carta
	 *            com a qual o jogador bloqueou.
	 * @return 0 se ele aceita, 1 se ele contesta o bloqueio.
	 */
	public int popUpBloqueioExtorcao(String nome, String carta) {

		ret = -1;

		if (carta.equals("Capitao"))
			while (ret == -1)
				ret = JOptionPane.showOptionDialog(null, nome + " bloqueou a sua extorsão!", "Bloqueio",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, capitao, opcoes2, opcoes2[0]);

		else if (carta.equals("Embaixador"))
			while (ret == -1)
				ret = JOptionPane.showOptionDialog(null, nome + " bloqueou a sua extorsão!", "Bloqueio",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, embaixador, opcoes2, opcoes2[0]);

		return ret;
	}

	/**
	 * Método que exibe uma janela na qual um jogador levou um golpe de estado.
	 * 
	 * @param nome
	 *            do jogador que aplicou o golpe.
	 */
	public void popUpGolpe(String nome) {
		JOptionPane.showOptionDialog(null, nome + " aplicou um golpe de estado em você!", "Golpe de Estado",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, golpe, null, null);
	}

	/**
	 * Método que pede para um jogador realizar uma ação.
	 * 
	 * @param acoes2
	 *            (ações que estão disponíveis nessa rodada para ele).
	 * @param nome
	 *            do jogador.
	 * @return qual ação foi realizada.
	 */
	public int popUpAcoes(ArrayList<String> acoes2, String nome) {

		ret = -1;
		acoes = new ArrayList<String>();
		acoes.add("Renda");
		acoes.add("Ajuda");
		acoes.add("Taxas");
		acoes.add("Troca");

		for (String acao : acoes2)
			acoes.add(acao);

		while (ret == -1) {
			ret = JOptionPane.showOptionDialog(null, "          " + nome + " é a sua vez de jogar, escolha a sua ação!",
					"Sua Vez", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, coup, acoes.toArray(),
					acoes.get(0));
			if (ret == -1) {
				ret = popUpQuit();
				if (ret == 0)
					ret = -1;
				else if (ret == 1)
					return Actions.LEFT;
			}
		}

		if (ret == 0)
			return Actions.INCOME;
		else if (ret == 1)
			return Actions.FOREIGN;
		else if (ret == 2)
			return Actions.TAXES;
		else if (ret == 3)
			return Actions.SWAP;

		else {
			if (acoes.get(ret).equals("Extorquir"))
				return Actions.STEAL;
			else if (acoes.get(ret).equals("Assassinar"))
				return Actions.ASSASSINATE;
			else if (acoes.get(ret).equals("Golpe"))
				return Actions.COUP;
		}

		return ret;
	}

	/**
	 * Método que exibe uma janela para um jogador escolher outro jogador para
	 * sofrer a ação.
	 * 
	 * @param jogadores
	 *            que estão na partida.
	 * @param jogador
	 *            que vai fazer a ação.
	 * @return qual jogador ele escolheu.
	 */
	public String popUpJogadores(ArrayList<String> jogadores, String jogador) {

		ArrayList<String> jogadores2 = new ArrayList<String>();
		String jogador_alvo = null;

		for (String iterator : jogadores)
			if (!iterator.equals(jogador))
				jogadores2.add(iterator);

		jogador_alvo = (String) JOptionPane.showInputDialog(null, "Escolha um Jogador\n \n", "Jogador",
				JOptionPane.INFORMATION_MESSAGE, icon, jogadores2.toArray(), jogadores2.get(0));

		while (jogador_alvo == null)
			jogador_alvo = (String) JOptionPane.showInputDialog(null, "Escolha um Jogador\n \n", "Jogador",
					JOptionPane.INFORMATION_MESSAGE, icon, jogadores2.toArray(), jogadores2.get(0));

		return jogador_alvo;
	}

	/**
	 * Método main apenas para testes na classe, sem função no programa final
	 * 
	 * @param args
	 *            null
	 */
	public static void main(String[] args) {

		ArrayList<String> b = new ArrayList<String>();
		ArrayList<String> a = new ArrayList<String>();

		a.add("Victor");
		a.add("Tiroliro");
		a.add("Rodorigo");
		a.add("Bruno");

		b.add("Extorquir");
		b.add("Assassinar");
		b.add("Golpe");

		PopUpPlayer t = new PopUpPlayer();
/*
		System.out.println(t.popUpAssassino("Victor"));
		System.out.println(t.popUpTaxas("Victor"));
		System.out.println(t.popUpCondessa("Victor"));
		System.out.println(t.popUpTroca("Victor"));
		System.out.println(t.popUpExtorcao("Victor"));
		System.out.println(t.popUpAjudaExterna("Victor"));
		System.out.println(t.popUpBloqueioExtorcao("victor", "Capitao"));
		System.out.println(t.popUpBloqueioExtorcao("victor", "Embaixador"));
		t.popUpGolpe("Victor");
		System.out.println(t.popUpAcoes(b, "Victor"));
		System.out.println(t.popUpJogadores(a, "Victor"));
		t.popUpDerrota();
		t.popUpVitoria();
		t.PopUpHistoria();
		t.popUpBloqueioAjuda("Victor");
		t.popUpQuit();
		*/
		System.out.println(t.popUpAcoes(b, "Victor"));
	}
}
