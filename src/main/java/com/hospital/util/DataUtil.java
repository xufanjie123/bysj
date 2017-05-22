package com.hospital.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {
	public static String formatDate(Timestamp date,String format){
		DateFormat sdf = new SimpleDateFormat(format);

		Timestamp ts = date;         

		String tsStr = ""; 

		tsStr = sdf.format(ts); 
		return tsStr;
	}
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	public static Date formatString(String str,String format) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
}
