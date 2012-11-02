package fr.shortcircuit.xml;


import java.io.File;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import fr.shortcircuit.model.Entry;


/**
 * Cette classe herite des proprietes de DefaultHandler, elle est compatible avec les specifications SAX2. 
 * Elle fournie les implementations par defaut pour l'ensemble des 4 classes majeures de handler : 
 * 
 * 
 * EntityResolver: entityResolver permet de resoudre les references externes rencontrees lors du parsage du contenu XML :
 * 
 * InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException
 * 
 * 
 * 
 * DTDHandler: Evenements propres a l'environnement de la DTD du document (si existante) :
 * 
 * void notationDecl(String name, String publicId, String systemId)
 * void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException
 * 
 * 
 * 
 * ContentHandler : gestion du contenu (les debuts-fin de prefixe-tags-document, leurs proprietes, les contenu texte, 
 * les Processing Instruction, Locator) :
 * 
 * void startElement(String namespaceURI, String sName, String qName, Attributes attrs) throws SAXException
 * void endElement(String namespaceURI, String sName, String qName) throws SAXException  
 * void startDocument() throws SAXException
 * void endDocument() throws SAXException								
 * void startPrefixMapping(String prefix, String uri) 
 * void endPrefixMapping(String prefix)					
 *		
 * void characters(char buf[], int offset, int len) throws SAXException
 * void ignorableWhitespace(char[] ch, int start, int length)
 * void processingInstruction(String target, String data)
 * void setDocumentLocator(Locator locator)
 * void skippedEntity(String name)
 * 
 *
 *  
 * ErrorHandler : Gestion des erreurs rencontres lors du parsage du document :
 * 
 * void error(SAXParseException exception)
 * void fatalError(SAXParseException exception)
 * void warning(SAXParseException exception)
 * 
 */

public class MySaxParser extends DefaultHandler implements IXMLFactory
{
	private Collection			collectionElt;

	private String				fileName;
	

	public MySaxParser(String fileName)
	{
		createStructures(fileName);

        createCollection();

        createSaxParser();
	
        hashEntry();
	}
	
	public void createStructures(String fileName)
	{
    	this.fileName			= fileName;  			
		
        System.out.println("MySaxParser starts parsing " + fileName);	
	}

	public void createCollection()
	{
		collectionElt			= new Vector();                		
	}

	/**
	 * Instanciation du parser Sax affecte.
	 */ 	
	public void createSaxParser()
	{		
		SAXParserFactory factory= SAXParserFactory.newInstance();
	
        try 
        {
        	SAXParser saxParser	= factory.newSAXParser();
	
        	saxParser.getXMLReader().setErrorHandler(this);
            saxParser.getXMLReader().setEntityResolver(this);
            saxParser.getXMLReader().setDTDHandler(this);
	
            saxParser.parse(new File(fileName), this); 
        }
        catch (Throwable t)	{System.out.println("createSaxParser: " + t.toString()); t.printStackTrace();}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Gestion des Structures 
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void addInCollection(Object elt)
	{
		collectionElt.add(elt);	//methode de Collection, convient a tout sous-type
	}
	
	/**
	 * L'Iteration peut s'effectuer a travers differents types d'objet: 
	 * ex:
	 * Vector.elements(): 		Renvoie une Enumeration ("NotFailFast": modification possible du Vector, car clone() des elements)
	 * Vector.iterator(): 		Renvoie un Iterator; ("FailFast": PAS de modification possible du Vector: throws ConcurrentModificationException)
	 * Vector.listIterator(): 	Renvoie un ListIterator (next-previous, index); ("FailFast": PAS de modification du Vector); 
	 * 
	 * Remarque: Iterator ne fait pas parti du FrameWork de Collection, 
	 */	
	public void hashEntry()
	{
		Map mapEntry					= new Hashtable();
        Iterator iterator				= collectionElt.iterator(); 				//methode de Collection
        //ListIterator listIterator		= ((List) collectionElt).listIterator();	//methode de List
		
        while (iterator.hasNext())
        {
        	Entry elt					= (Entry) iterator.next();			
			Vector vectorEntryForType	= (Vector) mapEntry.get(elt.type);		
					
			if (vectorEntryForType == null)
				vectorEntryForType		= new Vector();
						
			vectorEntryForType.addElement(elt);	
					
            mapEntry.put(elt.type, vectorEntryForType);
        }	
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//SAX parsing methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void startElement(String namespaceURI, String sName, String qName, Attributes attrs) throws SAXException
	{
		if (qName.equalsIgnoreCase("entry"))
	 		startElementEntry(attrs);
	}

	public void endElement(String namespaceURI, String sName, String qName) throws SAXException   	{}	
	public void characters(char buf[], int offset, int len) throws SAXException						{}
	public void startDocument() throws SAXException													{}	
	public void endDocument() throws SAXException													{System.out.println("MySaxParser: endDocument: " + collectionElt.size() + " elt(s)");}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Sub-parsing methods : create, chain, and feed objects 
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void startElementEntry(Attributes attrs)
	{
		String entryId			= attrs.getValue("id");
        String entryDesignation	= attrs.getValue("designation");
        String entryType		= attrs.getValue("type");
		
        addInCollection(new Entry(entryId, entryDesignation, entryType));
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Error handler methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void error(SAXParseException exception)					{System.out.println("error: " 		+ exception.toString());}
	public void fatalError(SAXParseException exception)				{System.out.println("fatalError: " 	+ exception.toString());}
	public void warning(SAXParseException exception)				{System.out.println("warning: " 	+ exception.toString());}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Entity handler methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public InputSource resolveEntity(String publicId, String systemId) throws SAXException		{return null;}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//DTD handler methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void notationDecl(String name, String publicId, String systemId)	
	{System.out.println("notationDecl: name: " + name + ", publicId: " + publicId + ", systemId: " + systemId);}
	
	public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException
	{System.out.println("unparsedEntityDecl: name: " + name + ", publicId: " + publicId + ", systemId: " + systemId + ", notationName: " + notationName);}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//IXMLFactory methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Collection<IXMLObject> getCollectionElt() 			{return collectionElt;}

}
