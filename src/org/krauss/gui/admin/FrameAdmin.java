package org.krauss.gui.admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import org.krauss.entity.Player;
import org.krauss.gui.login.FrameLogin;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class FrameAdmin extends JFrame {

	private PanelAdmin panelGame;
	private Player admin;
	
	
	//ToolBar components
	private JToolBar jtb_toolBar;
	private JButton jb_logout;

	public FrameAdmin(Player p) {
		
		this.admin = p;
		this.setResizable(false);
		this.setTitle("Administrator Panel");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(750, 630));
		this.setSize(750, 630);
		this.setLocationRelativeTo(null);

		initPanelGame();
		createToolBar();

		this.pack();
		this.setVisible(true);
	}
	
	private void initPanelGame() {

		panelGame = new PanelAdmin();


		this.add(panelGame);
	}
	
	private void createToolBar() {
		
		jtb_toolBar = new JToolBar();
		jtb_toolBar.setFloatable(false);
		jtb_toolBar.setLayout(new MigLayout("", "6[]50[]10[]10[]10[]10", "[]"));
		jb_logout = new JButton("Logout");
		jb_logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});
		
		Font fo = new Font("Arial", Font.PLAIN, 13);
		JLabel welcome = new JLabel("<html><b>User: </b>" + admin.getUserName()+ "</html>");
		welcome.setFont(fo);
		JLabel lastLogin = new JLabel("<html><b>Last login: </b>" + admin.getLastLogin() + "</html>");
		lastLogin.setFont(fo);
		JSeparator separator1 = new JSeparator(SwingConstants.VERTICAL);
		separator1.setPreferredSize(new Dimension(5, 20));
		JSeparator separator2 = new JSeparator(SwingConstants.VERTICAL);
		separator2.setPreferredSize(new Dimension(5, 20));
		
		jtb_toolBar.add(jb_logout, "cell 0 0, center");
		jtb_toolBar.add(welcome, "cell 1 0, center");
		jtb_toolBar.add(separator1, "cell 2 0, left");
		jtb_toolBar.add(lastLogin, "cell 3 0, center");
		jtb_toolBar.add(separator2, "cell 4 0, left");
		this.add(jtb_toolBar, BorderLayout.PAGE_END);

		
	}
	
	private void logout() {
		this.dispose();
		new FrameLogin();
	}


}
