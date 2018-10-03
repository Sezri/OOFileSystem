package fileSystem;

import java.util.Collection;
import java.util.HashMap;

public abstract class ContainerParent extends EntityParent{
	
	HashMap<String, EntityParent> children;
	boolean hasBeenChanged;

	public ContainerParent(String name, int size, ContainerParent parent, String type) {
		super(name, size, parent, type);
		children = new HashMap<String, EntityParent>();
		hasBeenChanged = true;
	}

	public Collection<EntityParent> getChildren() {
		return children.values();
	}

	@Override
	public int getSize() {
		if(!hasBeenChanged) return size;
		int totalSize = 0;
		for(EntityParent child : children.values()) {
			totalSize += child.getSize();
		}
		size = totalSize;
		hasBeenChanged = false;
		return totalSize;
	}

	public ContainerParent getParent() {
		return parent;
	}

	public void addChild(EntityParent child) {
		child.setParent(this);
		children.put(child.getName(), child);
		hasBeenChanged = true;
	}
	
	public boolean removeChild(EntityParent child) {
		if(!children.remove(child.getName(), child)) {
			throw new FileSystem.InvalidFilePathException("The specified file does not exist in " + name);
		}
			hasBeenChanged = true;
			return true;	
	}

	public boolean removeChild(String childName) {
		if(children.containsKey(childName)) {
			children.remove(childName);
			hasBeenChanged = true;
			return true;
		} 
		else throw new FileSystem.InvalidFilePathException("The file with name " + childName + " does not exist in " + name);
	}
		

	public EntityParent getChild(String childName) {
		if(children.containsKey(childName))
			return children.get(childName);
		else throw new FileSystem.InvalidFilePathException("The file with name " + childName + " does not exist in " + name);
	}
	
	public void toggleParentHasBeenChanged() {
		hasBeenChanged = true;
		parent.toggleParentHasBeenChanged();
	}

	public Collection<String> getChildrenNames() {
		return children.keySet();
	}

}
