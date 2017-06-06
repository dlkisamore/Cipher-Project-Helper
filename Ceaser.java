/**

*/

import java.util.*;
import java.io.*;

public class Ceaser extends Cipher {
	public static void enc(String file) {
		setup(file);
		shift(true);
	}
	
	public static void dec(String file) {
		setup(file);
		shift(false);
	}
	
	public static void brute(String file) {
		setup(file);
		/***/
	}
	
	public static void shift(boolean encrypt, int shiftValue) {
		//if a boolean value is sent and a key integer is sent, begin shifting
		//read file into character array
		String line = "";
		for(String s : text) {
			line += s;
		}
		line = line.toUpperCase();
		char[] letters = line.toCharArray();
		int[] numbers = new int[letters.length]; //convert char to int
		for(int i = 0; i < letters.length; i++) {
			numbers[i] = letters[i];
			//shift number to alphabet position
			numbers[i] -= 64;
			//shift based on shift value
			if(numbers[i] > 0 && numbers[i] < 27) { //ignores spaces
				if(encrypt) {
					numbers[i] += shiftValue;
					//adjust for numbers out of range
					if(numbers[i] > 26) {
						numbers[i] = numbers[i] - 26;
					}
				} else {
					numbers[i] -= shiftValue;
					//adjust for numbers out of range
					if(numbers[i] < 1) {
						numbers[i] = 26 + numbers[i];
					}
				}
			}
			//shift numbers to ASCII position
			numbers[i] += 64;
			//convert numbers to character array
			letters[i] = (char) numbers[i];
		}
		try {
			PrintWriter pw;
			if(encrypt) {
				pw = new PrintWriter("ciphertext.txt");
			} else {
				pw = new PrintWriter("plaintext.txt");
			}
			for(char c : letters) {
				pw.print(c);
			}
			pw.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void shift(boolean encrypt, char shiftValue) {
		//if a boolean value is sent and a key character is sent, convert the key character to an integer
		shiftValue -= 64;
		shift(encrypt, (int) shiftValue);
	}
	
	public static void shift(boolean encrypt) {
		//if only a boolean value is sent, get a key character
		getKey();
		key = key.toUpperCase();
		char singleKey = key.charAt(0);
		shift(encrypt, singleKey);
	}
}