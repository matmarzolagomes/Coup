package ssc0103.coup.gui;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class PopUpPlayer {
	
	private int ret = -1;
	private ImageIcon duque, assassino, embaixador, capitao, condessa, coins, golpe, coup, icon, derrota, vitoria;
	private String opcoes1[] = new String[] {"Aceitar", "Bloquear", "Contestar"};
	private String opcoes2[] = new String[] {"Permitir", "Negar"};
	private String opcoes3[] = new String[] {"Aceitar", "Contestar"};
	private String opcoes4[] = new String[] {"Aceitar", "Bloquear (Embaixador)", "Bloquear (Capitão)", "Contestar"};
	private String jogador = null;
	
	public PopUpPlayer(){
		
		coins = new ImageIcon("images/coins.png");
		duque = new ImageIcon("images/Duque.jpeg");
		assassino = new ImageIcon("images/Assassino.jpeg");
		embaixador = new ImageIcon("images/Embaixador.jpeg");
		capitao = new ImageIcon("images/Capitao.jpeg");
		condessa = new ImageIcon("images/Condessa.jpeg");
		golpe = new ImageIcon("images/golpe.png");
		coup = new ImageIcon("images/action.png");
		icon = new ImageIcon("images/icon.png");
		derrota = new ImageIcon("images/derrota.jpg");
		vitoria = new ImageIcon("images/vitoria.jpg");
		
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
	public void popUpErro(){
		JOptionPane.showMessageDialog(null,"Você deve escolher uma ação!","Erro", JOptionPane.ERROR_MESSAGE);
	}
	
	public void popUpDerrota(){
		JOptionPane.showMessageDialog(null,null,"Derrota", JOptionPane.DEFAULT_OPTION, derrota);
	}
	
	public void popUpVitoria(){
		JOptionPane.showMessageDialog(null,null,"Vitória", JOptionPane.DEFAULT_OPTION, vitoria);
	}
	
	public int popUpAssassino(String nome){
		
		ret = JOptionPane.showOptionDialog(null, nome + " está tentando te assassinar!", "Assassinato", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, assassino, opcoes1, opcoes1[0]);
		while(ret < 0){
			popUpErro();
			ret = JOptionPane.showOptionDialog(null, nome + " está tentando te assassinar!", "Assassinato", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, assassino, opcoes1, opcoes1[0]);
		}
		
	return ret; 
	}
	
	public int popUpTaxas(String nome){
	
		ret = JOptionPane.showOptionDialog(null, nome + " está reinvindicando taxas!", "Taxas", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, duque, opcoes2, opcoes2[0]);
		if (ret < 0) ret = 0;
		
	return ret;
	}
	
	public int popUpCondessa(String nome){
		
		ret = JOptionPane.showOptionDialog(null, nome + " bloqueou o seu assassinato!", "Bloqueio", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, condessa, opcoes3, opcoes3[0]);
		while(ret < 0){
			popUpErro();
			ret = JOptionPane.showOptionDialog(null, nome + " bloqueou o seu assassinato!", "Bloqueio", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, condessa, opcoes3, opcoes3[0]);
		}
		
	return ret;
	}
	
	public int popUpTroca(String nome){
		
		ret = JOptionPane.showOptionDialog(null, nome + " está reinvindicando uma troca de cartas!", "Troca", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, embaixador, opcoes2, opcoes2[0]);
		if(ret < 0) ret = 0;
		
	return ret;
	}
	
	public int popUpExtorcao(String nome){
		ret = JOptionPane.showOptionDialog(null, "                              "+ nome + " está tentando te extorquir!", "Extorsão", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, capitao, opcoes4, opcoes4[0]);
		
		while(ret < 0){
			popUpErro();
			ret = JOptionPane.showOptionDialog(null, "                              " + nome + " está tentando te extorquir!", "Extorsão", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, capitao, opcoes4, opcoes4[0]);
		}
	return ret;
	}
	
	public int popUpAjudaExterna(String nome){
		return JOptionPane.showOptionDialog(null, nome + " está pedindo ajuda externa!", "Ajuda Externa", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, coins, opcoes2, opcoes2[0]);
	}
	
	public int popUpBloqueioExtorcao(String nome, String carta){
		
		if(carta.equals("Capitao")){
			ret = JOptionPane.showOptionDialog(null, nome + " bloqueou a sua extorsão!", "Bloqueio", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, capitao, opcoes3, opcoes3[0]);
			while(ret < 0){
				popUpErro();
				ret = JOptionPane.showOptionDialog(null, nome + " bloqueou a sua extorsão!", "Bloqueio", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, capitao, opcoes3, opcoes3[0]);
			}
		}
		
		else if(carta.equals("Embaixador")){
			ret = JOptionPane.showOptionDialog(null, nome + " bloqueou a sua extorsão!", "Bloqueio", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, embaixador, opcoes3, opcoes3[0]);
			while(ret < 0){
				popUpErro();
				ret = JOptionPane.showOptionDialog(null, nome + " bloqueou a sua extorsão!", "Bloqueio", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, embaixador, opcoes3, opcoes3[0]);
			}
		}
	return ret;
	}
	
	public void popUpGolpe(String nome){
		JOptionPane.showOptionDialog(null, nome + " aplicou um golpe de estado em você!", "Golpe de Estado", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, golpe, null, null);
	}
	
	public int popUpAcoes(String[] acoes, String nome){
		ret = JOptionPane.showOptionDialog(null, "          " + nome + " é a sua vez de jogar, escolha a sua ação!", "Sua Vez", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, coup, acoes, acoes[0]);
		while(ret < 0){
			popUpErro();
			ret = JOptionPane.showOptionDialog(null, "          " + nome + " é a sua vez de jogar, escolha a sua ação!", "Sua Vez", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, coup, acoes, acoes[0]);
		}
		return ret;
	}
	
	public String popUpJogadores(String[] jogadores){

		jogador = (String) JOptionPane.showInputDialog(null, "Escolha um Jogador\n \n", "Jogador", JOptionPane.INFORMATION_MESSAGE, icon, jogadores, jogadores[0]);
		
		while(jogador == null){
			popUpErro();
			jogador = (String) JOptionPane.showInputDialog(null, "Escolha um Jogador\n \n", "Jogador", JOptionPane.INFORMATION_MESSAGE, icon, jogadores, jogadores[0]);
		}
		
	return jogador;
	
	}
	
	
	public static void main(String[] args){
		
		String a[] = new String[]{"Victor", "Bruno", "Matheus", "Rodrigo"};
		String b[] = new String[]{"Renda", "Taxas", "Assassinar", "Extorquir", "Ajuda", "Trocar", "Golpe"};
		PopUpPlayer t = new PopUpPlayer();
		//System.out.println(t.popUpAssassino("Victor"));
		//System.out.println(t.popUpTaxas("Victor"));
		//System.out.println(t.popUpCondessa("Victor"));
		//System.out.println(t.popUpTroca("Victor"));
		//System.out.println(t.popUpExtorcao("Victor"));
		//System.out.println(t.popUpAjudaExterna("Victor"));
		//System.out.println(t.popUpBloqueioExtorcao("victor", "Capitao"));
		//System.out.println(t.popUpBloqueioExtorcao("victor", "Embaixador"));
		//t.popUpGolpe("Victor");
		//System.out.println(t.popUpAcoes(b, "Victor"));
		//System.out.println(t.popUpJogadores(a));
		t.popUpDerrota();
		t.popUpVitoria();
	}
}
