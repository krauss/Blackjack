package au.com.eca.assignment.interfaces;


/**
 * 
 *@author <b> jrkrauss </b><br><br>
 *
 * Interface that defines basic method that the entities must implements 
 *
 */

public interface BJICard {

	
	public String getNumber();
	public String getSuit();
	public int getValue();
	public void setValue(int n);
	public String toString();
	
	
}
