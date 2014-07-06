package me.zwy.utils;

import java.io.DataOutputStream;
import java.io.IOException;

public class CommadUtil {
	
	public static boolean execCommad(String commad){
		Runtime runtime = Runtime.getRuntime();
		Process pro;
		try {
			pro = runtime.exec(commad);
//			DataOutputStream dos = new DataOutputStream(pro.getOutputStream());
//			dos.writeBytes("setprop service.adb.tcp.port 5555\n");
//			dos.writeBytes("stop adbd\n");
//			dos.writeBytes("start adbd\n");
//			dos.flush();
//			dos.close();
			if(pro.waitFor() == 0){
				return true;
			}else{
				return false;
			}
		} catch (IOException e) {
			return false;
		} catch (InterruptedException e) {
			return false;
		}
	}

}
