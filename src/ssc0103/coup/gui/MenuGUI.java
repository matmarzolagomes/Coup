package ssc0103.coup.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
/**
 * Classe que faz a barra de Menu usando um JMenuBar
 * @author Matheus Marzola Gomes
 *
 */
@SuppressWarnings("serial")
public class MenuGUI extends JMenuBar{
	JMenu ajuda;
	JMenuItem help;
	JMenuItem story;
	PopUpAjuda pophelp;
	PopUpPlayer popstory;
	/**
	 * Construtor da classe
	 */
	public MenuGUI() {
		super();
		ajuda = new JMenu("Ajuda");
		help = new JMenuItem("Regras");
		story = new JMenuItem("História do Jogo");		
		
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pophelp = new PopUpAjuda();
				pophelp.popUpGeral();
			}
		});
		
		story.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				popstory = new PopUpPlayer();
				popstory.PopUpHistoria();
			}
		});
		
		ajuda.add(help);
		ajuda.add(story);
		add(ajuda);
	}
	
	public static void main(String[] args) {
	}
}
