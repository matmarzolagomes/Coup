package scc0103.coup.lan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) {
		ServerSocket server = null;
		Socket client = null;

		try {
			server = new ServerSocket(30000);
			System.out.println("Porta 30000 aberta!");
			client = server.accept();

			System.out.println("Nova conexão com o cliente " + client.getInetAddress().getHostAddress());

			Scanner s = new Scanner(client.getInputStream());
			while (s.hasNextLine()) {
				System.out.println(s.nextLine());
			}

			s.close();
			server.close();
			client.close();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

}
