package ssc0103.coup.game;

import java.io.Serializable;
import java.util.Random;

/**
 * Classe usada para representar um jogador
 * @author Rodrigo Geurgas Zavarizz 9791080
 */
@SuppressWarnings("serial")
public class Player implements Serializable, Cloneable {
	private String name;
	private Deck hand;
	private int money;
	
	/**
	 * Construtor que inicia um novo jogador na partida
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
		hand = new Deck();
		money = 2;
	}
	
	/**
	 * Método que retorna o nome do jogador
	 * @return nome do jogador
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Método que retorna a mão do jogador
	 * @return mão do jogador
	 */
	public Deck getHand() {
		return hand;
	}
	
	/**
	 * Método que retorna a quantidade de dinheiro do jogador
	 * @return dinheiro do jogador
	 */
	public int getMoney() {
		return money;
	}
	
	/**
	 * Método que acrescenta a renda ao jogador
	 * @return se ação foi bem sucedida
	 */
	public boolean income() {
		money += 1;
		return true;
	}
	
	/**
	 * Método que acrescenta ajuda externa ao jogador
	 * @return se a ação foi bem sucedida
	 */
	public boolean foreign() {
		money += 2;
		return true;
	}
	
	/**
	 * Método que remove o dinheiro necessário para a ação de coup
	 * @return se a ação foi bem sucedida
	 */
	public boolean coup() {
		if (money < 7)
			return false;
		money -= 7;
		return true;
	}
	
	/**
	 * Método que adciona as taxas ao jogador
	 * @return se a ação foi bem sucedida ou não
	 */
	public boolean taxes() {
		money += 3;
		return true;
	}
	
	/**
	 * Método que remove o dinheiro necessário para o assassinato
	 * @return se a ação foi bem sucedida ou não
	 */
	public boolean assassinate() {
		if (money < 3)
			return false;
		money -= 3;
		return true;
	}
	
	/**
	 * Método que tira o dinheiro de uma extorção do jogador
	 * @return se a ação foi ou não bem sucedida
	 */
	public int drop() {
		if (money >= 2) {
			money -= 2;
			return 2;
		} else if (money == 1) {
			money -= 1;
			return 1;
		}
		return 0;
	}
	
	/**
	 * Método que adciona o dinheiro roubado ao jogador
	 * @param other player que teve o dinheiro roubado
	 * @return se a ação foi ou não bem sucedida
	 */
	public boolean steal(Player other) {
		int money = other.drop();
		if (money >= 1) {
			this.money += money;
			return true;
		}
		return false;
	}
	
	/**
	 * Método que adciona cartas na mão do jogador
	 * @param board (baralho com as cartas)
	 * @param size (quantidade de cartas)
	 * @return se a ação foi ou não bem sucedida 
	 */
	public boolean draw(Deck board, int size) {
		Random randomizer = new Random();
		for (int i = 0; i < size; i++)
			hand.add(board.remove(randomizer.nextInt(board.size())));
		return true;
	}
	
	/**
	 * Método que remove uma carta do jogador
	 * @param cards Array com as cartas
	 * @param dead Cemitério para onde a carta vai
	 * @return se a ação foi ou não bem sucedida
	 */
	public boolean removeCard(String[] cards, Deck dead) {
		for (String card : cards)
			if (!hand.contains(card))
				return false;

		for (String card : cards) {
			hand.remove(card);
			dead.add(card);
		}
		return true;
	}
	
	/**
	 * Método que verifica se um jogador possui ou não aquela carta
	 * @param card (carta desejada)
	 * @return se ele possui ou não
	 */
	public boolean checkCard(String card) {
		return hand.contains(card);
	}

	@Override
	protected Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
