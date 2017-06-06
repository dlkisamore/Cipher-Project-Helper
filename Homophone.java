/**
http://cryptogram.org/cdb/aca.info/ciphers/Homophonic.pdf
*/

import java.util.*;
import java.io.*;

public class Homophone extends Cipher {
	static Random rand = new Random();
	
	public static void enc(String file) {
		process(true, file);
	}
	
	public static void dec(String file) {
		process(false, file);
	}
	
	public static void process(boolean encrypt, String file) {
		setup(file);
		//get text to convert
		String line = "";
		for(String s : text) {
			line += s;
		}
		line = line.toUpperCase();
		//get key (must be 4 characters)
		getKey();
		while(key.length() < 4) { //deal with keys smaller than 4 characters
			key += "x";
		}
		if(key.length() > 4) { //deal with keys larger than 4 characters
			key = key.substring(0,4);
		}
		key = key.toUpperCase();
		//change all Js in the key to Is
		key = key.replaceAll("J", "I");
		//set up grid for key-based values
		String[][] grid = new String[5][25];
		int current = 65; //start at A
		for(int i = 0; i < 25; i++) { //set up first row (letters)
			if(current == 74) { //skip j
				current++;
			}
			char letter = (char) current;
			grid[0][i] = String.valueOf(letter);
			current++;
		}
		//fill in numbers for grid
		int currentNumber = 1;
		for(int i = 1; i < 5; i++) { //for each row (and it's corresponding key letter)...
			//starting in the column of the key letter...
			int startingColumn = 0;
			for(int j = 0; j < 25; j++) {
				if(grid[0][j].equals(key.substring(i - 1, i))) {
					startingColumn = j;
				}
			}
			//fill blanks at that column with numbers counting up from 1, looping around when necessary
			int currentColumn = startingColumn;
			do {
				//convert current number into a string
				String cellValue = String.valueOf(currentNumber);
				//add a 0 to the beginning if needed
				if(cellValue.length() == 1) {
					cellValue = "0" + cellValue;
				}
				//change 100 to 00 if it is the cell value
				if(cellValue.length() == 3) {
					cellValue = "00";
				}
				//add the value to the current cell i,currentColumn
				grid[i][currentColumn] = cellValue;
				//move on to the next cell
				currentColumn++;
				currentNumber++;
				if(currentColumn == 25) {
					currentColumn = 0;
				}
			} while(currentColumn != startingColumn);
		}
		String newLine = "";
		if(encrypt) {
			//encryption : find each letter in grid : assign random value from same column
			for(int i = 0; i < line.length(); i++) { //for each character in line...
				//do not encrypt spaces
				String letter = line.substring(i, i+1);
				if(letter.equals(" ")) {
					newLine += " ";
					continue;
				}
				//locate the column the letter is in...
				for(int j = 0; j < 25; j++) {
					if(letter.equals(grid[0][j])) {
						//randomly choose a number from 1 to 4 and use the choice to select an item in column j
						newLine += grid[rand.nextInt(4) + 1][j];
					}
				}
			}
		} else {
			//decryption : find value in grid: assign letter at top of grid in same column
			for(int i = 0; i + 1 < line.length(); i += 2) {//for every two characters in line...
				//skip spaces
				String letter;
				do {
					//read in next 2 characters
					letter = line.substring(i, i+2);
					if(letter.contains(" ")) {
						newLine += " ";
						i++;
					}
				} while(letter.contains(" "));
				//locate character set in grid
				boolean found = false;
				for(int j = 1; j < 5; j++) {
					for(int k = 0; k < 25; k++) {
						if(letter.equals(grid[j][k])) {
							newLine += grid[0][k]; //add the letter at the top of the grid to newLine
							found = true;
						}
						if(found == true) {
							break;
						}
					}
					if(found == true) {
						break;
					}
				}
			}
		}
		//output to text file
		try {
			PrintWriter pw;
			if(encrypt) {
				pw = new PrintWriter("ciphertext.txt");
			} else {
				pw = new PrintWriter("plaintext.txt");
			}
			pw.print(newLine);
			pw.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}