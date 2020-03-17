package org.krauss.gui.login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
	private JLabel jl_createUser;
	private JCheckBox jc_createUser;
	private JTextField jt_login;
	private JPasswordField jt_password;
	private JButton jb_login;
	private JLabel jl_login_error;
	private DatabaseHandler conn;

	public PanelLogin() {
		this.setLayout(new MigLayout("", "10[grow]10", "10[grow]10"));
		createPanelLogin();
		createLoginComponents();
	}

	private void createPanelLogin() {
		loginPanel = new JPanel();
		loginPanel.setBorder(BorderFactory.createEtchedBorder());
		loginPanel.setLayout(new MigLayout("", "10[grow]10", "10[]10[]10[]10"));
		loginPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4, true));
		loginPanel.setBackground(new Color(0x03853E));
		this.add(loginPanel, "growx, growy");		

	}

	private void createLoginComponents() {
		jt_login = new JTextField("username");
		jt_login.setPreferredSize(new Dimension(150, 22));
		jt_login.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (jt_login.getText().equalsIgnoreCase("")) {
					jt_login.setText("username");
					if(createUserPanel.isVisible()){
						removeCreationPanel();
					}
				} else if (!jt_login.getText().equalsIgnoreCase("")) {
					conn = new DatabaseHandler();
					if (!conn.checkExistingUser(jt_login.getText())) {
						createUserCreationPanel();
					} else if (createUserPanel.isVisible()){
						removeCreationPanel();
					}
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (jt_login.getText().equalsIgnoreCase("username")) {
					jt_login.setText("");
				}

			}
		});

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
		jl_login_error.setFont(new Font("Arial", Font.BOLD, 11));
		jl_login_error.setForeground(Color.YELLOW);
		
		loginPanel.add(new JLabel(new ImageIcon("./resources/Blackjack-Game.png")), "cell 0 0, center");
		loginPanel.add(jt_login, "cell 0 1, center");
		loginPanel.add(jt_password, "cell 0 2, center");
		loginPanel.add(jb_login, "cell 0 3, center");
		loginPanel.add(jl_login_error,"cell 0 4, center" );
		

		createUserPanel = new JPanel();
		createUserPanel.setPreferredSize(new Dimension(150, 25));
		createUserPanel.setLayout(new MigLayout());
		createUserPanel.setBackground(new Color(0x03853E));
		createUserPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));

		jl_createUser = new JLabel("User not found!");
		jl_createUser.setFont(new Font("arial", Font.BOLD, 11));
		jl_createUser.setForeground(Color.YELLOW);
		jc_createUser = new JCheckBox("Create it?");
		jc_createUser.setBackground(new Color(0x03853E));
		jc_createUser.setFont(new Font("arial", Font.BOLD, 11));
		jc_createUser.setForeground(Color.WHITE);

		createUserPanel.add(jl_createUser, "wrap");
		createUserPanel.add(jc_createUser, "wrap");

	}

	private void createUserCreationPanel() {

		loginPanel.remove(jt_password);
		loginPanel.remove(jb_login);
		loginPanel.add(createUserPanel, "cell 0 2, center");
		loginPanel.add(jt_password, "cell 0 3, center");
		loginPanel.add(jb_login, "cell 0 4, center");
		loginPanel.add(jl_login_error, "cell 0 5, center");
		loginPanel.updateUI();
	}

	public void removeCreationPanel() {
		
		jc_createUser.setSelected(false);
		loginPanel.remove(createUserPanel);
		loginPanel.remove(jt_password);
		loginPanel.remove(jb_login);
		loginPanel.add(jt_password, "cell 0 2, center");
		loginPanel.add(jb_login, "cell 0 3, center");
		loginPanel.add(jl_login_error, "cell 0 4, center");
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

	public JCheckBox getJc_createUser() {
		return jc_createUser;
	}

}
