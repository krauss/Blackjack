package au.com.eca.assignment.gui;

import java.awt.Dimension;

import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class BJTabbedPanel extends JTabbedPane{
	
	
	
	public BJTabbedPanel(){
		
		this.setTabPlacement(JTabbedPane.TOP);
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		this.setPreferredSize(new Dimension(600, 460));
		
		
		
	}
	
}
