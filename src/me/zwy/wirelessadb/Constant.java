package me.zwy.wirelessadb;

public class Constant {
	
	public static final String[] COMMAD_START = {"setprop service.adb.tcp.port 5555", "stop adbd", "start adbd"};
	public static final String[] COMMAD_STOP = {"setprop service.adb.tcp.port -1", "stop adbd", "start adbd"};

}
