package scc0103.coup.lan;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Client {
	private String host;
	private int porta;

	public Client(String host, int porta) {
		this.host = host;
		this.porta = porta;
	}

	public void executa() throws UnknownHostException, IOException {
		Socket client = new Socket(this.host, this.porta);
		System.out.println("Cliente Ativado.");
		System.out.println("O cliente se conectou ao servidor!");

		/* Thread Recebe Mensagens do Servidor. */
		Recebedor r = new Recebedor(client.getInputStream());
		new Thread(r).start();

		/* Lê as msgs do teclado e manda para o servidor. */
		Scanner teclado = new Scanner(System.in);
		PrintStream saida = new PrintStream(client.getOutputStream());
		while (teclado.hasNextLine()) {
			saida.println(teclado.nextLine());
		}

		saida.close();
		teclado.close();
		client.close();
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		int porta = Integer.parseInt(JOptionPane.showInputDialog("Informe a porta que deseja se conectar."));
		new Client("127.0.0.1", porta).executa();
	}
}
