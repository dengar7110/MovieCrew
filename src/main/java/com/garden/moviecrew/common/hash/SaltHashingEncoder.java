package com.garden.moviecrew.common.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component("saltHashing")
public class SaltHashingEncoder implements HashingEncoder {

	private int SALT_LENGTH = 16;
	
	
	private byte[] generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_LENGTH];
		random.nextBytes(salt);
		return salt;
	}
	
	@Override
	public String encode(String message) {
		
		String result = "";
		
		try {
			
			byte[] salt = generateSalt();
			
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			
			messageDigest.update(salt);
			
			byte[] bytes = message.getBytes();
			byte[] digest = messageDigest.digest(bytes);
			
			for(int i = 0; i < digest.length; i++) {
				result += Integer.toHexString(digest[i] & 0xff);
			}
			
			String saltString = Base64.getEncoder().encodeToString(salt);
			
			result = saltString + ":" + result;
			
			
		} catch (NoSuchAlgorithmException e) {
			
			return null;
			
		}
		
		return result;
	}
 	
}