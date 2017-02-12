package com.eca.assignment.entity;

import java.awt.Color;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class HeartsCard extends Card{
	
	/**
	 * 
	 * This specific contructor creates an object HeartsCard using the super constructor
	 * 
	 * @param n
	 */
	public HeartsCard(int n) {
		super(n, "\u2665");
		this.setIcon(new ImageIcon("./resources/imgs/"+this.getNumber()+"H.png"));
	}
	
	
	public void setBackImage(boolean s) {
		if (s){
			this.setIcon(new ImageIcon("./resources/imgs/back.png"));
		}else{
			this.setIcon(new ImageIcon("./resources/imgs/"+getNumber()+"H.png"));
		}
	}
}
