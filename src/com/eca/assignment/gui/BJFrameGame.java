package com.eca.assignment.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import com.eca.assignment.entity.BJCard;
import com.eca.assignment.entity.BJPlayer;
import com.eca.assignment.game.BJDatabaseConn;
import com.eca.assignment.game.BJGame;

@SuppressWarnings("serial")
public class BJFrameGame extends JFrame {

	private BJPanelGame panelGame;
	// Game Objects
	private BJPlayer player;
	private BJPlayer dealer;
	private BJGame game;
	private BJDatabaseConn conn;
	private boolean standPressed = false;

	public BJFrameGame(BJPlayer p) {
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

		panelGame = new BJPanelGame();

		// Button actions implementations
		panelGame.getJb_hit().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// The Player gets one card from the game deck.
				player.setHandCards(game.getDeckCard());

				// print the cards and the sum
				addCardsOnPanel(player, panelGame.getPlayerCardsPanel());
				panelGame.getPlayerSum().setText("Sum:  " + player.getSum());

				checkFinish();

			}
		});

		// Button STAND's actions
		panelGame.getJb_stand().addActionListener(new ActionListener() {

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
		});

		this.add(panelGame);
	}

	private void initGame() {
		player.setHandCards(null);
		dealer = null;
		game = null;
		dealer = new BJPlayer("Dealer");
		game = new BJGame(player, dealer);

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

	private void addCardsOnPanel(BJPlayer player, JPanel p) {

		p.removeAll();
		p.repaint();
		for (BJCard card : player.getHandCards()) {
			p.add(card, "west, gap 10 0 10 10");
		}
	}

	private void terminatesGame(String t) {
		panelGame.getJb_hit().setEnabled(false);
		panelGame.getJb_stand().setEnabled(false);

		panelGame.setLayout(new BorderLayout());
		panelGame.add(panelGame.getBetPanel(), BorderLayout.CENTER);
		panelGame.getOverlayPanel().repaint();

		if (t.equalsIgnoreCase("LOSE")) {
			panelGame.getWinLoseLabel().setForeground(Color.RED);
			panelGame.getWinLoseLabel().setText("YOU LOST!");
		} else if (t.equalsIgnoreCase("WIN")) {
			panelGame.getWinLoseLabel().setForeground(Color.green);
			panelGame.getWinLoseLabel().setText("YOU WON!   +50 pts");
			conn = new BJDatabaseConn();
			conn.setPlayerScore(player);

		} else {
			panelGame.getWinLoseLabel().setForeground(Color.ORANGE);
			panelGame.getWinLoseLabel().setText("NO ONE WON!");

		}

		createReplayButtonAction();
	}

	private void resetDealerComps() {

		standPressed = false;
		panelGame.getDealerHints().setText("Hints:  ");
		panelGame.getDealerSum().setText("Sum:  ");

	}

	private void createReplayButtonAction() {

		panelGame.getJb_replay().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				conn = new BJDatabaseConn();
				conn.refreshPlayerData(player);

				panelGame.getJb_hit().setEnabled(true);
				panelGame.getJb_stand().setEnabled(true);
				panelGame.remove(panelGame.getOverlayPanel());
				resetDealerComps();
				initGame();
				panelGame.repaint();

			}
		});
	}

	private void initDealersGame() {

		Timer timer = new Timer(2500, null);
		timer.setRepeats(true);
		timer.start();

		timer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Dealer will keep hinting if its sum is under 20,
				// its hints is under 3 and its sum is smaller than the player

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
			enableButtonReplay();
			break;
		case WIN:
			terminatesGame("WIN");
			enableButtonReplay();
			break;
		default:
			break;
		}
	}

	private void enableButtonReplay() {

		panelGame.getJb_replay().setVisible(true);

	}

	/*
	 * public static void main(String[] args) { new BJFrameGame(new
	 * BJPlayer("TEST")); }
	 */
}
