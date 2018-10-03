package fileSystem;

public abstract class EntityParent {
	
	int size;
	String name;
	transient ContainerParent parent;
	String type;

	public EntityParent(String name, int size, ContainerParent parent, String type) {
		this.name = name;
		this.size = size;
		this.parent = parent;
		this.type = type;
	}
	
	public ContainerParent getParent() {
		return parent;
	}
	
	public int getSize() {
		return size;
	}
	
	public String getPath() {
		return parent.getPath() + "\\" + name;
	}
	
	public String getName() {
		return name;
	}
	
	protected void initParentFromChildren() {
		if(this instanceof ContainerParent) {
		ContainerParent current = (ContainerParent)this;
			for(EntityParent child:current.getChildren()) {
				child.setParent(current);
				child.initParentFromChildren();
			}
		}
	}

	protected void setParent(ContainerParent parent) {
		this.parent = (ContainerParent) parent;
	}

	public void resetFileType() {
	}
}
