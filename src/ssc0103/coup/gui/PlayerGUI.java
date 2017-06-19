package ssc0103.coup.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ssc0103.coup.game.Player;
/**
 * Classe do Panel com a tabela dos jogadores
 * @author Matheus Marzola Gomes
 * @author Rodrigo Geurgas Zavarizz 9791080
 *
 */
@SuppressWarnings("serial")
public class PlayerGUI extends JPanel {
	JTable table;
	String[] column;
	Object[][] data;
	JScrollPane scrollPane;
	int tablesize;
	/**
	 * Construtor da classe
	 * @param players HashMap dos jogadores
	 */
	public PlayerGUI(HashMap<String, Player> players) {
		super();
		setLayout(new GridLayout(1, 1));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		tablesize = players.size();

		column = new String[] { "Jogadores", "Moedas", "Cartas" };
		data = new Object[players.size()][3];
		createTable(players);
	}
	/**
	 * Cria a tabela
	 * @param players HashMap dos jogadores
	 */
	private void createTable(HashMap<String, Player> players) {
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

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

		scrollPane = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setFillsViewportHeight(false);
		table.setFont(new Font("Serif", Font.BOLD, table.getFont().getSize()));

		add(scrollPane);
	}
	/**
	 * Atualiza a tabela
	 * @param players HashMap dos jogadores
	 */
	public void attTable(HashMap<String, Player> players) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		for (Player player : players.values()) {
			for (int j = 0; j < tablesize; ++j) {
				if (!players.containsKey(model.getValueAt(j, 0))) {
					model.setValueAt(0, j, 1);
					model.setValueAt(0, j, 2);
					table.addRowSelectionInterval(j, j);
				} else if (player.getName().equals(data[j][0])){
					if ((int) model.getValueAt(j, 1) != player.getMoney())
						model.setValueAt(player.getMoney(), j, 1);
					if ((int) model.getValueAt(j, 2) != player.getHand().size()) 
						model.setValueAt(player.getHand().size(), j, 2);
				}
			}
		}
		
//		table.updateUI();
	}

	public static void main(String[] args) {
	}
}
