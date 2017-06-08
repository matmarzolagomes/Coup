package ssc0103.coup.lan;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Teste {
	
	int ret = -1;
	ImageIcon duque, assassino, embaixador, capitao, condessa;
	String opcoes1[] = new String[] {"Aceitar", "Bloquear", "Contestar"};
	String opcoes2[] = new String[] {"Perimitir", "Negar"};
	String opcoes3[] = new String[] {"Aceitar", "Contestar"};
	
	public Teste(){
		
		duque = new ImageIcon("images/Duque.jpeg");
		assassino = new ImageIcon("images/Assassino.jpeg");
		embaixador = new ImageIcon("images/Embaixador.jpeg");
		capitao = new ImageIcon("images/Capitao.jpeg");
		condessa = new ImageIcon("images/Condessa.jpeg");
		
		duque.setImage(duque.getImage().getScaledInstance(150, 250, 100));
		assassino.setImage(assassino.getImage().getScaledInstance(150, 250, 100));
		embaixador.setImage(embaixador.getImage().getScaledInstance(150, 250, 100));
		capitao.setImage(capitao.getImage().getScaledInstance(150, 250, 100));
		condessa.setImage(condessa.getImage().getScaledInstance(150, 250, 100));
		
	}
	public void popUpAcao(){
		JOptionPane.showMessageDialog(null,"Você deve escolher uma ação!","Erro", JOptionPane.ERROR_MESSAGE);
	}
	
	public int popUpAssassino(String nome){
		
		ret = JOptionPane.showOptionDialog(null, nome + " está tentando te assassinar!", "Assassinato", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, assassino, opcoes1, opcoes1[0]);
		while(ret < 0){
			popUpAcao();
			ret = JOptionPane.showOptionDialog(null, nome + " está tentando te assassinar!", "Assassinato", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, assassino, opcoes1, opcoes1[0]);
		}
		
	return ret; 
	}
	
	public int popUpTaxas(String nome){
	
		ret = JOptionPane.showOptionDialog(null, nome + " está reinvindicando taxas", "Taxas", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, duque, opcoes2, opcoes2[0]);
		if (ret < 0) ret = 0;
		
	return ret;
	}
	
	public int popUpCondessa(String nome){
		
		ret = JOptionPane.showOptionDialog(null, nome + " bloqueou o seu assassinato", "Bloqueio", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, condessa, opcoes3, opcoes3[0]);
		while(ret < 0){
			popUpAcao();
			ret = JOptionPane.showOptionDialog(null, nome + " bloqueou o seu assassinato", "Bloqueio", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, condessa, opcoes3, opcoes3[0]);
		}
		
	return ret;
	}
	
	public int popUpTroca(String nome){
		
		ret = JOptionPane.showOptionDialog(null, nome + " está reinvindicando uma troca de cartas", "Troca", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, embaixador, opcoes2, opcoes2[0]);
		if(ret < 0) ret = 0;
		
	return ret;
	}
	
	public int popUpExtorcao(String nome){
		ret = JOptionPane.showOptionDialog(null, nome + " está tentando te extorquir!", "Extorsão", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, capitao, opcoes1, opcoes1[0]);
		
		while(ret < 0){
			popUpAcao();
			ret = JOptionPane.showOptionDialog(null, nome + " está tentando te extorquir!", "Extorsão", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, capitao, opcoes1, opcoes1[0]);
		}
	return ret;
	}
	
	public int popUpAjudaExterna(String nome){
		String opcoes2[] = new String[] {"Perimitir", "Negar"};
		return JOptionPane.showOptionDialog(null, nome + " está pedindo ajuda externa", "Ajuda Externa", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes2, opcoes2[0]);
	}
	
	public int popUpBloqueioExtorcao(String nome, String carta){
		
		if(carta.equals("Capitao")){
			ret = JOptionPane.showOptionDialog(null, nome + " bloqueou a sua extorsão", "Bloqueio", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, capitao, opcoes3, opcoes3[0]);
			while(ret < 0){
				popUpAcao();
				ret = JOptionPane.showOptionDialog(null, nome + " bloqueou a sua extorsão", "Bloqueio", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, capitao, opcoes3, opcoes3[0]);
			}
		}
		
		else if(carta.equals("Embaixador")){
			ret = JOptionPane.showOptionDialog(null, nome + " bloqueou a sua extorsão", "Bloqueio", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, embaixador, opcoes3, opcoes3[0]);
			while(ret < 0){
				popUpAcao();
				ret = JOptionPane.showOptionDialog(null, nome + " bloqueou a sua extorsão", "Bloqueio", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, embaixador, opcoes3, opcoes3[0]);
			}
		}
		
	return ret;
	}
	
	public void popUpGolpe(String nome){
		JOptionPane.showMessageDialog(null,"O jogador " + nome + " aplicou em você um golpe de estado!","Golpe de Estado", JOptionPane.ERROR_MESSAGE);
	}
	
	
	public static void main(String[] args){
		
		Teste t = new Teste();
		System.out.println(t.popUpAssassino("Victor"));
		System.out.println(t.popUpTaxas("Victor"));
		System.out.println(t.popUpCondessa("Victor"));
		System.out.println(t.popUpTroca("Victor"));
		System.out.println(t.popUpExtorcao("Victor"));
		System.out.println(t.popUpBloqueioExtorcao("victor", "Capitao"));
		System.out.println(t.popUpBloqueioExtorcao("victor", "Embaixador"));
		
	}
}
