package ssc0103.coup.lan;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class SwingJRadioButtonDemo extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JButton buttonOK = new JButton("OK");
	private JRadioButton renda = new JRadioButton("Renda");
	private JRadioButton ajuda = new JRadioButton("Ajuda Externa");
	private JRadioButton taxa = new JRadioButton("Obter Taxas");
	private JRadioButton assassinar = new JRadioButton("Assassinar Jogador");
	private JRadioButton extorquir = new JRadioButton("Extorquir Jogador");
	private JRadioButton trocar = new JRadioButton("Trocar Cartas");
	private JRadioButton golpe = new JRadioButton("Golpe de Estado");

	private JLabel labelImage = new JLabel();

	private ImageIcon rendai = new ImageIcon("images2/moeda.png");
	private ImageIcon ajudai = new ImageIcon("images2/moeda1.png");
	private ImageIcon taxai = new ImageIcon("images2/moedas.png");
	private ImageIcon assassinari = new ImageIcon("images2/foice.png");
	private ImageIcon extorquiri = new ImageIcon("images2/roubo.png");
	private ImageIcon trocari = new ImageIcon("images2/troca.png");
	private ImageIcon golpei = new ImageIcon("images2/golpe.png");
	
	public SwingJRadioButtonDemo() {
		
		super("Ações");
		setSize(400, 600);
		setResizable(false);
		this.setLocationRelativeTo(null);
		
		golpei.setImage(golpei.getImage().getScaledInstance(75, 75, 100));
		rendai.setImage(rendai.getImage().getScaledInstance(80, 65, 100));
		taxai.setImage(taxai.getImage().getScaledInstance(75, 75, 100));
		assassinari.setImage(assassinari.getImage().getScaledInstance(75, 75, 100));
		extorquiri.setImage(extorquiri.getImage().getScaledInstance(75, 75, 100));
		trocari.setImage(trocari.getImage().getScaledInstance(75, 75, 100));
		ajudai.setImage(ajudai.getImage().getScaledInstance(75, 75, 100));

		ButtonGroup group = new ButtonGroup();
		group.add(renda);
		group.add(ajuda);
		group.add(taxa);
		group.add(assassinar);
		group.add(extorquir);
		group.add(trocar);
		group.add(golpe);

		setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;	//alinhamento
		constraints.insets = new Insets(10, 10, 10, 10);			//espaco entre celulas
	
		add(renda, constraints);
		add(ajuda, constraints);
		add(taxa, constraints);
		add(assassinar, constraints);
		add(extorquir, constraints);
		add(trocar, constraints);
		add(golpe, constraints);
		
		constraints.anchor = GridBagConstraints.CENTER;
		add(labelImage, constraints);
		buttonOK.setText("Confirmar");
		add(buttonOK, constraints);

		RadioButtonActionListener actionListener = new RadioButtonActionListener();
		ajuda.addActionListener(actionListener);
		taxa.addActionListener(actionListener);
		golpe.addActionListener(actionListener);
		renda.addActionListener(actionListener);
		assassinar.addActionListener(actionListener);
		trocar.addActionListener(actionListener);
		extorquir.addActionListener(actionListener);
		
		buttonOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				/*
				if(ajuda.isSelected()) 
				else if(taxa.isSelected())
				else if(golpe.isSelected())
				else if(renda.isSelected())
				else if(assassinar.isSelected())
				else if(trocar.isSelected())
				else if(extorquir.isSelected())*/
			}
		});
		
	}

	class RadioButtonActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			JRadioButton button = (JRadioButton) event.getSource();
			
			if (button == ajuda) labelImage.setIcon(ajudai);
			else if (button == taxa) labelImage.setIcon(taxai);
			else if (button == golpe) labelImage.setIcon(golpei);
			else if (button == renda) labelImage.setIcon(rendai);
			else if (button == extorquir) labelImage.setIcon(extorquiri);
			else if (button == trocar) labelImage.setIcon(trocari);
			else if (button == assassinar) labelImage.setIcon(assassinari);
			
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new SwingJRadioButtonDemo().setVisible(true);
			}
		});
	}
}