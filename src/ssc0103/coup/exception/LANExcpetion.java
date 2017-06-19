package ssc0103.coup.exception;

/**
 * Exception usada para a parte de rede.
 * 
 * @author Bruno Mendes da Costa - Nº USP 9779433
 *
 */
@SuppressWarnings("serial")
public class LANExcpetion extends Exception {
	public LANExcpetion(String message) {
		super(message);
	}
}
