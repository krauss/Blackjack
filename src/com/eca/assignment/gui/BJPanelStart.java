package com.eca.assignment.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class BJPanelStart extends JPanel{

	private JButton jb_start;
	

	public BJPanelStart(){
		this.setLayout(new MigLayout("", "[grow]", "[grow]"));
		jb_start = new JButton(" Start ");
		jb_start.setBackground(Color.BLACK);
		jb_start.setForeground(Color.WHITE);
		jb_start.setFont(new Font("arial", Font.BOLD, 15));
		jb_start.setPreferredSize(new Dimension(90, 40));
		jb_start.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
		this.setBackground(new Color(0x03853E));
		this.add(jb_start, "center");
	}
	
	
	public JButton getJb_start() {
		return jb_start;
	}	
	
}
