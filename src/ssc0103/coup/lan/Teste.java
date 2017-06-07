package ssc0103.coup.lan;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Teste {
	
	public void popUpAssassino(String nome){
		String opcoes[] = new String[] {"Aceitar", "Bloquear", "Contestar"};
		JOptionPane.showOptionDialog(null,"O jogador " + nome + " afirma ser o Assassino, e está tentando te assassinar!", "Assassinato", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcoes, opcoes[0]);
	}
	
	public void popUpAjudaExterna(String nome){
		String opcoes2[] = new String[] {"Perimitir", "Negar"};
		JOptionPane.showOptionDialog(null,"O jogador " + nome + " está pedindo ajuda externa", "Ajuda Externa", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes2, opcoes2[0]);
	}
	
	public void popUpGolpe(String nome){
		JOptionPane.showMessageDialog(null,"O jogador " + nome + " aplicou em você um golpe de estado!","Golpe de Estado", JOptionPane.ERROR_MESSAGE);
	}
	
	public void popUpTaxas(String nome){
		String opcoes2[] = new String[] {"Perimitir", "Negar"};
		JOptionPane.showOptionDialog(null,"O jogador " + nome + " afirma ser o Duque, e está reinvindicando taxas", "Taxas", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes2, opcoes2[0]);
	}
	
	public void popUpExtorcao(String nome){
		String opcoes[] = new String[] {"Aceitar", "Bloquear", "Contestar"};
		JOptionPane.showOptionDialog(null,"O jogador " + nome + " está tentando te extorquir!", "Extorsão", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcoes, opcoes[0]);
	}
	
	public void popUpTroca(String nome){
			String opcoes2[] = new String[] {"Perimitir", "Negar"};
			JOptionPane.showOptionDialog(null,"O jogador " + nome + " afirma ser o Embaixador, e está reinvindicando uma troca de cartas", "Taxas", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes2, opcoes2[0]);
	}
	
	public static void main(String[] args){
		
		Teste t = new Teste();
		t.popUpAjudaExterna("Victor");
		t.popUpAssassino("Victor");
		t.popUpExtorcao("Victor");
		t.popUpGolpe("Victor");
		t.popUpTaxas("Victor");
		t.popUpTroca("Victor");
	}
}
