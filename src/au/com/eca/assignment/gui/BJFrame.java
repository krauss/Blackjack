package au.com.eca.assignment.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import au.com.eca.assignment.entity.BJCard;
import au.com.eca.assignment.entity.BJPlayer;
import au.com.eca.assignment.game.BJGame;
import au.com.eca.assignment.game.BJDatabaseConn;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class BJFrame extends JFrame {

	// Container components
	private BJTabbedPanel tabbedpanel;
	private BJPanelLogin panelLogin;
	private BJPanelStart panelStart;
	private BJPanelGame panelGame;
	private MigLayout layout_jframe;

	// Game Objects
	private BJPlayer player;
	private BJPlayer dealer;
	private BJGame game;
	private BJDatabaseConn conn;
	private boolean standPressed = false;

	public BJFrame() {

		createJFrame();
		createJTabbedPanel();
		createJPanelLogin();

	}

	private void createJFrame() {
		this.setTitle(" .: Blackjack :.");
		this.setSize(500, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.layout_jframe = new MigLayout("", "20[600]20", "20[460]20");
		this.setLayout(layout_jframe);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.pack();

	}

	private void createJTabbedPanel() {
		tabbedpanel = new BJTabbedPanel();
		this.add(tabbedpanel);

	}

	private void createJPanelLogin() {

		// Creates de Login panel using the BJPanelLogin class
		panelLogin = new BJPanelLogin();

		// Adds the login action to the button
		panelLogin.getJb_login().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (panelLogin.getJt_login().getText().trim().length() != 0
						&& panelLogin.getJt_password().getPassword().length != 0) {
					conn = new BJDatabaseConn();
					try {
						player = conn.getAuthentication(panelLogin.getJt_login().getText(),
								panelLogin.getJt_password().getPassword());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if (player != null) {

						createJPanelStart();

					} else {
						panelLogin.getJl_login_error().setText("<html>User and/or Pass incorrect!</html>");
					}

				}
			}
		});

		// Add the JPanel component to the container JTabbedPanel
		tabbedpanel.add("Login", panelLogin);

	}

	private void createJPanelStart() {
		// Creates the Start Game panel using the BJPanelStart class
		panelStart = new BJPanelStart();

		panelStart.getJb_start().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Removes the actual tab at the index 1
				panelStart.removeAll();
				tabbedpanel.removeTabAt(1);

				createJPanelGame();
				initGame();

			}
		});

		if (tabbedpanel.getTabCount() > 1) {
			tabbedpanel.removeTabAt(1);
		}
		tabbedpanel.add("Game", panelStart);
		tabbedpanel.setSelectedIndex(1);

	}

	private void createJPanelGame() {
		// Creates the new tab using the BJPanelGame class
		panelGame = new BJPanelGame();
		// Adds it to the container JTabbedPanel
		tabbedpanel.addTab(" Game ", panelGame);
		// Place the tab focus on the new tab created
		tabbedpanel.setSelectedIndex(1);
		
		standPressed = false;
		
		//Button HIT's actions
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
		
		//Button STAND's actions
		panelGame.getJb_stand().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panelGame.getJb_hit().setEnabled(false);
				panelGame.getJb_stand().setEnabled(false);
				
				dealer.getHandCards().get(0).setVisible(true);
				
				panelGame.getDealerSum().setText("Sum:  "+dealer.getSum());
				panelGame.getDealerHints().setText("Hints:  "+game.getDealerHints());
				standPressed = true;
				initDealersGame();
			}
		});
		
		panelGame.getJb_replay().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createJPanelStart();
				conn = new BJDatabaseConn();
				conn.refreshPlayerData(player);
				panelGame.getPlayerCardsPanel().repaint();
				panelGame.getDealerCardsPanel().repaint();
				
			}
		});

		panelGame.getAce1().addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				changeAceValue();
				
			}
		});

		panelGame.getAce11().addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				changeAceValue();
			}
		});

	}

	
	
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
		
		dealer.getHandCards().get(0).setVisible(false);

	}

	private void addCardsOnPanel(BJPlayer player, JPanel p) {
		
		p.removeAll();
		p.repaint();
		for (BJCard card : player.getHandCards()) {
			p.add(card, "center");
		}		
	}

	
	private void terminatesGame(String t) {
		panelGame.getJb_hit().setEnabled(false);
		panelGame.getJb_stand().setEnabled(false);
		panelGame.getAce1().setEnabled(false);
		panelGame.getAce11().setEnabled(false);
		if (t.equalsIgnoreCase("LOSE")) {
			panelGame.getWinLoseLabel().setText("YOU LOST!");
		} else {
			panelGame.getWinLoseLabel().setForeground(Color.green);
			panelGame.getWinLoseLabel().setText("YOU WON!   +100 pts");
			conn = new BJDatabaseConn();
			conn.setPlayerScore(player);
			
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

	private void changeAceValue() {
		
		if (panelGame.getAce1().isSelected()) {

			for (BJCard d : player.getHandCards()) {
				if (d.getNumber().equalsIgnoreCase("A")) {
					d.setValue(1);
				}
			}
		} else if (panelGame.getAce11().isSelected()) {

			for (BJCard d : player.getHandCards()) {
				if (d.getNumber().equalsIgnoreCase("A")) {
					d.setValue(11);
				}
			}

		}
		panelGame.getPlayerSum().setText("Sum:  " + player.getSum());

	}

	private void initDealersGame() {

		Timer timer = new Timer(3000, null);
		timer.setRepeats(true);
		timer.start(); 
		
		timer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Dealer will keep hinting if its sum is under 20, 
				//its hints is under 3 and its sum is smaller than the player
				
				if(dealer.getSum() < 20 && (game.getDealerHints() > 0 ) && (dealer.getSum() <= player.getSum())){
					dealer.setHandCards(game.getDeckCard());
					game.decDealerHints();
					panelGame.getDealerHints().setText("Hints:  "+game.getDealerHints());
					addCardsOnPanel(dealer, panelGame.getDealerCardsPanel());
					panelGame.getDealerSum().setText("Sum:  "+dealer.getSum());
				}else{
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
}
