package org.krauss.gui.login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Timer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.krauss.game.DatabaseHandler;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class PanelLogin extends JPanel {

	private JPanel loginPanel;
	private JPanel createUserPanel;
	private JLabel jl_createPlayer;
	private JCheckBox jc_createPlayer;
	private JTextField jt_login;
	private JPasswordField jt_password;
	private JLabel jl_playername;
	private JLabel jl_password;
	private JLabel jl_database;
	private JButton jb_login;
	private JLabel jl_login_error;
	private JLabel jl_adminPass;
	private JLabel jl_9_34;
	private DatabaseHandler conn;
	private Timer showAdminAccess;

	public PanelLogin() {
		this.setLayout(new MigLayout("", "10[grow]10", "10[grow]10"));
		createPanelLogin();
		createLoginComponents();
	}

	private void createPanelLogin() {
		loginPanel = new JPanel();
		loginPanel.setBorder(BorderFactory.createEtchedBorder());
		loginPanel.setLayout(new MigLayout("", "20[grow]10", "10[]10"));
		loginPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4, true));
		loginPanel.setBackground(new Color(0x03853E));
		this.add(loginPanel, "growx, growy");

	}

	private void createLoginComponents() {
		jt_login = new JTextField("playername");
		jt_login.setPreferredSize(new Dimension(150, 22));

		jt_login.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (jt_login.getText().equalsIgnoreCase("")) {
					jt_login.setText("playername");
					if (createUserPanel.isVisible()) {
						removeCreationPanel();
					}
				} else if (!jt_login.getText().equalsIgnoreCase("") & validatePlayername(jt_login.getText())) {
					if (!jl_login_error.getText().isEmpty()) {
						jl_login_error.setText("");
					}
					conn = new DatabaseHandler();
					if (!conn.checkExistingUser(jt_login.getText())) {
						createPlayerCreationPanel();
					} else if (createUserPanel.isVisible()) {
						removeCreationPanel();
					}
				} else {
					jl_login_error.setText("Sorry mate, the playername is invalid");
					if (createUserPanel.isVisible()) {
						removeCreationPanel();
					}
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (jt_login.getText().equalsIgnoreCase("playername")) {
					jt_login.setText("");
				}

			}
		});

		jt_login.setToolTipText(
				"Pick a playername no longer than 10 characters with only\n vowals and consonants. No special characters, please!");

		jt_password = new JPasswordField();
		jt_password.setPreferredSize(new Dimension(150, 22));
		jt_password.setText("password");
		jt_password.addFocusListener(new FocusListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void focusLost(FocusEvent e) {
				if (jt_password.getText().equalsIgnoreCase("")) {
					jt_password.setText("password");

				}
			}

			@SuppressWarnings("deprecation")
			@Override
			public void focusGained(FocusEvent e) {
				if (jt_password.getText().equalsIgnoreCase("password")) {
					jt_password.setText("");

				}

			}
		});

		jb_login = new JButton("Login");

		jl_login_error = new JLabel("");
		jl_login_error.setFont(new Font("Arial", Font.BOLD, 13));
		jl_login_error.setForeground(Color.ORANGE);

		URL pathToImg = getClass().getResource("/Blackjack-Game.png");
		loginPanel.add(new JLabel(new ImageIcon(pathToImg)), "dock north");

		jl_playername = new JLabel("Playername: ");
		jl_playername.setForeground(Color.WHITE);
		loginPanel.add(jl_playername, "cell 0 1, center");
		loginPanel.add(jt_login, "cell 0 1, center");
		jl_password = new JLabel("Password: ");
		jl_password.setForeground(Color.WHITE);
		jl_database = new JLabel("Database: ");
		jl_database.setForeground(Color.WHITE);
		loginPanel.add(jl_password, "cell 0 2, center");
		loginPanel.add(jt_password, "cell 0 2, center");
		loginPanel.add(jb_login, "cell 0 3, center");
		loginPanel.add(jl_login_error, "cell 0 4, center");
		jl_adminPass = new JLabel("Admin access:  admin | password");
		jl_adminPass.setForeground(new Color(0x02642e));
		jl_adminPass.setVisible(false);
		jl_9_34 = new JLabel("9\u00BE");
		jl_9_34.setForeground(new Color(0x02642e));
		loginPanel.add(jl_9_34, "cell 0 5, center");

		jl_9_34.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jl_adminPass.setVisible(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				jl_adminPass.setVisible(true);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		loginPanel.add(jl_adminPass, "cell 0 6, center");

		showAdminAccess = new Timer(2500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loginPanel.remove(jl_adminPass);
				loginPanel.remove(jl_9_34);
				loginPanel.updateUI();
				showAdminAccess.stop();
			}
		});
		showAdminAccess.setRepeats(false);
		showAdminAccess.start();

		createUserPanel = new JPanel();
		createUserPanel.setPreferredSize(new Dimension(150, 25));
		createUserPanel.setLayout(new MigLayout());
		createUserPanel.setBackground(new Color(0x03853E));
		createUserPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));

		jl_createPlayer = new JLabel("Player not found! ");
		jl_createPlayer.setFont(new Font("arial", Font.BOLD, 12));
		jl_createPlayer.setForeground(Color.ORANGE);
		jc_createPlayer = new JCheckBox("Create? ");
		jc_createPlayer.setBackground(new Color(0x03853E));
		jc_createPlayer.setFont(new Font("arial", Font.BOLD, 12));
		jc_createPlayer.setForeground(Color.WHITE);

		createUserPanel.add(jl_createPlayer, "wrap");
		createUserPanel.add(jc_createPlayer, "wrap");

	}

	private boolean validatePlayername(String playername) {
		Pattern regex = Pattern.compile("[a-zA-Z_0-9]*");
		Matcher matcher = regex.matcher(playername);
		
		return matcher.matches() ? true : false;
	}

	private void createPlayerCreationPanel() {

		loginPanel.remove(jt_password);
		loginPanel.remove(jb_login);
		loginPanel.add(jl_database, "cell 0 2, center");
		loginPanel.add(createUserPanel, "cell 0 2, center");
		loginPanel.add(jl_password, "cell 0 4, center");
		loginPanel.add(jt_password, "cell 0 4, center");
		loginPanel.add(jb_login, "cell 0 5, center");
		loginPanel.add(jl_login_error, "cell 0 6, center");
		jl_password.setText("New passw: ");
		loginPanel.updateUI();
	}

	public void removeCreationPanel() {

		jc_createPlayer.setSelected(false);
		loginPanel.remove(jl_database);
		loginPanel.remove(createUserPanel);
		loginPanel.remove(jt_password);
		loginPanel.remove(jb_login);
		loginPanel.add(jl_password, "cell 0 2, center");
		loginPanel.add(jt_password, "cell 0 2, center");
		loginPanel.add(jb_login, "cell 0 3, center");
		loginPanel.add(jl_login_error, "cell 0 4, center");
		jl_password.setText("Password: ");
		loginPanel.updateUI();
	}

	public JPanel getLoginPanel() {
		return loginPanel;
	}

	public JTextField getJt_login() {
		return jt_login;
	}

	public JPasswordField getJt_password() {
		return jt_password;
	}

	public JButton getJb_login() {
		return jb_login;
	}

	public JLabel getJl_login_error() {
		return jl_login_error;
	}

	public JCheckBox getJc_createPlayer() {
		return jc_createPlayer;
	}

}
