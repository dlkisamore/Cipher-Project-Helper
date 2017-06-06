/**
http://practicalcryptography.com/ciphers/baconian-cipher/
*/

import java.util.*;
import java.io.*;

public class Baconian extends Cipher {
	static String[] key;
	
	static String filler = "lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
	
	public static void enc(String file) {
		process(true, file);
	}
	
	public static void dec(String file) {
		process(false, file);
	}
	
	public static void process(boolean encrypt, String file) {
		setKey(encrypt);
		setup(file);
		//get line from text
		String line = "";
		for(String s : text) {
			line += s;
		}
		//remove all spaces from line
		while(line.contains(" ")) {
			String tempLine = line.substring(0, line.indexOf(" "));
			tempLine += line.substring(line.indexOf(" ") + 1);
			line = tempLine;
		}
		//encryption
		String newLine = "";
		String finalText = "";
		if(encrypt) {
			//capitalize line
			line = line.toUpperCase();
			//convert each letter into a string of 0s and 1s based on key...ignore non-letters
			for(int i = 0; i < line.length(); i++) {
				//convert each letter in plain text to alphabet position
				char letter = line.charAt(i);
				int number = (int) letter;
				number -= 64;
				//shift all letters from V on left 1 to account for merging U and V
				if(number >= 22) {
					number--;
				}
				//shift all letters from J on left 1 to account for merging I and J
				if(number >= 10) {
					number--;
				}
				//shift all letters left 1 to get array position in key
				number--;
				//only encrypt letters
				if(number >= 0 && number <= 23) {
					newLine += key[number];
				}
			}
			//capitalize all 1s in filler string using filler and newLine as parallel arrays
			int fillerPos = -1;
			for(int i = 0; i < newLine.length(); i++) {
				fillerPos = -1;
				if(newLine.charAt(i) == '1') {
					//find the i letter in filler text
					String tempFiller = filler.toUpperCase();
					for(int j = 0; j < filler.length(); j++) {
						//check to see if j is a letter
						char letter = tempFiller.charAt(j);
						int number = (int) letter;
						number -= 64;
						if(number > 0 && number < 27) {
							//if it is, add 1 to fillerPos
							fillerPos++;
						}
						//if fillerPos == i, make the letter at j uppercase
						if(fillerPos == i) {
							//break filler up into before, at, after
							String before = filler.substring(0,j);
							String target = filler.substring(j, j + 1);
							String after =  filler.substring(j + 1);
							target = target.toUpperCase();
							filler = before + target + after;
							break;
						}
					}
					continue;
				}
			}
		} else { //decryption
			//convert each letter into a string of 0s and 1s...ignore non-letters
			char[] letters = line.toCharArray();
			int[] numbers = new int[line.length()];
			for(int i = 0; i < letters.length; i++) {
				numbers[i] = (int) letters[i];
			}
			for(int i = 0; i < line.length(); i++) {
				if(numbers[i] > 64 && numbers[i] < 91) { //upper case
					newLine += "1";
				} else if(numbers[i] > 96 && numbers[i] < 123) { //lower case
					newLine += "0";
				}
			}
			//convert 0s and 1s to letters in key
			for(int i = 0; i + 5 <= newLine.length(); i += 5) { //for each group of 5 letters in newLine...
				//find the corresponding key value
				for(int j = 0; j < 26; j++) {
					if(newLine.substring(i,i+5).equals(key[j])) { //locate key index
						j++; //shift j right 1 to alphabet position
						j += 64; //shift j from alphabet to ASCII position
						char letter = (char) j; //convert j to character
						String processedLetter = String.valueOf(letter); //convert character to string
						finalText += processedLetter; //add the processed letter to the decrypted text
					}
				}
			}
		}
		//output to text file
		try {
			PrintWriter pw;
			if(encrypt) {
				pw = new PrintWriter("ciphertext.txt");
				pw.print(filler);
			} else {
				pw = new PrintWriter("plaintext.txt");
				pw.print(finalText);
			}
			pw.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setKey(boolean encrypt) {
		if(encrypt) {
			key = new String[24];
		} else {
			key = new String[26];
		}
		key[0] = "00000"; //A
		key[1] = "00001"; //B
		key[2] = "00010"; //C
		key[3] = "00011"; //D
		key[4] = "00100"; //E
		key[5] = "00101"; //F
		key[6] = "00110"; //G
		key[7] = "00111"; //H
		if(encrypt) {
			key[8] = "01000"; //I AND J
			key[9] = "01001"; //K
			key[10] = "01010"; //L
			key[11] = "01011"; //M
			key[12] = "01100"; //N
			key[13] = "01101"; //O
			key[14] = "01110"; //P
			key[15] = "01111"; //Q
			key[16] = "10000"; //R
			key[17] = "10001"; //S
			key[18] = "10010"; //T
			key[19] = "10011"; //U AND V
			key[20] = "10100"; //W
			key[21] = "10101"; //X
			key[22] = "10110"; //Y
			key[23] = "10111"; //Z
		} else {
			key[8] = "01000"; //I
			key[9] = "01000"; //J
			key[10] = "01001"; //K
			key[11] = "01010"; //L
			key[12] = "01011"; //M
			key[13] = "01100"; //N
			key[14] = "01101"; //O
			key[15] = "01110"; //P
			key[16] = "01111"; //Q
			key[17] = "10000"; //R
			key[18] = "10001"; //S
			key[19] = "10010"; //T
			key[20] = "10011"; //U
			key[21] = "10011"; //V
			key[22] = "10100"; //W
			key[23] = "10101"; //X
			key[24] = "10110"; //Y
			key[25] = "10111"; //Z
		}
	}
}