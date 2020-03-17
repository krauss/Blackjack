package org.krauss.gui.admin;

import java.awt.Dimension;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class FrameAdmin extends JFrame {

	private PanelAdmin panelGame;
	//private DBConnection conn;

	public FrameAdmin() {

		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(750, 690));
		this.setSize(750, 690);
		this.setLocationRelativeTo(null);

		initPanelGame();

		this.pack();
		this.setVisible(true);
	}
	
	private void initPanelGame() {

		panelGame = new PanelAdmin();


		this.add(panelGame);
	}


}
