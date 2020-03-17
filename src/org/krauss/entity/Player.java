package org.krauss.entity;

import java.util.ArrayList;

import org.krauss.entity.Card;


/**
 * 
 * @author <b> jrkrauss </b><br>
 * 		<br>
 *
 *         Class that represents the entity Player within the game. It can be a
 *         normal player or the dealer.
 *
 */
public class Player {

	private String userName;
	private int score;
	private ArrayList<Card> handCards;

	public Player(String userName) {
		this.userName = userName;
		this.handCards = new ArrayList<Card>();
	}

	public String getUserName() {
		return userName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}	

	public ArrayList<Card> getHandCards() {
		return handCards;
	}
	
	public int getSum() {
		int sum = 0;
		
		for (Card card : handCards) {
			sum += card.getValue();
		}
		
		return sum;
	}


	public void setHandCards(Card handCards) {
		if(handCards == null){
			this.handCards = new ArrayList<Card>();
		}else{
			this.handCards.add(handCards);
		}
	}

	public String printHandCards() {
		String cards = "";
		
		for (Card n : handCards) {
			cards += n.getNumber() + "" + n.getSuit() + "  ";
			
		}

		return cards;
	}
	
}