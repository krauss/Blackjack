package au.com.eca.assignment.entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import au.com.eca.assignment.interfaces.BJICard;

/**
 * 
 *@author <b> jrkrauss </b><br><br>
 *
 * Definition entity that represents the object 'Card' in the game
 *
 *
 */
@SuppressWarnings("serial")
public class BJCard extends JLabel implements BJICard{
	
	private String number;
	private String suit;
	private int value;
		
	
	/**
	 *  
	 * 
	 * @param n = randomly generated number between 1-13
	 * @param s = the suit might be one of these four: SPADES, HEARTS, DIAMONDS, CLUBS;
	 */
	public BJCard(int n, String s) {
		this.suit = s;
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.setPreferredSize(new Dimension(35, 40));
		this.setFont(new Font("arial", Font.PLAIN, 17));
		setValueAndNumber(n);
		this.setText(number+suit);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.CENTER);
	}
	
	
	/**
	 * 
	 * @return the card number
	 * 
	 */
	@Override
	public String getNumber() {
		return this.number;
	}

	
	/**
	 * 
	 * @return the card suit
	 * 
	 */
	@Override
	public String getSuit() {
		return this.suit;
	}
	
	
	/**
	 * 
	 * @return the card value in equivalent points
	 * 
	 */
	@Override
	public int getValue() {
		return value;
	}
	
	/**
	 * 
	 * set the card value
	 * 
	 */
	@Override
	public void setValue(int i) {
		value = i;
	}
	
	
	/**
	 * 
	 * String representation of the object in memory
	 */
	@Override
	public String toString(){
		return getClass() + " nro: " + number + " of " + suit;
	}
	
		
	/**
	 * Assigns the points that each card has
	 * 
	 */
	private void setValueAndNumber(int n){
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

}
