package com.eca.assignment.gui.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import com.eca.assignment.entity.Card;
import com.eca.assignment.entity.Player;
import com.eca.assignment.game.DBConnection;
import com.eca.assignment.game.Game;

@SuppressWarnings("serial")
public class FrameAdmin extends JFrame {

	private PanelAdmin panelGame;
	// Game Objects
	private Player player;
	private Player dealer;
	private Game game;
	private DBConnection conn;
	private boolean standPressed = false;

	public FrameAdmin(Player p) {
		this.player = p;

		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(750, 690));
		this.setSize(750, 690);
		this.setLocationRelativeTo(null);

		initPanelGame();
		initGame();

		this.pack();
		this.setVisible(true);
	}
	
	private void initPanelGame() {

		panelGame = new PanelAdmin();

		// HIT button's action
		panelGame.getJb_hit().addActionListener(new ACHit());
		// STAND button's action
		panelGame.getJb_stand().addActionListener(new ACStand());
		// Replay Button's action
		panelGame.getJb_replay().addActionListener(new ACReplay());

		this.add(panelGame);
	}

	private void initGame() {
		player.setHandCards(null);
		dealer = null;
		game = null;
		dealer = new Player("Dealer");
		game = new Game(player, dealer);

		// Sets the Player name on the JLabel
		panelGame.getPlayerName().setText("Player:  " + player.getName());

		// Shows the previous player's score
		panelGame.getPlayerScore().setText("Score:  " + player.getScore());

		// Give him the two initial cards each, to the player and dealer
		player.setHandCards(game.getDeckCard());
		dealer.setHandCards(game.getDeckCard());
		player.setHandCards(game.getDeckCard());
		dealer.setHandCards(game.getDeckCard());

		addCardsOnPanel(player, panelGame.getPlayerCardsPanel());
		addCardsOnPanel(dealer, panelGame.getDealerCardsPanel());

		panelGame.getPlayerSum().setText("Sum:  " + player.getSum());

		dealer.getHandCards().get(0).setBackImage(true);

	}

	private void addCardsOnPanel(Player player, JPanel p) {

		p.removeAll();
		p.repaint();
		for (Card card : player.getHandCards()) {
			p.add(card, "west, gap 10 0 10 10");
		}
	}

	private void terminatesGame(String t) {
		panelGame.getJb_hit().setEnabled(false);
		panelGame.getJb_stand().setEnabled(false);

		if (t.equalsIgnoreCase("LOSE")) {
			panelGame.getWinLoseLabel().setForeground(Color.RED);
			panelGame.getWinLoseLabel().setText("YOU LOST!");
		} else if (t.equalsIgnoreCase("WIN")) {
			panelGame.getWinLoseLabel().setForeground(Color.green);
			panelGame.getWinLoseLabel().setText("YOU WON!   +50 pts");
			conn = new DBConnection();
			conn.setPlayerScore(player);

		} else {
			panelGame.getWinLoseLabel().setForeground(Color.ORANGE);
			panelGame.getWinLoseLabel().setText("NO ONE WON!");

		}
		
		panelGame.setLayout(new BorderLayout());
		panelGame.validate();
		panelGame.add(panelGame.getBetPanel(), BorderLayout.CENTER);
		panelGame.getBetPanel().repaint();
		
	}

	private void resetDealerComps() {

		standPressed = false;
		panelGame.getDealerHints().setText("Hints:  ");
		panelGame.getDealerSum().setText("Sum:  ");

	}

	private void initDealersGame() {

		Timer timer = new Timer(2500, null);
		timer.setRepeats(true);
		timer.start();

		timer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (dealer.getSum() < 20 && (game.getDealerHints() > 0) && (dealer.getSum() <= player.getSum())) {
					dealer.setHandCards(game.getDeckCard());
					game.decDealerHints();
					panelGame.getDealerHints().setText("Hints:  " + game.getDealerHints());
					addCardsOnPanel(dealer, panelGame.getDealerCardsPanel());
					panelGame.getDealerSum().setText("Sum:  " + dealer.getSum());
				} else {
					timer.stop();

					checkFinish();

				}
			}

		});

	}

	private void checkFinish() {
		
		switch (game.checkGameOver(standPressed)) {
		case LOSE:
			terminatesGame("LOSE");
			break;
		case WIN:
			terminatesGame("WIN");
			break;
		default:
			break;
		}
	}

	
	
	private class ACHit implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// The Player gets one card from the game deck.
			player.setHandCards(game.getDeckCard());

			// print the cards and the sum
			addCardsOnPanel(player, panelGame.getPlayerCardsPanel());
			panelGame.getPlayerSum().setText("Sum:  " + player.getSum());

			checkFinish();
			
		}
		
	}
	
	private class ACStand implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			panelGame.getJb_hit().setEnabled(false);
			panelGame.getJb_stand().setEnabled(false);

			dealer.getHandCards().get(0).setBackImage(false);

			panelGame.getDealerSum().setText("Sum:  " + dealer.getSum());
			panelGame.getDealerHints().setText("Hints:  " + game.getDealerHints());
			standPressed = true;
			initDealersGame();
		}
		
	}
	
	private class ACReplay implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			conn = new DBConnection();
			conn.refreshPlayerData(player);

			panelGame.getJb_hit().setEnabled(true);
			panelGame.getJb_stand().setEnabled(true);
			panelGame.remove(panelGame.getOverlayPanel());
			resetDealerComps();
			initGame();
			panelGame.updateUI();
		}
		
	}

}
