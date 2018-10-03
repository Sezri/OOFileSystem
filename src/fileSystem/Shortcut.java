package fileSystem;

public class Shortcut extends EntityParent {

	private EntityParent shortcutTo;
	public Shortcut(EntityParent shortcutTo, String name, ContainerParent parent, String type) {
		super(name, 0, parent, type);
		this.shortcutTo = shortcutTo; /* We could immediately check that this is a text file, but by
			instead checking in the methods below, we will be able to easily modify it later so that the
			shortcut can link to non-text files, should they gain some methods that modify their contents */
	}
	
	public void setContent(String content) {
		if(shortcutTo instanceof TextFile) {
			((TextFile)shortcutTo).setContent(content);
		} else {
			throw new FileSystem.InvalidFileTypeException("This shortcut does not lead to a text file");
		}
	}

	public String getContent() {
		if(shortcutTo instanceof TextFile) {
			return ((TextFile)shortcutTo).getContent();
		} else {
			throw new FileSystem.InvalidFileTypeException("This shortcut does not lead to a text file");
		}
	}
	
	@Override
	public void resetFileType() {
		type = "shortcut";
	}
}
