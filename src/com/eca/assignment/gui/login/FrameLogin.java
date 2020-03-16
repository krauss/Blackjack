package com.eca.assignment.gui.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.Timer;
import com.eca.assignment.entity.Player;
import com.eca.assignment.game.DBConnection;
import com.eca.assignment.gui.game.FrameGame;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class FrameLogin extends JFrame {

	// Container components
	private PanelLogin panelLogin;
	private MigLayout layout_jframe;

	// Game Objects
	private Player player;
	private DBConnection conn;
	private Timer timer;
	private String text = "<html><b><font color=\"#00FF00\"><br>Cool, let's play!</font></b></html>";

	public FrameLogin() {

		this.setResizable(false);
		this.setSize(600, 460);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.layout_jframe = new MigLayout("", "0[600]0", "0[460]0");
		this.setLayout(layout_jframe);
		this.setLocationRelativeTo(null);
		this.pack();

		createJPanelLogin();

		// It gives the initial focus to the Login button
		this.getRootPane().setDefaultButton(panelLogin.getJb_login());
		panelLogin.getJb_login().requestFocus();

		this.setVisible(true);
	}

	private void createJPanelLogin() {

		// Creates de Login panel using the BJPanelLogin class
		panelLogin = new PanelLogin();

		// Initialize the timer
		timer = new Timer(2000, null);
		timer.setRepeats(false);

		timer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createJPanelGame();
			}
		});

		// Adds the login action to the button
		panelLogin.getJb_login().addActionListener(new ACLogin());
		this.add(panelLogin, "growx, growy");

	}

	private void createJPanelGame() {
		new FrameGame(player);
		this.setVisible(false);
		timer.stop();

	}

	private class ACLogin implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (panelLogin.getJc_createUser().isSelected()) {
				conn = new DBConnection();

				conn.insertNewUser(panelLogin.getJt_login().getText(), panelLogin.getJt_password().getPassword(),
						panelLogin.getJt_login().getText());

				player = new Player(panelLogin.getJt_login().getText());
				player.setName(panelLogin.getJt_login().getText());
				panelLogin.getJl_login_error().setText(text);
				panelLogin.getJb_login().setText("<html>&#x1F44D;</html>");
				panelLogin.getJb_login().setEnabled(false);
				// It executes the actionPerformed method from the
				// ActionListener previously defined
				timer.start();
				panelLogin.removeCreationPanel();

			} else if ((panelLogin.getJt_login().getText().trim().length() != 0)
					&& (panelLogin.getJt_password().getPassword().length != 0)) {
				try {

					conn = new DBConnection();
					player = conn.authenticate(panelLogin.getJt_login().getText(),
							panelLogin.getJt_password().getPassword());

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (player != null) {

					panelLogin.getJl_login_error().setText(text);
					panelLogin.getJb_login().setText("<html>&#x1F44D;</html>");
					timer.start();

				} else {
					panelLogin.getJl_login_error().setText("<html>&#x26D4; <b>User and/or Pass incorrect!</b></html>");
				}

			}
		}

	}
}
