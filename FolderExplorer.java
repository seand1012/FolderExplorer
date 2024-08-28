import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

////////////////FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
//Title:    FolderExplorer
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

public class FolderExplorer {

	/**
	 * Creates and returns an ArrayList holding the names of all Files and
	 * directories
	 * 
	 * @param currentDirectory Directory we're searching through
	 * @return ArrayList with names of Files and directories
	 * @throws NotDirectoryException if currentDirectory isn't a directory
	 */
	public static ArrayList<String> getContents(File currentDirectory) throws NotDirectoryException {
		// Returns a list of the names of all files and directories in
		// the the given folder currentDirectory.
		// Throws NotDirectoryException with a description error message if
		// the provided currentDirectory does not exist or if it is not a directory
		ArrayList<String> content = new ArrayList<String>();
		String[] names = new String[(int) currentDirectory.length()];
		names = currentDirectory.list(); // array holding names of all files

		// testing that the directory exists and is a directory
		if (currentDirectory.isDirectory() && currentDirectory.exists()) {
			for (int i = 0; i < names.length; i++) {
				content.add(names[i]); // add files to arraylist
			}
		} else {
			throw new NotDirectoryException("Error: Directory is illegal");
		}

		return content;
	}

	/**
	 * Creates and returns an ArrayList of just the file names in a directory
	 * 
	 * @param currentDirectory Directory we're searching through
	 * @return ArrayList of file names
	 * @throws NotDirectoryException if currentDirectory is not a directory
	 */
	public static ArrayList<String> getDeepContents(File currentDirectory) throws NotDirectoryException {
		// Recursive method that lists the names of all the files (not directories)
		// in the given directory and its sub-directories.
		// Throws NotDirectoryException with a descriptive error message if
		// the provided currentDirectory does not exist or if it is not a directory
		ArrayList<String> content = new ArrayList<String>();

		// testing that the directory exists and is a directory
		if ((currentDirectory.isDirectory())) {
			// loop through all the files in the directory
			for (int i = 0; i < currentDirectory.list().length; i++) {
				// base case
				// if it's a file at i, add it to ArrayList
				if (currentDirectory.listFiles()[i].isFile()) {
					content.add(currentDirectory.list()[i]);
				}
				// recursive case
				// if it's not a file, step into directory and add files, repeat if necessary
				else {
					ArrayList<String> deepFiles = getDeepContents((currentDirectory.listFiles()[i]));
					content.addAll(deepFiles);
				}
			}
		} else {
			throw new NotDirectoryException("Error: Directory is illegal");
		}
		return content;

	}

	/**
	 * Searches for a certain file in the directory Returns it if it exists, throws
	 * an exception if not
	 * 
	 * @param currentDirectory Directory we're searching through
	 * @param fileName         file we're searching for
	 * @return path to the file
	 * @throws NoSuchElementException if no result is found, fileName is null,
	 *                                currentDirectory doesn't exist, or not a
	 *                                directory
	 */
	public static String lookupByName(File currentDirectory, String fileName) throws NoSuchElementException {
		// Searches the given directory and all of its sub-directories for
		// an exact match to the provided fileName. This method must be
		// recursive or must use a recursive private helper method to operate.
		// This method returns a path to the file, if it exists.
		// Throws NoSuchElementException with a descriptive error message if the
		// search operation returns with no results found (including the case if
		// fileName is null or currentDirectory does not exist, or was not a directory)

		String found = "";

		if (fileName == null || !(currentDirectory.isDirectory()) || !(currentDirectory.exists())) {
			throw new NoSuchElementException("Error: The element does not exist in this directory");
		} else {
			for (int i = 0; i < currentDirectory.list().length; i++) {
				// Base case
				// the file we're searching for has been found
				if (currentDirectory.list()[i].equals(fileName)) {
					found += currentDirectory.getPath();

				}
				// Recursive case
				// if it's not the file we're looking for, keep searching the directory
				else {
					try {
						found += lookupByName(currentDirectory.listFiles()[i], fileName);
					} catch (NoSuchElementException e) {
						// do nothing
					}
				}
			}
		}
		if (found.isEmpty()) {
			throw new NoSuchElementException("Error: The element does not exist in this directory!");
		}
		return found;
	}

	/**
	 * Searches the directory and returns an ArrayList of all the instances of key
	 * in the directory
	 * 
	 * @param currentDirectory directory we're looking through
	 * @param key term we're searching for
	 * @return ArrayList of all files with that key
	 */
	public static ArrayList<String> lookupByKey(File currentDirectory, String key) {
		// Recursive method that searches the given folder and its sub-directories
		// for ALL files that contain the given key in part of their name.
		// Returns An arraylist of all the names of files that match and an empty
		// arraylist when the operation returns with no results found (including
		// the case where currentDirectory is not a directory).
		ArrayList<String> content = new ArrayList<String>();

		if (currentDirectory.isDirectory()) {
			for (int i = 0; i < currentDirectory.list().length; i++) {
				if (currentDirectory.listFiles()[i].isFile()) {
					if (currentDirectory.list()[i].contains(key)) {
						content.add(currentDirectory.list()[i]);
					}
				} else {
					content.addAll(lookupByKey(currentDirectory.listFiles()[i], key));
				}

			}
		}
		return content;
	}

	

	/**
	 * Searches the folder for all files within a given size
	 * Returns ArrayList of those files
	 * @param currentDirectory directory we're searching through
	 * @param sizeMin minimum size
	 * @param sizeMax maximum size
	 * @return ArrayList of all files within size
	 */
	public static ArrayList<String> lookupBySize(File currentDirectory, long sizeMin, long sizeMax) {
		// Recursive method that searches the given folder and its sub-directories
		// for ALL files whose size is within the given max and min values, inclusive.
		// Returns an array list of the names of all files whose size are within
		// the boundaries and an empty arraylist if the search operation returns
		// with no results found (including the case where currentDirectory
		// is not a directory).
		ArrayList<String> content = new ArrayList<String>();

		// testing if it's a directory
		if (currentDirectory.isDirectory()) {
			for (int i = 0; i < currentDirectory.list().length; i++) {
				// base case
				// test if it's a file
				if (currentDirectory.listFiles()[i].isFile()) {
					// if it is within the size, add it to the ArrayList
					if (currentDirectory.listFiles()[i].length() >= sizeMin
							&& currentDirectory.listFiles()[i].length() <= sizeMax) {
						content.add(currentDirectory.list()[i]);
					}
				} 
				// recursive case
				// if it's not a file, step into the directory
				else {
					content.addAll(lookupBySize(currentDirectory.listFiles()[i], sizeMin, sizeMax));
				}

			}
		}
		return content;
	}

}
