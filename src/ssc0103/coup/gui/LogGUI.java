package ssc0103.coup.gui;

import java.awt.Font;
import java.awt.GridLayout;

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
        
        area = new JTextArea();
        area.setRows(10);
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
    }
    
    public static void main(String[] args) {
    }
    
}
