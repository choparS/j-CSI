package fr.shortcircuit.test;

import java.awt.Dimension;
import java.awt.Toolkit;

import fr.shortcircuit.gui.IConversionConstants;
import fr.shortcircuit.gui.SwingConversionView;


/**
 * This very simple program converts french's francs to euro 
 * @author	: Dimitri Dean DARSEYNE (dim@shortcircuit.fr)
 * @version : 1.1 (first implementation: 2000 :)
 * @since	: JDK 1.2, use of Swing object !!
 */

public class Main implements IConversionConstants
{	
    /** Fenetre de conversion avec des composants Swing */ 
    public SwingConversionView		swingFrame;
	
    /** Positions (x,y) sur lesquels vont se fonder le positionnement de la fenetre graphique. */
    public int 						conversionFrameWidthPosition;
    public int 						conversionFrameHeightPosition;
		
    
    
    /** Point d'entree de l'application, appelee via $PROMPT> java Main */
    public static void main(String argv[])
    {
        // on instancie un object de type eurosConverter (permet ainsi de sortir du contexte static)
        Main myEurosConverter		= new Main(); 	
    }

    /** constructeur : retourne une instance de la classe Main */
    public Main()
    {
    	try
        {	
            setPosition();
	
            createSwingConversionFrame();
        }
        catch(Exception e) {handleException(e);}
    }

    /** Calcul des valeurs necessaires au positionnement de la fenetre contenant le convertisseur */
    public void setPosition()
    {
    	// recup�re les dimensions de la fenetre du poste client 
        // Remarque: appel ˆ la classe Singleton Toolkit (Design Pattern de construction) !
    	Dimension screenDimension		= Toolkit.getDefaultToolkit().getScreenSize();	
    	conversionFrameWidthPosition	= (screenDimension.width - INT_CONVERSION_FRAME_WIDTH) / 2;		
        conversionFrameHeightPosition	= (screenDimension.height - INT_CONVERSION_FRAME_HEIGHT) / 2;
    }
		
    /** Cette methode est chargee d'instancier la fenetre qui convertit les infos */
    public void createSwingConversionFrame()
    {	
    	swingFrame 						= new SwingConversionView();
		
		// on fixe les valeurs importantes de notre fenetre : taille, position, sa visibilite
		swingFrame.setSize(		INT_CONVERSION_FRAME_WIDTH, INT_CONVERSION_FRAME_HEIGHT);
		swingFrame.setLocation(	conversionFrameWidthPosition, conversionFrameHeightPosition);
		swingFrame.setVisible(	true) ;
    }
	
    /**
    * @param e : L'instance de l'exception generee par la machine virtuelle.
    * 
    * Remarquons que le type de cette instance est de haut niveau : 
    * java.lang.Throwable.Exception
    * on assimile en passant ainsi le type p�re tout type d'exception possible.
    * <p>
    * Dans Java on distingue 3 types d'exceptions 
    * <p>
    * (Erreurs, Exceptions non verfiees, Exception Verifiees) 
    * <p>
    * structurees selon la hierarchie suivante:
    * <p>
    * Toutes les exceptions quelqu'elles soient heritent de java.lang.Throwable
    * <p>
    * La classe Throwable a deux fils interessant: 
    * <p>
    * "Error"		: Probl�me fatal au programme 
    * (insuffisance de memoire, impossibilite de charger une classe, securite, ...)
    * <p>
    * Il est inutile de recuperer des errors car de toute fa�on, le programme 
    * ne pourra continuer. Les Errors sont des probl�mes de haut niveau qui 
    * concerne l'environnement d'execution
    * <p>
    * "Exception"	: Le p�re des exceptions verifiees et non verifiees
    * <p>
    *		Exceptions Verifiees:		
    * <p>
    *			herite de la classe "Exception"
    *			doivent etre obligatoirement recuperees (try catch) dans les sources
    *			sinon le compilateur ne validera pas le programme
    *			Exemple : UrlMalformedException, on cree un object Url que l'on
    *			essaye ensuite d'ouvrir, et potentiellement on a un risque
    *			de manque d'integrite de l'url, donc la VM nous oblige ˆ prendre
    *			en compte ce risque. 
    * <p>
    *		Exceptions non Verifiees:	
    * <p>
    *			Elles heritent de la classe "RuntimeException" fils d'"Exception"
    *			Elles concernent tous les problemes relatifs ˆ l'execution du programme
    *			(division par 0, depassement de la taille d'un tableau, object null,
    *			format de nombre incorrect, ...)
    * <p>
    *			C'est ˆ vous de prevoir les erreurs potentiels qui peuvent etre lancees
    *			par exemple vous savez qu'un champ de texte suppose numerique peut prendre des 
    *			caract�res alphabetiques ! lorsque vous essayerez de transformer ces lettres
    *			en valeur enti�re, ou reelle, une exception de type NumberFormatException
    *			sera levee, ˆ vous de le prevoir dans votre code et dans le catch on peut par 
    *			exemple creer une boite de dialogue en stipulant l'erreur, ainsi que remettre 
    *			le champ de texte ˆ vide.
    * 
    * <p>
    * Remarque: on peut creer ses propres classes d'exception qui herite de la classe "Exception" !
    * 
    */
    public void handleException(Exception e)
    {
		// La method toString() qui renvoie une chaine de caract�re (String)
		// est propre ˆ la class "Object" qui est la classe de plus haut niveau
		// en java.
		//
		// Quelque soit l'object avec lequel on travaille, on peut toujours 
		// appele myObject.toString() car meme si le corps de fonction n'est 
		// pas definie dans la classe de l'object, on peut quand meme appele
		// celui par default fournie par la VM.
		System.out.println("exception " + e.toString());
			
		// imprime sur la sortie standard la pile d'appel de la fonction qui a generee 
		// l'exception
		e.printStackTrace();	
    }
}
