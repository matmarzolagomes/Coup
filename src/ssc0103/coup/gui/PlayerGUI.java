package ssc0103.coup.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ssc0103.coup.game.Player;

@SuppressWarnings("serial")
public class PlayerGUI extends JPanel{
	JTable table;
	String[] column;
	Object[][] data;
	JScrollPane scrollPane;
	
	public PlayerGUI(HashMap<String, Player> players) {
		super();
		setLayout(new GridLayout(1, 1));
		column = new String[] {"Jogadores", "Moedas", "Cartas"};
		data = new Object[players.size()][3];
		createTable(players);
	}
	
	private void createTable(HashMap<String,Player> players) {
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
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setFillsViewportHeight(false);
		
		add(scrollPane);
	}
	
	public void attTable(HashMap<String,Player> players) {
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
	}
}
