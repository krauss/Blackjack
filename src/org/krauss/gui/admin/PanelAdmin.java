package org.krauss.gui.admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
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
	
	//Option panel components
	private JPanel jp_optionsPanel;

	//Database Table components
	private JScrollPane jsp_scrollPane;
	private JTable jt_rankTable;
	private String collumns[] = {"Player", "Score", "Last Login"};
	
	//Rank List components
	private JList<Object> jli_rank;
	
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
		
		jp_optionsPanel = new JPanel();
		jp_optionsPanel.setLayout(new MigLayout("", "10[]7[]10", "15[]7[]7[]10[]10"));
		jp_optionsPanel.setPreferredSize(new Dimension(200, 490));
		jp_optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		
		jl_player = new JLabel("Player:");
		jl_player.setPreferredSize(new Dimension(100, 29));
		jl_playerData = new JLabel();
		jb_resetPasswd = new JButton("Reset Password");
		jb_delete = new JButton("Delete");
		jb_delete.setForeground(Color.RED);
		jb_delete.addActionListener(new DeleteButtonListener());
		jb_cancel = new JButton("Cancel");
		jb_cancel.setForeground(Color.BLUE);
		jb_cancel.addActionListener(new CancelButtonListener());
		jb_edit = new JButton("Edit");
		jb_edit.setPreferredSize(new Dimension(190, 29));
		jb_edit.addActionListener(new EditButtonListener());
		jb_edit.setEnabled(false);
		
		jl_player.setEnabled(false);
		jl_playerData.setEnabled(false);
		
		jp_optionsPanel.add(jl_player, "cell 0 0, left");
		jp_optionsPanel.add(jl_playerData, "cell 1 0, left");
		/*jp_optionsPanel.add(jb_resetPasswd, "cell 0 1, span 2, growx, left");
		jp_optionsPanel.add(jb_delete, "cell 0 2, left");
		jp_optionsPanel.add(jb_cancel, "cell 1 2, left");
		jp_optionsPanel.add(jb_edit, "cell 0 3, span 2, growx, left");*/
		jp_optionsPanel.add(jb_edit, "cell 0 3, span 2, left");
		
		
		this.add(jp_optionsPanel, "cell 0 0, center");
	}
	
	private void createJPanelDatabase() {
		
		JPanel jp_rankPanel = new JPanel();
		jp_rankPanel.setLayout(new MigLayout());
		
		jt_rankTable = new JTable(fetchDataBaseData(), collumns);
		jt_rankTable.setModel(new DefaultTableModel(fetchDataBaseData(), collumns));
		jt_rankTable.setFillsViewportHeight(true);
		jt_rankTable.setPreferredSize(new Dimension(355, 480));
		jt_rankTable.setToolTipText("Select a player to enable the options");
		jsp_scrollPane = new JScrollPane(jt_rankTable);
		jsp_scrollPane.setPreferredSize(new Dimension(360, 490));
		
		jt_rankTable.getColumnModel().getColumn(0).setPreferredWidth(120);
		jt_rankTable.getColumnModel().getColumn(1).setPreferredWidth(70);
		jt_rankTable.getColumnModel().getColumn(2).setPreferredWidth(160);
		
		jt_rankTable.setIntercellSpacing(new Dimension(3, 3));
		
		jt_rankTable.addMouseListener(new TableListener());
		
		
		jp_rankPanel.add(jt_rankTable.getTableHeader(), "cell 0 0, center");
		jp_rankPanel.add(jsp_scrollPane, "cell 0 1, center");

		this.add(jp_rankPanel, "cell 1 0, center");
		
	}
	
	
	private void createJPanelRank() {
		
		JPanel jp_rankPanel = new JPanel();
		jp_rankPanel.setLayout(new MigLayout("", "5[]5", "15[]5"));
		jp_rankPanel.setPreferredSize(new Dimension(220, 490));
		jp_rankPanel.setBorder(BorderFactory.createTitledBorder("Ranking"));
		
		ArrayList<Player> rankPlayers = (ArrayList<Player>) rows.clone();
		rankPlayers.sort(Comparator.comparing(Player::getScore).reversed());
		
		DefaultListModel<Object> m = new DefaultListModel<Object>();
		m.addAll(rankPlayers);
		jli_rank = new JList<Object>(m);
				
		jli_rank.setEnabled(false);
		jli_rank.setBackground(jp_rankPanel.getBackground());
		JScrollPane rank_listScroller = new JScrollPane(jli_rank);
		rank_listScroller.setPreferredSize(new Dimension(200, 470));
		
		jp_rankPanel.add(rank_listScroller, "cell 0 0, center");
				
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
	
	
	private void updateJTableData(int row) {
		
		if (jt_rankTable.getModel() instanceof DefaultTableModel) {
			((DefaultTableModel)jt_rankTable.getModel()).removeRow(row);
			dbHandler = new DatabaseHandler();
			rows = dbHandler.getDBData();
			jt_rankTable.updateUI();
			jl_player.setEnabled(false);
			jl_playerData.setEnabled(false);
			jl_playerData.setText("");
			jp_optionsPanel.remove(jb_resetPasswd);
			jp_optionsPanel.remove(jb_delete);
			jp_optionsPanel.remove(jb_cancel);
			jp_optionsPanel.updateUI();
		}
		
		
	}
	
	
	private void updateJListRank(String playername) {
		
		Object[] list = ((DefaultListModel<Object>) jli_rank.getModel()).toArray();
			
		for (int i = 0; i < list.length; i++) {
			if (list[i] instanceof Player) {
				
				if (((Player)list[i]).getPlayerName().equalsIgnoreCase(playername)) {
					((DefaultListModel<Object>) jli_rank.getModel()).remove(i);
					break;
				}
			}
		}
		jli_rank.setEnabled(false);
		jli_rank.updateUI();
		
	}
	
	
	/**
	 * 
	 * @author jrkrauss
	 *	
	 *	Listener private classes
	 *
	 */	
	private class TableListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof JTable) {

				JTable jt = (JTable) e.getSource();
				if (jt.getSelectedRow() >= 0) {
					jl_playerData.setText(rows.get(jt.getSelectedRow()).getPlayerName());
					jb_edit.setEnabled(true);
				}
				
			} 
			
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
	}

	private class EditButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			jl_player.setEnabled(true);
			jl_playerData.setEnabled(true);
			jp_optionsPanel.add(jb_resetPasswd, "cell 0 1, span 2, growx, left");
			jp_optionsPanel.add(jb_delete, "cell 0 2, left");
			jp_optionsPanel.add(jb_cancel, "cell 1 2, left");
			jp_optionsPanel.updateUI();
		}
		
	}
	
	private class CancelButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			jl_player.setEnabled(false);
			jl_playerData.setEnabled(false);
			jp_optionsPanel.remove(jb_resetPasswd);
			jp_optionsPanel.remove(jb_delete);
			jp_optionsPanel.remove(jb_cancel);
			jp_optionsPanel.updateUI();
		}
		
	}
	
	private class DeleteButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//Open the DB connection
			dbHandler = new DatabaseHandler();
			//Delete the player
			dbHandler.deletePlayer(jl_playerData.getText());
			
			//Find his index within the ArrayList rows
			int index = 0;
			
			for(int i = 0; i < rows.size(); i++) {
				 if (rows.get(i).getPlayerName().equalsIgnoreCase(jl_playerData.getText())) {
					index = i;
					break;
				 }
			}
			updateJListRank(jl_playerData.getText());
			updateJTableData(index);
			
		}
		
	}
	
	
}
