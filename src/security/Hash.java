package security;

import java.security.*;
import java.math.*;

public class Hash {
	private String hashed;

	public Hash (String toHash) throws Exception{
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(toHash.getBytes(),0,toHash.length());
		setHashed(new BigInteger(1,m.digest()).toString(16));
	}
	
	private void setHashed(String toSet) {
		this.hashed = toSet;
	}
	
	public String getHashed() {
		return hashed;
	}
}