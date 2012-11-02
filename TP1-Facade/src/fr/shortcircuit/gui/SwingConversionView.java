package fr.shortcircuit.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * La structure de l'object conversionFrame herite des proprietes de la class JFrame.
 * Cet object est une fenetre externe dans la librairie de composant Swing
 * Les classes Swing sont reperees par un "J" devant le nom de la class.
 * <p>
 * conversionFrame implemente les interfaces suivantes
 * <p>
 * ActionListener	: Gestion des evenements de type "action" sur un bouton 
 * methode ˆ redefinir obligatoirement: public void actionPerformed(ActionEvent e)
 * <p>
 * FocusListener	: Gestion du focus sur des composants graphiques
 * methode ˆ redefinir obligatoirement: public void focusGained(FocusEvent e) <br>
 *					public void focusLost(FocusEvent e) <br>
 * <p>
 * WindowListener	: Gestion des evenements propres aux Fenetres
 * methode ˆ redefinir obligatoirement: public void windowActivated(WindowEvent e)	<br>
 * 					public void windowClosed(WindowEvent e)		<br>	
 *					public void windowDeactivated(WindowEvent e) 	<br>
 * 					public void windowDeiconified(WindowEvent e)	<br>	
 * 					public void windowIconified(WindowEvent e)	<br>	
 * 					public void windowOpened(WindowEvent e)		<br>	
 *					public void windowClosing(WindowEvent e)	<br> 
 *<p>
 * Si une des methodes enumerees precedemment n'est pas redefinie dans la class 
 * SwingConversionFrame, alors on obtiendra un echec ˆ a compilation
 * <p>
 * Les interfaces suivantes sont aussi disponibles en Java, toutefois nous ne nous en 
 * servirons dans cet exemple
 * <p>
 * ComponentListener : Listener d'evenements de haut niveau
 * methode ˆ redefinir obligatoirement: public void componentHidden(ComponentEvent e)	<br>
 *					public void componentMoved(ComponentEvent e)	<br>
 *					public void componentResized(ComponentEvent e)	<br>
 *					public void componentShown(ComponentEvent e)	<br>
 * <p>
 * KeyListener		 : Listener d'evenements clavier
 * methode ˆ redefinir obligatoirement:	public void keyPressed(KeyEvent e)	<br>
 *					public void keyReleased(KeyEvent e)	<br>
 * 					public void keyTyped(KeyEvent e)	<br>
 * <p>
 * MouseMotionListener : Listener d'evenements souris de deplacement
 * methode ˆ redefinir obligatoirement: public void mouseDragged(MouseMotionEvent e)	<br>
 *					public void mouseMoved(MouseMotionEvent e)	<br>
 * <p>
 * <hr>
 * <p>
 * 
 * En java on peut aussi, pour traiter les evenements, passer par des methodes specifiques
 * qui sont l'equivalent en un seul point d'entree des methodes propres aux interfaces !
 * (mais moins propre en terme de design, et de segmentation fonctionnelle.
 * <p>
 * Les methodes sont les suivantes
 * <p>
 * 
 * processKeyEvent(KeyEvent e)	<br>
 * processFocusEvent(FocusEvent e)	<br>
 * processMouseEvent(MouseEvent e) 	<br>
 * processMouseMotionEvent(MouseMotionEvent e) 	<br>
 * processWindowEvent(WindowEvent e) 	<br>
 * 
 * <p>
 * 
 * pour pouvoir les utiliser il faut utiliser la commande suivante
 * <p>
 * enableEvents(AWTEvent.MOUSE_EVENT_MASK);	<br>
 * enableEvents(AWTEvent.FOCUS_EVENT_MASK);	<br>
 * enableEvents(AWTEvent.KEY_EVENT_MASK);	<br>
 * enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);	<br>
 * enableEvents(AWTEvent.WINDOW_EVENT_MASK);	<br>
 *
 * <p>
 * 
 * Exemple pour les evenements souris 
 * <p>
 * public void processMouseEvent(MouseEvent e) 	<br>
 * {	<br>
 *		if (e.getID() == MouseEvent.MOUSE_RELEASED){}	<br>
 * 	<p>
 *		if (e.isPopupTrigger()){} 	<br>
 * <p>
 *		if (e.getID() == MouseEvent.MOUSE_CLICKED) {} 	<br>
 * }
 * <p>
 * Remarque ici pour le click droit on la procedure e.isPopupTrigger()
 * 
 */
public class SwingConversionView extends JFrame implements IConversionConstants
{	
	//Events Controler (Pattern Model-View-Controler)
	public ConversionControler		controler;
	
    /** Bouton graphiques */
    public JButton 					exit, clear;
	
    /** Les labels sont des etiquettes graphiques que l'on place sur le panel desire */
    public JLabel					frenchPriceLabel, euroPriceLabel;
	
    /** Les textfields sont des zones graphiques propre ˆ l'insertion de texte */
    public JTextField				txfEntryFrancs, txfEntryEuros;
			
    /**
    * @return une instance de la class conversionFrame
    * @param width	: largeur de l'object courant
    * @param height	: hauteur de l'object courant
    */
    public SwingConversionView()
    {
    	createControler();
		stateOnCurrentObject();	
		
		createLabels();
		createButtons();
		createTextField();
		
		addComponents();
    } 

	private void createControler()
	{
		controler		= new ConversionControler(this);		
	}

    /** Fixe les proprietes de l'object courant */
    public void stateOnCurrentObject()
    {
		// le layout, object en charge du positionnement des composants graphiques est  
		// "null", par ce biais on informe la machine virtuelle que les composants vont 
		// etre fixes via l'appel elt.setBounds(positionX, positionY, largeur, hauteur)
		//
		// Java propose un certain nombre de layout :
		//
		// BorderLayout		: compose suivant les points cardinaux (Nord, Sud, Est, Ouest) 
		// ainsi que le centre
		//
		// GridLayout		: Layout en tableau, chaque cellule ayant la particularite d'avoir 
		// la meme taille.
		//
		// FlowLayout		: Layout sur une seule ligne
		//
		// BoxLayout		: Layout en pile
		//
		// GridBagLayout	: Layout le plus complexe, il permet cependant de placer des 
		// cellules de la fa�on dont on l'entend
		//
		//
		//getContentPane().setLayout(null);
			
	    // tableau de cellules: 2 lignes, 3 colonnes
	    setLayout(new GridLayout(2,3));
	
		
		// Propriete de redimensionnement de la fenetre
		setResizable(true);
		
		// ajout d'un listener d'evenement de type "window" vers la source "this"
		// En clair l'object courant "this" va etre charge de gerer les evenements propres
		// aux fenetre, ouverture, fermeture, ...
		//
		// Ces evenements sont manipules par le biais des methodes declares par l'interface,
		// methodes redefinies dans l'object courant. 
		//
		// En Java les evenements suivent la loi "producteur-consommateur"
		// Un object "source" genere une instance d'evenement (ici une instance de la 
		// class "WindowEvent") qui fait partie d'un contexte (rappelons nous la pile d'appel)
		// Un gestionnaire charge d'ecouter des types precis d'evenements est appele par la 
		// machine virtuelle lorsque cette derniere emet des signaux
		//
		// 2eme solution
		// on pourrait aussi avoir comme cible d'ecoute un object qui herite de la classe
		// WindowAdapter. Un adapter est une classe qui se charge de redefinir les methodes
		// specifies par l'interface auquel il se rapporte (WindowListener - WindowAdpater,
		// KeyListener - keyAdapter, ..., remarque : il n'existe pas d'actionAdapter)
		// dans cette classe on ne redefinirait que les methodes que l'on souhaite traiter.
		//
		// Troisieme solution on peut declarer comme cible de traitement des evenement 
		// un objet interne qui va etre declarer ˆ la volee:
		//
		// addWindowListener(new WindowListener() {....redefinition des methodes souhaitees...} 
			
        addWindowListener(controler);
    }

    /** Creation des objects de type JLabel */
    public void createLabels()
    {
		this.frenchPriceLabel 		= new JLabel(STR_FRENCH_PRICE_LABEL);
		this.euroPriceLabel			= new JLabel(STR_EUROS_PRICE_LABEL);	
		
		// on fixe ici les "contraintes" graphiques evoquees precedemment
		// (positionX, positionY, largeur, hauteur)
		//
		// Remarquons ici l'utilite de l'object fontMetrics se chargeant de determiner
		// la taille exact, en pixel, des String affichees dans les objects JLabel
		// frenchPriceLabel.setBounds(	25, 15, FONT_METRICS.stringWidth(STR_FRENCH_PRICE_LABEL), 20);
		// euroPriceLabel.setBounds(	25, 45, FONT_METRICS.stringWidth(STR_EUROS_PRICE_LABEL), 20); 	
		
		// Le panel, recupere par la methode getContentPane(), applique ˆ l'object courant, 
		// est une zone graphique qui va se charger de contenir des composants visuels.
		//
		// On distingue en Java deux types d'objects graphiques
		// les "hauts niveaux", la classe Panel, ou JPanel pour swing, en sont des exemples.
		// Ces objects sont voues ˆ stocker et representer en leur sein d'autres composants 
		// graphiques
		//
		// Les "atomiques" ces objects ne peuvent pas contenir d'autres composants graphiques
		// les objects buttons (Button, JButton) ou label (Label, JLabel) en sont des 
		// exemples

		// les deux labels crees fixe des "infos-bulles" en appelant la methode 
		// setToolTipText(String).
		frenchPriceLabel.setToolTipText(STR_FRENCH_TOOLTIP_PRICE_LABEL);
		euroPriceLabel.setToolTipText(	STR_EUROS_TOOLTIP_PRICE_LABEL);
    }
	
    /** Service de creation des boutons */
    public void createButtons()
    {
	    // Pour recapituler l'ajout d'un composant graphiques passe par les phases suivantes :
		//
		// instanciation, mise ˆ jour des contraintes, ajout au panel, 
		this.clear 				= new JButton(STR_CLEAR_LABEL);
		this.exit 				= new JButton(STR_EXIT_LABEL);
		
		// clear.setBounds(260, 15, FONT_METRICS.stringWidth(STR_CLEAR_LABEL) + 50, 20);
		// exit.setBounds(260, 45, FONT_METRICS.stringWidth(STR_EXIT_LABEL) + 50, 20);
			
		// ici le bouton "clear" declare ajouter un gestionnaire d'evenement de type "Action"
		// sur l'object "this". L'object courant va donc etre responsable de la gestion des 
		// evenements de type "Action", qui seront traites dans la methode actionPerformed
		clear.addActionListener(controler);
		exit.addActionListener( controler);
			
		//Infos-Bulles
		clear.setToolTipText(STR_CLEAR_TOOLTIP_LABEL);
		exit.setToolTipText( STR_EXIT_TOOLTIP_LABEL);
    }

    /**
    * Creation des champs de texte, quasiment identique ˆ la methode createButtons, 
    * <p>
    * Le point important cependant ˆ prendre en compte est le type 
    * (evenements-gestionnaire) mise en place, ici on traite des evenements de type 
    * "FocusEvent" geres par les methodes "focusGained", "focusLost"
    */
    public void createTextField()
    {
		this.txfEntryFrancs			= new JTextField();
		this.txfEntryEuros			= new JTextField();
		//int strWidth				= FONT_METRICS.stringWidth(STR_FRENCH_PRICE_LABEL);
		//txfEntryFrancs.setBounds(25 + strWidth, 15, 100, 20);
		//txfEntryEuros.setBounds( 25 + strWidth, 45, 100, 20);
								
		txfEntryFrancs.addFocusListener(controler);
		txfEntryEuros.addFocusListener( controler);
    }
    
    /** Ajout des composants graphiques dans l'ordre pris par le container de type GridLayout */
    public void addComponents()
    {
 		// Ce panel appel ensuite la fonction add(Component) qui rajoute l'object graphique
 		// en son sein. Les "components" (ou "composants" en français) sont les labels crees
 		// au dessus
 		//
 		getContentPane().add(frenchPriceLabel);
 		getContentPane().add(txfEntryFrancs);
 		getContentPane().add(clear);
 		getContentPane().add(euroPriceLabel);
 		getContentPane().add(txfEntryEuros);
 		getContentPane().add(exit);
	}
    
}