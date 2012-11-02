package fr.shortcircuit.xml;


import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

import fr.shortcircuit.model.Entry;


/** Simple DOM Parser implementation. */ 

public class MyDomParser implements IXMLFactory
{
	private Collection			collectionElt 	= new Vector();

	private String				fileName;
	


	public MyDomParser(String fileName)
	{
		createStructures(fileName);

		createDomParser();
	}
		
	public void createStructures(String fileName)
	{
		this.fileName			= fileName;  			
		
		System.out.println("MyDomParser: starts parsing " + fileName);	
	}

	/** Instanciation du parser Dom.
	 * 
	 * L'objet Document recupere permet, en dehors du parsage, 
	 * d'enrichir ses structures internes facilement
	 */ 	
	public void createDomParser()
	{		
	  	try 
	   	{	   	
	  		DOMParser domParser = new DOMParser();

	  		domParser.parse(fileName);

	  		Document doc		= domParser.getDocument();
	    	
	    	parseDocumentContent(doc, "\t");

	    	System.out.println("MyDomParser: endDocument: " + collectionElt.size() + " elt(s)");
		}
		catch (Throwable t) {System.out.println("createSaxParser: " + t.toString()); /*t.printStackTrace();*/}
	}

	/**
	 * Methode generale de traitement de l'arborescence du document.
	 * appelee recursivement pour le parcours de l'arbre.
	 */ 
	public void parseDocumentContent(Node node, String indentLevel)
	{
		switch (node.getNodeType())
		{
			case Node.DOCUMENT_NODE: //contenu de l'objet Document
				parseDocument(node);
				break;
			
			case Node.ELEMENT_NODE: //Element et attributs du noeud
				parseElement(node, indentLevel);
				break;
			
			case Node.TEXT_NODE: 	//Donnees textuelles
			case Node.CDATA_SECTION_NODE: 
				parseTextContent(node);
				break;
			
			case Node.PROCESSING_INSTRUCTION_NODE: //instruction de traitement
				parseProcessingInstruction(node);
				break;
			
			case Node.ENTITY_REFERENCE_NODE: //affiche la reference d'entite
				parseEntityReference(node);
				break;
			
			case Node.DOCUMENT_TYPE_NODE: //declaration de la DTD
				parseDocumentType(node);
				break;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//DOM sub-parsing methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void parseDocument(Node node)
	{
		parseDocumentContent(((Document) node).getDocumentElement(), "\t");
	}	
	
	public void parseElement(Node node, String indent)
	{
		String nodeName											= node.getNodeName();
		NamedNodeMap attributesMap								= node.getAttributes();
		NodeList nodeSiblingList								= node.getChildNodes();
			
		//instanciation des feuilles de type siteElement
		if (nodeName.equalsIgnoreCase("entry"))
	 		startElementEntry(attributesMap);

		//lance la recursion sur la descendance		
		if (nodeSiblingList != null)
			for (int i = 0; i != nodeSiblingList.getLength(); i++)
				parseDocumentContent(nodeSiblingList.item(i), indent + "\t");
	}
	
	public void parseTextContent(Node node)				{/*System.out.println("parseTextContent: " + node.getNodeValue());*/}
	public void parseProcessingInstruction(Node node)	{System.out.println("<?" + node.getNodeName() + " " + node.getNodeValue() + "?>");}
	public void parseEntityReference(Node node)			{System.out.println("&" + node.getNodeName() + ";");}
	public void parseDocumentType(Node node)
	{
		/*
			DocumentType docType									= (DocumentType) node;
			
			System.out.println("<!DOCTYPE " + docType.getName());
	
			if (docType.getPublicId() != null)
				System.out.println((docType.getPublicId() != null)? 
						" PUBLIC \"" + docType.getPublicId() + "\" " : 
						" SYSTEM \"" + docType.getSystemId() + "\" ");
				
			System.out.println(">");
		*/	
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Object-side methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void startElementEntry(NamedNodeMap attributesMap)
	{
		String entryId											= attributesMap.getNamedItem("id").getNodeValue();
		String entryDesignation									= attributesMap.getNamedItem("designation").getNodeValue();
		String entryType										= attributesMap.getNamedItem("type").getNodeValue();
		
		collectionElt.add(new Entry(entryId, entryDesignation, entryType));
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//IXMLFactory methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Collection<IXMLObject> getCollectionElt() 			{return collectionElt;}
}
