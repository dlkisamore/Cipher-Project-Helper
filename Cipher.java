/**
Contains common functions in most ciphers.
*/

import java.io.*;
import java.util.*;

abstract public class Cipher {
	static File source;
	static String key = "";
	static ArrayList<String> text;
	
	public static void enc(String file) {}
	
	public static void dec(String file) {}
	
	public static void getText() {
		text = FileManip.getFile(source);
	}
	
	public static void setup(String file) {
		source = new File(file); //set source file
		getText(); //get text from source file
	}
	
	//get encryption/decryption key
	public static void getKey() {
		System.out.print("KEY: ");
		Scanner scanner = new Scanner(System.in);
		key = scanner.nextLine().trim();
	}
	
	public static void getKey(int times) {
		key = "";
		for(int i = 0; i < times; i++) {
			System.out.print("KEY: ");
			Scanner scanner = new Scanner(System.in);
			key += scanner.nextLine().trim() + " ";
		}
	}
}