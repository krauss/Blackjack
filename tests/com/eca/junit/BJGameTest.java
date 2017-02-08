package com.eca.junit;

import static org.junit.Assert.*;
import org.junit.Test;

import com.eca.assignment.entity.BJCard;
import com.eca.assignment.entity.BJClubsCard;
import com.eca.assignment.entity.BJHeartsCard;
import com.eca.assignment.entity.BJPlayer;
import com.eca.assignment.entity.BJSpadesCard;
import com.eca.assignment.game.BJGame;
import com.eca.assignment.game.BJGame.GameStatus;

public class BJGameTest {
	
	BJPlayer player = new BJPlayer("Player");
	BJPlayer dealer = new BJPlayer("dealer");
	BJGame game = new BJGame(player, dealer);

	
	@Test
	public void testCheckGameOver(){
		System.out.println("Testing whether the game finishes or not based on their somatory");
		System.out.println("Giving player three cards...");		
		player.setHandCards(new BJClubsCard(9));
		player.setHandCards(new BJHeartsCard(10));
		player.setHandCards(new BJSpadesCard(1));
		System.out.println("Giving dealer three cards...");
		dealer.setHandCards(new BJClubsCard(10));
		dealer.setHandCards(new BJHeartsCard(10));
		dealer.setHandCards(new BJSpadesCard(1));		
		
		assertEquals(game.checkGameOver(true), GameStatus.LOSE);
	}

	
}
