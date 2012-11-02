package fr.shortcircuit.model;


import java.util.*;

import fr.shortcircuit.xml.IXMLObject;


public class ProductElement implements Comparable, IXMLObject
{
	public Collection<Actor>		actors = new ArrayList();
	
	//declared as public to allow java.lang.reflect.Field read/write access.
	public String 					id, designation, type, price, genre, realisateur, annee, musique, scenario, provenance;
	
	
	public int 						coefBonus		= 10;
	

	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Constructors
	////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ProductElement()		{}	

	public ProductElement(String id, String designation, String type, String price, String genre, String realisateur, String annee, String musique, String scenario)
	{
		createStructure(id, designation, type, price, genre, realisateur, annee, musique, scenario);	
	}
	
	public void createStructure(String id, String designation, String type, String price, String genre, String realisateur, String annee, String musique, String scenario)
	{
		this.id					= id;
		this.designation		= designation;		   
		this.type				= type;
		this.price				= price;	
		this.genre				= genre;
		this.realisateur		= realisateur;
		this.annee				= annee;
		this.musique			= musique;
		this.scenario			= scenario;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Business logic
	////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int calculateBonus()
	{
		return Integer.parseInt(price) * coefBonus;	
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Getters, Setters, and Add
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void addActor(Actor a)							{actors.add(a);}

	
	public Collection<Actor> getActors() 					{return actors;}
	public int getCoefBonus() 								{return coefBonus;}
	public String getId()									{return id;}
	public String getDesignation()							{return designation;}
	public String getType()									{return type;}
	public String getPrice()								{return price;}
	public String getGenre()								{return genre;}
	public String getRealisateur()							{return realisateur;}
	public String getAnnee()								{return annee;}
	public String getMusique()								{return musique;}
	public String getScenario()								{return scenario;}
	public String getProvenance()							{return provenance;}
	
	public void setActors(Collection<Actor> actors)			{this.actors 			= actors;}
	public void setCoefBonus(int coefBonus) 				{this.coefBonus 		= coefBonus;}
	public void setId(String id)							{this.id				= id;}
	public void setDesignation(String designation)			{this.designation		= designation;}
	public void setType(String type)						{this.type				= type;}
	public void setPrice(String price)						{this.price				= price;}
	public void setGenre(String genre)						{this.genre				= genre;}
	public void setRealisateur(String realisateur)			{this.realisateur		= realisateur;}
	public void setAnnee(String annee)						{this.annee				= annee;}
	public void setMusique(String musique)					{this.musique			= musique;}
	public void setScenario(String scenario)				{this.scenario			= scenario;}
	public void setProvenance(String provenance)			{this.provenance		= provenance;}

	//override java.lang.Object method 
	public String toString()								{return designation;}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//IXMLFactory methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String toXmlString()
	{
		StringBuffer bufContent	= new StringBuffer();
		
		bufContent.append("<");
		bufContent.append(this.getClass().getSimpleName() + " ");
		bufContent.append("Id=\"" + this.getId() + "\" ");
		bufContent.append("Designation=\"" + this.getDesignation() + "\" ");
		bufContent.append("Type=\"" + this.getType() + "\" ");
		bufContent.append("Price=\"" + this.getPrice() + "\" ");
		
		try {bufContent.append("Genre=\"" + this.getGenre() + "\" ");} 				catch(Exception e) {}
		try {bufContent.append("Realisateur=\"" + this.getRealisateur() + "\" ");} 	catch(Exception e) {}
		try {bufContent.append("Annee=\"" + this.getAnnee() + "\" ");} 				catch(Exception e) {}
		try {bufContent.append("Musique=\"" + this.getMusique() + "\" ");} 			catch(Exception e) {}
		try {bufContent.append("Scenario=\"" + this.getScenario() + "\" ");} 		catch(Exception e) {}
		
		bufContent.append("/>");
		
		if (actors.size() != 0)
		{
			bufContent.append("\r\n <actors>");
			
			for (Actor a : actors)
				bufContent.append("\r\n  " + a.toXmlString());
				
			bufContent.append("\r\n </actors>");	
		}
		
		return bufContent.toString();	
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Comparable methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int compareTo(Object o)
	{
		if (!(o instanceof ProductElement))
			throw new ClassCastException("compareTo ProductElement only");

		return designation.compareTo((((ProductElement) o).designation));	

		//return id.compareTo((((ProductElement) o).id));	//Gestion par Id
	}
	
}