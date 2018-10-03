package fileSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * A basic file system with files accessible by their address
 * 
 * @author Nate Miller
 * @version 10/2/18
 */
public class FileSystem {
	
	private Drive drive;
	private EntityParent currentFile;
	
	public FileSystem(String driveName) {
		drive = new Drive(driveName);
		currentFile = drive;
	}
	
	/**
	 * Creates a new file at the specified path, displaying an error if path is invalid
	 * 
	 * @param type - File type to create, input should be "folder", "text", "shortcut", or "zip"
	 * @param name - Name of file to create
	 * @param path - Path at which to create file
	 * @param content - Only applicable for text files, otherwise put a blank string
	 */
	public void create(String type, String name, String path, String content, String shortcutTo) {
		
		ContainerParent parent = (ContainerParent) getObjectAt(path);
		switch(type) {
			case "folder":
				parent.addChild(new Folder(name, parent, type));
				break;
			case "text":
				parent.addChild(new TextFile(name, content, parent, type));
				break;
			case "zip":
				parent.addChild(new ZipFile(name, parent, type));
				break;
			case "shortcut":
				if(!isAncestor(shortcutTo, name))
					parent.addChild(new Shortcut(getObjectAt(shortcutTo), name, parent, type));
				else throw new InvalidFilePathException("The shortcut destination cannot be an ancestor of the shortcut");
				break;
			default:
				throw new InvalidFileTypeException("You entered an invalid file type,"
						+ " please enter \"folder\", \"text\", \"shortcut\", or \"zip\".");
		}
	}
	
	/**
	 * Deletes the file at the given path, displaying an error message if the path is invalid
	 * 
	 * @param pathName -- Path at which file should be found
	 * @return -- true if a file is deleted
	 */
	public boolean delete(String pathName) {
		EntityParent current = getObjectAt(pathName);
		if(current.getParent().removeChild(current))
			return true;
		else return false;
	}
	
	/**
	 * Changes a file's path, displaying an error if either parameter is invalid <br>
	 * **NOTE: The destination cannot be inside of the source folder
	 * 
	 * @param source -- Path of the file to be moved
	 * @param destination -- Where the file should be moved
	 */
	public void move(String source, String destination) {
		
		if(!isAncestor(source, destination)) {
			EntityParent movingObject = getObjectAt(source);
			ContainerParent newParent = (ContainerParent) getObjectAt(destination);
			ContainerParent origParent = movingObject.getParent();
			newParent.addChild(movingObject);
			origParent.removeChild(movingObject);
		} else {
			throw new InvalidFilePathException("The destination cannot be inside the source folder");
		}
	}
	
	private boolean isAncestor(String source, String destination) {
		return destination.contains(source);
	}
	
	/**
	 * Changes the content of a text file, displaying an error if the input path is invalid
	 * 
	 * @param path -- Path where the text file should be found
	 * @param content -- What the new content of the file should be
	 */
	public void writeToFile(String path, String content) {
		EntityParent file = getObjectAt(path);
		if(file instanceof TextFile)
			((TextFile)file).setContent(content);
		else if(file instanceof Shortcut)
			((Shortcut)file).setContent(content);
		else
			throw new InvalidFileTypeException("The file at specified path name is not a text file");
	}
	
	/**
	 * Returns the content of a given text file as a string
	 * @param path -- Path where the text file should be found
	 */
	public String readFromTextFile(String path) {
		EntityParent file = getObjectAt(path);
		if(file instanceof TextFile)
			return ((TextFile)file).getContent();
		else if(file instanceof Shortcut)
			return ((Shortcut)file).getContent();
		else
			throw new InvalidFileTypeException("The file at specified path name is not a text file");
	}
	
	/**
	 * 
	 * @param path
	 * @return the size of the file
	 */
	public int getFileSize(String path) {
		return getObjectAt(path).getSize();
	}
	
	/**
	 * Saves the file system to a json file with the specified file name
	 * @param filename
	 * @return true if save was successful, false otherwise
	 */
	public boolean saveFileSystem(String filename) {
		try(FileWriter fw = new FileWriter(new File(filename + ".json"))) {
			fw.write(new Gson().toJson(this));
			return true;
		} catch (IOException e) {
			throw new InvalidFilePathException("File Not Found");
		}
	}
	
	/**
	 * Creates a file system from a json file
	 * @param filename
	 * @return The file system
	 */
	public static FileSystem loadFileSystem(String filename) {
		FileSystem fs;
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(filename + ".json")))) {
			final RuntimeTypeAdapterFactory<EntityParent> fileAdapter = RuntimeTypeAdapterFactory
					.of(EntityParent.class)
					.registerSubtype(ZipFile.class, "zip")
					.registerSubtype(Folder.class, "folder")
					.registerSubtype(TextFile.class, "text")
					.registerSubtype(Shortcut.class, "shortcut")
					.registerSubtype(Drive.class, "drive");
			final Gson gson = new GsonBuilder().registerTypeAdapterFactory(fileAdapter).create();
			fs = gson.fromJson(reader, FileSystem.class);
			fs.initParentsFromChildren();
			fs.resetFileTypes();
		} catch(IOException e) {
			throw new InvalidFilePathException("File Not Found");
		}
		return fs;
	}
	
	/**
	 * Cycles down from the drive and resets the types of each file so that the file system
	 * can be resaved
	 */
	private void resetFileTypes() {
		drive.resetFileType();
	}

	/**
	 * Cycles down from the drive and initializes parents on all entities
	 */
	private void initParentsFromChildren() {
		for(EntityParent child : drive.getChildren()) {
			child.setParent(drive);
			child.initParentFromChildren();
		}
	}

	/**
	 * Returns a file given a path, displaying an error if the input path is invalid
	 * 
	 * @param pathName -- Path of the file to be found
	 * @return -- The File at the specified path
	 */
	private EntityParent getObjectAt(String pathName) {
		
		String[] path =	pathName.split("\\\\");
		EntityParent current;

		if(drive.getName().equals(path[0])) {
			if(path.length == 1) return drive;
			current = drive.getChild(path[1]);
			int i=2;
			try {
				for(; i < path.length; i++) {
					current = ((ContainerParent)current).getChild(path[i]);	
				}
				return current;
			} catch(Exception e) {
				throw new InvalidFilePathException("File with name " + path[i] + " does not exist at specified path.");
			}
		} else {
			throw new InvalidFilePathException("Invalid drive, please try again");
		}
	}
	
	/* Note: The following methods are not currently especially useful, but
	 * would be useful in future implementation of this file system into a command
	 * line type of environment.
	 */
	public String getCurrentFile() {
		return currentFile.getName();
	}
	
	public String getCurrentFilePath() {
		return currentFile.getPath();
	}
	
	public Collection<String> listChildrenOfCurrentFile() {
		if(currentFile instanceof ContainerParent)
			return ((ContainerParent)currentFile).getChildrenNames();
		else return new ArrayList<String>();
	}
	
	public void goToChildFile(String childName) {
		if(currentFile instanceof ContainerParent)
			currentFile = ((ContainerParent)currentFile).getChild(childName);
		else throw new InvalidFileTypeException("The current file cannot contain other files");
	}
	
	public void changeDirectory(String path) {
		currentFile = getObjectAt(path);
	}
	/*
	 */
		
	static class InvalidFilePathException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		InvalidFilePathException(String reason) {
			super(reason);
		}
	}
	
	static class InvalidFileTypeException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		InvalidFileTypeException(String reason) {
			super(reason);
		}
	}
}
	

