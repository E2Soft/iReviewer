package com.example.ireviewr.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewerTools {

	public static String PATTERN = "dd.MM.yyyy";
	
	public static String preapreDate(Date date) {
		
		SimpleDateFormat formater = new SimpleDateFormat(PATTERN);
		
		return formater.format(date);
	}
	
}
