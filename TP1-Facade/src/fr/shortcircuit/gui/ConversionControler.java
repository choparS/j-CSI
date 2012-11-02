package fr.shortcircuit.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import fr.shortcircuit.utils.ConverterHelper;

public class ConversionControler implements IConversionConstants, ActionListener, FocusListener, WindowListener
{
	private SwingConversionView conversionFrame;
	
	
	
	public ConversionControler(SwingConversionView conversionFrame)
	{
		setConversionFrame(conversionFrame);
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Evenements action
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /** Methode chargee de la gestion des evenements de type "Action" */
    public void actionPerformed(ActionEvent e)
    {
    	// On accede directement au producteur, la source, de l'evenement en passant par la
    	// methode getSource() qui renvoie un type de haut niveau "Object"
        if (e.getSource() == conversionFrame.exit)
        	byeBye();
		
        if (e.getSource() == conversionFrame.clear)
        {
            // on fixe le contenu des champs de texte avec une valeur de chaine vide
        	conversionFrame.txfEntryEuros.setText("");
            conversionFrame.txfEntryFrancs.setText("");
        }		
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Evenements focus
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
    * Methode chargee des evenements de type "Gain de focus"
    * @param e : instance de "FocusEvent" qui contient les infos propres ˆ l'evenements
    */
    public void focusGained(FocusEvent e) {}

    /**
    * Methode chargee des evenements de type "Perte de focus"
    * @param e : instance de "FocusEvent" qui contient les infos propres ˆ l'evenements
    * <p>
    * Le principe ici est que des qu'une perte de focus est generee par un object, de type
    * textField, on sait alors forcement que l'utilisateur vient d'y rentrer un litteral
    * (une valeur), on enchaine donc directement avec le traitement de cette valeur : 
    * conversion
    * <p>
    * Si une exception est generee on sait alors que ce qu'a rentre l'utilisateur n'est pas 
    * une chaine de caractere de nombre, on refixe donc les contenus des champs de texte ˆ 
    * vide 
    */
    public void focusLost(FocusEvent e)
    {
		try
		{
			if (e.getSource() == conversionFrame.txfEntryFrancs)
				conversionFrame.txfEntryEuros.setText(ConverterHelper.francsEnEuros(Double.parseDouble(conversionFrame.txfEntryFrancs.getText())));
			
			if (e.getSource() == conversionFrame.txfEntryEuros)
				conversionFrame.txfEntryFrancs.setText(ConverterHelper.eurosEnFrancs(Double.parseDouble(conversionFrame.txfEntryEuros.getText())));
		}
		catch (Exception ex)
		{
	    	// un erreur s'est produite on pourrait tres bien ici afficher ˆ l'utilisateur 
	        // une boite de dialogue en lui specifiant son erreur.
				
			conversionFrame.txfEntryEuros.setText("");
			conversionFrame.txfEntryFrancs.setText("");	
		}
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Evenements fenetres
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void windowActivated		(WindowEvent e)	{}
    public void windowClosed		(WindowEvent e)	{}
    public void windowDeactivated	(WindowEvent e)	{}
    public void windowDeiconified	(WindowEvent e)	{}
    public void windowIconified		(WindowEvent e)	{}
    public void windowOpened		(WindowEvent e)	{}

    public void windowClosing		(WindowEvent e) {byeBye();}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Application ending
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void byeBye()
    {
        // la fenetre fixe sa propriete de visibilite ˆ "faux"
    	conversionFrame.setVisible(false);
		
        // Declaration d'inutilite de l'object courant en vue de son traitement 
        // par le Garbage Collector
    	conversionFrame.dispose();
		
        // Exit de la machine virtuelle, en specifiant une valeur de retour.
        System.exit(42);
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Getters & Setters
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public SwingConversionView getConversionFrame() 					{return conversionFrame;}

	public void setConversionFrame(SwingConversionView conversionFrame) {this.conversionFrame 	= conversionFrame;}
	
}