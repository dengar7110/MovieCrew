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

	@Override
	public boolean matches(String rawPassword, String encodedPassword) {
	    try {
	        // 저장된 비밀번호에서 salt와 해시된 비밀번호를 분리
	        String[] parts = encodedPassword.split(":");
	        if (parts.length != 2) {
	            return false; // 올바른 형식이 아니면 false 반환
	        }
	        
	        String saltString = parts[0];  // Base64로 인코딩된 salt 부분
	        String storedHash = parts[1];  // 저장된 해시된 비밀번호
	        
	        // Base64로 인코딩된 salt를 다시 byte[]로 변환
	        byte[] salt = Base64.getDecoder().decode(saltString);
	        
	        // 입력된 비밀번호를 같은 방식으로 해시
	        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
	        messageDigest.update(salt); // 저장된 salt를 사용
	        byte[] rawBytes = rawPassword.getBytes();
	        byte[] digest = messageDigest.digest(rawBytes);
	        
	        // 해시 결과를 16진수 문자열로 변환
	        StringBuilder sb = new StringBuilder();
	        for (byte b : digest) {
	            sb.append(Integer.toHexString(b & 0xff));
	        }
	        
	        // 해시된 비밀번호가 저장된 비밀번호와 일치하는지 확인
	        return sb.toString().equals(storedHash);
	        
	    } catch (NoSuchAlgorithmException e) {
	        return false;
	    }
	}
 	
}