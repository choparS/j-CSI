package fr.shortcircuit.model;


public class Dvd extends AbstractProduct
{
	//furthermore members (fields, methods) to be added...
	
	
	public Dvd() {}	

	/** Constructor overloading:  */
	public Dvd(String id, String designation, Category category, int price, String type)
	{
		createStructure(id, designation, category, price, type);	
	}

	
}