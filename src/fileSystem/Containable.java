package fileSystem;

public interface Containable {

	/**
	 * 
	 * @return The parent of the container, null if the container is a drive
	 */
	Container getParent();

	/**
	 * Removes a child from the container given the child's name
	 * @param childName
	 * @return -- True if the child was removed
	 */
	boolean removeChild(String string);

	/**
	 *
	 * @param childName
	 * @return -- The child object with the specified name
	 */
	Containable getChild(String string);

	/**
	 * 
	 * @return -- This object's name
	 */
	String getName();

	/**
	 * 
	 * @return -- This object's size
	 */
	int getSize();

}
