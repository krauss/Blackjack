package com.eca.assignment.gui.login;

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
import javax.swing.SwingConstants;

import com.eca.assignment.game.DBConnection;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class PanelLogin extends JPanel {
	
	private JPanel welcomeLoginPanel;
	private JPanel loginPanel;
	private JPanel createUserPanel;
	private JLabel jl_createUser;
	private JCheckBox jc_createUser;
	private JTextField jt_login;
	private JPasswordField jt_password;
	private JButton jb_login;
	private JLabel jl_login_error;
	private JLabel bjimage;
	private DBConnection conn;

	public PanelLogin() {
		this.setLayout(new MigLayout("", "20[grow]20[grow]20", "20[200]20[grow]20"));
		this.setBackground(Color.BLACK);
		createPanelLogin();
		createLoginComponents();	
	}

	private void createPanelLogin() {	
		
		welcomeLoginPanel = new JPanel();
		welcomeLoginPanel.setBorder(BorderFactory.createEtchedBorder());
		welcomeLoginPanel.setBackground(Color.BLACK);
		welcomeLoginPanel.setLayout(new MigLayout("", "10[250]10", "10[70][70]10"));
		
		loginPanel = new JPanel();
		loginPanel.setBackground(Color.BLACK);
		loginPanel.setLayout(new MigLayout("", "10[250]10", "10[][][]10"));
		
		bjimage = new JLabel(new ImageIcon("./resources/blackjack.png"));		
		bjimage.setOpaque(true);
		bjimage.setBackground(Color.BLACK);
		
		this.add(bjimage, "span 0 2, growx, growy");
		this.add(welcomeLoginPanel, "right, top, wrap");
		this.add(loginPanel, "cell 1 1, growy, right, top");		

	}

	private void createLoginComponents() {
		JLabel welcome = new JLabel("<html> The BlackJack Game </html>");
		welcome.setFont(new Font("Arial", Font.BOLD, 15));
		welcome.setHorizontalAlignment(SwingConstants.CENTER);
		welcome.setVerticalAlignment(SwingConstants.CENTER);
		welcome.setForeground(Color.WHITE);
		JLabel txt = new JLabel("<html>Welcome! This is an aca<font color=\"gray\">d</font>emic implementati<font color=\"gray\">on</font> "
				+ "of <font color=\"gray\">t</font>he classic cards game <font color=\"gray\">b</font>lackjack, in ord<font color=\"gray\">e</font>r "
				+ "<font color=\"gray\">t</font>o tr<font color=\"gray\">y</font> different pr<font color=\"gray\">o</font>gramming concepts "
				+ "and patterns. Before start playing, check whether yo<font color=\"gray\">ur</font> username and pass<font color=\"gray\">w</font>ord "
				+ "ex<font color=\"gray\">i</font>st in our database. If it's not, just check the little box and set a password. That will create a new user for you</html>");
		txt.setForeground(Color.WHITE);
		welcomeLoginPanel.add(welcome, "center, wrap");
		welcomeLoginPanel.add(txt, "center");
		
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
					conn = new DBConnection();
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

		jb_login = new JButton(" Play ");
		jl_login_error = new JLabel("");
		jl_login_error.setFont(new Font("Arial", Font.BOLD, 12));
		jl_login_error.setForeground(Color.RED);
		
		loginPanel.add(jt_login, "cell 0 0, right");
		loginPanel.add(jt_password, "cell 0 1, right");
		loginPanel.add(jb_login, "cell 0 2, right");
		loginPanel.add(jl_login_error,"cell 0 3, right" );
		

		// Creation of the New User Panel
		createUserPanel = new JPanel();
		createUserPanel.setPreferredSize(new Dimension(150, 30));
		createUserPanel.setLayout(new MigLayout());
		createUserPanel.setBackground(Color.BLACK);

		jl_createUser = new JLabel("Unknown Player!");
		jl_createUser.setFont(new Font("arial", Font.BOLD, 11));
		jl_createUser.setForeground(Color.RED);
		jc_createUser = new JCheckBox("Create it?");
		jc_createUser.setBackground(Color.BLACK);
		jc_createUser.setForeground(Color.WHITE);
		jc_createUser.setFont(new Font("arial", Font.BOLD, 11));

		createUserPanel.add(jl_createUser, "wrap");
		createUserPanel.add(jc_createUser, "wrap");

	}

	private void createUserCreationPanel() {

		loginPanel.remove(jt_password);
		loginPanel.remove(jb_login);
		loginPanel.add(createUserPanel, "cell 0 1, right");
		loginPanel.add(jt_password, "cell 0 2, right");
		loginPanel.add(jb_login, "cell 0 3, right");
		loginPanel.add(jl_login_error, "cell 0 4, right");
		loginPanel.updateUI();
	}

	public void removeCreationPanel() {
		
		jc_createUser.setSelected(false);
		loginPanel.remove(createUserPanel);
		loginPanel.remove(jt_password);
		loginPanel.remove(jb_login);
		loginPanel.add(jt_password, "cell 0 1, right");
		loginPanel.add(jb_login, "cell 0 2, right");
		loginPanel.add(jl_login_error, "cell 0 3, right");
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
