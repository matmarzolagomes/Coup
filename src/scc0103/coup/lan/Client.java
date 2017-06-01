package scc0103.coup.lan;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {

		Socket cliente = null;
		try {
			cliente = new Socket("192.168.182.158", 8080);

			System.out.println("O cliente se conectou ao servidor!");

			Scanner teclado = new Scanner(System.in);
			PrintStream saida = null;
			saida = new PrintStream(cliente.getOutputStream());

			while (teclado.hasNextLine()) {
				saida.println(teclado.nextLine());
			}

			saida.close();
			teclado.close();

			cliente.close();

		} catch (UnknownHostException e) {
			e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
}
