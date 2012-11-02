package fr.shortcircuit.utils;

import java.text.DecimalFormat;

/**
  * La class Converter sert a la conversion des devises
  * Pas de constructeur, les methodes sont static
  */
public class ConverterHelper
{
    //Variable globale
    public static double DOUBLE_FRANCS_EURO_VALUE = 6.55957;

    //permet de renvoye une chaine de caracteres avec un format specifique 
    public static DecimalFormat		numberFormatter				= new DecimalFormat("###,###,##0.0000");

    /**
    * @return une chaine de caractere contenant la valeur du montant en Francs
    * (passee en entree) convertit en euros
    * @param val une valeur double a convertir en euros
    *
    * throws nous precise que potentiellement cet environnement peut lancer des signaux 
    * d'erreurs de type "NullPointerException", ici l'exception n'est pas catchee, on 
    * demande a la VM de la remonter a la pile appelante qui elle se charge de traiter 
    * l'erreur.
    * <p>
    * Un throws implique toujours qu'un try-catch de l'exception specifie soit opere 
    * dans la methode appelante, si ce n'etait pas le cas on aurait une erreur a la 
    * compilation
    */
        
    public static String francsEnEuros (double val) throws NullPointerException 
    {
        return numberFormatter.format(val / DOUBLE_FRANCS_EURO_VALUE);
    }

    /**
    * Schema inverse par rapport au precedent, ici la conversion se fait depuis 
    * des euros vers des francs
    * <p>
    * @return une chaine de caractere contenant la valeur des Euros
    * (passe en entree) convertit en francs
    * @param val une valeur reelle a convertir en francs
    */
    public static String eurosEnFrancs (double val) throws NullPointerException 
    {
        return numberFormatter.format(DOUBLE_FRANCS_EURO_VALUE * val);
    }
	
    // Ici on peut rajouter des methodes EurosInPesetas, PesetasInEuros...
}
