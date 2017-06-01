package scc0103.coup.lan;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Server {
	private int porta;
	private List<PrintStream> clientes;

	public Server(int porta) {
		this.porta = porta;
		this.clientes = new ArrayList<PrintStream>();
	}

	public void executa(int numPlayers) throws IOException {
		ServerSocket server = new ServerSocket(this.porta);
		System.out.println("Servidor Ativado.");
		System.out.println("Porta " + this.porta + " aberta!");

		for (int i = 0; i < numPlayers; ++i) {
			/* Aceita uma conexão com um cliente. */
			Socket client = server.accept();

			System.out.println("Nova conexão com o cliente " + client.getInetAddress().getHostAddress());

			/* Adiciona saida do cliente a lista. */
			PrintStream ps = new PrintStream(client.getOutputStream());
			this.clientes.add(ps);

			/* Cria Tratador de Cliente em uma nova thread. */
			TrataCliente tc = new TrataCliente(client.getInputStream(), this);
			new Thread(tc).start();
		}

	}

	public void distribuiMensagem(String msg) {
		// envia msg para todo mundo
		for (PrintStream cliente : this.clientes) {
			cliente.println(msg);
		}
	}

	public static void main(String[] args) throws IOException {
		/* Inicia o Servidor. */
		int porta = Integer.parseInt(JOptionPane.showInputDialog("Informe a porta de conexão."));
		new Server(porta).executa(Integer.parseInt(JOptionPane.showInputDialog("Informe o número de jogadores.")));		
	}

}
