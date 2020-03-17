package org.krauss.game;

import java.util.Collections;
import java.util.Stack;

import org.krauss.entity.Card;
import org.krauss.entity.Player;

public class GameLogic {

	private Stack<Card> deckCards;
	
	private Player player;
	private Player dealer;
	private int dealerHints = 3;
	
	public static enum GameStatus{
		WIN, 
		LOSE, 
		KEEP;
		
	}
	

	public GameLogic(Player p, Player d) {
		this.player = p;
		this.dealer = d;
		init();

	}

	private void init() {

		deckCards = new Stack<Card>();


		int number = 1;

		for (int i = 0; i < 52; i++) {

			if (i < 13) {
				deckCards.push(new Card(number, "\u2663"));
			} else if (i < 26) {
				deckCards.push(new Card(number, "\u2665"));
			} else if (i < 39) {
				deckCards.push(new Card(number, "\u2660"));
			} else {
				deckCards.push(new Card(number, "\u2666"));
			}
			number++;
			if (number == 14) {
				number = 1;
			}

		}
		Collections.shuffle(deckCards);
		Collections.shuffle(deckCards);

	}

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
