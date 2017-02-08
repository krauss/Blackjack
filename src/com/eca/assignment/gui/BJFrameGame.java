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
				checkAce();

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

		panelGame.getJb_replay().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				conn = new BJDatabaseConn();
				conn.refreshPlayerData(player);
				
				panelGame.getJb_hit().setEnabled(true);
				panelGame.getJb_stand().setEnabled(true);
				panelGame.remove(panelGame.getOverlayPanel());
				initGame();
				panelGame.getPlayerCardsPanel().repaint();
				panelGame.getDealerCardsPanel().repaint();
				panelGame.repaint();
				

			}
		});

		panelGame.getAce1().addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// In case there are more than one ace, it's going to change
				// just the first one.
				for (BJCard d : player.getHandCards()) {
					if (d.getNumber().equalsIgnoreCase("A")) {
						d.setValue(1);
						break;
					}
				}
				panelGame.getPlayerSum().setText("Sum:  " + player.getSum());

			}
		});

		panelGame.getAce11().addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// In case there are more than one ace, it's going to change
				// just the first one.
				for (BJCard d : player.getHandCards()) {
					if (d.getNumber().equalsIgnoreCase("A")) {
						d.setValue(11);
					}
				}
				panelGame.getPlayerSum().setText("Sum:  " + player.getSum());
			}
		});

		this.add(panelGame);
	}

	/**
	 * 
	 * Private method that creates the only instance of the BJGame and also
	 * gives the two firsts cards for the Player and Dealer.
	 * 
	 */
	private void initGame() {
		player.setHandCards(null);
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
		checkAce();

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
		panelGame.getAce1().setEnabled(false);
		panelGame.getAce11().setEnabled(false);
		panelGame.setLayout(new BorderLayout());
		panelGame.add(panelGame.getOverlayPanel(), BorderLayout.EAST);
		if (t.equalsIgnoreCase("LOSE")) {
			panelGame.getWinLoseLabel().setText("YOU LOST!");
		} else if (t.equalsIgnoreCase("WIN")) {
			panelGame.getWinLoseLabel().setForeground(Color.BLUE);
			panelGame.getWinLoseLabel().setText("YOU WON!   +50 pts");
			conn = new BJDatabaseConn();
			conn.setPlayerScore(player);

		} else{
			panelGame.getWinLoseLabel().setForeground(Color.ORANGE);
			panelGame.getWinLoseLabel().setText("NO ONE WON!");

		}
	}

	private void checkAce() {
		for (BJCard d : player.getHandCards()) {
			if (d.getNumber().equalsIgnoreCase("A")) {
				panelGame.getPlayerAce().setText("Ace:  " + d.getNumber() + "" + d.getSuit());
				panelGame.getAce1().setEnabled(true);
				panelGame.getAce1().setSelected(true);
				panelGame.getAce11().setEnabled(true);
			}
		}

	}

	/**
	 * 
	 * When the Player hits STAND button, this method is called to start the
	 * Dealer's step<br>
	 * <br>
	 * The Dealer keeps getting card while:<br>
	 * <b>- sum isn't over 20</b>,<br>
	 * <b>- his hints aren't equal 0</b> <br>
	 * <b>- his sum is still smaller than the Player</b>.
	 * 
	 */
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

					switch (game.checkGameOver(standPressed)) {
					case LOSE:
						terminatesGame("LOSE");
						enableButtonReplay();
						break;
					case WIN:
						terminatesGame("WIN");
						enableButtonReplay();
						break;
					case KEEP:
						terminatesGame("DRAW");
						enableButtonReplay();
						break;
					default:
						break;
					}
				}
			}

		});

	}

	private void enableButtonReplay() {

		panelGame.getJb_replay().setVisible(true);

	}

	/*
	 * public static void main(String[] args) { new BJFrameGame(new
	 * BJPlayer("TEST")); }
	 */
}
