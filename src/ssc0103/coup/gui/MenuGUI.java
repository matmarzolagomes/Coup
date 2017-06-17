package ssc0103.coup.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuGUI extends JMenuBar{
	JMenu help;
	JMenuItem item;
	PopUpAjuda pophelp;
	
	public MenuGUI() {
		super();
		help = new JMenu("Ajuda");
		item = new JMenuItem("Regras");
		
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pophelp = new PopUpAjuda();
				pophelp.popUpGeral();
			}
		});
		
		help.add(item);
		add(help);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
