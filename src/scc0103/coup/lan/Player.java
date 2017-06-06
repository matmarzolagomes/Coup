package scc0103.coup.lan;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Player {
	private String host;
	private int port;

	public Player(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void execute() {
		Socket player = null;
		ObjectInputStream input;
		ObjectOutputStream output;
		
		try {
			player = new Socket(this.host, this.port);
			System.out.println("Player Conectado.");
			System.out.println("O player se juntou a mesa!");

			input = new ObjectInputStream(player.getInputStream());
						
			Object object;
			while(!((object = input.readObject()) instanceof Actions));
			
			Actions actions = (Actions) object;			
						
			if (actions.getId() == Actions.GET_NAME) {
				String name = JOptionPane.showInputDialog("Informe o seu nome:");
				actions.setFrom(name);
				actions.setPlayerResponse(true);
				
				output = new ObjectOutputStream(player.getOutputStream());
				output.writeObject(actions);
				output.flush();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();		
		} finally {			
			
		}
	}

	public static void main(String[] args) {
		String host = JOptionPane.showInputDialog("Informe o ip de conexao. Exemplo: 127.0.0.1");
		int port = Integer.parseInt(JOptionPane.showInputDialog("Informe a porta que deseja se conectar."));
		new Player(host, port).execute();
	}

}
