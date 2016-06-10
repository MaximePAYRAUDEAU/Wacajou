package com.wacajou.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Validate {
	
	public static void isValid(String string, String message){
	    Pattern pattern = Pattern.compile("[\\=;]");
	    Matcher matcher = pattern.matcher(string);
	    while(matcher.find())
	    	throw new IllegalArgumentException("Illegal string input.");
	}
}
