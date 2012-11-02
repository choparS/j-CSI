package fr.shortcircuit.xml;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.shortcircuit.gui.IXMLViewConstants;
import fr.shortcircuit.model.Actor;
import fr.shortcircuit.model.ProductElement;


/**
 * Gestion de Collection (package java.util): Interfaces, Classes abstraites et concretes.
 * ======================================================================================
 *  
 * Les classes d'Adapter abstraites: (AbstractCollection <- (AbstractList, AbstractMap, AbstractSequentialList, AbstractSet)), 
 * specifient comme 'abstract' les methodes des interfaces (a implementer obligatoirement dans les classes concretes): 
 * 
 * 
 * * Interface Collection: 	gestion de listes diverses d'objets
 * 
 * 
 * * Interface Set: 		extends Collection; 
 * 							PAS de doublons (nouvel element non ajoute); 	
 * 
 * * Interface SortedSet: 	extends Set; 
 * 							ordonnee (index de position); triee ascendante (independant de l'insert); recherche rapide (* 10k/List); 
 * 
 * 
 * - Class HashSet: 		extends AbstractSet implements Set, Cloneable, Collection, Serializable; 
 * 							NON synchronise; null autorise; pas de garantie de l'ordre lors d'iteration (chaotique); 	
 * 
 * - Class LinkedHashSet:	extends HashSet; 
 * 							gere une double liste interne ordonnee (selon l'insertion); NON synchronise; 
 * 
 * - Class TreeSet: 		extends AbstractSet implements Set, SortedSet, Cloneable, Collection, Serializable; 
 * 							NON synchronise; cout d'operations basiques (add, remove, contains): log(n)
 *
 * 							2 constructeurs: 	simple: 			ordre naturel ou avec des java.lang.Comparable (int compareTo());
 * 																	RuntimeChecked, throws ClassCastException ! 
 *
 * 												avec Comparator:	interface de tri par comparaison du package java.util; 
 * 																	methodes (boolean equals(Object o), compare(Object o, Object o));
 * 																	Recommande d'implementer aussi Serializable (car manipule ce type)
 * 
 * 
 * 
 * * Interface List: 		extends Collection; 
 * 							doublons et null(s) autorises; ordonnee (index de position) Mais NON tries (plus lent); 
 * 
 * 
 * - Class Vector: 			extends AbstractList implements List, Cloneable, Collection, RandomAccess, Serializable; 
 * 							issue du JDK 1.0 (inclue dans les Collections pour compatibilite); object synchronise !
 * 
 * - Class ArrayList: 		extends AbstractList implements List, Cloneable, Collection, RandomAccess, Serializable; 
 * 							proche de la Classe Vector; objet NON synchronise (utiliser l'operateur ou Collections.synchronizedList); 
 * 							redimensionnable; gestion d'index (insert, get); objet Comparable non obligatoire (ignore);
 * 
 * - Class LinkedList: 		extends AbstractSequentialList(extends AbstractList) implements List, Cloneable, Collection, Serializable; 
 * 							Fournit un ListIterator (has+next()-previous(), remove, index), (model de Stack, Queue, ...)
 * 
 * 
 * 
 * Remarque: 				JDK 1.3; certains constructeurs sont a base de Collection;
 * 							pas d'implementation concrete de l'interface Collection (uniquement les sous interfaces);							
 * 
 * 							Collections proposent des methodes de manipulation de Collection, List, SortedSet, SortedMap, Comparator:  
 * 							(copy, *index*, list, max, min, reverseOrder, rotate, search, shuffle, singleton, sort, swap, 
 * 							synchronized{Collection, List, Map, Set, SortedMap, SortedSet}, 
 * 							unmodifiable{Collection, List, Map, Set, SortedMap, SortedSet}).
 * 
 * Remarque: 				Le Vector utilise ordonne les elements selon leur ordre de lecture dans le fichier
 * 							Le TreeSet tri des objets de type Comparable, (compareTo() utilise le champ "Designation"
 **/ 

public class MySaxReflecter<T extends Collection<ProductElement>> extends DefaultHandler implements IXMLViewConstants, IXMLFactory
{
	private Map					mapType, mapGenre;
	
	private T					collectionElt;

	private String 				fileName;

	private Object				currentObject;
	
	private Collection			currentCollection;

	private String 				STR_PACKAGE_PREFIX	= "fr.shortcircuit.model"; 
	

	
	public MySaxReflecter(T collectionElt, String fileName)
	{
		createStructures(collectionElt, fileName);

		createSaxParser();
		
		mapContent();
		
		setParents();
	}
	
	public void createStructures(T collectionElt, String fileName)
	{
		this.fileName					= fileName;  			
		this.collectionElt				= collectionElt;
		
		System.out.println("MySaxReflecter starts parsing " + fileName);	
	}

	//currentCollection				= collectionElt;

	/** Instanciation du parser Sax affecte. */ 	
	public void createSaxParser()
	{		
	  	SAXParserFactory factory 		= SAXParserFactory.newInstance();
	
	   	try 
	   	{
	    	SAXParser saxParser 		= factory.newSAXParser();
	
	    	saxParser.parse(new File(fileName), this); 
	    	
		}
		catch (Throwable t) 	{System.out.println("createSaxParser: " + t.toString()); t.printStackTrace();}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//SAX parsing methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Cette methode effectue une verification sur le nouveau noeud XML rencontre 
	 * par le biais d'un tableau declare dans l'interface "Constants":
	 * 
	 * //Nom des classes supportees pour le mecanisme de reflection SAX
	 * public String tabClassSupported[] = {"ProductElement", "GameElement", "DvdElement", "K7Element"};
	 * 
	 * ("Existe t'il bien une association entre le nom du noeud rencontre et une class associee ?")
	 *  
	 * L'instruction "Arrays.asList(tabClassSupported).contains(qName)", 
	 * nous permet d'obtenir depuis un (Object[] tabObject) passe en parametre un object "List",
	 * sur lequels applique la methode usuelle: "contains" renvoyant un boolean.	 
	 * 
	 * Si cette condition est satisfaite, alors on appel la methode de traitement par reflexion du noeud.
	 * 
	 * 
	 * Remarque: ici c'est toujours la meme Collection "collectionElt" passe en parametre qui stocke les objects
	 * independamment de leur type, on pourrait envisager d'avoir un tableau de Collection dedie remediant a cela...
	 */ 
	public void startElement(String namespaceURI, String sName, String qName, Attributes attrs) throws SAXException
	{
		//System.out.println("startElement: " + qName);
		
		//Verification du type dans la classe Constants
		//{"ProductElement", "GameElement", "DvdElement", "K7Element", "actor"};
		if (LIST_AUTOMATION_SUPPORTED_CLASS.contains(qName))  
	  	{
			if (!qName.equalsIgnoreCase("actor"))
				currentCollection = collectionElt;
			else if (!(currentObject instanceof Actor)) 
				currentCollection = ((ProductElement) currentObject).getActors();
	 		
	  		reflectElement(qName, attrs, currentCollection);
	 		//reflectElementByFields(qName, attrs, currentCollection);
	  	}
	}

	public void endElement(String namespaceURI, String sName, String qName) throws SAXException   	{}	
	public void characters(char buf[], int offset, int len) throws SAXException						{}
	public void startDocument() throws SAXException													{}	
	public void endDocument() throws SAXException													{System.out.println("MySaxReflecter: endDocument: " + collectionElt.size() + " elt(s)");}

	///////////////////////////////////////////////////////
	//Sub-parsing methods : create, chain, and feed objects 
	///////////////////////////////////////////////////////
	
	/**
	 * Cette methode procede par etapes afin d'automatiser la creation d'instances diverses de classes, 
	 * a partir des types de noeuds XML rencontres (String className, en parametre):
	 * 
	 * 1: elle recupere une instance de type "Class" concernant le modele demande 
	 * (Class associatedClass = Class.forName(className);)
	 * 
	 * 2: elle produit par cet objet une instance vierge de donnees 
	 * (Object newInstance = associatedClass.newInstance();).
	 * 
	 * 3: elle parse le tableau des attributs rencontres dans le noeud XML 
	 * (for (int i = 0; i != attrs.getLength(); i++))
	 * 
	 * 4: elle complete le contenu des objets crees en appellant la methode "setXXX",
	 * (ayant comme parametre de methode un objet de type String (Class tabParameterTypes[] = {String.class};)),
	 * associee a chacun des champs XXX recontres dans la liste des attributs XML du noeud.
	 * (Method associatedSetMethod = associatedClass.getMethod("set" + currentAttributeName, tabParameterTypes);)
	 * 
	 * A cette fin (associatedSetMethod.invoke(newInstance, tabParameterValues);) 
	 * elle utilise 2 references supplementaires:
	 * 
	 * 		- la reference "newInstance" de l'objet cree sur laquelle invoquer la methode 
	 * 		(Class, Field et Method ne sont pas ici directement lies a une instance, 
	 * 		ce sont des types propres au Model de classe)
	 * 
	 * 		- un tableau de parametre compose d'une valeur litterale issu de l'attribut XML parse
	 * 		(Object tabParameterValues[]						= {currentAttributeValue};).
	 * 
	 * 5: enfin, l'objet parametre collectionStorer reference la nouvelle instance creee
	 * (collectionStorer.add(newInstance);)
	 * 
	 * Remarques:
	 * 
	 * 1: les attributs d'un noeud XML commmencent tous par une majuscule Id, Designation, Type, Price
	 * ("<k7Element Id="K001" Designation="la belle verte" Type="K7" Price="100" />")	 * 
	 *
	 * 2: cette methode marche tres bien, elle se fonde sur les principes de l'encapsulation 
	 * pour affecter les valeurs litterales des champs (suppose ici "private"). 
	 */ 
	public void reflectElement(String className, Attributes attrs, Collection collectionStorer)
	{
		//System.out.println("reflectElement: " + className);
		
		try
		{
			Class associatedClass								= Class.forName(STR_PACKAGE_PREFIX + "." + className);
			Class tabParameterTypes[]							= {String.class};
			
			//permet de recuperer un constructeur specifique
			//Constructors tabAssociatedConstructors			= associatedClass.getDeclaredConstructors(tabParameterTypes);
			
			Object newInstance									= associatedClass.newInstance();
			currentObject										= newInstance;
			
			for (int i = 0; i != attrs.getLength(); i++)
			{
				String currentAttributeName						= attrs.getQName(i);
				String currentAttributeValue					= attrs.getValue(i);
				String currentMethodName						= "set" + currentAttributeName.substring(0, 1).toUpperCase() + currentAttributeName.substring(1);
				Object tabParameterValues[]						= {currentAttributeValue};
				
				Method associatedSetMethod						= associatedClass.getMethod(currentMethodName, tabParameterTypes);
				
				associatedSetMethod.invoke(newInstance, tabParameterValues);					
			}
			
			//System.out.println("collectionStorer: " + collectionStorer.getClass().getName());
			
			collectionStorer.add(newInstance);
		}
		catch (Exception e) {System.out.println("reflectElement: " + e.toString()); e.printStackTrace();}
	}

	/**
	 * Cette methode est inspiree de la precedente cependant elle diverge en utilisant une regle simple:
	 * "Tout ou partie (au minimum les equivalents XML !) des champs de mes objets son declares en public"
	 * 
	 * On va donc ici s'employer a affecter la valeur d'un Field directement sans passer par la methode set associee.
	 * ((Field) associatedClass.getField(attrs.getQName(i))).set(newInstance, attrs.getValue(i));)
	 * 
	 * Remarques: 
	 * 
	 * 1: Le champ recherche dans ce mecanisme reflexif doit etre declare en "public" dans la classe concernee, 
	 * sinon l'objet eltField (Field eltField = elt.getClass().getField(publicKeyField);) vaut null.  
	 * 	 
	 */ 
	public void reflectElementByFields(String className, Attributes attrs, Collection collectionStorer)
	{
		try
		{
			Class associatedClass								= Class.forName(STR_PACKAGE_PREFIX + "." + className);
			Object newInstance									= associatedClass.newInstance();

			for (int i = 0; i != attrs.getLength(); i++)
				associatedClass.getField(attrs.getQName(i)).set(newInstance, attrs.getValue(i));
			
			collectionStorer.add(newInstance);
		}
		catch (Exception e) {System.out.println(e.toString());e.printStackTrace();}
	}

	/**
	 * Mapping des objets
	 * ==================
	 * 
	 * * Interface Map: 		Gestion de structures de hashage 
	 * 
	 * 
	 * * Interface SortedMap:	extends Map; 
	 * 							hashage ordonne avec tri; 
	 * 
	 * - Class Hashtable: 		extends Dictionary implements Map, Cloneable, Serializable  
	 * 							JDK 1.0 (inclue dans les Map pour compatibilite); key-value null NON autorises; synchronisee
	 * 							Les objets utilises comme cles doivent implementer les methodes int hashCode(), boolean equals(Object o)
	 * 
	 * - Class HashMap: 		extends AbstractMap implements Map, Cloneable, Serializable 
	 * 							JDK 1.2; remplace la HashTable; key-value null autorises; NON synchronisee; pas de garantie sur l'ordre de la Map.	
	 * 
	 * - Class LinkedHashMap:	extends HashMap 
	 * 							JDK 1.4; HashMap maintient une double liste liee ordonnee (par index de position) des entrees; 
	 * 							evite les insertion chaotique de HashMap (et Hashtable), ne subit pas le cout eleve des TreeMap;
	 * 							pratique pour effectuer des copies ordonnees de Map; 
	 * 							moins performant que des HashMap (a cause de la double liste).
	 * 							Fourni un constructeur renvoyant un Iterator base sur l'ordre d'acces des entrees (de la moins a la plus recente).
	 * 
	 * - Class TreeMap: 		extends AbstractMap; implements SortedMap, Cloneable, Serializable; 
	 * 							keys triees dans l'ordre ascendant (ordre naturel ou type Comparable) ou selon un Comparator (cf constructors); 
	 * 							JDK 1.2; NON synchronise; cout d'operations basiques (get, put, remove, containsKey): log(n)
	 * 
	 * 
	 * - Class WeakHashMap: 	extends AbstractMap implements Map
	 * 							Ne conserve pas dans les keys les entrees garbage-collectees (~ thread de redimensionnement automatique).
	 * 							Traitement possible de value encapsulees en tant qu'objets de type 'WeakReference' (loopback vers des objets GC) 
	 * 							JDK 1.2; NON synchronise; key-value null autorises; prôche de HashMap; 
	 * 
	 * 
	 * Remarque: 				Parametres important: initialCapacity et loadFactor 
	 **/
	public void mapContent()
	{
		mapType													= new HashMap();
		mapGenre												= new TreeMap();

		reflectMap("ProductElement", "price", collectionElt, mapType);
		reflectMap("ProductElement", "genre", collectionElt, mapGenre);
	}
	
	
	/***************************************************************************************************************
	 * Cette methode permet ici encore d'illustrer un mecanisme de haut niveau par reflexion:
	 * 
	 * Elle se charge de hasher un vecteur d'objets, 
	 * en choisissant un champ dans le modele de ces derniers comme responsable des cles de la table produite. 
	 * (Field eltField = elt.getClass().getField(publicKeyField);)
	 * 
	 * La boucle lit la valeur du champ associe a l'objet (eltField.get(elt).toString();)
	 * 
	 * Ici encore nous associons un objet de reflexivite ("Field") et instance, 
	 * afin d'obtenir l'interaction dynamique voulue permettant de recuperer la valeur associee au champ de l'objet.   
	 * 
	 * Cet appel est directement associe a l'interrogation de la Map
	 * si cette derniere n'a pas de Collection associee a cette cle alors on en construit une, 
	 * puis on complete la Collection courante propre a la cle.
	 * 
	 * Remarques: 
	 * 
	 * 1: Le champ recherche dans ce mecanisme reflexif doit etre declare en "public" dans la classe concernee, 
	 * sinon l'objet eltField (Field eltField = elt.getClass().getField(publicKeyField);) vaut null.  
	 * 
	 * 2: L'implementation par acces de Method, au lieu de Field est laisse a la libre implementation du lecteur !)
	 ****************************************************************************************************************
	 */ 	
	public Map reflectMap(String className, String publicKeyField, Collection collection2map, Map mapElt)
	{
		//System.out.println("reflectMap for class: " + className + ", publicKeyField: " + publicKeyField);
				
		try
		{
			//Referencement du "Member" utilise: optimisation du cout de l'algo
			Field eltField									= Class.forName(STR_PACKAGE_PREFIX + "." + className).getField(publicKeyField);
			Iterator iteratorElt							= collection2map.iterator();
		
			while (iteratorElt.hasNext())
			{
				try
				{
					Object elt								= iteratorElt.next();		
					Object memberValue						= eltField.get(elt);

					if (memberValue != null)
					{
						Collection collectionEntry				= (Collection) mapElt.get(memberValue.toString());	
						
						if (collectionEntry == null)
						{
							//collectionEntry					= new Vector(); //pas de tri sur les entrees
							collectionEntry						= new TreeSet(); //trie et ordonne, ProductElement isAssignableFrom(Comparable) !
							
							collectionEntry.add(elt);	
						
							mapElt.put(eltField.get(elt).toString(), collectionEntry);
						}
						else
							collectionEntry.add(elt);
					}
				}
				catch (Exception e) {System.out.println(e.toString());/*e.printStackTrace();*/}		
			}	
		}
		catch (Exception e) {System.out.println(e.toString());e.printStackTrace();}

		return mapElt;			
	}
	 
	//Dans cet exemple, il n'y a pas de gestion de la profondeur de l'arbre: dimension 0
	//pas de gestion du modele composite de l'arbre, nous devons donc injecter les "dependances" dans nos objects 
	public void setParents()
	{
		for (ProductElement product : collectionElt)
			for (Actor a : product.getActors())
				a.setParent(product);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//IXMLFactory methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Collection getCollectionElt() 						{return collectionElt;}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Getters
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Map getMapType() 									{return mapType;}
	public Map getMapGenre() 									{return mapGenre;} 

	
}
