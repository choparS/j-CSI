package fr.shortcircuit.model;


public class K7Element extends ProductElement
{
	
	public int 					coefBonus		= 15;
	
	
	public K7Element()
	{
		
	}	
	
	public int calculateBonus()
	{
		return Integer.parseInt(getPrice()) * coefBonus; 	
	}

	
}