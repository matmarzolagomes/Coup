package scc0103.coup.lan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket server = null;
		Socket client = null;
		Scanner s = null;

		try {
			server = new ServerSocket(12345);
			System.out.println("Porta 12345 aberta!");
			client = server.accept();

			System.out.println("Nova conexão com o cliente " + client.getInetAddress().getHostAddress());

			s = new Scanner(client.getInputStream());
			while (s.hasNextLine()) {
				System.out.println(s.nextLine());
			}
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		} finally {
			s.close();
			server.close();
			client.close();
		}
	}

}
