package fr.shortcircuit.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import fr.shortcircuit.db.DbManager;


public class DesktopWindowView extends JFrame 
{
	public DbManager 				myDbManager;
	
	//Desktop permettant de contenir des fenetres internes.
	public JDesktopPane				desk;	

	//Elements de la barre d'outils: containeur principal, boutons et ressources graphiques
	public JToolBar 				toolBar;

	//Elements de la barre de menus
	public JMenuBar 				menuBar;
	public JMenu					menuAbout;
	public JMenuItem				menuAboutContent;

	public JTable					tableResults;

	
	
	public DesktopWindowView(DbManager myDbManager)
	{
		super("Application Data-Structures Part1");
		
		setMyDbManager(myDbManager);
		buildDesktopPane();
		buildToolbarAndMenu();
		buildTable();
	}
	
	public void buildDesktopPane()
	{	 
		this.desk				= new JDesktopPane();
		
		this.getContentPane().add(desk, BorderLayout.CENTER);
		
		desk.putClientProperty("JDesktopPane.dragMode", "faster");

		this.addWindowListener(new DesktopWindowControler(this));
	}

	public void buildToolbarAndMenu()
	{
		this.menuBar			= new JMenuBar();
		this.toolBar			= new JToolBar(SwingConstants.HORIZONTAL);

		toolBar.putClientProperty("JToolbar.isRollover", Boolean.TRUE);

		//menu
		menuAbout				= new JMenu("About");	
		menuAboutContent		= new JMenuItem("About TP2");
		
		menuAbout.add(menuAboutContent);
		menuBar.add(menuAbout);				
		
		this.setJMenuBar(menuBar);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);	
		
		//Exemple d'utilisation d'une "inner" classe: red�finition � la vol�e des m�thodes de l'instance de l'objet. 
		menuAboutContent.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(getParent(), "TP 2, Data Structures Part 1\r\nSHORT-CIRCUIT"); 
			}
		});
	}				
	
 	public void buildTable()
 	{
 		DefaultTableModel 	tableModelResults 	= new DefaultTableModel(myDbManager.arrayContent, myDbManager.arrayHeader); 		
 		JTable 				tableResults 		= new JTable(tableModelResults);
 		JScrollPane			scpTable 			= new JScrollPane(tableResults);
 		JInternalFrame 		jifEx				= new JInternalFrame("Table Content", true, true, true, true);
 		
 		jifEx.getContentPane().add(scpTable, BorderLayout.CENTER); 
 		desk.add(jifEx, 1);
 		
 		//Layout & size managment
 		scpTable.setPreferredSize(new Dimension(300, 75)); 		
 		tableResults.setVisible(true);
 		jifEx.setVisible(true);
 		jifEx.setBounds(50, 50, 300, 75); //x, y, width, height								
 		jifEx.pack();
 		
 		//Ex de m�thode d'update graphique du composant de plus haut niveau
 		//jifEx.getContentPane().doLayout();		
 		//jifEx.updateUI();			
 	}


	//////////////////////////////////////////////////////////////////////////////////////////////
	//Getters & Setters
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	public DbManager getMyDbManager() 					{return myDbManager;}

	public void setMyDbManager(DbManager myDbManager) 	{this.myDbManager = myDbManager;}
	


}