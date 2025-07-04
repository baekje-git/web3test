package org.kada.kadam.test;

import java.util.Random;

public class CertSmsTest {

	public static void main(String[] args) {
		Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    System.out.println(String.format("%06d", number));
	}

}
