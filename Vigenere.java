/**
plaintext is written into a block under a key
letters are encrypted based on the key letter they are under

POLYALPHABETIC
--------------
inthevigenerec
equalskpluspwh
ereaiszerobiso
neetc
*/

import java.util.*;
import java.io.*;

public class Vigenere extends Cipher {
	static char[][] grid;
	static int[][] numGrid;
	static int rows;
	static int columns;
	
	public static void enc(String file) {
		process(file);
		shift(true);
	}
	
	public static void dec(String file) {
		process(file);
		shift(false);
	}
	
	public static void shift(boolean encrypt) {
		//if decryption is needed, make all key numbers negative
		if(!encrypt) {
			for(int i = 0; i < columns; i++) {
				numGrid[0][i] *= -1;
			}
		}
		//add key to numeric text; spaces will always be less than 0
		for(int i = 0; i < columns; i++) { //for each column...
			//add the key value to all row values
			for(int j = 1; j < rows; j++) {
				numGrid[j][i] += numGrid[0][i];
			}
		}
		//convert number array back to characters
		for(int i = 1; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				//account for spaces
				if(encrypt) {
					if(numGrid[i][j] < 0) {
						numGrid[i][j] = -32;
						continue;
					}
				} else {
					if(numGrid[i][j] < -25) {
						numGrid[i][j] = -32;
						continue;
					}
				}
				//account for items out of range
				if(encrypt) { //numbers could be above 26
					if(numGrid[i][j] > 26) {
						numGrid[i][j] -= 26;
					}
				} else { //numbers could be below 1
					if(numGrid[i][j] < 1) {
						numGrid[i][j] += 26;
					}
				}
			}
		}
		for(int i = 1; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				//shift from ABC to ASCII
				numGrid[i][j] += 64;
				//cast int to char
				grid[i][j] = (char) numGrid[i][j];
			}
		}
		//create string to output
		outputToFile(encrypt);
	}
	
	public static void outputToFile(boolean encrypt) {
		String fileName;
		if(encrypt) {
			fileName = "ciphertext.txt";
		} else {
			fileName = "plaintext.txt";
		}
		try(PrintWriter pw = new PrintWriter(fileName)) {
			for(int i = 1; i < rows; i++) {
				for(int j = 0; j < columns; j++) {
					pw.print(grid[i][j]);
				}
			}
			pw.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void process(String file) {
		setup(file); 
		getKey();
		key = key.toUpperCase();
		/**good through here*/
		makeGrid();
		convertToNumbers();
	}
	
	//convert all characters into their numeric position in the alphabet; spaces are 32 and become -32
	public static void convertToNumbers() {
		numGrid = new int[rows][columns];
		for(int i = 0; i < columns; i++) {
			numGrid[0][i] = grid[0][i];
			numGrid[0][i] -= 64;
		}
		for(int i = 1; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				numGrid[i][j] = grid[i][j];
				numGrid[i][j] -= 64;
			}
		}
	}
	
	//create a grid that is the same length as the key
	public static void makeGrid() {
		int lineSize = columns = key.length();
		columns = key.length();
		String allText = "";
		for(String s : text) {
			allText += s;
		}
		int lineCount = 1; //start with 1 for the keyword
		int textSize = allText.length();
		allText = allText.toUpperCase();
		while(true) {
			textSize -= lineSize;
			lineCount++;
			if(textSize <= 0) {
				break;
			}
		}
		rows = lineCount;
		grid = new char[rows][columns];
		String[] parsedText = new String[lineCount];
		parsedText[0] = key;
		//set remaining lines of text
		for(int i = 1; i < lineCount; i++) {
			if(allText.length() >= lineSize) {
				parsedText[i] = allText.substring(0,lineSize);
				allText = allText.substring(lineSize);
			} else {
				parsedText[i] = allText;
			}
		}
		//convert string array to character array
		for(int i = 0; i < key.length(); i++) {
			grid[0][i] = key.charAt(i); /**SOMETHING ABOUT THIS DOESN'T WORK...PROBABLY THE GRID*/
		}
		for(int i = 1; i < parsedText.length; i++) { //for all lines of text...
			for(int j = 0; j < parsedText[i].length(); j++) {
				try {
					grid[i][j] = parsedText[i].charAt(j);
				} catch(IndexOutOfBoundsException e) {
					grid[i][j] = ' ';
					continue;
				}
			}
		}
	}
}