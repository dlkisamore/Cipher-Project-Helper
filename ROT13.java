/**
Ceaser shift of 13
*/

import java.util.*;
import java.io.*;

public class ROT13 extends Cipher {
	public static void enc(String file) {
		setup(file);
		Ceaser.shift(true, 13);
	}
	
	public static void dec(String file) {
		setup(file);
		Ceaser.shift(false, 13);
	}
}