package ssc0103.coup.gui;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import ssc0103.coup.lan.Actions;

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

	public int popUpBloqueioAjuda(String nome) {

		ret = -1;
		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null, nome + " bloqueou o pedido de ajuda externa!", "Bloqueio",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, duque, opcoes2, opcoes2[0]);

		return ret;
	}

	public void PopUpHistoria() {
		JOptionPane.showMessageDialog(null, null, "História do Jogo", JOptionPane.DEFAULT_OPTION, historia);
	}

	public int popUpQuit() {

		ret = -1;
		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null, null, "Sair", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE, quit, opcoes5, opcoes5[0]);

		return ret;

	}

	public void popUpDerrota() {
		JOptionPane.showMessageDialog(null, null, "Derrota", JOptionPane.DEFAULT_OPTION, derrota);
	}

	public void popUpVitoria() {
		JOptionPane.showMessageDialog(null, null, "Vitória", JOptionPane.DEFAULT_OPTION, vitoria);

	}

	public int popUpAssassino(String nome) {

		ret = -1;
		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null, nome + " está tentando te assassinar!", "Assassinato",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, assassino, opcoes1, opcoes1[0]);

		return ret;
	}

	public int popUpTaxas(String nome) {

		ret = -1;
		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null, nome + " está reinvindicando taxas!", "Taxas",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, duque, opcoes2, opcoes2[0]);

		return ret;

	}

	public int popUpCondessa(String nome) {

		ret = -1;
		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null, nome + " bloqueou o seu assassinato!", "Bloqueio",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, condessa, opcoes2, opcoes2[0]);

		return ret;
	}

	public int popUpTroca(String nome) {

		ret = -1;
		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null, nome + " está reinvindicando uma troca de cartas!", "Troca",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, embaixador, opcoes2, opcoes2[0]);

		return ret;
	}

	public int popUpExtorcao(String nome) {

		ret = -1;
		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null,
					"                              " + nome + " está tentando te extorquir!", "Extorsão",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, capitao, opcoes4, opcoes4[0]);

		return ret;
	}

	public int popUpAjudaExterna(String nome) {

		ret = -1;
		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null, nome + " está pedindo ajuda externa!", "Ajuda Externa",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, coins, opcoes3, opcoes3[0]);

		return ret;
	}

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

	public void popUpGolpe(String nome) {
		JOptionPane.showOptionDialog(null, nome + " aplicou um golpe de estado em você!", "Golpe de Estado",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, golpe, null, null);
	}

	public int popUpAcoes(ArrayList<String> acoes2, String nome) {

		ret = -1;
		acoes = new ArrayList<String>();
		acoes.add("Renda");
		acoes.add("Ajuda");
		acoes.add("Taxas");
		acoes.add("Troca");

		for (String acao : acoes2)
			acoes.add(acao);

		while (ret == -1)
			ret = JOptionPane.showOptionDialog(null, "          " + nome + " é a sua vez de jogar, escolha a sua ação!",
					"Sua Vez", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, coup, acoes.toArray(),
					acoes.get(0));

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
	}
}
