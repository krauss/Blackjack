package org.krauss.main;

import javax.swing.SwingUtilities;
import org.krauss.gui.login.FrameLogin;


public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				try {

					new FrameLogin();

				} catch (Exception ex) {
					System.err.println(ex);
				}

			}
		});
	}
}
