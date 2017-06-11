package ssc0103.coup.gui;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class PopUpAjuda {

	private ImageIcon contrarias, gerais, personagem, acao, assassino, capitao, condessa, contestacoes, duque, duque2;
	private ImageIcon embaixador_capitao, embaixador, influencia, jogo, objetivo, logo, exemplo;
	private String opcoes[] = new String[] { "Ações Gerais", "Ações Contrárias", "Ações de Personagem" };
	private String opcoes2[] = new String[] {"Duque", "Condessa", "Embaixador ou Capitão"};
	private String opcoes3[] = new String[] {"Duque", "Capitão", "Assassino", "Embaixador"};
	private String opcoes4[] = new String[] {"O Jogo", "Objetivo", "Influência", "Contestações", "Ações", "Exemplo"};
	private int ret = 0, option = 0, option2 = 0;

	public PopUpAjuda() {

		contrarias = new ImageIcon("CoupAjuda/Acoes_contrarias.png");
		gerais = new ImageIcon("CoupAjuda/Acoes_gerais.png");
		personagem = new ImageIcon("CoupAjuda/Acoes_Personagem.png");
		acao = new ImageIcon("CoupAjuda/Acoes.png");
		assassino = new ImageIcon("CoupAjuda/Assassino.png");
		capitao = new ImageIcon("CoupAjuda/Capitao.png");
		condessa = new ImageIcon("CoupAjuda/Condessa.png");
		contestacoes = new ImageIcon("CoupAjuda/Contest.png");
		duque = new ImageIcon("CoupAjuda/Duque.png");
		duque2 = new ImageIcon("CoupAjuda/Duque2.png");
		embaixador_capitao = new ImageIcon("CoupAjuda/Embaixador_capitao.png");
		embaixador = new ImageIcon("CoupAjuda/Embaixador.png");
		influencia = new ImageIcon("CoupAjuda/Influencia.png");
		jogo = new ImageIcon("CoupAjuda/Jogo.png");
		objetivo = new ImageIcon("CoupAjuda/Objetivo.png");
		logo = new ImageIcon("CoupAjuda/coup_logo.png");
		exemplo = new ImageIcon("CoupAjuda/Exemplo.png");
		
		logo.setImage(logo.getImage().getScaledInstance(650, 200, 100));
		
		
	}
	
	public void popUpGeral(){
		
		while(option2 != -1){
			option2 = JOptionPane.showOptionDialog(null, null, "Ajuda", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, logo, opcoes4, opcoes4[0]);
			if(option2 == 0) popUpJogo();
			else if(option2 == 1) popUpObjetivo();
			else if(option2 == 2) popUpInfluencia();
			else if(option2 == 3) popUpContestacoes();
			else if(option2 == 4) popUpAcoes();
			else if(option2 == 5) popUpExemplo();
		}
	}
	
	public void popUpExemplo(){
		JOptionPane.showMessageDialog(null, null, "Exemplo de Jogo", JOptionPane.DEFAULT_OPTION, exemplo);
	}
	
	public void popUpJogo() {
		JOptionPane.showMessageDialog(null, null, "Jogo", JOptionPane.DEFAULT_OPTION, jogo);
	}

	public void popUpObjetivo() {
		JOptionPane.showMessageDialog(null, null, "Objetivo", JOptionPane.DEFAULT_OPTION, objetivo);
	}

	public void popUpInfluencia() {
		JOptionPane.showMessageDialog(null, null, "Influência", JOptionPane.DEFAULT_OPTION, influencia);
	}
	
	public void popUpContestacoes(){
		JOptionPane.showMessageDialog(null, null, "Contestações", JOptionPane.DEFAULT_OPTION, contestacoes);
	}

	public void popUpAcoes(){
		
		while(ret != -1){
			ret = JOptionPane.showOptionDialog(null, null, "Ações", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, acao, opcoes, opcoes[0]);
		
			if(ret == 0)
				JOptionPane.showMessageDialog(null,null,"Ações Gerais", JOptionPane.DEFAULT_OPTION, gerais);
		
			else if(ret == 1){
				option = 0;
				while(option != -1){
					option = JOptionPane.showOptionDialog(null, null, "Ações Contrárias", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, contrarias, opcoes2, opcoes2[0]);
					if(option == 0) JOptionPane.showMessageDialog(null,null,"Duque", JOptionPane.DEFAULT_OPTION, duque2);
					else if(option == 1) JOptionPane.showMessageDialog(null,null,"Condessa", JOptionPane.DEFAULT_OPTION, condessa);
					else if(option == 2) JOptionPane.showMessageDialog(null,null,"Embaixador e Capitão", JOptionPane.DEFAULT_OPTION, embaixador_capitao);
				}
			}
			
			else if(ret == 2){
				option = 0;
				while(option != -1){
					option = JOptionPane.showOptionDialog(null, null, "Ações de Personagem", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, personagem, opcoes3, opcoes3[0]);
					if(option == 0) JOptionPane.showMessageDialog(null,null,"Duque", JOptionPane.DEFAULT_OPTION, duque);
					else if(option == 1) JOptionPane.showMessageDialog(null,null,"Capitão", JOptionPane.DEFAULT_OPTION, capitao);
					else if(option == 2) JOptionPane.showMessageDialog(null,null,"Assassino", JOptionPane.DEFAULT_OPTION, assassino);
					else if(option == 3) JOptionPane.showMessageDialog(null,null,"Embaixador", JOptionPane.DEFAULT_OPTION, embaixador);
					}
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		PopUpAjuda p = new PopUpAjuda();
		// p.popUpJogo();
		// p.popUpObjetivo();
		p.popUpGeral();
	}

}
