package ssc0103.coup.gui;

import java.awt.*;
import javax.swing.*;

/**
 * JPanel com um JTextField limitado e um JLabel.
 * @author Rodrigo Geurgas Zavarizz 9791080
 *
 */
@SuppressWarnings("serial")
public class JTextFieldWithLimit extends JPanel {
	private JTextField textfield;
	private JLabel label;
	
	/**
	 * Construtor da classe.
	 * @param label nome a ser colocado no JLabel.
	 * @param limit limite de caracteres no JTextField.
	 */
	public JTextFieldWithLimit(String label, int limit) {
		super(new GridBagLayout());
		setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 20));
		
		GridBagConstraints cons = new GridBagConstraints();
		
		this.label = new JLabel(label);
		textfield = new JTextField(limit);

		cons.anchor = GridBagConstraints.FIRST_LINE_START;
        cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 0.1;
		
		add(this.label, cons);
		
		cons.weightx = 0.9;
		
		add(textfield, cons);
		
		textfield.setDocument(new JTextFieldLimit(limit));
	}
	
	/**
	 * Desabilita o JTextField.
	 */
	public void disable() {
		textfield.setEditable(false);
		textfield.setBackground(Color.GRAY);
	}
	
	/**
	 * Habilita o JTextField.
	 */
	public void enable() {
		textfield.setEditable(true);
		textfield.setBackground(Color.WHITE);
	}
	
	/**
	 * Retorna o texto no JTextField.
	 * @return texto escrito no JTextField.
	 */
	public String getText() {
		return textfield.getText();
	}
	
	/**
	 * Atribui uma String ao JTextField.
	 * @param txt texto a ser escrito no JTextField.
	 */
	public void setText(String txt) {
		textfield.setText(txt);
	}
}