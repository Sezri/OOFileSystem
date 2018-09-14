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
		system.create("folder", "Nate's Stuff", "C:", "");
		system.create("text", "MyDoc.txt", "C:\\Nate's Stuff", "Hi, my name is Nate. I'm working on this program.");
		system.create("zip", "Secret Files", "C:\\Nate's Stuff", "");
		system.create("text", "ILoveLucy.txt", "C:\\Nate's Stuff\\Secret Files", "I love Lucy so much! But I can't tell anyone...");
		assertEquals(((Drive)system.traverseSystem("C:")).getSize(), 72);
	}
	
	@Test
	void moveTest()	 {
		createTest();
		system.move("C:\\Nate's Stuff\\Secret Files", "C:");
		assertEquals(((Folder) system.traverseSystem("C:\\Nate's Stuff")).getSize(), 49);
	}
	
	@Test
	void deleteTest() {
		moveTest();
		system.delete("C:\\Nate's Stuff");
		assertEquals(((Drive)system.traverseSystem("C:")).getSize(), 23);
	}
	
	@Test
	void writeToFileTest() {
		createTest();
		system.writeToFile("C:\\Nate's Stuff\\Secret Files\\ILoveLucy.txt", "Hmm.. Maybe I don't love lucy so much anymore. Too 80's.");
		assertEquals(((Drive)system.traverseSystem("C:")).getSize(), 77);
	}
	
	@Test
	void createInvalidPath() {
		createTest();
		system.create("folder", "More Stuff", "C:\\Not A Folder", "");
	}
	
	@Test
	void deleteInvalidPath() {
		createTest();
		system.delete("C:\\Nate's Stuff\\Also Not A Folder");
	}
		
	@Test
	void moveInvalidDestination() {
		createTest();
		system.move("C:\\Nate's Stuff\\Secret Files", "C:\\Nate's Stuff\\Another Not A Folder");
	}
	
	@Test
	void moveInvalidSource() {
		createTest();
		system.move("C:\\Nate's Stuff\\Not A Folder Either", "C:\\Nate's Stuff");
	}
	
	
	@Test
	void writeInvalidPath() {
		createTest();
		system.writeToFile("C:\\Nate's Stuff\\My Journals", "Journal Entry #1: Today was good :)");
	}
	
	@Test
	void writeToNonFile() {
		createTest();
		system.writeToFile("C:", "Hi! This is totally invalid but YOLO.");
	}
	
	

}
