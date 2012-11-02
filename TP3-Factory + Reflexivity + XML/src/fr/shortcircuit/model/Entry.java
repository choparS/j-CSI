package fr.shortcircuit.model;

import fr.shortcircuit.xml.IXMLObject;

public class Entry implements IXMLObject
{
	public String 				id, designation, type;
	
	
	public Entry(String id, String designation, String type)
	{
		createStructure(id, designation, type);	
	}
	
	public void createStructure(String id, String designation, String type)
	{
		this.id					= id;
		this.designation		= designation;		   
		this.type				= type;
	}
	
	public String toString()		{return "" + id + ", " + designation;}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//IXMLObject implementation
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String toXmlString()
	{
		StringBuffer bufContent	= new StringBuffer();
		
		bufContent.append("<");
		bufContent.append("entry ");
		bufContent.append("id=\"" + this.id + "\" ");
		bufContent.append("designation=\"" + this.designation + "\" ");
		bufContent.append("type=\"" + this.type + "\" ");
		
		bufContent.append("/>");
		
		return bufContent.toString();	
	}

}