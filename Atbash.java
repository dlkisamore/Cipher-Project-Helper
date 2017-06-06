/**
Reversal of alphabet.
ABCDEFGHIJKLMNOPQRSTUVWXYZ
ZYXWVUTSRQPONMLKJIHGFEDCBA
*/

import java.util.*;
import java.io.*;

public class Atbash extends Cipher {
	public static void enc(String file) {
		reverse(file, true);
	}
	
	public static void dec(String file) {
		reverse(file, false);
	}
	
	public static void reverse(String file, boolean encrypt) {
		setup(file);
		String line = "";
		for(String s : text) {
			line += s;
		}
		line = line.toUpperCase();
		char[] letters = line.toCharArray();
		int[] numbers = new int[letters.length];
		for(int i = 0; i < letters.length; i++) {
			if(letters[i] != ' ') {
				numbers[i] = letters[i]; //convert char to int
				numbers[i] -= 64; //get position in alphabet
				numbers[i] = 27 - numbers[i]; //reverse position
				numbers[i] += 64;
				letters[i] = (char) numbers[i]; //convert int to char
			}
		}
		//output
		String fileName;
		if(encrypt) {
			fileName = "ciphertext.txt";
		} else {
			fileName = "plaintext.txt";
		}
		try(PrintWriter pw = new PrintWriter(fileName)) {
			for(char c : letters) {
				pw.print(c);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}