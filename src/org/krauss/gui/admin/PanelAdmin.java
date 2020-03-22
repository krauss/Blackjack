package org.krauss.gui.admin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import org.krauss.entity.Player;
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
	private ArrayList<Player> rows;
	private Object[][] data;

	//Database Table components
	private JScrollPane jsp_scrollPane;
	private JTable jt_rankTable;
	private String collumns[] = {"Player", "Score", "Last Login"};
	
	//Rank List components
	private JList<Object> rank;
	
	//Options components
	private JLabel jl_player;
	private JLabel jl_playerData;
	private JButton jb_delete;
	private JButton jb_resetPasswd;
	private JButton jb_cancel;
	private JButton jb_edit;

	public PanelAdmin() {
		
		this.setLayout(new MigLayout("", "10[]5[]5[]10", "10[grow]10"));
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5, true));
		
		createJPanelOptins();
		createJPanelDatabase();
		createJPanelRank();

	}
	
	private void createJPanelOptins() {
		
		JPanel jp_optionsPanel = new JPanel();
		jp_optionsPanel.setLayout(new MigLayout("", "10[]7[]10", "15[]7[]7[]10[]10"));
		jp_optionsPanel.setPreferredSize(new Dimension(160, 440));
		jp_optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		
		jl_player = new JLabel("Player:");
		jl_playerData = new JLabel();
		jb_resetPasswd = new JButton("Reset Password");
		jb_delete = new JButton("Delete");
		jb_cancel = new JButton("Cancel");	
		jb_edit = new JButton("Edit");
		
		jl_player.setEnabled(false);
		jl_playerData.setEnabled(false);
		jb_resetPasswd.setEnabled(false);
		jb_delete.setEnabled(false);
		jb_cancel.setEnabled(false);
		
		jp_optionsPanel.add(jl_player, "cell 0 0, left");
		jp_optionsPanel.add(jl_playerData, "cell 1 0, left");
		jp_optionsPanel.add(jb_resetPasswd, "cell 0 1, span 2, growx, left");
		jp_optionsPanel.add(jb_delete, "cell 0 2, left");
		jp_optionsPanel.add(jb_cancel, "cell 1 2, left");
		jp_optionsPanel.add(jb_edit, "cell 0 3, left");

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
		
		jt_rankTable.setIntercellSpacing(new Dimension(3, 3));
		
		jt_rankTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() instanceof JTable) {

					JTable jt = (JTable) e.getSource();
					jl_playerData.setText(rows.get(jt.getSelectedRow()).getUserName());
					
				}
				
				
				
			}
		});
		
		
		jp_rankPanel.add(jt_rankTable.getTableHeader(), "cell 0 0, center");
		jp_rankPanel.add(jt_rankTable, "cell 0 1, center");

		this.add(jp_rankPanel, "cell 1 0, center");
		
	}
	
	
	private void createJPanelRank() {
		
		JPanel jp_rankPanel = new JPanel();
		jp_rankPanel.setLayout(new MigLayout("", "5[]5", "15[]5"));
		jp_rankPanel.setPreferredSize(new Dimension(160, 440));
		jp_rankPanel.setBorder(BorderFactory.createTitledBorder("Ranking"));
		
		ArrayList<Player> rankPlayers = (ArrayList<Player>) rows.clone();
		rankPlayers.sort(Comparator.comparing(Player::getScore).reversed());
		
		
		rank = new JList<Object>(rankPlayers.toArray());
		rank.setEnabled(false);
		rank.setBackground(jp_rankPanel.getBackground());
		JScrollPane rank_listScroller = new JScrollPane(rank);
		rank_listScroller.setViewportView(rank);
		
		jp_rankPanel.add(rank, "cell 0 0, center");
				
		this.add(jp_rankPanel, "cell 2 0, center");
	}
	
	
	private Object[][] fetchDataBaseData() {
		
		dbHandler = new DatabaseHandler();
		rows = dbHandler.getDBData(); 
		data = new Object[rows.size()][];
		
		for (int i = 0; i < rows.size(); i++ ) {
			
			data[i] = rows.get(i).playerToString().split(",");
		} 
		
		return data;
		
	}


}
