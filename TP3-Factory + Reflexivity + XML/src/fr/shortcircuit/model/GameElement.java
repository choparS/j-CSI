package fr.shortcircuit.model;


public class GameElement extends ProductElement
{
	
	public int 					coefBonus		= 20;

	public int 					gameBonus		= 15;		

	
	
	public GameElement()
	{
		
	}	
	
	public int calculateBonus()
	{
		return (Integer.parseInt(getPrice()) * coefBonus) + gameBonus; 	
	}

	
}