package fileSystem;

import java.util.ArrayList;

public class ContainerParent extends EntityParent implements Container{
	
	ArrayList<Containable> children;

	public ContainerParent(String name, String path, int size, Container parent) {
		super(name, path, size, parent);
		children = new ArrayList<Containable>();
	}

	@Override
	public ArrayList<Containable> getChildren() {
		return children;
	}

	@Override
	public void calculateSize() {
		size = 0;
		for(Containable child:children)
			size += child.getSize();
	}

	@Override
	public Container getParent() {
		return parent;
	}

	@Override
	public void addChild(Containable child) {
		Container parent = this;
		children.add(child);
		while(parent != null) {
			parent.calculateSize();
			parent = parent.getParent();
		}
		
	}

	@Override
	public boolean removeChild(Containable child) {
		Container parent = this;
		if(!children.remove(child)) {
			System.out.println("The specified file does not exist in " + name);
			return false;
		} else {
			while(parent != null) {
				parent.calculateSize();
				parent = parent.getParent();
			}
			return true;
		}
			
	}

	@Override
	public boolean removeChild(String childName) {
		Container parent = this;
		for(Containable child:children)
			if(child.getName().equals(childName)) {
				children.remove(child);
				while(parent != null) {
					parent.calculateSize();
					parent = parent.getParent();
				}
				return true;
			}
		System.out.println("The file with name " + childName + " does not exist in " + name);
		return false;
	}
		

	@Override
	public Containable getChild(String childName) {
		for(Containable child:children)
			if(child.getName().equals(childName))
				return child;
		System.out.println("The file with name " + childName + " does not exist in " + name);
		return null;
	}

}
