package fr.shortcircuit.xml;

import java.util.Collection;
import java.util.Map;

/** XML Factory design */
public interface IXMLFactory 
{
	/** provides a 1 dimension collection (Vector, List, Set) of the produced object population.
	 * Check Sorting/Indexing option in concrete implementation. */
	public Collection<IXMLObject> 		getCollectionElt();
}
