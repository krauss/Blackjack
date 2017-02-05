package com.eca.assignment.entity;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class BJSpadesCard extends BJCard{
	

	public BJSpadesCard(int n) {
		super(n, "\u2660");
		this.setIcon(new ImageIcon("./resources/imgs/"+this.getNumber()+"S.png"));
	}

	
	public void setBackImage(boolean s) {
		if (s){
			this.setIcon(new ImageIcon("./resources/imgs/back.png"));
		}else{
			this.setIcon(new ImageIcon("./resources/imgs/"+getNumber()+"S.png"));
		}
	}
}
