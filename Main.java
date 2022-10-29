package HelloWorld;

import java.io.*;

public class Main {
	public static void main(String[] args)throws FileNotFoundException, Exception {
		FileReader fileReader = new FileReader();
		Thread t1 = new Thread(fileReader);
		t1.start();
	}
}
