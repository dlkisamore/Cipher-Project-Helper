/**
Plaintext:	just a test
Key: exam	
ABC:		ABCDEFGHIJKLMNOPQRSTUVWXYZ
Code:		EXAMBCDFGHIJKLNOPQRSTUVWYZ
Ciphertext:	htrs e sbrs
*/

import java.util.*;
import java.io.*;

public class Keyword extends Cipher {
	public static void enc(String file) {
		setup(file);
		process(true);
	}
	
	public static void dec(String file) {
		setup(file);
		process(false);
	}
	
	public static void process(boolean encrypt) {
		//create ciphered alphabet
		int[] cipherbet = new int[26];
		getKey();
		key = key.toUpperCase();
		char[] keyChars = key.toCharArray();
		int[] numKey = new int[keyChars.length];
		for(int i = 0; i < keyChars.length; i++) {
			numKey[i] = keyChars[i];
			numKey[i] -= 64;
		}
		int currentLetter = 1;
		for(int i = 0; i < 26; i++) {
			if(i + 1 <= numKey.length) { //take all letters from key first
				cipherbet[i] = numKey[i];
			} else { //then take the remaining parts of the alphabet
				//check to see if the currentLetter in the alphabet is present in the numeric key
				boolean found = false;
				while(!found) { //if the letter has been found, move on to the next letter and search again
					for(int j : numKey) {
						if(j == currentLetter) {
							found = true;
						}
					}
					if(found) {
						currentLetter++;
					} else {
						break;
					}
				}
				//when a letter that has not been used is found, add it to the cipherbet
				cipherbet[i] = currentLetter;
				currentLetter++;
			}
		}
		//NUMERIC CIPHERBET HAS BEEN CREATED AS AN ARRAY OF INTS
		//convert text to ints
		String line = "";
		for(String s : text) {
			line += s;
		}
		line = line.toUpperCase();
		char[] textToConvert = line.toCharArray();
		int[] numericText = new int[textToConvert.length];
		char[] convertedText = new char[textToConvert.length];
		for(int i = 0; i < numericText.length; i++) {
			numericText[i] = textToConvert[i];
			numericText[i] -= 64;
		}
		//translate
		for(int i = 0; i < numericText.length; i++) {
			if(numericText[i] > 0 && numericText[i] < 27) { //only affects letters
				if(encrypt) { //when encrypting, the integer in numericText refers to the position within cipherbet
					numericText[i] = cipherbet[numericText[i] - 1];
				} else { //when decrypting, the index of the integer should be found within cipherbet
					for(int j = 0; j < 26; j++) {
						if(numericText[i] == cipherbet[j]) {
							numericText[i] = j + 1;
							break;
						}
					}
				}
			}
			numericText[i] += 64;
			convertedText[i] = (char) numericText[i];
		}
		//output
		try {
			PrintWriter pw;
			if(encrypt) {
				pw = new PrintWriter("ciphertext.txt");
			} else {
				pw = new PrintWriter("plaintext.txt");
			}
			for(char c : convertedText) {
				pw.print(c);
			}
			pw.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}