package ssc0103.coup.gui;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Classe para exibir e controlar a parte de ajuda do jogo
 * 
 * @author Victor Henrique de Souza Rodrigues (9791027)
 *
 */
public class PopUpAjuda {

	private ImageIcon contrarias, gerais, acao, contestacoes;
	private ImageIcon influencia, jogo, objetivo, logo, exemplo, exemplo2, personagens;
	private String opcoes[] = new String[] { "Ações Gerais", "Ações Contrárias", "Ações de Personagem" };
	private String opcoes4[] = new String[] { "O Jogo", "Objetivo", "Influência", "Contestações", "Ações", "Exemplo" };
	private String opcoes2[] = new String[] { "Próxima Página", "OK" };
	private String opcoes3[] = new String[] { "Página Anterior", "OK" };
	private int ret = 0, option2 = 0, option = 0;

	/**
	 * Construtor que abre e redimensiona as imagens necessárias
	 */
	public PopUpAjuda() {

		contrarias = new ImageIcon("CoupAjuda/Acoes_contrarias.png");
		gerais = new ImageIcon("CoupAjuda/Acoes_gerais.png");
		acao = new ImageIcon("CoupAjuda/Acoes.png");
		contestacoes = new ImageIcon("CoupAjuda/Contest.png");
		influencia = new ImageIcon("CoupAjuda/Influencia.png");
		jogo = new ImageIcon("CoupAjuda/Jogo.png");
		objetivo = new ImageIcon("CoupAjuda/Objetivo.png");
		logo = new ImageIcon("CoupAjuda/coup_logo.png");
		exemplo = new ImageIcon("CoupAjuda/example.png");
		exemplo2 = new ImageIcon("CoupAjuda/example2.png");
		personagens = new ImageIcon("CoupAjuda/Personagens.png");

		logo.setImage(logo.getImage().getScaledInstance(650, 200, 100));
	}

	/**
	 * Método que controla as janelas de ajuda
	 */
	public void popUpGeral() {

		while (option2 != -1) {
			option2 = JOptionPane.showOptionDialog(null, null, "Ajuda", JOptionPane.DEFAULT_OPTION,
					JOptionPane.DEFAULT_OPTION, logo, opcoes4, opcoes4[0]);
			if (option2 == 0)
				popUpJogo();
			else if (option2 == 1)
				popUpObjetivo();
			else if (option2 == 2)
				popUpInfluencia();
			else if (option2 == 3)
				popUpContestacoes();
			else if (option2 == 4)
				popUpAcoes();
			else if (option2 == 5)
				popUpExemplo();
		}
	}

	/**
	 * Método que exibe uma janela com um exemplo de partida.
	 */
	private void popUpExemplo() {
		option = 0;

		while (option != -1 && option != 1) {
			option = JOptionPane.showOptionDialog(null, null, "Exemplo de Jogo", JOptionPane.DEFAULT_OPTION,
					JOptionPane.DEFAULT_OPTION, exemplo, opcoes2, opcoes2[0]);
			if (option != 1 && option != -1)
				option = JOptionPane.showOptionDialog(null, null, "Exemplo de Jogo", JOptionPane.DEFAULT_OPTION,
						JOptionPane.DEFAULT_OPTION, exemplo2, opcoes3, opcoes3[0]);
		}
	}

	/**
	 * Método que exibe uma janela explicando o jogo
	 */
	private void popUpJogo() {
		JOptionPane.showMessageDialog(null, null, "Jogo", JOptionPane.DEFAULT_OPTION, jogo);
	}

	/**
	 * Método que exibe uma janela mostrando o objetivo do jogo.
	 */
	private void popUpObjetivo() {
		JOptionPane.showMessageDialog(null, null, "Objetivo", JOptionPane.DEFAULT_OPTION, objetivo);
	}

	/**
	 * Método que exibe uma janela explicando as influências.
	 */
	private void popUpInfluencia() {
		JOptionPane.showMessageDialog(null, null, "Influência", JOptionPane.DEFAULT_OPTION, influencia);
	}

	/**
	 * Método que exibe uma janela explicando as contestações.
	 */
	private void popUpContestacoes() {
		JOptionPane.showMessageDialog(null, null, "Contestações", JOptionPane.DEFAULT_OPTION, contestacoes);
	}

	/**
	 * Método que exibe uma janela com os tipos de ações possíveis.
	 */
	private void popUpAcoes() {

		ret = 0;
		while (ret != -1) {
			ret = JOptionPane.showOptionDialog(null, null, "Ações", JOptionPane.DEFAULT_OPTION,
					JOptionPane.DEFAULT_OPTION, acao, opcoes, opcoes[0]);

			if (ret == 0)
				JOptionPane.showMessageDialog(null, null, "Ações Gerais", JOptionPane.DEFAULT_OPTION, gerais);

			else if (ret == 1)
				JOptionPane.showMessageDialog(null, null, "Ações Contrarias", JOptionPane.DEFAULT_OPTION, contrarias);

			else if (ret == 2)
				JOptionPane.showMessageDialog(null, null, "Ações de Personagem", JOptionPane.DEFAULT_OPTION,
						personagens);
		}
	}

	public static void main(String[] args) {
	}
}
