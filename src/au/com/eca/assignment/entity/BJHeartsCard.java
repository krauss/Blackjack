package au.com.eca.assignment.entity;

import java.awt.Color;

public class BJHeartsCard extends BJCard{
	
	/**
	 * 
	 * This specific contructor creates an object HeartsCard using the super constructor
	 * 
	 * @param n
	 */
	public BJHeartsCard(int n) {
		super(n, "\u2665");
		this.setForeground(Color.RED);
	}

}
