package au.com.eca.assignment.gui;


import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class BJPanelStart extends JPanel{

	private JButton jb_start;
	

	public BJPanelStart(){
		this.setLayout(new MigLayout("", "[grow]", "[grow]"));
		jb_start = new JButton(" Start ");
		//this.setBackground(new Color(0x03853E));
		this.add(jb_start, "center");
	}
	
	
	public JButton getJb_start() {
		return jb_start;
	}	
	
}
