package au.com.eca.assignment.entity;

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
public class Player {

	private String userName;
	private String name;
	private int score;
	private ArrayList<Card> handCards;

	public Player(String userName) {
		this.userName = userName;
		this.handCards = new ArrayList<Card>();
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
		this.handCards.add(handCards);
	}

	public String printHandCards() {
		String cards = "";
		
		for (Card n : handCards) {
			cards += n.getNumber() + "" + n.getSuit() + "  ";
			
		}

		return cards;
	}


	
}
