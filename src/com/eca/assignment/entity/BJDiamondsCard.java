package com.eca.assignment.entity;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class BJDiamondsCard extends BJCard{

	public BJDiamondsCard(int n) {
		super(n, "\u2666");
		this.setIcon(new ImageIcon("./resources/imgs/"+this.getNumber()+"D.png"));
	}
	
	public void setBackImage(boolean s) {
		if (s){
			this.setIcon(new ImageIcon("./resources/imgs/back.png"));
		}else{
			this.setIcon(new ImageIcon("./resources/imgs/"+getNumber()+"D.png"));
		}
	}

}
