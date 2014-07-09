package me.zwy.utils;

public final class CommonUtils {
	
	public static int str2Int(String str) throws NumberFormatException{
		int ret = 0;
		if(str!=null){
			ret = Integer.parseInt(str);
		}
		return ret;
	}

}
