package com.eca.assignment.main;

import javax.swing.SwingUtilities;

import com.eca.assignment.gui.login.FrameLogin;

public class Main {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new FrameLogin();
			}
		});
	}
}
