package org.krauss.main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.krauss.gui.login.FrameLogin;
import com.formdev.flatlaf.FlatIntelliJLaf;

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				try {
					// Set a different Look-and-Feel before creating the game
					UIManager.setLookAndFeel(new FlatIntelliJLaf());

					new FrameLogin();

				} catch (Exception ex) {
					System.err.println(ex);
				}

			}
		});
	}
}
