package ssc0103.coup.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class LogGUI extends JPanel{
	JTextArea area;
	JScrollPane scrollPane;
	JLabel label;
	
	public LogGUI() {
		super(new GridBagLayout());
		
        area = new JTextArea(7, 40);
        area.setEditable(false);
        area.setFont(new Font("Serif", Font.PLAIN, 16));
        area.setLineWrap(true);
        scrollPane = new JScrollPane(area);

        //Adicionando o label
        label = new JLabel("LOG");
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        add(label, c);

        // Adicionando o textarea com scroll
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
	}
	
	public void insertLog(String string) {
		area.append(string+"\n");
	}

	public static void main(String[] args) {
		JFrame game = new JFrame();
		game.setSize(600, 200);
		game.setVisible(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		LogGUI log = new LogGUI();
		log.insertLog("Tentei matar o victor");
		log.insertLog("Victor contestou");
		log.insertLog("Só digo uma coisa rogerinho:");
		log.insertLog("show");
		log.insertLog("Tentei matar o victor");
		log.insertLog("Victor contestou");
		log.insertLog("Só digo uma coisa rogerinho:");
		log.insertLog("show");
		
		
		game.add(log);
		//game.pack();

	}

}
