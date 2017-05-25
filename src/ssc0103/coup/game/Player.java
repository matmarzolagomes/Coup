package ssc0103.coup.game;

import java.util.Random;

public class Player {
	private Deck hand;
	private int money;
	
	public Player() {
		hand = new Deck();		
		money = 2;
	}
	
	public Deck getHand() {
		return hand;
	}

	public int getMoney() {
		return money;
	}
	
	public boolean Income() {
		money += 1;
		return true;
	}
	
	public boolean Foreign() {
		money += 2;
		return true;
	}
	
	public boolean Coup() {
		if(money < 7) return false;
		money -= 7;
		return true;
	}
	
	public boolean Taxes() {
		money += 3;
		return true;
	}
	
	public boolean Assassinate() {
		if(money < 3) return false;
		money -= 3;
		return true;
	}
	
	public boolean Steal(Player other) {
		other.money -= 2;
		if(other.money >= 0) this.money += 2;
		else if(other.money == -1) this.money += 1;
		return true;
	}
	
	public boolean Draw(Deck board) {
		Random randomizer = new Random();
		hand.add(board.get(randomizer.nextInt(board.size())));
		hand.add(board.get(randomizer.nextInt(board.size())));
		return true;
	}
	
	public boolean removeCard(String card) {
		if(hand.remove(card)) return true;
		return false;
	}
	
	public boolean checkCard(String card) {
		return hand.contains(card);
	}
}
