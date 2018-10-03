package fileSystem;


public class Folder extends ContainerParent{
	
	public Folder(String name, ContainerParent parent, String type) {
		
		super(name, 0, parent, type);
	}
	
	@Override
	public void resetFileType() {
		type = "folder";
		for(EntityParent child : children.values())
			child.resetFileType();
		
	}
}
