package HelloWorld;

import java.sql.*;
import java.util.HashSet;

public class Test {
	public static void main (String[] args) {
		int duplicate = 0;
		HashSet<String> phoneNumber = new HashSet<>();
		HashSet<String> emailAddress = new HashSet<>();
		
		long start = System.currentTimeMillis();
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Test","root", "");
			PreparedStatement stmt = conn.prepareStatement("SELECT phone, email FROM CustomerData");
			ResultSet rs = stmt.executeQuery();
			rs.setFetchSize(100000);
			
			/*
			 * Here use hashset to check duplicate phone and email address
			 * Hashset is faster because it use hash function
			 * To retrieve faster data from database initialize heap size 
			 * 
			 * **/
			
			while (rs.next()) {
				String phone = rs.getString(1);
				String email = rs.getString(2);
				if (!phoneNumber.add(phone) && !emailAddress.add(email)) {     		/* If both phone and email are duplicate */
					++duplicate;
				} else if (!phoneNumber.add(phone)) {								/* If phone number is duplicate */
					++duplicate;
				} else if (!emailAddress.add(email)) { 								/* If email address is duplicate */
					++duplicate;
				} else {
					phoneNumber.add(phone);
					emailAddress.add(email);
				}
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		long end = System.currentTimeMillis();
		System.out.println("Duplicate Customer:  " + duplicate);
		System.out.println("Execute Time:        " + (end - start) * 0.001);
	}
}


