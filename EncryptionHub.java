import java.io.*;
import java.util.*;

public class EncryptionHub {
	static int cipher = 0;
	static String file = "";
	
	public static void main(String args[]) {
		//menu
		Scanner scanner = new Scanner(System.in);
		while(true) {
			int choice;
			System.out.println("MAIN MENU");
			//show current settings
			System.out.print("Settings: ");
			switch(cipher) {
				case 0:
					System.out.print("<NO CIPHER SELECTED>");
				break;
				case 1:
					System.out.print("Ceaser Shift");
				break;
				case 2:
					System.out.print("Atbash");
				break;
				case 3:
					System.out.print("Keyword");
				break;
				case 4:
					System.out.print("Playfair");
				break;
				case 5:
					System.out.print("Vigenere");
				break;
				case 6:
					System.out.print("Railfence");
				break;
				case 7:
					System.out.print("ROT13");
				break;
				case 8:
					System.out.print("Two-Square");
				break;
				case 9:
					System.out.print("Homophone");
				break;
				case 10:
					System.out.print("Baconian");
				break;
			}
			System.out.print("   ");
			if(file.equals("")) {
				System.out.println("<NO FILE SELECTED>");
			} else {
				System.out.println(file);
			}
			System.out.println("---------------------------------");
			System.out.println("1. Select a file");
			System.out.println("2. Select a cipher");
			System.out.println("3. Encrypt");
			System.out.println("4. Decrypt");
			System.out.println("5. Brute Force");
			System.out.println("---------------------------------");
			System.out.print(">> ");
			try {
				choice = Integer.valueOf(scanner.nextLine().trim());
				if(choice > 5 || choice < 1) {
					continue;
				} else {
					switch(choice) {
						case 1:
							System.out.print("File name: ");
							file = scanner.nextLine();
						break;
						case 2:
							while(true) {
								System.out.println("Cipher");
								System.out.println("-----------------");
								System.out.println("1. Ceasar Shift");
								System.out.println("2. Atbash");
								System.out.println("3. Keyword");
								System.out.println("4. Playfair");
								System.out.println("5. Vigenere");
								System.out.println("6. Railfence");
								System.out.println("7. ROT13");
								System.out.println("8. Two-Square");
								System.out.println("9. Homophone");
								System.out.println("10. Baconian");
								System.out.println("-----------------");
								System.out.print(">> ");
								try {
									choice = Integer.valueOf(scanner.nextLine().trim());
									if(choice < 1 || choice > 10) {
										continue;
									} else {
										cipher = choice;
										break;
									}
								} catch(Exception e) {
									continue;
								}
							}
						break;
						case 3:
							switch(cipher) {
								case 0:
									continue;
								case 1:
									Ceaser.enc(file);
								break;
								case 2:
									Atbash.enc(file);
								break;
								case 3:
									Keyword.enc(file);
								break;
								case 4:
									Playfair.enc(file);
								break;
								case 5:
									Vigenere.enc(file);
								break;
								case 6:
									Railfence.enc(file);
								break;
								case 7:
									ROT13.enc(file);
								break;
								case 8:
									TwoSquare.enc(file);
								break;
								case 9:
									Homophone.enc(file);
								break;
								case 10:
									Baconian.enc(file);
								break;
							}
						break;
						case 4:
							switch(cipher) {
								case 0:
									continue;
								case 1:
									Ceaser.dec(file);
								break;
								case 2:
									Atbash.dec(file);
								break;
								case 3:
									Keyword.dec(file);
								break;
								case 4:
									Playfair.dec(file);
								break;
								case 5:
									Vigenere.dec(file);
								break;
								case 6:
									Railfence.dec(file);
								break;
								case 7:
									ROT13.dec(file);
								break;
								case 8:
									TwoSquare.dec(file);
								break;
								case 9:
									Homophone.dec(file);
								break;
								case 10:
									Baconian.dec(file);
								break;
							}
						break;
						case 5:
							switch(cipher) {
								case 0:
									continue;
								case 1:
									Ceaser.brute(file);
								break;
								case 2:
									Atbash.brute(file);
								break;
								case 3:
									Keyword.brute(file);
								break;
								case 4:
									Playfair.brute(file);
								break;
								case 5:
									Vigenere.brute(file);
								break;
								case 6:
									Railfence.brute(file);
								break;
								case 7:
									ROT13.brute(file);
								break;
								case 8:
									TwoSquare.brute(file);
								break;
								case 9:
									Homophone.brute(file);
								break;
								case 10:
									Baconian.brute(file);
								break;
							}
						break;
					}
				}
			} catch(Exception e) {
				continue;
			}
		}
	}
}