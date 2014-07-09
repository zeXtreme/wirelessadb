package me.zwy.wirelessadb;

public final class Constant {
	
	/**
	 * 开始的命令
	 */
	public static final String[] COMMAD_START = {"setprop service.adb.tcp.port " + BaseApplication.port_current, "stop adbd", "start adbd"};
	
	/**
	 * 结束的命令
	 */
	public static final String[] COMMAD_STOP = {"setprop service.adb.tcp.port -1", "stop adbd", "start adbd"};
	
	/**
	 * 检查当前状态的命令
	 */
	public static final String[] COMMAD_STATUS = {"getprop service.adb.tcp.port"};
	
	/**
	 * 默认端口号
	 */
	public static final String PORT_DEFAULT = "5555";

}
