package fr.shortcircuit.model.state;

import fr.shortcircuit.model.validation.IValidable;

/**
 * A very simple interface to define the abstraction of a state changing process
 * 
 * Please Note: this interface extends the IValidable instance
 * 
 * @author Dim
 */
public interface IStateChangeable extends IValidable
{
	public EOrderState getState();

	public void setState(EOrderState orderState);
	
}
