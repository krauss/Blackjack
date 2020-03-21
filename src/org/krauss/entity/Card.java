package org.krauss.entity;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 
 *@author <b> jrkrauss </b><br><br>
 *
 * Definition entity that represents the object 'Card' in the game
 *
 *
 */
@SuppressWarnings("serial")
public class Card extends JLabel {
	
	private String number;
	private String suit;
	private int value;
	
	public Card(int n, String s) {
		this.suit = s;
		setValNum(n);
		setImage();
	}
	

	public String getNumber() {
		return this.number;
	}

	
	
	public String getSuit() {
		return this.suit;
	}
	
	
	
	public int getValue() {
		return value;
	}
	
	
	public void setValue(int i) {
		value = i;
	}
	
		
	private void setValNum(int n){
		switch(n){
		case 1:
			this.value = 1;
			this.number = "A";
			break;
		case 11:
			this.value = 10;
			this.number = "J";
			break;
		case 12:
			this.value = 10;
			this.number = "Q";
			break;
		case 13:
			this.value = 10;
			this.number = "K";
			break;
		default:
			this.number = n+"";	
			this.value = Integer.valueOf(number);
			break;
		}		
	}
	
	
	private void setImage() {
		URL pathToImg = null;
		
		switch(suit){
			case "\u2663":
				pathToImg = getClass().getResource("/imgs/"+number+"C.png");
				if (pathToImg != null) {
					this.setIcon(new ImageIcon(pathToImg));
				}				
				break;
			case "\u2665":				
				pathToImg = getClass().getResource("/imgs/"+number+"H.png");
				if (pathToImg != null) {
					this.setIcon(new ImageIcon(pathToImg));
				}
				break;
			case "\u2660":				
				pathToImg = getClass().getResource("/imgs/"+number+"S.png");
				if (pathToImg != null) {
					this.setIcon(new ImageIcon(pathToImg));
				}
				break;
			default:
				pathToImg = getClass().getResource("/imgs/"+number+"D.png");
				if (pathToImg != null) {
					this.setIcon(new ImageIcon(pathToImg));
				}
				break;				
		}
	}


	public void setBackImage(boolean s) {
		
		if(s){
			URL pathToImg = getClass().getResource("/imgs/back.png");
			if (pathToImg != null) {
				this.setIcon(new ImageIcon(pathToImg));
			}
			this.setIcon(new ImageIcon(pathToImg));
		} else{
			setImage();
		}		
	}

}
