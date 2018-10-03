package fileSystem;

public class ZipFile extends ContainerParent{

	public ZipFile(String name, ContainerParent parent, String type) {
		super(name, 0, parent, type);
	}
	
	@Override
	public int getSize() {
		if(!hasBeenChanged) return size;
		int totalSize = 0;
		for(EntityParent child : children.values()) {
			totalSize += child.getSize();
		}
		size = totalSize/2;
		hasBeenChanged = false;
		return size;
	}
	
	@Override
	public void resetFileType() {
		type = "zip";
		for(EntityParent child : children.values())
			child.resetFileType();
		
	}
}
