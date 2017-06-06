/**

*/

import java.util.*;
import java.io.*;

public class TwoSquare extends Cipher {
	static int[][] grid1 = new int[5][5];
	static int[][] grid2 = new int[5][5];
	
	public static void enc(String file) {
		process(file, true);
	}
	
	public static void dec(String file) {
		process(file, false);
	}
	
	public static void process(String file, boolean encrypt) {
		setup(file);
		getKey(2);
		key = key.toUpperCase();
		//replace all Qs in key
		key = key.replace("Q", "KW");
		//separate keys
		String key1 = key.substring(0, key.indexOf(" ")).trim();
		String key2 = key.substring(key.indexOf(" ") + 1).trim();
		//weed out repeated letters in the keys
		String uniqueKey1 = key1.substring(0,1);
		for(int i = 1; i < key1.length(); i++) {
			//if the next letter to be added has not already been added to the uniqueKey, add it
			if(!uniqueKey1.contains(key1.substring(i, i + 1))) {
				uniqueKey1 += key1.substring(i, i + 1);
			}
		}
		String uniqueKey2 = key2.substring(0,1);
		for(int i = 1; i < key2.length(); i++) {
			//if the next letter to be added has not already been added to the uniqueKey, add it
			if(!uniqueKey2.contains(key2.substring(i, i + 1))) {
				uniqueKey2 += key2.substring(i, i + 1);
			}
		}
		//set up remaining alphabet
		String alphabet = "ABCDEFGHIJKLMNOPRSTUVWXYZ";
		String uniqueAlphabet1 = "";
		for(int i = 0; i < alphabet.length(); i++) {
			if(!uniqueKey1.contains(alphabet.substring(i,i+1))) {
				uniqueAlphabet1 += alphabet.substring(i,i+1);
			}
		}
		String uniqueAlphabet2 = "";
		for(int i = 0; i < alphabet.length(); i++) {
			if(!uniqueKey2.contains(alphabet.substring(i,i+1))) {
				uniqueAlphabet2 += alphabet.substring(i,i+1);
			}
		}
		//process unique non-q letters into array of integers
		char[] keyLetters1 = uniqueKey1.toCharArray();
		int[] keyNumbers1 = new int[keyLetters1.length];
		for(int i = 0; i < keyLetters1.length; i++) {
			keyNumbers1[i] = (int) keyLetters1[i];
			keyNumbers1[i] -= 64; //shift to alphabet position
		}
		char[] keyLetters2 = uniqueKey2.toCharArray();
		int[] keyNumbers2 = new int[keyLetters2.length];
		for(int i = 0; i < keyLetters2.length; i++) {
			keyNumbers2[i] = (int) keyLetters2[i];
			keyNumbers2[i] -= 64; //shift to alphabet position
		}
		//process uniqueAlphabet into array of integeres
		char[] alphabetLetters1 = uniqueAlphabet1.toCharArray();
		int[] alphabetNumbers1 = new int[alphabetLetters1.length];
		for(int i = 0; i < alphabetLetters1.length; i++) {
			alphabetNumbers1[i] = (int) alphabetLetters1[i];
			alphabetNumbers1[i] -=64; //shift to alphabet position
		}
		char[] alphabetLetters2 = uniqueAlphabet2.toCharArray();
		int[] alphabetNumbers2 = new int[alphabetLetters2.length];
		for(int i = 0; i < alphabetLetters2.length; i++) {
			alphabetNumbers2[i] = (int) alphabetLetters2[i];
			alphabetNumbers2[i] -=64; //shift to alphabet position
		}
		//set up grid
		int keyPosition1 = 0;
		int alphabetPosition1 = 0;
		int keyPosition2 = 0;
		int alphabetPosition2 = 0;
		for(int i = 0; i < 5; i++) { //for each cell in the grid...
			for(int j = 0; j < 5; j++) {
				//fill with unique letters from key then alphabet
				if(keyPosition1 < keyNumbers1.length) {
					grid1[i][j] = keyNumbers1[keyPosition1];
					keyPosition1++;
				} else {
					//add the remainder of the alphabet to the grid
					grid1[i][j] = alphabetNumbers1[alphabetPosition1];
					alphabetPosition1++;
				}
				if(keyPosition2 < keyNumbers2.length) {
					grid2[i][j] = keyNumbers2[keyPosition2];
					keyPosition2++;
				} else {
					//add the remainder of the alphabet to the grid
					grid2[i][j] = alphabetNumbers2[alphabetPosition2];
					alphabetPosition2++;
				}
			}
		}
		//GRIDS HAVE BEEN CREATED
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
					if(encrypt) {
						//check for first letter
						if(grid1[x][y] == numbers[i]) {
							x1 = x;
							y1 = y;
						}
						//check for second letter
						if(grid2[x][y] == numbers[i + 1]) {
							x2 = x;
							y2 = y;
						}
					} else {
						//check for first letter
						if(grid2[x][y] == numbers[i]) {
							x1 = x;
							y1 = y;
						}
						//check for second letter
						if(grid1[x][y] == numbers[i + 1]) {
							x2 = x;
							y2 = y;
						}
					}
				}
			}
			int newX1, newY1, newX2, newY2;
			//identify the pattern and assign new values
			if(x1 == x2) { //same row
				//switch letters
				newX1 = x2;
				newY1 = y2;
				newX2 = x1;
				newY2 = y1;
			} else { //box...shift to same row, other column (y value changes)
				newX1 = x1;
				newY1 = y2;
				newX2 = x2;
				newY2 = y1;
			}
			//change numbers array to new values
			if(encrypt) {
				numbers[i] = grid2[newX1][newY1];
				numbers[i + 1] = grid1[newX2][newY2];
			} else {
				numbers[i] = grid1[newX1][newY1];
				numbers[i + 1] = grid2[newX2][newY2];
			}
			
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