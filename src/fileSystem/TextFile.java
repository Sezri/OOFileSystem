package fileSystem;

public class TextFile extends EntityParent {

	private String content;

	public TextFile(String name, String content, ContainerParent parent, String type) {
		super(name, content.length(), parent, type);
		this.content = content;
	}

	public void setContent(String content) {
		this.content = content;
		size = content.length();
		parent.toggleParentHasBeenChanged();
	}

	public String getContent() {
		return content;
	}
	
	@Override
	public void resetFileType() {
		type = "text";
	}

}
