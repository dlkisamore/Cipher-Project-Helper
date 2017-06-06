/**
just a test
justatest

j...a...t
.u.t.t.s.
..s...e..

jatuttsse
*/

import java.util.*;
import java.io.*;

public class Railfence extends Cipher {
	public static void enc(String file) {
		setup(file);
		getKey();
		makeFence(true, key);
	}
	
	public static void dec(String file) {
		setup(file);
		getKey();
		makeFence(false, key);
	}
	
	public static void makeFence(boolean encrypt, String key) {
		//get text
		String line = "";
		for(String s : text) {
			line += s;
		}
		//set line to all upper case and remove spaces
		line = line.toUpperCase();
		while(line.contains(" ")) {
			String tempLine = line.substring(0, line.indexOf(" "));
			tempLine += line.substring(line.indexOf(" ") + 1);
			line = tempLine;
		}
		//convert key to int
		int fenceCount = Integer.valueOf(key.trim());
		//set up fence
		char[][] fence = new char[fenceCount][line.length()];
		//fill fence with default values (periods)
		for(int i = 0; i < fenceCount; i++) {
			for(int j = 0; j < line.length(); j++) {
				fence[i][j] = '.';
			}
		}
		//fill fences
		int xPos = 0;
		int yPos = 0;
		boolean down = true;
		for(int i = 0; i < line.length(); i++) {
			if(encrypt) { //add letters in zigzag patter for encryption
				fence[xPos][yPos] = line.charAt(i);
			} else { //create blanks where text should go for decryption
				fence[xPos][yPos] = ' ';
			}
			yPos++;
			if(down) {
				xPos++;
				if(xPos + 1 >= fenceCount) {
					down = false;
				}
			} else {
				xPos--;
				if(xPos - 1 < 0) {
					down = true;
				}
			}
		}
		if(!encrypt) { //add text sequentially to the blanks
			int linePos = 0;
			for(int i = 0; i < fenceCount; i++) {
				for(int j = 0; j < line.length(); j++) {
					if(fence[i][j] == ' ') {
						fence[i][j] = line.charAt(linePos);
						linePos++;
					}
				}
			}
		}
		//get new line from fence
		String newLine = "";
		if(encrypt) {
			//create new line reading in only letters from each line
			for(int i = 0; i < fenceCount; i++) {
				for(int j = 0; j < line.length(); j++) {
					if(fence[i][j] != '.') {
						newLine += String.valueOf(fence[i][j]);
					}
				}
			}
		} else {
			//create new line reading in zigzag pattern
			xPos = 0;
			yPos = 0;
			down = true;
			for(int i = 0; i < line.length(); i++) {
				//read character at current xyPos
				newLine += fence[xPos][yPos];
				//move to next xyPos
				yPos++;
				if(down) {
					xPos++;
					if(xPos + 1 >= fenceCount) {
						down = false;
					}
				} else {
					xPos--;
					if(xPos - 1 < 0) {
						down = true;
					}
				}
			}
		}
		//output string to file
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