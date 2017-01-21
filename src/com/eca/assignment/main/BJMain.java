package com.eca.assignment.main;

import javax.swing.SwingUtilities;
import com.eca.assignment.gui.BJFrame;

public class BJMain {	
	
	
	/**
	 * Class that starts the whole BlackJack game
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		 SwingUtilities.invokeLater(new Runnable() {
	            @Override
				public void run() {
	            	new BJFrame();
	            }
	        });		

	}
}
