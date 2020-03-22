package org.krauss.gui.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import org.krauss.entity.Card;
import org.krauss.entity.Player;
import org.krauss.game.DatabaseHandler;
import org.krauss.game.GameLogic;
import org.krauss.gui.login.FrameLogin;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class FrameGame extends JFrame {

	private PanelGame gp;
	private Player player;
	private Player dealer;
	private GameLogic game;
	private DatabaseHandler conn;
	private boolean standPressed = false;

	// ToolBar components
	private JToolBar jtb_toolBar;
	private JButton jb_logout;

	public FrameGame(Player pl) {

		this.player = pl;

		this.setTitle("\u2663 \u2665    The BlackJack Game   \u2660 \u2666");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(950, 610));
		this.setSize(950, 610);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		createGameScreen();
		createToolBar();

		this.setVisible(true);
		this.pack();

		initGame();
	}

	private void createGameScreen() {

		gp = new PanelGame();

		standPressed = false;

		gp.getJb_hit().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				player.setHandCards(game.getDeckCard());

				addCardsOnPanel(player, gp.getPlayerCardsPanel());
				gp.getPlayerSum().setText("Sum:  " + player.getSum());
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

		gp.getJb_stand().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gp.getJb_hit().setEnabled(false);
				gp.getJb_stand().setEnabled(false);

				dealer.getHandCards().get(0).setBackImage(false);

				gp.getDealerSum().setText("Sum:  " + dealer.getSum());
				gp.getDealerHints().setText("Hints:  " + game.getDealerHints());
				standPressed = true;
				initDealersGame();
			}
		});

		gp.getJb_replay().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gp.removeAll();
				gp.validate();
				createGameScreen();
				conn = new DatabaseHandler();
				conn.refreshPlayerData(player);
				initGame();
				gp.getPlayerCardsPanel().repaint();
				gp.getDealerCardsPanel().repaint();

			}
		});

		this.add(gp);

	}

	private void initGame() {
		player.setHandCards(null);
		dealer = new Player("Dealer");
		game = new GameLogic(player, dealer);

		gp.getPlayerName().setText("Player:  " + player.getUserName());

		gp.getPlayerScore().setText("Score:  " + player.getScore());

		player.setHandCards(game.getDeckCard());
		dealer.setHandCards(game.getDeckCard());
		dealer.getHandCards().get(0).setBackImage(true);
		player.setHandCards(game.getDeckCard());
		dealer.setHandCards(game.getDeckCard());

		addCardsOnPanel(player, gp.getPlayerCardsPanel());
		addCardsOnPanel(dealer, gp.getDealerCardsPanel());

		gp.getPlayerSum().setText("Sum:  " + player.getSum());
		checkAce();

	}

	private void addCardsOnPanel(Player player, JPanel p) {

		p.removeAll();
		p.repaint();
		for (Card card : player.getHandCards()) {
			p.add(card, "west, gapleft 5");
		}
	}

	private void terminatesGame(String t) {
		gp.getJb_hit().setEnabled(false);
		gp.getJb_stand().setEnabled(false);
		gp.setLayout(new BorderLayout());
		gp.add(gp.getOverlayPanel(), BorderLayout.CENTER);
		gp.validate();

		switch (t) {
		
		case "LOSE":
			gp.getWinLoseLabel().setText("YOU LOSE!");
			break;
		case "WIN":
			gp.getWinLoseLabel().setForeground(Color.BLUE);
			gp.getWinLoseLabel().setText("YOU WIN!   +50 pts");
			conn = new DatabaseHandler();
			conn.setPlayerScore(player);
			break;
		default:
			gp.getWinLoseLabel().setText("YOU GOT A DRAW!");
			break;
		}
	}

	private void checkAce() {
		for (Card d : player.getHandCards()) {
			if (d.getNumber().equalsIgnoreCase("A")) {
				// TODO
			}
		}

	}

	private void initDealersGame() {

		Timer timer = new Timer(3000, null);
		timer.setRepeats(true);
		timer.start();

		timer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (dealer.getSum() < 21 & (game.getDealerHints() > 0) && (dealer.getSum() <= player.getSum())) {
					dealer.setHandCards(game.getDeckCard());
					game.decDealerHints();
					gp.getDealerHints().setText("Hints:  " + game.getDealerHints());
					addCardsOnPanel(dealer, gp.getDealerCardsPanel());
					gp.getDealerSum().setText("Sum:  " + dealer.getSum());
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
					default:
						terminatesGame("DRAW");
						enableButtonReplay();
						break;
					}
				}
			}

		});

	}

	private void enableButtonReplay() {
		gp.getJb_replay().setVisible(true);
	}
	
	private void createToolBar() {

		jtb_toolBar = new JToolBar();
		jtb_toolBar.setFloatable(false);
		jtb_toolBar.setLayout(new MigLayout("", "6[]50[]10[]10[]10[]10[]10[]10", "[]"));
		jb_logout = new JButton("Logout");
		jb_logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});

		Font fo = new Font("Arial", Font.PLAIN, 13);
		JLabel welcome = new JLabel("<html><b>Player: </b>" + player.getUserName() + "</html>");
		welcome.setFont(fo);
		JLabel score = new JLabel("<html><b>Total score: </b>" + player.getScore() + "</html>");
		score.setFont(fo);
		JLabel lastLogin = new JLabel("<html><b>Last login: </b>" + player.getLastLogin() + "</html>");
		lastLogin.setFont(fo);
		JSeparator separator1 = new JSeparator(SwingConstants.VERTICAL);
		separator1.setPreferredSize(new Dimension(5, 20));
		JSeparator separator2 = new JSeparator(SwingConstants.VERTICAL);
		separator2.setPreferredSize(new Dimension(5, 20));
		JSeparator separator3 = new JSeparator(SwingConstants.VERTICAL);
		separator3.setPreferredSize(new Dimension(5, 20));

		jtb_toolBar.add(jb_logout, "cell 0 0, center");
		jtb_toolBar.add(welcome, "cell 1 0, center");
		jtb_toolBar.add(separator1, "cell 2 0, left");
		jtb_toolBar.add(score, "cell 3 0, center");
		jtb_toolBar.add(separator2, "cell 4 0, left");
		jtb_toolBar.add(lastLogin, "cell 5 0, center");
		jtb_toolBar.add(separator3, "cell 6 0, left");
		this.add(jtb_toolBar, BorderLayout.PAGE_END);

	}

	private void logout() {
		this.dispose();
		new FrameLogin();
	}

}
