package com.eca.assignment.entity;

import java.util.ArrayList;


/**
 * 
 * @author <b> jrkrauss </b><br>
 * 		<br>
 *
 *         Class that represents the entity Player within the game. It can be a
 *         normal player or the dealer.
 *
 */
public class BJPlayer {

	private String userName;
	private String name;
	private int score;
	private ArrayList<BJCard> handCards;

	public BJPlayer(String userName) {
		this.userName = userName;
		this.handCards = new ArrayList<BJCard>();
	}

	public String getUserName() {
		return userName;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}	

	public ArrayList<BJCard> getHandCards() {
		return handCards;
	}
	
	public int getSum() {
		int sum = 0;
		
		for (BJCard card : handCards) {
			sum += card.getValue();
		}
		
		return sum;
	}


	public void setHandCards(BJCard handCards) {
		if(handCards == null){
			this.handCards = new ArrayList<BJCard>();
		}else{
			this.handCards.add(handCards);
		}
	}

	public String printHandCards() {
		String cards = "";
		
		for (BJCard n : handCards) {
			cards += n.getNumber() + "" + n.getSuit() + "  ";
			
		}

		return cards;
	}

	

	
}
