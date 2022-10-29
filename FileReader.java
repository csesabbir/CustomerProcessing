/**
 * 
 * This program file connect to database
 * FileReader Constructor initalize file 
 * Connect with database
 * 
 * */

package HelloWorld;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class FileReader implements Runnable {
	BufferedReader br;
	String str;
	Connection conn;
	PreparedStatement ps;
	int cnt;
	int invalid;
	Validate valid;
	
	/**
	 *
	 * This Constructor initialize all variables and connect with database
	 * 
	 * 
	 * */
	
	FileReader() throws FileNotFoundException {
		br = new BufferedReader(new InputStreamReader(new FileInputStream("Mcustomers.txt")));
		str = "";
		cnt = 0;
		invalid = 0;
		valid = new Validate();
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Test","root", ""); 			/* mysql connector database connect with mysql server*/
			ps = conn.prepareStatement("INSERT INTO CustomerData VALUES(?,?,?,?,?)");				/* Insert statement to insert CustomerData Table*/
		} catch(Exception e) {
			System.out.println("Cannot Connect to database");
		}
	}
	
	/**
	 *
	 * This Method read from file then call numberValid and emailValid method
	 * Batch processing use to insert data faster into database
	 * Every 500 data fetch from file and then 
	 * Clear the batch to reduce insert time
	 * 
	 *
	 * */
	
	void Read() throws IOException, SQLException {
		conn.setAutoCommit(false);
		long s = System.currentTimeMillis();
		
		/** This statement start reading from file */
		
		while ((str = br.readLine()) != null) {
				String[] words = str.split(",");
				
				if (words.length > 8 || words.length < 8) {
					continue;
				}
				if (valid.numberValid(words[5]) && valid.emailValid(words[6])) {
						++cnt;
						
						ps.setString(1, words[0] + " " + words[1]);
						ps.setString(2, words[2] + " " + words[3] + " " + words[4]);
						ps.setString(3, words[5]);
						ps.setString(4, words[6]);
						ps.setString(5, words[7]);
						ps.addBatch();
						ps.executeBatch();
						if (cnt == 500) {
							cnt = 0;
							ps.clearBatch();
						}
					
				} else {
					++invalid;
				}
		} 
		if (cnt > 0) {
			ps.executeBatch();
		}
		conn.commit();
		conn.setAutoCommit(true);
		long e = System.currentTimeMillis();
		System.out.println("Data Insert Time	: " + ((e - s) * 0.001));
		System.out.println("Invalid Customer	: " + invalid);
	}
	@Override
	public void run() {
		try {
			Read();
		} catch(IOException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}