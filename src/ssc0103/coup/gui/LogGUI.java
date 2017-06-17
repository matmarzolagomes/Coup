package ssc0103.coup.gui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
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
        super();
        setLayout(new GridLayout(1, 1));
        setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
        
        area = new JTextArea();
        area.setRows(6);
        area.setEditable(false);
        area.setAutoscrolls(true);
        area.setFont(new Font("Serif", Font.PLAIN, 16));
        area.setLineWrap(true);
        scrollPane = new JScrollPane(area);
        
        // Adicionando o textarea com scroll
        add(scrollPane);
    }
    
    public void insertLog(String string) {
        area.append(string+"\n");
        area.setCaretPosition(area.getText().length());
        
    }
    
    public static void main(String[] args) {
    	LogGUI log = new LogGUI();
    	JFrame frame = new JFrame("Game");
    	frame.setVisible(true);
    	log.insertLog("1");
    	log.insertLog("2");
    	log.insertLog("3");
    	log.insertLog("4");
    	log.insertLog("5");
    	log.insertLog("6");
    	log.insertLog("7");
    	log.insertLog("8");
    	log.insertLog("9");
    	
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.add(log);
        frame.pack();
    }
    
}
