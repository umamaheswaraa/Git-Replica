package com.imaginea.gr.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Utilities {

	public static String dateDiff(long startDate, long endDate){
		
		String diffStr=null;
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTimeInMillis(startDate);
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTimeInMillis(endDate);

		int diffYear = startCalendar.get(Calendar.YEAR) - endCalendar.get(Calendar.YEAR);
		int diffMonth = startCalendar.get(Calendar.MONTH) - endCalendar.get(Calendar.MONTH);
		int diffDay =   startCalendar.get(Calendar.DAY_OF_MONTH) - endCalendar.get(Calendar.DAY_OF_MONTH);
		if(diffYear>0)
		{
			diffStr = diffYear + " Years ";
		}else if(diffMonth>0)
		{
			diffStr = diffMonth + " Months ";
		}else if(diffDay>0){
			diffStr = diffDay + " Days ";
		}
		
		return diffStr;
		
	}
}
