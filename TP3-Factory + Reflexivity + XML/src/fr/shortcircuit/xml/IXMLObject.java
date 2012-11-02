package fr.shortcircuit.xml;


/** XML Object design */
public interface IXMLObject
{
	/** To provide an XML buffer representation of the concrete class.*/
	public String toXmlString();
}