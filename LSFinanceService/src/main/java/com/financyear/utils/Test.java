package com.financyear.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		/*Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.YEAR, 2016);
		Date date = calendar.getTime();
		
		System.out.println(date);
		System.out.println(Utils.convertDateToStringWithSlash(date));*/
		
		Double val = 845656.00;
		
		DecimalFormat dc = new DecimalFormat("#.##");
		System.out.println(dc.format(val));
		System.out.println(String.format("%.2f", val));
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	}
}
