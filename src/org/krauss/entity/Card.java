package org.krauss.entity;

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
		
		switch(suit){
			case "\u2663":
				this.setIcon(new ImageIcon("./resources/imgs/"+number+"C.png"));
				break;
			case "\u2665":
				this.setIcon(new ImageIcon("./resources/imgs/"+number+"H.png"));
				break;
			case "\u2660":
				this.setIcon(new ImageIcon("./resources/imgs/"+number+"S.png"));
				break;
			default:
				this.setIcon(new ImageIcon("./resources/imgs/"+number+"D.png"));
				break;				
		}
	}


	public void setBackImage(boolean s) {
		
		if(s){
			this.setIcon(new ImageIcon("./resources/imgs/back.png"));
		} else{
			setImage();
		}		
	}

}
