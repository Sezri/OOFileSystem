package fileSystem;

public class EntityParent {
	
	protected int size;
	protected String pathName;
	protected String name;
	protected Container parent;
	
	public EntityParent(String name, String path, int size, Container parent) {
		this.name = name;
		pathName = path + "\\" + name;
		this.size = size;
		this.parent = parent;
	}
	
	public Container getParent() {
		return parent;
	}

	public void setParent(Container parent) {
		this.parent = parent;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}
	
	public String getPathName() {
		return pathName;
	}
	
	public String getName() {
		return name;
	}
}
