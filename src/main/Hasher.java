package main;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


//class responsible for hashing and salting string using MD5 
//the salt could be consider a pepper since is the same for every password
public class Hasher {
	
	private String hash;
	private String salt = "salt";
	
	public Hasher(String pass) {
		String salted = pass + salt;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(salted.getBytes(), 0, salted.length());
			this.hash = new BigInteger(1, md.digest()).toString(32);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public String getHash() {
		return this.hash;
	}

}
