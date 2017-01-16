package au.com.eca.assignment.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class BJPanelGame extends JPanel {

	// Dealer components
	private JPanel gamePanelDealer;
	private JPanel dealerCardsPanel;
	private JPanel controlPanelDealer;
	private JPanel replayPanelDealer;
	private JLabel dealerCardslabel;
	private JLabel dealerSum;
	private JLabel dealerHints;
	private JLabel dealerName;
	private JLabel dealerScore;
	private JButton jb_replay;
	
	// Player components
	private JPanel gamePanelPLayer;
	private JPanel playerCardsPanel;
	private JPanel controlPanelPLayer;
	private JPanel hitStandPanel;
	private JButton jb_hit;
	private JButton jb_stand;
	private JLabel playerCardslabel;
	private JLabel playerSum;
	private JLabel playerAce;
	private JRadioButton ace1;
	private JRadioButton ace11;
	private String one = "1";
	private String eleven = "11";
	private ButtonGroup radiogroup;
	private JLabel playerName;
	private JLabel playerScore;
	private JLabel winLoseLabel;

	public BJPanelGame() {

		this.setLayout(new MigLayout("", "10[grow]10[grow]10", "10[grow]10"));
		
		createPanelGamePlayer();
		createPanelGameDealer();

	}
	
	
	//The Player side of the game
	private void createPanelGamePlayer() {
		// Creates the player back panel
		gamePanelPLayer = new JPanel();
		gamePanelPLayer.setLayout(new MigLayout("", "10[250]10", "10[140]10[140]10"));
		this.add(gamePanelPLayer);

		// Creates the panel with the two buttons, HIT and STAND
		playerCardsPanel = new JPanel();
		playerCardsPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
		playerCardsPanel.setBackground(new Color(0x03853E));
		playerCardsPanel.setLayout(new MigLayout("", "5[grow]5", "5[80]5"));

		// Creates the control panel
		controlPanelPLayer = new JPanel();
		controlPanelPLayer.setBorder(BorderFactory.createEtchedBorder());
		controlPanelPLayer.setLayout(new MigLayout("", "5[grow]5", "5[60]5"));

		playerSum = new JLabel();
		playerAce = new JLabel("Ace:");
		ace1 = new JRadioButton(one);
		ace11 = new JRadioButton(eleven);
		playerName = new JLabel();
		playerScore = new JLabel();

		playerSum.setFont(new Font("arial", Font.PLAIN, 12));
		playerAce.setFont(new Font("arial", Font.PLAIN, 12));
		playerName.setFont(new Font("arial", Font.PLAIN, 12));
		playerScore.setFont(new Font("arial", Font.PLAIN, 12));

		ace1.setEnabled(false);
		ace1.setActionCommand(one);
		ace11.setEnabled(false);
		ace11.setActionCommand(eleven);

		radiogroup = new ButtonGroup();
		radiogroup.add(ace1);
		radiogroup.add(ace11);

		controlPanelPLayer.add(playerSum, "wrap");
		controlPanelPLayer.add(playerAce);
		controlPanelPLayer.add(ace1);
		controlPanelPLayer.add(ace11, "wrap");
		controlPanelPLayer.add(playerName, "wrap");
		controlPanelPLayer.add(playerScore);

		// Creates the panel with the two buttons, HIT and STAND
		hitStandPanel = new JPanel();
		hitStandPanel.setLayout(new MigLayout("", "5[145]5", "5[30]15[30]5"));

		jb_hit = new JButton("Hit");
		jb_stand = new JButton("Stand");
		winLoseLabel = new JLabel();
		winLoseLabel.setFont(new Font("arial", Font.BOLD, 17));
		winLoseLabel.setForeground(Color.RED);

		hitStandPanel.add(jb_hit, "growx");
		hitStandPanel.add(jb_stand, "growx, wrap");
		hitStandPanel.add(winLoseLabel, "span 2");

		gamePanelPLayer.add(playerCardsPanel, " growx, wrap");
		gamePanelPLayer.add(controlPanelPLayer, "growx, wrap");
		gamePanelPLayer.add(hitStandPanel);
	}
	
	
	//The Dealer side of the game
	private void createPanelGameDealer() {
		gamePanelDealer = new JPanel();
		gamePanelDealer.setLayout(new MigLayout("", "10[250]10", "10[140]10[140]10"));
		this.add(gamePanelDealer);

		// Creates the panel with the two buttons, HIT and STAND
		dealerCardsPanel = new JPanel();
		dealerCardsPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
		dealerCardsPanel.setBackground(new Color(0x03853E));
		dealerCardsPanel.setLayout(new MigLayout("", "5[grow]5", "5[80]5"));

		// Creates the control panel
		controlPanelDealer = new JPanel();
		controlPanelDealer.setBorder(BorderFactory.createEtchedBorder());
		controlPanelDealer.setLayout(new MigLayout("", "5[grow]5", "5[60]5"));
		
		dealerSum = new JLabel();
		dealerHints = new JLabel("Hints:  ");
		dealerName = new JLabel("Name:  Dealer");
		dealerScore = new JLabel();

		dealerSum.setFont(new Font("arial", Font.PLAIN, 12));
		dealerHints.setFont(new Font("arial", Font.PLAIN, 12));
		dealerName.setFont(new Font("arial", Font.PLAIN, 12));
		dealerScore.setFont(new Font("arial", Font.PLAIN, 12));
		
		controlPanelDealer.add(dealerSum, "wrap");
		controlPanelDealer.add(dealerHints, "wrap");
		controlPanelDealer.add(dealerName, "wrap");
		controlPanelDealer.add(dealerScore);
		
		replayPanelDealer = new JPanel();
		replayPanelDealer.setLayout(new MigLayout("", "5[grow]5", "5[30]5"));
		
		jb_replay = new JButton("replay");
		jb_replay.setVisible(false);
		
		replayPanelDealer.add(jb_replay, "center");
		
		gamePanelDealer.add(dealerCardsPanel, "growx, wrap");
		gamePanelDealer.add(controlPanelDealer, "growx, wrap");
		gamePanelDealer.add(replayPanelDealer);
		
	}

	public JPanel getGamePanelPLayer() {
		return gamePanelPLayer;
	}

	public JPanel getGamePanelDealer() {
		return gamePanelDealer;
	}

	public JPanel getHitStandPanel() {
		return hitStandPanel;
	}

	public JPanel getPlayerCardsPanel() {
		return playerCardsPanel;
	}

	public JPanel getDealerCardsPanel() {
		return dealerCardsPanel;
	}

	public JPanel getControlPanelPLayer() {
		return controlPanelPLayer;
	}

	public JButton getJb_hit() {
		return jb_hit;
	}

	public JButton getJb_stand() {
		return jb_stand;
	}

	public JLabel getPlayerCardslabel() {
		return playerCardslabel;
	}

	public JLabel getWinLoseLabel() {
		return winLoseLabel;
	}

	public JLabel getPlayerName() {
		return playerName;
	}

	public JLabel getPlayerScore() {
		return playerScore;
	}

	public JLabel getPlayerSum() {
		return playerSum;
	}

	public JLabel getPlayerAce() {
		return playerAce;
	}

	public JRadioButton getAce1() {
		return ace1;
	}

	public JRadioButton getAce11() {
		return ace11;
	}

	public ButtonGroup getRadiogroup() {
		return radiogroup;
	}

	public JPanel getControlPanelDealer() {
		return controlPanelDealer;
	}

	public JLabel getDealerCardslabel() {
		return dealerCardslabel;
	}


	public JLabel getDealerSum() {
		return dealerSum;
	}


	public JLabel getDealerName() {
		return dealerName;
	}


	public JLabel getDealerScore() {
		return dealerScore;
	}


	public JLabel getDealerHints() {
		return dealerHints;
	}


	public JPanel getReplayPanelDealer() {
		return replayPanelDealer;
	}


	public JButton getJb_replay() {
		return jb_replay;
	}

}
