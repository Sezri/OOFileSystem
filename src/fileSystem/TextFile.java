package fileSystem;

public class TextFile extends EntityParent implements Containable {

	private String content;

	public TextFile(String name, String path, String content, Container parent) {
		super(name, path, content.length(), parent);
		this.content = content;
	}

	public int getSize() {
		return size;
	}

	@Override
	public Container getParent() {
		return null;
	}

	public void setContent(String content) {
		this.content = content;
		size = content.length();
		Container tempParent = parent;
		while(tempParent != null) {
			tempParent.calculateSize();
			tempParent = tempParent.getParent();
		}
	}

	@Override
	public boolean removeChild(String string) {
		System.out.println("File with name " + string + " does not exist at specified path.");
		return false;
	}

	@Override
	public Containable getChild(String string) {
		System.out.println("File with name " + string + " does not exist at specified path.");
		return null;
	}

	public String getContent() {
		return content;
	}

}
