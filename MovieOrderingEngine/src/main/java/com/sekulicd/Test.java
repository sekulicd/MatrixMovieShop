package com.sekulicd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		System.out.println(isValidFormat("yyyy-MM-dd", "2018-01-4"));
	}
	
	public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

}
