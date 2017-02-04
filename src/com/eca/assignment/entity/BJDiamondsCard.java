package com.eca.assignment.entity;

import java.awt.Color;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class BJDiamondsCard extends BJCard{

	public BJDiamondsCard(int n) {
		super(n, "\u2666");
		this.setIcon(new ImageIcon("./resources/imgs/"+this.getNumber()+"D.png"));
	}

}
