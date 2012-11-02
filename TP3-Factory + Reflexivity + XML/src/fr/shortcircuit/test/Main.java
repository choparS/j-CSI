package fr.shortcircuit.test;

import fr.shortcircuit.gui.DesktopWindowView;
import fr.shortcircuit.gui.IXMLViewConstants;
import fr.shortcircuit.xml.MyDomParser;
import fr.shortcircuit.xml.MySaxParser;
import fr.shortcircuit.xml.MySaxReflecter;


public class Main 
{
	
	public static void main(String args[])
	{		
		Main newAppli			= new Main();
	}
	
	/**
	 * Dans un souci de produire un code comprehensible et intelligible par la plupart
	 * il est souhaitable de presenter ce dernier d'une facon "propre".
	 * 
	 * La structuration de certaines portion de code plus importantes que d'autre
	 * (constructeurs, methode centrale de traitement d'evenements, de redirections, ...) 
	 */ 
	public Main()
	{
		
		buildUI();
	}	

	private void buildUI()
	{
		DesktopWindowView view 		= new DesktopWindowView();
		
		view.setSize(IXMLViewConstants.INT_APPLI_WIDTH, IXMLViewConstants.INT_APPLI_HEIGHT);
		view.setLocation(150, 100);
		view.setVisible(true);
	}

	
}
