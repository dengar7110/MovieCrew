package com.garden.moviecrew.common.hash;

public interface HashingEncoder {
	
	public String encode(String message);

	public boolean matches(String password, String password2);

}
