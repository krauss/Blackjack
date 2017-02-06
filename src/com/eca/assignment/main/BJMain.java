package com.eca.assignment.main;

import javax.swing.SwingUtilities;
import com.eca.assignment.gui.BJFrameLogin;

public class BJMain {

	public static void main(String[] args) {

		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new BJFrameLogin();
			}
		});
	}
}
