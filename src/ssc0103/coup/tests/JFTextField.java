package ssc0103.coup.tests;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import java.awt.BorderLayout;
import javax.swing.DropMode;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JFTextField {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFTextField window = new JFTextField();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JFTextField() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JTextField textField = new JTextField();
		textField.setDocument(new DocumentoLimitado(5));	
		textField.setBounds(127, 95, 98, 20);
				
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(textField);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, textField.getText());
			}
		});
		btnNewButton.setBounds(136, 160, 89, 23);
		frame.getContentPane().add(btnNewButton);
	}
}
