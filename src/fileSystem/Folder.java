package fileSystem;


public class Folder extends ContainerParent implements Containable, Container {
	
	public Folder(String name, String path, Container parent) {
		
		super(name, path, 0, parent);
		
	}
}
