import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

////////////////FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
//Title:    FolderExplorerTester
//Course:   CS 300 Fall 2021
//
//Author:   Sean DeGrazia
//Email:    sdegrazia@wisc.edu
//Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
//Persons:         (identify each by name and describe how they helped)
//Online Sources:  (identify each by URL and describe how it helped)
//
///////////////////////////////////////////////////////////////////////////////

public class FolderExplorerTester {

	/**
	 * Tests getContents
	 * @param folder directory we're searching through
	 * @return true if it passes all tests, false if otherwise
	 */
	public static boolean testGetContents(File folder) {
		try {
			// Scenario 1
			// list the basic contents of the cs300 folder
			ArrayList<String> listContent = FolderExplorer.getContents(folder);
			// expected output must contain "exams preparation", "grades",
			// "lecture notes", "programs", "reading notes", "syllabus.txt",
			// and "todo.txt" only.
			String[] contents = new String[] { "exams preparation", "grades", "lecture notes", "programs",
					"reading notes", "syllabus.txt", "todo.txt" };
			List<String> expectedList = Arrays.asList(contents);
			// check the size and the contents of the output
			if (listContent.size() != 7) {
				System.out.println("Problem detected: cs300 folder must contain 7 elements.");
				return false;
			}
			for (int i = 0; i < expectedList.size(); i++) {
				if (!listContent.contains(expectedList.get(i))) {
					System.out.println("Problem detected: " + expectedList.get(i)
							+ " is missing from the output of the list contents of cs300 folder.");
					return false;
				}
			}
			// Scenario 2 - list the contents of the grades folder
			File f = new File(folder.getPath() + File.separator + "grades");
			listContent = FolderExplorer.getContents(f);
			if (listContent.size() != 0) {
				System.out.println("Problem detected: grades folder must be empty.");
				return false;
			}
			// Scenario 3 - list the contents of the p02 folder
			f = new File(folder.getPath() + File.separator + "programs" + File.separator + "p02");
			listContent = FolderExplorer.getContents(f);
			if (listContent.size() != 1 || !listContent.contains("FishTank.java")) {
				System.out.println("Problem detected: p02 folder must contain only " + "one file named FishTank.java.");
				return false;
			}
			// Scenario 4 - List the contents of a file
			f = new File(folder.getPath() + File.separator + "todo.txt");
			try {
				listContent = FolderExplorer.getContents(f);
				System.out.println("Problem detected: Your FolderExplorer.getContents() must "
						+ "throw a NotDirectoryException if it is provided an input which is not" + "a directory.");
				return false;
			} catch (NotDirectoryException e) { // catch only the expected exception
				// no problem detected
			}
			// Scenario 5 - List the contents of not found directory/file
			f = new File(folder.getPath() + File.separator + "music.txt");
			try {
				listContent = FolderExplorer.getContents(f);
				System.out.println("Problem detected: Your FolderExplorer.getContents() must "
						+ "throw a NotDirectoryException if the provided File does not exist.");
				return false;
			} catch (NotDirectoryException e) {
				// behavior expected
			}
		} catch (Exception e) {
			System.out.println(
					"Problem detected: Your FolderExplorer.getContents() has thrown" + " a non expected exception.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Testing getDeepContents base case
	 * @param folder folder we're searching through
	 * @return true if it runs as expected, false if not
	 */
	public static boolean testGetDeepContentsBaseCase(File folder) {
		try {
			ArrayList<String> listContent = FolderExplorer.getDeepContents(folder);
			String[] contents = {"todo.txt"};
			int count = 0;
			
			// checking to see if a top level file is being added (base case)
			for(int i = 0; i < listContent.size(); i++) {
				if(contents[0].equals(listContent.get(i))) {
					count++;
				}
			}
			
			// if not, return false
			if(count == 0) {
				return false;
			}
			
		}
		catch(Exception e){
			System.out.println(
					"Problem detected: Your FolderExplorer.getContents() has thrown" + " a non expected exception.");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean testGetDeepContentsRecursiveCase(File folder) {
		try {
			ArrayList<String> listContent = FolderExplorer.getDeepContents(folder);
			String[] names = new String[(int) folder.length()+1];
			names = folder.list();
			int count = 0;
			for(int i = 0; i < listContent.size()-1; i++) {
				if(listContent.get(i).equals(names[i])) {
					count++;
				}
			}
			if(count != listContent.size()-1) {
				return false;
			}
		}
		catch (Exception e) {
			System.out.println(
					"Problem detected: Your FolderExplorer.getContents() has thrown" + " a non expected exception.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws NotDirectoryException {
		System.out.println("testGetContents: " + testGetContents(new File("cs300")));
		System.out.println("testGetDeepContentsBaseCase: " + testGetDeepContentsBaseCase(new File("cs300")));
		System.out.println("testGetDeepContentsRecursiveCase: " + testGetDeepContentsRecursiveCase(new File("cs300")));
		FolderExplorer.getDeepContents(new File("cs300"));
		FolderExplorer.lookupByName(new File("cs300"), "ExceptionHandling.txt");
		FolderExplorer.lookupByKey(new File("cs300"), "txt");
	}
}
