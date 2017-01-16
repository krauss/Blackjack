package au.com.eca.assignment.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class BJPanelLogin extends JPanel{
	
	private JPanel loginPanel;
	private JTextField jt_login;
	private JPasswordField jt_password;
	private JButton jb_login;
	private JLabel jl_login_error;
	
	public BJPanelLogin(){		
		this.setLayout(new MigLayout("", "20[grow]10[grow]20", "20[grow]20"));		
		createPanelLogin();
		createLoginComponents();		
	}
	
	
	private void createPanelLogin() {
		loginPanel = new JPanel();
		//loginPanel.setPreferredSize(new Dimension(165, 280));
		loginPanel.setBorder(BorderFactory.createEtchedBorder());
		loginPanel.setLayout(new MigLayout("", "20[grow]20", "20[]10[]10[]20"));		
		this.add(loginPanel, "growy");
		this.add(new JLabel(new ImageIcon("./resources/blackjack.png")), "growy, growx");		
				
	}
	
	
	private void createLoginComponents() {
		jt_login = new JTextField("username");
		jt_login.setPreferredSize(new Dimension(120, 22));
		jt_login.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {					
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if (jt_login.getText().equalsIgnoreCase("username")){
					jt_login.setText("");
				}
				
			}
		});
		
		jt_password = new JPasswordField();
		jt_password.setPreferredSize(new Dimension(120, 22));
		jt_password.setText("password");
		jt_password.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if(jt_password.getText().equalsIgnoreCase("password")){
					jt_password.setText("");
					
				}
				
			}
		});
		
		jb_login = new JButton("Login");		
		jl_login_error = new JLabel("");
		jl_login_error.setFont(new Font("Arial", Font.BOLD, 11));
		jl_login_error.setForeground(Color.RED);	
		loginPanel.add(new JLabel("<html><br>Welcome to BlackJack Game <br><br><br>Please, insert your username <br>and password in the fields below.<br><br><br></html>"), "wrap");
		loginPanel.add(jt_login, "wrap");
		loginPanel.add(jt_password, "wrap");
		loginPanel.add(jb_login, "wrap");
		loginPanel.add(jl_login_error);
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
	
	
}
