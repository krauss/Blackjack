package au.com.eca.assignment.main;

import java.util.Collections;
import java.util.Stack;
import au.com.eca.assignment.entity.*;

public class BlackJackMain {

	// Creates the deck of Card in form of a stack
	private Stack<Card> deckCards;
	
	//Instances of the Player and Dealer
	private Player player;
	private Player dealer;
	private int dealerHints = 2;
	
	public static enum GameStatus{
		WIN, 
		LOSE, 
		KEEP;
		
	}
	

	public BlackJackMain(Player p, Player d) {
		this.player = p;
		this.dealer = d;
		init();

	}

	private void init() {

		// Create the deck of 52 cards 4x13, from A-K each of which of 4 suits
		deckCards = new Stack<Card>();


		// Card numbers
		int number = 1;

		for (int i = 0; i < 52; i++) {

			if (i < 13) {
				deckCards.push(new ClubsCard(number));
			} else if (i < 26) {
				deckCards.push(new HeartsCard(number));
			} else if (i < 39) {
				deckCards.push(new SpadesCard(number));
			} else {
				deckCards.push(new DiamondsCard(number));
			}
			number++;
			if (number == 14) {
				number = 1;
			}

		}
		// After created, the stack of cards gets shuffled twice
		Collections.shuffle(deckCards);
		Collections.shuffle(deckCards);

	}

	// Pulls out one card from the deck
	public Card getDeckCard() {
		return deckCards.pop();
	}


	public GameStatus checkGameOver(boolean standPressed){
		int playerResult = 0;
		int dealerResult = 0;
		
		for (Card n : player.getHandCards()) {
			playerResult += n.getValue();
		}
		
		for (Card n : dealer.getHandCards()) {
			dealerResult += n.getValue();
		}
		
		if(playerResult > 21){			
			return GameStatus.LOSE;
		}else if (playerResult == 21){
			return GameStatus.WIN;
		} else if(dealerResult > 21){
			return GameStatus.WIN;
		}else if ((playerResult > dealerResult) && standPressed){
			return GameStatus.WIN;
		} else if ((playerResult < dealerResult) && standPressed){
			return GameStatus.LOSE;
		} else {
			return GameStatus.KEEP;
		}
		
	}

	public int getDealerHints() {
		return dealerHints;
	}

	public void decDealerHints() {
		this.dealerHints--;
	}

}
