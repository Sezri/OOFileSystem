package fileSystem;

/**
 * A basic file system with files accessible through their address
 * 
 * @author Nate Miller
 * @version 3/19/17
 */
public class FileSystem {
	
	private Drive drive;
	
	public FileSystem(String driveName) {
		drive = new Drive(driveName);
	}
	
	/**
	 * Creates a new file at the specified path, displaying an error if path is invalid
	 * 
	 * @param type - File type to create, input should be "folder", "text", or "zip"
	 * @param name - Name of file to create
	 * @param path - Path at which to create file
	 * @param content - Only applicable for text files, otherwise put a blank string
	 */
	public void create(String type, String name, String path, String content) {
		
		Container parent = (Container) traverseSystem(path);
		try {
			switch(type) {
				case "folder":
					parent.addChild(new Folder(name, path, parent));
					break;
				case "text":
					parent.addChild(new TextFile(name, path, content, parent));
					break;
				case "zip":
					parent.addChild(new ZipFile(name, path, parent));
					break;
				default:
					System.out.println("You entered an invalid file type,"
							+ " please enter \"folder\", \"text\", or \"zip\".");
			}
		}catch (Exception e) {
		}
	}
	
	/**
	 * Deletes the file at the given path, displaying an error message if the path is invalid
	 * 
	 * @param pathName -- Path at which file should be found
	 * @return -- true if a file is deleted
	 */
	public boolean delete(String pathName) {
		try {
			Containable current = (Containable) traverseSystem(pathName);
			if(current.getParent().removeChild(current))
				return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	/**
	 * Changes a file's path, displaying an error if either parameter is invalid
	 * 
	 * @param source -- Path of the file to be moved
	 * @param destination -- Where the file should be moved
	 */
	public void move(String source, String destination) {
		try {
			Containable sourceLoc = (Containable) traverseSystem(source);
			Container destLoc = (Container) traverseSystem(destination);
			destLoc.addChild(sourceLoc);
			sourceLoc.getParent().removeChild(sourceLoc);
		} catch (Exception e) {
		}	
	}
	
	/**
	 * Changes the content of a text file, displaying an error if the input path is invalid
	 * 
	 * @param path -- Path where the text file should be found
	 * @param content -- What the new content of the file should be
	 */
	public void writeToFile(String path, String content) {
		try {
			TextFile file = (TextFile) traverseSystem(path);
			file.setContent(content);
		} catch (ClassCastException e) {
			System.out.println("The file at specified path name is not a text file");
		} catch (Exception e) {
		}
	}
	
	/**
	 * Returns a file given a path, displaying an error if the input path is invalid
	 * 
	 * @param pathName -- Path of the file to be found
	 * @return -- The File at the specified path
	 */
	public Object traverseSystem(String pathName) {
		
		String[] path = pathName.split("\\\\");
		if(path.length == 1) return drive;
		Containable current = null;

		
		if(drive.getName().equals(path[0])) {
			current = drive.getChild(path[1]);
			for(int i=2; i < path.length; i++) {
				current = current.getChild(path[i]);	
			}
		} else {
			System.out.println("Invalid drive, please try again");
			return null;
		}
		return current;
	}
		
}
	

