package scc0103.coup.lan;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Teste {
	
	public void popUpAssassino(String nome){
		String opcoes[] = new String[] {"Aceitar", "Bloquear", "Contestar"};
		JOptionPane.showOptionDialog(null,"O jogador " + nome + " está tentando te assassinar!", "Escolha a sua ação", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcoes, opcoes[0]);
	}
	
	public void popUpAjudaExterna(String nome){
		String opcoes2[] = new String[] {"Perimitir", "Negar"};
		JOptionPane.showOptionDialog(null,"O jogador " + nome + " está pedindo ajuda externa", "Escolha a sua ação", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes2, opcoes2[0]);
	}
	
	public void popUpGolpe(String nome){
		JOptionPane.showMessageDialog(null,"O jogador " + nome + " aplicou em você um golpe de estado!",null, JOptionPane.ERROR_MESSAGE);
	}
	
	//public void 
	
	public static void main(String[] args){
		
		Teste t = new Teste();
		t.popUpGolpe("Victor");
	}
	
}
