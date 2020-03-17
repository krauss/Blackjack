package org.krauss.gui.admin;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

/**
 * 
 * @author jrkrauss <br>
 *         <br>
 *         This class defines the panel of the admin interface. All the components are
 *         setup in this class.
 *
 */
@SuppressWarnings("serial")
public class PanelAdmin extends JPanel {


	public PanelAdmin() {

		this.setLayout(new MigLayout("", "10[grow]10", "10[grow]10"));
		this.setBackground(new Color(0x03853E));
		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4, true));

		

	}



}
