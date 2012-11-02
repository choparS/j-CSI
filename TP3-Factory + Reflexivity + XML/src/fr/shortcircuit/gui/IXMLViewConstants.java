package fr.shortcircuit.gui;

import java.util.Arrays;
import java.util.List;

/**
 * Les interfaces permettent de structurer proprement la separation physique 
 * d'une partie des informations "constantes" utilisees par l'application.
 */  
public interface IXMLViewConstants
{
	//Constantes de taille de la frame de l'application	
	public int			INT_APPLI_WIDTH						= 1000;
	public int			INT_APPLI_HEIGHT					= 750;
	
	//Nom des classes supportees pour le mecanisme de reflection SAX
	public String		ARRAY_AUTOMATION_SUPPORTED_CLASS[] 	= {"ProductElement", "GameElement", "DvdElement", "K7Element", "Actor"};
	
	public List			LIST_AUTOMATION_SUPPORTED_CLASS		= Arrays.asList(ARRAY_AUTOMATION_SUPPORTED_CLASS);
	
}