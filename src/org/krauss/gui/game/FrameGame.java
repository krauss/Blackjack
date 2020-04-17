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

	private PanelGame panelGame;
	private Player player;
	private Player dealer;
	private GameLogic game;
	private DatabaseHandler dbHandler;
	private boolean standPressed = false;

	// ToolBar components
	private JToolBar jtb_toolBar;
	private JButton jb_logout;
	private JLabel jl_score;

	public FrameGame(Player pl) {

		this.player = pl;

		this.setTitle("\u2663 \u2665    The BlackJack Game   \u2660 \u2666");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(900, 610));
		this.setSize(900, 610);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		createGameScreen();
		createToolBar();

		this.setVisible(true);
		this.pack();

		initGame();
	}

	private void createGameScreen() {

		panelGame = new PanelGame();

		standPressed = false;

		panelGame.getJb_hit().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				player.setHandCards(game.getDeckCard());

				addCardsOnPanel(player, panelGame.getPlayerCardsPanel());
				panelGame.getPlayerSum().setText("Sum:  " + player.getSum());

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
				panelGame.removeAll();
				panelGame.validate();
				createGameScreen();
				dbHandler = DatabaseHandler.getDbHandler();
				dbHandler.refreshPlayerData(player);
				jl_score.setText("<html><b>Total score: </b>" + player.getScore() + "</html>");
				initGame();
				panelGame.getPlayerCardsPanel().updateUI();
				panelGame.getDealerCardsPanel().updateUI();

			}
		});

		this.add(panelGame);

	}

	private void initGame() {
		player.setHandCards(null);
		dealer = new Player("Dealer");
		game = new GameLogic(player, dealer);

		panelGame.getPlayerName().setText("Player:  " + player.getPlayerName());

		panelGame.getPlayerScore().setText("Score:  " + player.getScore());

		player.setHandCards(game.getDeckCard());
		dealer.setHandCards(game.getDeckCard());
		dealer.getHandCards().get(0).setBackImage(true);
		player.setHandCards(game.getDeckCard());
		dealer.setHandCards(game.getDeckCard());

		addCardsOnPanel(player, panelGame.getPlayerCardsPanel());
		addCardsOnPanel(dealer, panelGame.getDealerCardsPanel());

		panelGame.getPlayerSum().setText("Sum:  " + player.getSum());

	}

	private void addCardsOnPanel(Player player, JPanel p) {

		p.removeAll();
		p.repaint();
		for (Card card : player.getHandCards()) {
			p.add(card, "west, gapleft 5");
		}
	}

	private void terminatesGame(String t) {
		panelGame.getJb_hit().setEnabled(false);
		panelGame.getJb_stand().setEnabled(false);
		panelGame.setLayout(new BorderLayout());
		panelGame.add(panelGame.getOverlayPanel(), BorderLayout.CENTER);
		panelGame.validate();

		switch (t) {
		
		case "LOSE":
			panelGame.getWinLoseLabel().setText("YOU LOSE!");
			break;
		case "WIN":
			panelGame.getWinLoseLabel().setForeground(Color.BLUE);
			panelGame.getWinLoseLabel().setText("YOU WIN!   +50 pts");
			dbHandler = DatabaseHandler.getDbHandler();
			dbHandler.setPlayerScore(player);
			break;
		default:
			panelGame.getWinLoseLabel().setText("YOU GOT A DRAW!");
			break;
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
		panelGame.getJb_replay().setVisible(true);
	}
	
	private void createToolBar() {

		jtb_toolBar = new JToolBar();
		jtb_toolBar.setFloatable(false);
		jtb_toolBar.setLayout(new MigLayout("", "8[]100[]10[]10[]10[]10[]10[]10", "[]"));
		jb_logout = new JButton("<html><b>Logout</b></html>");
		jb_logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});

		Font fo = new Font("Arial", Font.PLAIN, 13);
		JLabel welcome = new JLabel("<html><b>Player: </b>" + player.getPlayerName() + "</html>");
		welcome.setFont(fo);
		jl_score = new JLabel("<html><b>Total score: </b>" + player.getScore() + "</html>");
		jl_score.setFont(fo);
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
		jtb_toolBar.add(jl_score, "cell 3 0, center");
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
