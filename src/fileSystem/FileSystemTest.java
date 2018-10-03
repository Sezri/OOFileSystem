package fileSystem;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileSystemTest {

	FileSystem system;
	
	@BeforeEach
	void setUp() throws Exception {
		system = new FileSystem("C:");
	}
	
	@Test
	void createTest() {
		system.create("folder", "Nate's Stuff", "C:", "", "");
		system.create("text", "MyDoc.txt", "C:\\Nate's Stuff", "Hi, my name is Nate. I'm working on this program.", "");
		system.create("zip", "Secret Files", "C:\\Nate's Stuff", "", "");
		system.create("text", "ILoveLucy.txt", "C:\\Nate's Stuff\\Secret Files", "I love Lucy so much! But I can't tell anyone...", "");
		assertEquals(system.getFileSize("C:"), 72);
	}
	
	@Test
	void saveLoadFileSystemTest() {
		createTest();
		system.saveFileSystem("testfile");
		FileSystem ss = FileSystem.loadFileSystem("testfile");
		assertEquals(ss.getFileSize("C:"), 72);
	}
	
	@Test
	void moveTest()	 {
		createTest();
		system.move("C:\\Nate's Stuff\\Secret Files", "C:");
		assertEquals(system.getFileSize("C:\\Nate's Stuff"), 49);
	}
	
	@Test
	void deleteTest() {
		moveTest();
		system.delete("C:\\Nate's Stuff");
		assertEquals(system.getFileSize("C:"), 23);
	}
	
	@Test
	void writeToFileTest() {
		createTest();
		system.writeToFile("C:\\Nate's Stuff\\Secret Files\\ILoveLucy.txt", "Hmm.. Maybe I don't love lucy so much anymore. Too 80's.");
		assertEquals(system.getFileSize("C:"), 77);
	}
	
	@Test
	void createInvalidPath() {
		createTest();
		assertThrows(FileSystem.InvalidFilePathException.class, () -> {
		system.create("folder", "More Stuff", "C:\\Not A Folder", "", "");});
	}
	
	@Test
	void createInvalidFileTypeTest() {
		createTest();
		assertThrows(FileSystem.InvalidFileTypeException.class, () -> {
		system.create("json", "Stuff", "C:", "", "");});
	}
	
	@Test
	void deleteInvalidPath() {
		createTest();
		assertThrows(FileSystem.InvalidFilePathException.class, () -> {
		system.delete("C:\\Nate's Stuff\\Also Not A Folder");});
	}
		
	@Test
	void moveInvalidDestination() {
		createTest();
		assertThrows(FileSystem.InvalidFilePathException.class, () -> {
		system.move("C:\\Nate's Stuff\\Secret Files", "C:\\Nate's Stuff\\Another Not A Folder");});
	}
	
	@Test
	void moveInvalidSource() {
		createTest();
		assertThrows(FileSystem.InvalidFilePathException.class, () -> {
		system.move("C:\\Nate's Stuff\\Not A Folder Either", "C:\\Nate's Stuff");});
	}
	
	
	@Test
	void writeInvalidPath() {
		createTest();
		assertThrows(FileSystem.InvalidFilePathException.class, () -> {
			system.writeToFile("C:\\Nate's Stuff\\My Journals", "Journal Entry #1: Today was good :)");});
	}
	
	@Test
	void writeToNonFile() {
		createTest();
		assertThrows(FileSystem.InvalidFileTypeException.class, () -> {
			system.writeToFile("C:", "Hi! This is totally invalid but YOLO.");});
	}
	
	@Test
	void moveInsideOfSource() {
		createTest();
		assertThrows(FileSystem.InvalidFilePathException.class, () -> {
			system.move("C:\\Nate's Stuff", "C:\\Nate's Stuff\\Secret Files");});
	}
	
	@Test
	void getPathTest() {
		createTest();
		system.goToChildFile("Nate's Stuff");
		system.goToChildFile("Secret Files");
		assertEquals("C:\\Nate's Stuff\\Secret Files", system.getCurrentFilePath());
	}
	
	@Test
	void writeToShortcutTest() {
		createTest();
		system.create("shortcut", "shortcut", "C:", "", "C:\\Nate's Stuff\\Secret Files\\ILoveLucy.txt");
		system.writeToFile("C:\\shortcut", "I'm really just testing out this shortcut, hope it's working *fingers crossed*");
		assertEquals(88, system.getFileSize("C:"));
	}

}
