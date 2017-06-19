package ssc0103.coup.main;

import ssc0103.coup.lan.Player;

/**
 * Main do cliente.
 * @author Rodrigo Geurgas Zavarizz 9791080
 *
 */
public class Client {
	public static void main(String[] args) {
		while(true)
			new Player().execute();
	}
}
