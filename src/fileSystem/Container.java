package fileSystem;

import java.util.ArrayList;

public interface Container {

	/**
	 * 
	 * @return -- An arraylist of the container's children
	 */
	ArrayList<Containable> getChildren();

	/**
	 * Re-Calculates the size of the container based on it's children
	 */
	void calculateSize();

	/**
	 * 
	 * @return The parent of the container, null if the container is a drive
	 */
	Container getParent();

	/**
	 * Adds a new child to the container
	 * @param newEntity - The child to add
	 */
	void addChild(Containable newEntity);

	/**
	 * Removes a child from the container
	 * @param child -- the child to remove
	 * @return -- True if the child was removed
	 */
	boolean removeChild(Containable child);
	
	/**
	 * Removes a child from the container given the child's name
	 * @param childName
	 * @return -- True if the child was removed
	 */
	boolean removeChild(String childName);
	
	/**
	 *
	 * @param childName
	 * @return -- The child object with the specified name
	 */
	Containable getChild(String childName);

}
