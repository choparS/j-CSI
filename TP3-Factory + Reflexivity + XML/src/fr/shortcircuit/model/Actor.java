package fr.shortcircuit.model;

import fr.shortcircuit.xml.IXMLObject;

public class Actor implements IXMLObject
{
	public ProductElement 	parent;
	
	public String name = "";

	public String role = "";
	
	
	public Actor()
	{
	
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String newName)
	{
		this.name = newName;
	}
	
	public String getRole()
	{
		return role;
	}
	
	public void setRole(String newRole)
	{
		this.role = newRole;
	}
	
	public ProductElement getParent()
	{
		return parent;
	}
	
	public void setParent(ProductElement newParent)
	{
		this.parent = newParent;
	}

	/**  to return full XML node*/ 
	public String toXmlString()
	{
		StringBuffer bufContent	= new StringBuffer();
		
		bufContent.append("<Actor ");
		bufContent.append("name=\"" + this.getName() + "\" ");

		if (role != null && role.length() != 0)
			bufContent.append("role=\"" + this.getRole() + "\" ");	
		
		bufContent.append("/>");
		
		return bufContent.toString();	
	}
	
	public String toString()
	{
		if (role != null && role.length() != 0)
			return name + ", role: " + role;		
		else
			return name;	
	}
}
