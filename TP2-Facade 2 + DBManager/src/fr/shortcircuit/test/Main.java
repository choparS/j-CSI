package fr.shortcircuit.test;

//import java.io.*;

import fr.shortcircuit.db.DbManager;
import fr.shortcircuit.gui.DesktopWindowView;
import fr.shortcircuit.gui.IDatabaseViewConstants;


public class Main implements IDatabaseViewConstants
{
	//Connecteur à la base de donnée
	public DbManager 				dbManager;
	
	//UI
	public DesktopWindowView		view;
	
	
	
	public static void main(String args[])
	{		
		Main newAppli			= new Main();
	}
	
	public Main()
	{
		callDataBase();
		
		buildUI();
	}	

	public void callDataBase()
	{
		dbManager 	= new DbManager();
		
		dbManager.dbConnect();
	}
	
	private void buildUI()
	{
		view 		= new DesktopWindowView(dbManager);
		
		view.setSize(INT_APPLI_WIDTH, INT_APPLI_HEIGHT);
		view.setLocation(150, 100);
		view.setVisible(true);
	}
	
}
