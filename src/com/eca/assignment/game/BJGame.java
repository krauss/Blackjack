package com.eca.assignment.game;

import java.util.Collections;
import java.util.Stack;
import com.eca.assignment.entity.*;

public class BJGame {

	// Creates the deck of Card in form of a stack
	private Stack<BJCard> deckCards;
	
	//Instances of the Player and Dealer
	private BJPlayer player;
	private BJPlayer dealer;
	private int dealerHints = 3;
	
	public static enum GameStatus{
		WIN, 
		LOSE, 
		KEEP;
		
	}
	

	public BJGame(BJPlayer p, BJPlayer d) {
		this.player = p;
		this.dealer = d;
		init();

	}

	private void init() {

		// Create the deck of 52 cards 4x13, from A-K each of which of 4 suits
		deckCards = new Stack<BJCard>();


		// Card numbers
		int number = 1;

		for (int i = 0; i < 52; i++) {

			if (i < 13) {
				deckCards.push(new BJClubsCard(number));
			} else if (i < 26) {
				deckCards.push(new BJHeartsCard(number));
			} else if (i < 39) {
				deckCards.push(new BJSpadesCard(number));
			} else {
				deckCards.push(new BJDiamondsCard(number));
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
	public BJCard getDeckCard() {
		return deckCards.pop();
	}


	public GameStatus checkGameOver(boolean standPressed){
		int playerResult = 0;
		int dealerResult = 0;
		
		for (BJCard n : player.getHandCards()) {
			playerResult += n.getValue();
		}
		
		for (BJCard n : dealer.getHandCards()) {
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
