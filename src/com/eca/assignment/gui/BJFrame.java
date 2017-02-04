package com.eca.assignment.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.eca.assignment.entity.BJCard;
import com.eca.assignment.entity.BJPlayer;
import com.eca.assignment.game.BJGame;
import com.eca.assignment.game.BJDatabaseConn;
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
	
	/**
	 * 
	 * Default constructor runs these 3 private methods that start the login panel.
	 * 
	 * It was used JTabbedPanel in order to easily swap panels without having to 
	 * create different JFrame
	 * 
	 *   
	 */
	public BJFrame() {
		
		this.setSize(600, 460);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.layout_jframe = new MigLayout("", "0[600]0", "0[460]0");
		this.setLayout(layout_jframe);		
		this.setLocationRelativeTo(null);		
		this.pack();
		
		createJPanelLogin();	
		
		//It gives the initial focus to the Login button
		this.getRootPane().setDefaultButton(panelLogin.getJb_login());
		panelLogin.getJb_login().requestFocus();
		
		this.setVisible(true);
	}
	
	
	/**
	 * 
	 * Private method that creates an instance of BJPanelLogin.
	 * 
	 */
	private void createJPanelLogin() {

		// Creates de Login panel using the BJPanelLogin class
		panelLogin = new BJPanelLogin();

		// Adds the login action to the button
		
		panelLogin.getJb_login().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(panelLogin.getJc_createUser().isSelected()){
					conn = new BJDatabaseConn();
					
					conn.insertNewUser(panelLogin.getJt_login().getText(), panelLogin.getJt_password().getPassword(), panelLogin.getJt_login().getText());
					
					player = new BJPlayer(panelLogin.getJt_login().getText());
					player.setName(panelLogin.getJt_login().getText());
					panelLogin.removeCreationPanel();
					createJPanelStart();
					
				}else if (panelLogin.getJt_login().getText().trim().length() != 0
						&& panelLogin.getJt_password().getPassword().length != 0) {
					conn = new BJDatabaseConn();
					try {
						player = conn.authenticate(panelLogin.getJt_login().getText(),
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

		this.add(panelLogin, "growx, growy");

	}
	
	/**
	 * 
	 * Private method that creates an instance of BJPanelStart. It is called after the login step
	 * succeeds. 
	 * 
	 */
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
	
	/**
	 * 
	 * Private method that creates an instance of BJPanelGame. It is called when the user 
	 * press the button 'Start' at the StartPanel
	 * 
	 * Everything regarded to GUI is defined on the BJPanelGame class. Therefore, <b>ALL</b> the actions performed
	 * by any component is implemented in this class, more specifically, in this method. 
	 * 
	 */
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
				//In case there are more than one ace, it's going to change just the first one.
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
				//In case there are more than one ace, it's going to change just the first one.
				for (BJCard d : player.getHandCards()) {
					if (d.getNumber().equalsIgnoreCase("A")) {
						d.setValue(11);
					}
				}
				panelGame.getPlayerSum().setText("Sum:  " + player.getSum());
			}
		});

	}

	
	/**
	 * 
	 * Private method that creates the only instance of the BJGame and also gives the two firsts cards 
	 * for the Player and Dealer. 
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
		
		dealer.getHandCards().get(0).setVisible(false);

	}

	private void addCardsOnPanel(BJPlayer player, JPanel p) {
		
		p.removeAll();
		p.repaint();
		for (BJCard card : player.getHandCards()) {
			p.add(card, "west, h 40!");
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
	
	/**
	 *  
	 * When the Player hits STAND button, this method is called to start the Dealer's fase.<br>
	 * <br>
	 * The Dealer keeps getting card while:<br>
	 * <b>- sum isn't over 20</b>,<br> 
	 * <b>- his hints aren't equal 0</b> <br>
	 * <b>- his sum is still smaller than the Player</b>.  
	 * 
	 */	
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
