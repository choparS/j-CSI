package fr.shortcircuit.gui;

import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JPanel;

public interface IConversionConstants 
{
	//Diverses chaines de caractère constantes
	public String 		STR_EXIT_LABEL 					= "Sortie";
	public String 		STR_CLEAR_LABEL 					= "Clear";
	
	public String 		STR_EXIT_TOOLTIP_LABEL 			= "Sortie";
	public String 		STR_CLEAR_TOOLTIP_LABEL 			= "Effacer les conversion";
	
	public String 		STR_FRENCH_PRICE_LABEL 			= " Prix en Francs ";
	public String 		STR_EUROS_PRICE_LABEL 			= " Prix en Euros ";

	public String 		STR_FRENCH_TOOLTIP_PRICE_LABEL 	= "Le montant a convertir en FF";
	public String 		STR_EUROS_TOOLTIP_PRICE_LABEL 	= "Le montant a convertir en Euros";

	public int			INT_CONVERSION_FRAME_WIDTH		= 370;
	public int			INT_CONVERSION_FRAME_HEIGHT		= 80;
	
	
	/** Une Police de caractere */
	public Font			FONT_PANEL						= new Font("Arial", Font.PLAIN , 13);
	
	/** Object permettant de calculer la taille en pixel des textes utilises */
	public FontMetrics	FONT_METRICS						= new JPanel().getFontMetrics(FONT_PANEL);
	
}

