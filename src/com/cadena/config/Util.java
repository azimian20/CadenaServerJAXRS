package com.cadena.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	//static Log log = LogFactory.getLog(Util.class);

	private Util() {
		
	}
	public static Date dateFormatter(String input) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			return formatter.parse(input);
		} catch (ParseException e) {
			//log.error("Date formatter failed on imput: " + input + " server date will be applied");
			return new Date();
		}
	}
}
