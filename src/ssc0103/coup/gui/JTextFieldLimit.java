package ssc0103.coup.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Classe que extende PlainDocument. Limita a quantidade de caracteres em um JTextField.
 * @author Rodrigo Geurgas Zavarizz 9791080
 */
@SuppressWarnings("serial")
public class JTextFieldLimit extends PlainDocument {
	private int limit;
	private boolean toUppercase = false;

	/**
	 * Construtor da classe.
	 * @param limit limite de caracteres.
	 */
	JTextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}

	/**
	 * Construtor da classe.
	 * @param limit limite de caracteres.
	 * @param upper apenas letras maiúsculas.
	 */
	JTextFieldLimit(int limit, boolean upper) {
		super();
		this.limit = limit;
		toUppercase = upper;
	}

	/**
	 * Atualiza a String dentro do JTextField.
	 */
	public void insertString(int offset, String  str, AttributeSet attr) throws BadLocationException {
		if (str == null) return;

		if ((getLength() + str.length()) <= limit) {
			if (toUppercase) str = str.toUpperCase();
			super.insertString(offset, str, attr);
		}
	}
}