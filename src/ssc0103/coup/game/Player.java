package ssc0103.coup.game;

import java.io.Serializable;
import java.util.Random;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Player implements Serializable, Cloneable {
	private String name;
	private Deck hand;
	private int money;
	
	public Player(String name) {
		JOptionPane.showMessageDialog(null, "O nome desse player é:" + name);
		this.name = name;
		hand = new Deck();
		money = 2;
	}
	
	public String getName() {
		return name;
	}

	public Deck getHand() {
		return hand;
	}

	public int getMoney() {
		return money;
	}

	public boolean income() {
		money += 1;
		return true;
	}

	public boolean foreign() {
		money += 2;
		return true;
	}

	public boolean coup() {
		if (money < 7)
			return false;
		money -= 7;
		return true;
	}

	public boolean taxes() {
		money += 3;
		return true;
	}

	public boolean assassinate() {
		if (money < 3)
			return false;
		money -= 3;
		return true;
	}

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

	public boolean steal(Player other) {
		int money = other.drop();
		if (money >= 1) {
			this.money += money;
			return true;
		}
		return false;
	}

	public boolean draw(Deck board, int size) {
		Random randomizer = new Random();
		for (int i = 0; i < size; i++)
			hand.add(board.remove(randomizer.nextInt(board.size())));
		return true;
	}

	public boolean removeCard(String[] cards, Deck dead) {
		for(String card : cards) 
			if(!hand.contains(card)) return false;
		
		for(String card : cards) {
			hand.remove(card);
			dead.add(card);
		}
		return true;
	}

	public boolean checkCard(String card) {
		return hand.contains(card);
	}
	
	public static void main(String[] args) {
		
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
