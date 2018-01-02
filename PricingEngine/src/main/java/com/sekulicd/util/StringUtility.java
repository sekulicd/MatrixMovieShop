package com.sekulicd.util;

public class StringUtility {
	
	public static boolean isStringEmptyOrNull(String str){
		if(str==null || str.isEmpty()){
			return true;
		}
		return false;
	}

}
