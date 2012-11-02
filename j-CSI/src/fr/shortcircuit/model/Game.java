package fr.shortcircuit.model;



public class Game extends AbstractProduct
{	
	//furthermore members (fields, methods) to be added...
	
	
	public Game() {}	
	
	/** Constructor overloading:  */
	public Game(String id, String designation, Category category, int price, String type)
	{
		createStructure(id, designation, category, price, type);	
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Business logic: "behaviour" defined by the IBonusProducer business interface 
	////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getBonusPoint()
	{
		return 20;	
	}

	
}