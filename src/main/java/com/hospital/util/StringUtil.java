package com.hospital.util;

public class StringUtil {
	public static boolean isEmpty(String str){
		if(str == null || str.equals("")){
			return true;
		}else{
			return false;
		}
	}
}
