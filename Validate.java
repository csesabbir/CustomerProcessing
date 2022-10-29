package HelloWorld;


/**
 * 
 * This class validate phone number and email address
 * 
 * 
 * */

import java.util.regex.*;

public class Validate {
	public String phone;
	public String email;
	public Validate() {
		
	} 
	
	/**
	 * 
	 * North American number format NXX NXX XXXX
	 * N should be N = 2 - 9
	 * X Should be X = 0 - 9 
	 * 
	 * */
	
	public boolean numberValid(String phone) {
		this.phone = phone;
		int len = phone.length();
		String areaCode = phone.substring(0, 3); 	// area code check 3 digit
		String exchange = phone.substring(3, 6);	//exchange code check 3 digit
		char ch1 = areaCode.charAt(0);
		char ch2 = exchange.charAt(0);
		if (len == 10 && ch1 >= '2' && ch1 <= '9' && ch2 >= '2' && ch2 <= '9') {   
			return true;
		} else {
			return false;
		}
		
	}
	/**
	 * Pattern class use to check valid or not
	 * https://en.wikipedia.org/wiki/Email_address
	 * I take information from above link
	 * Then just class matcher method from pattern class
	 * 
	 * */
	public boolean emailValid(String email) {
		this.email = email;
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}"; 
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
