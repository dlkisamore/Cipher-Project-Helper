/**
Rules
--------
- bigrams
- null x
- Q = KW in key and plaintext
- no Q in ciphertext

- rectangle - same row, different column
- column - one down
- row - one right
*/

import java.util.*;
import java.io.*;

public class Playfair extends Cipher {
	static int[][] grid = new int[5][5];
	
	public static void enc(String file) {
		process(file, true);
	}
	
	public static void dec(String file) {
		process(file, false);
	}
	
	public static void process(String file, boolean encrypt) {
		setup(file);
		getKey();
		key = key.toUpperCase();
		//replace all Qs in key
		key = key.replace("Q", "KW");
		//weed out repeated letters in the key
		String uniqueKey = key.substring(0,1);
		for(int i = 1; i < key.length(); i++) {
			//if the next letter to be added has not already been added to the uniqueKey, add it
			if(!uniqueKey.contains(key.substring(i, i + 1))) {
				uniqueKey += key.substring(i, i + 1);
			}
		}
		//set up remaining alphabet
		String alphabet = "ABCDEFGHIJKLMNOPRSTUVWXYZ";
		String uniqueAlphabet = "";
		for(int i = 0; i < alphabet.length(); i++) {
			if(!uniqueKey.contains(alphabet.substring(i,i+1))) {
				uniqueAlphabet += alphabet.substring(i,i+1);
			}
		}
		//process unique non-q letters into array of integers
		char[] keyLetters = uniqueKey.toCharArray();
		int[] keyNumbers = new int[keyLetters.length];
		for(int i = 0; i < keyLetters.length; i++) {
			keyNumbers[i] = (int) keyLetters[i];
			keyNumbers[i] -= 64; //shift to alphabet position
		}
		//process uniqueAlphabet into array of integeres
		char[] alphabetLetters = uniqueAlphabet.toCharArray();
		int[] alphabetNumbers = new int[alphabetLetters.length];
		for(int i = 0; i < alphabetLetters.length; i++) {
			alphabetNumbers[i] = (int) alphabetLetters[i];
			alphabetNumbers[i] -=64; //shift to alphabet position
		}
		//set up grid
		int keyPosition = 0;
		int alphabetPosition = 0;
		for(int i = 0; i < 5; i++) { //for each cell in the grid...
			for(int j = 0; j < 5; j++) {
				//fill with unique letters from key then alphabet
				if(keyPosition < keyNumbers.length) {
					grid[i][j] = keyNumbers[keyPosition];
					keyPosition++;
				} else {
					//add the remainder of the alphabet to the grid
					grid[i][j] = alphabetNumbers[alphabetPosition];
					alphabetPosition++;
				}
			}
		}
		//GRID HAS BEEN CREATED
		//read text to convert into string
		String line = "";
		for(String s : text) {
			line += s;
		}
		line = line.toUpperCase().trim();
		//remove spaces
		while(line.contains(" ")) {
			String tempLine = line.substring(0, line.indexOf(" "));
			tempLine += line.substring(line.indexOf(" ") + 1);
			line = tempLine;
		}
		//insert X between duplicate letters if they would form a bigram...unless the bigram is XX, then use Z
		for(int i = 0; i < line.length() - 2; i += 2) {
			if(line.charAt(i) == line.charAt(i+1)) {
				String sub1 = line.substring(0,i+1);
				String sub2 = line.substring(i+1);
				if(line.charAt(i) == 'X') {
					line = sub1 + "Z" + sub2;
				} else {
					line = sub1 + "X" + sub2;
				}
			}
		}
		//add null X to string if not divisible by 2
		if(line.length() % 2 == 1) {
			line += "X";
		}
		//convert line into array of char then array of int
		char[] letters = line.toCharArray();
		int[] numbers = new int[letters.length];
		for(int i = 0; i < numbers.length; i++) {
			numbers[i] = (int) letters[i];
		}
		for(int i = 0; i < letters.length; i++) {
			numbers[i] -= 64;
		}
		//for all bigrams...
		for(int i = 0; i < numbers.length; i += 2) {
			//get position of letters
			int x1 = 9, y1 = 9, x2 = 9, y2 = 9;
			//find initial letters
			for(int x = 0; x < 5; x++) {
				for(int y = 0; y < 5; y++) {
					//check for first letter
					if(grid[x][y] == numbers[i]) {
						x1 = x;
						y1 = y;
					}
					//check for second letter
					if(grid[x][y] == numbers[i + 1]) {
						x2 = x;
						y2 = y;
					}
				}
			}
			int newX1, newY1, newX2, newY2;
			//identify the pattern and assign new values
			if(x1 == x2) { //same row
				if(encrypt) {
					//shift right by 1
					newX1 = x1;
					newY1 = y1 + 1;
					newX2 = x2;
					newY2 = y2 + 1;
				} else {
					//shift left by 1
					newX1 = x1;
					newY1 = y1 - 1;
					newX2 = x2;
					newY2 = y2 - 1;
				}
				//account for looping back left
				if(newY1 == 5) {
					newY1 = 0;
				}
				if(newY2 == 5) {
					newY2 = 0;
				}
				//account for looping back right
				if(newY1 == -1) {
					newY1 = 4;
				}
				if(newY2 == -1) {
					newY2 = 4;
				}
			} else if(y1 == y2) { //same column
				if(encrypt) {
					//shift down by 1
					newX1 = x1 + 1;
					newY1 = y1;
					newX2 = x2 + 1;
					newY2 = y2;
				} else {
					//shift down by 1
					newX1 = x1 - 1;
					newY1 = y1;
					newX2 = x2 - 1;
					newY2 = y2;
				}
				//account for looping back up
				if(newX1 == 5) {
					newX1 = 0;
				}
				if(newX2 == 5) {
					newX2 = 0;
				}
				//account for looping back down
				if(newX1 == -1) {
					newX1 = 4;
				}
				if(newX2 == -1) {
					newX2 = 4;
				}
			} else { //box...shift to same row, other column (y value changes)
				newX1 = x1;
				newY1 = y2;
				newX2 = x2;
				newY2 = y1;
			}
			//change numbers array to new values
			numbers[i] = grid[newX1][newY1];
			numbers[i + 1] = grid[newX2][newY2];
		}
		//shift values from alphabet position to ASCII position
		for(int i = 0; i < numbers.length; i++) {
			numbers[i] += 64;
		}
		//change numbers array to char array
		for(int i = 0; i < numbers.length; i++) {
			letters[i] = (char) numbers[i];
		}
		//change char array to string
		line = "";
		for(char c : letters) {
			line += c;
		}
		//output string to file
		try {
			PrintWriter pw;
			if(encrypt) {
				pw = new PrintWriter("ciphertext.txt");
			} else {
				pw = new PrintWriter("plaintext.txt");
			}
			pw.print(line);
			pw.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}