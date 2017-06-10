package ssc0103.coup.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ssc0103.coup.game.Deck;
import ssc0103.coup.game.Player;

@SuppressWarnings("serial")
public class PlayerGUI extends JPanel{
	HashMap<String,Player> players;
	JTable table;
	String[] column;
	Object[][] data;
	JScrollPane scrollPane;
	
	public PlayerGUI(HashMap<String, Player> players) {
		super();
		this.players = players;
		this.setLayout(new BorderLayout());
		table = new JTable();
		column = new String[] {"Jogadores", "Moedas", "Cartas"};
		data = new Object[players.size()][3];
		createTable();
	}
	
	private void createTable() {
		int i = 0;
		
		for (Player player : players.values()) {
			data[i][0] = player.getName();
			data[i][1] = player.getMoney();
			data[i][2] = player.getHand().size();
			i++;
		}
		
		table = new JTable(data, column);
		table.setEnabled(false);
		table.setSelectionBackground(Color.LIGHT_GRAY);
		
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		add(scrollPane);
		
	}
	
	public void attTable() {
		int i = 0;
		
		for (Player player : players.values()) {
			if((int)data[i][1] != player.getMoney()) {
				data[i][1] = player.getMoney();
			}
			if((int)data[i][2] != player.getHand().size()) {
				data[i][2] = player.getHand().size();
			}
			
			if ((int)data[i][2] == 0) 
				table.addRowSelectionInterval(i, i);
			
			i++;
		}
		
	}
	
	public static void main(String[] args) {
		JFrame game = new JFrame();
		game.setSize(300, 400);
		game.setVisible(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		HashMap<String,Player> p = new HashMap<String,Player>();
		Player p1;
		
		Deck deck = new Deck();
		deck.createDeck(4);
		
		p1 = new Player("um");
		p1.income();
		p1.draw(deck);
		p.put(p1.getName(), p1);
		
		p1 = new Player("dois");
		p1.foreign();
		p.put(p1.getName(), p1);
		
		p1 = new Player("tres");
		p1.foreign();
		p.put(p1.getName(), p1);
		
		p1 = new Player("indiozinhos");
		p1.income();
		p1.draw(deck);
		p.put(p1.getName(), p1);
		
		p1 = new Player("quatro");
		p1.foreign();
		p.put(p1.getName(), p1);
		
		p1 = new Player("cinco");
		p1.foreign();
		p.put(p1.getName(), p1);
		
		p1 = new Player("seis");
		p1.income();
		p1.draw(deck);
		p.put(p1.getName(), p1);
		
		p1 = new Player("indiozinhos");
		p1.foreign();
		p.put(p1.getName(), p1);
		
		p1 = new Player("aeeee");
		p1.foreign();
		p.put(p1.getName(), p1);
		
		p1 = new Player("Matheus");
		p1.income();
		p1.draw(deck);
		p.put(p1.getName(), p1);
		
		p1 = new Player("Rodrigo");
		p1.foreign();
		p.put(p1.getName(), p1);
		
		p1 = new Player("Ola");
		p1.foreign();
		p.put(p1.getName(), p1);
		
		PlayerGUI list = new PlayerGUI(p);
		list.createTable();
		list.attTable();
		game.add(list);
		
		
	}

}
