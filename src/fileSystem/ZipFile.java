package fileSystem;

public class ZipFile extends ContainerParent implements Containable, Container{

	public ZipFile(String name, String path, Container parent) {
		super(name, path, 0, parent);
	}
	
	@Override
	public void calculateSize() {
		size = 0;
		for(Containable child:children) {
			size += child.getSize()/2;
		}
	}
}
