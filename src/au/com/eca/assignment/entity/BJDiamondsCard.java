package au.com.eca.assignment.entity;

import java.awt.Color;

@SuppressWarnings("serial")
public class BJDiamondsCard extends BJCard{

	public BJDiamondsCard(int n) {
		super(n, "\u2666");
		this.setForeground(Color.RED);
	}

}
