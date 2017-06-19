package ssc0103.coup.gui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 * Panel do Log do jogo
 * @author Matheus Marzola Gomes
 *
 */
@SuppressWarnings("serial")
public class LogGUI extends JPanel{
    JTextArea area;
    JScrollPane scrollPane;
    JLabel label;
    
    /**
     * Construtor da classe
     */
    public LogGUI() {
        super();
        setLayout(new GridLayout(1, 1));
        setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
        
        area = new JTextArea(6, 0);
        area.setEditable(false);
        area.setAutoscrolls(true);
        area.setFont(new Font("Serif", Font.PLAIN, 16));
        area.setLineWrap(true);
        DefaultCaret caret = (DefaultCaret) area.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scrollPane = new JScrollPane(area);
        
        // Adicionando o textarea com scroll
        add(scrollPane);
    }
    
    /**
     * Insere um texto no log
     * @param string Texto inserido
     */
    public void insertLog(String string) {
        area.append(string+"\n");
    }
    
    public static void main(String[] args) {
    }
}
