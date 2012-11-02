package fr.shortcircuit.test;

import java.util.GregorianCalendar;

import fr.shortcircuit.model.Address;
import fr.shortcircuit.model.Category;
import fr.shortcircuit.model.Client;
import fr.shortcircuit.model.Dvd;
import fr.shortcircuit.model.Game;
import fr.shortcircuit.model.Order;
import fr.shortcircuit.model.OrderLine;
import fr.shortcircuit.model.AbstractProduct;
import fr.shortcircuit.model.state.OrderStateMachine;
import fr.shortcircuit.model.validation.ValidationException;

/**
 * Main application class, providing a starting point with the usual "main" method. 
 * 
 * This class declares the import of our "model" package, to be able to use them.
 * 
 * @author Dim
 */
public class Main
{

	/**
	 * Starting method for a classical application.
	 * 
	 * @param argv: command line parameters array (the common "argc" is givent through argv.length
	 */
	public static void main(String argv[])
	{
		buildEntities();
	}
	
	/**
	 * A dedicated method to instanciate our model objects.
	 * 
	 * Please Note: This method, tagged as "static" can only be accessed through another static "context", 
	 * the referer here is the main method, which is also declared as static.
	 */
	public static void buildEntities()
	{
		//Categories
		Category c1	= new Category("US");
		Category c2	= new Category("Asia");
		Category c3	= new Category("Europe");
		
		//Products instanciation: declared as "Higher" definition types, constructed with "Lower" types
		AbstractProduct p1 	= new Dvd("1", 	"Any Given Sunday", 	c1, 10, "Dvd");
		AbstractProduct p2 	= new Dvd("2", 	"Braveheart", 			c3, 15, "Dvd");
		AbstractProduct p3 	= new Dvd("3", 	"Tigres et dragons", 	c2, 15, "Dvd");
		AbstractProduct p4 	= new Dvd("4", 	"Les grands ducs", 		c3, 20, "Dvd");
		
		AbstractProduct p5	= new Game("5", "Heroes of Might & Magic 6", 	c3, 50, "Game");
		AbstractProduct p6	= new Game("6", "Civilisation 5", 				c1, 50, "Game");
				
		System.out.println("test => "+ p5.getType());
		
		//Address
		Address	a	= new Address();

		a.setStreetNumber(	"42");
		a.setStreetName(	"King Street");
		a.setCity(			"Metroplex");
		a.setZipCode(		"1313");
		a.setCountry(		"US");
		
		//Client
		Client c 	= new Client();
		
		c.setFirstName(		"Dim");
		c.setMiddleName(	"Dam");
		c.setLastName(		"Doum");
		c.setBillingAddress(	a);
		
		//Order
		Order o 	= new Order();
		
		//o.setClient(c);
		o.addLine(new OrderLine(p1, 1));
		o.addLine(new OrderLine(p5, 2));
		
		o.setDateOrderStarted(new GregorianCalendar().getTime());
				
		System.out.println(o.toString());
		
		System.out.println("\r\nMain: try to change Order state...");
		
		try {OrderStateMachine.changeStep(o);}
		catch (ValidationException e) {System.out.println("Main: OrderStateMachine.changeStep() throws " + e.toString());}

		//Re-set the mandatory reference...
		o.setClient(c);

		//Recall stateMachine operating...
		try {OrderStateMachine.changeStep(o);}
		catch (ValidationException e) {System.out.println("Main: OrderStateMachine.changeStep() throws " + e.toString());}

		System.out.println("\r\nMain: after re-setting the mandatory reference, order state is now: " + o.getState().toString());
	
	}

}
