package au.com.eca.assignment.main;

import javax.swing.SwingUtilities;
import au.com.eca.assignment.gui.BJFrame;

public class BJMain {	
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		 SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	new BJFrame();
	            }
	        });		

	}
}
