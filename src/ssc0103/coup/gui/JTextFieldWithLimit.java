package ssc0103.coup.gui;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class JTextFieldWithLimit extends JPanel {
	private JTextField textfield;
	private JLabel label;
	
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
	
	public String getText() {
		return textfield.getText();
	}
}