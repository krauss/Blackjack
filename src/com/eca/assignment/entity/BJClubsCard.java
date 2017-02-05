package com.eca.assignment.entity;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class BJClubsCard extends BJCard{
		
	/**
	 * 
	 * The contructor creates an object ClubsCard using the super constructor
	 * 
	 * @param n
	 */
	public BJClubsCard(int n) {
		super(n, "\u2663");
		this.setIcon(new ImageIcon("./resources/imgs/"+this.getNumber()+"C.png"));
	}
	
	
	public void setBackImage(boolean s) {
		if (s){
			this.setIcon(new ImageIcon("./resources/imgs/back.png"));
		}else{
			this.setIcon(new ImageIcon("./resources/imgs/"+getNumber()+"C.png"));
		}
	}	

}
