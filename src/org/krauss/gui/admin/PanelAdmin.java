package org.krauss.gui.admin;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.krauss.game.DatabaseHandler;
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
	
	//Database
	private static DatabaseHandler dbHandler;
	private Object[][] data;

	//Table components
	private JScrollPane jsp_scrollPane;
	private JTable jt_rankTable;
	private String collumns[] = {"Player", "Score", "Last Login"};

	public PanelAdmin() {
		
		this.setLayout(new MigLayout("", "10[]5[]5[]10", "10[grow]10"));
		this.setBackground(Color.DARK_GRAY);
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4, true));
		
		createJPanelOptins();
		createJPanelDatabase();
		createJPanelRank();

	}
	
	private void createJPanelOptins() {
		
		JPanel jp_optionsPanel = new JPanel();
		jp_optionsPanel.setLayout(new MigLayout("", "5[]5", "5[]5"));
		jp_optionsPanel.setPreferredSize(new Dimension(120, 440));
		jp_optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		

		this.add(jp_optionsPanel, "cell 0 0, center");
	}
	
	private void createJPanelDatabase() {
		
		JPanel jp_rankPanel = new JPanel();
		jp_rankPanel.setLayout(new MigLayout());
		
		jt_rankTable = new JTable(fetchDataBaseData(), collumns);
		jt_rankTable.setFillsViewportHeight(true);
		jt_rankTable.setPreferredSize(new Dimension(350, 400));
		jsp_scrollPane = new JScrollPane();
		jsp_scrollPane.setViewportView(jt_rankTable);
		
		jt_rankTable.getColumnModel().getColumn(0).setPreferredWidth(120);
		jt_rankTable.getColumnModel().getColumn(1).setPreferredWidth(70);
		jt_rankTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		
		
		jp_rankPanel.add(jt_rankTable.getTableHeader(), "cell 0 0, center");
		jp_rankPanel.add(jt_rankTable, "cell 0 1, center");

		this.add(jp_rankPanel, "cell 1 0, center");
		
	}
	
	
	private void createJPanelRank() {
		
		JPanel jp_rankPanel = new JPanel();
		jp_rankPanel.setLayout(new MigLayout("", "5[]5", "5[]5"));
		jp_rankPanel.setPreferredSize(new Dimension(120, 440));
		jp_rankPanel.setBorder(BorderFactory.createTitledBorder("Ranking"));
		

		this.add(jp_rankPanel, "cell 2 0, center");
	}
	
	
	private Object[][] fetchDataBaseData() {
		
		dbHandler = new DatabaseHandler();
		ArrayList<String> rows = dbHandler.getDatabaseData(); 
		data = new Object[rows.size()][];
		
		for (int i = 0; i < rows.size(); i++ ) {			
			data[i] = rows.get(i).split(",");
		} 
		
		return data;
		
	}


}
