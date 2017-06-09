package ssc0103.coup.lan;

import java.io.IOException;

import ssc0103.coup.exception.PException;

public class RunBoard {

	public static void main(String[] args) {
		try {
			/* Inicia o Servidor. */
			new Board().execute();
		} catch (ClassNotFoundException | IOException | PException e) {

			e.printStackTrace();
		}
	}
}