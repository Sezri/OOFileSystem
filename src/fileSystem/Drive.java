package fileSystem;

public class Drive extends ContainerParent{
	
	public Drive(String driveName) {
		super(driveName, 0, null, "drive");
	}
	
	@Override
	public String getPath() {
		return name;
	}
	
	public void toggleParentHasBeenChanged() {
		hasBeenChanged = true;
	}

	@Override
	public void resetFileType() {
		type = "drive";
		for(EntityParent child : children.values())
			child.resetFileType();
		
	}
	
}
