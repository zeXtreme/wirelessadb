package me.zwy.wirelessadb;

import me.zwy.utils.CommonUtils;
import me.zwy.utils.ShellUtils;
import android.app.Application;
import android.content.SharedPreferences;

public class BaseApplication extends Application {
	
	public static int port_current;
	public static boolean isRoot;
	
	@Override
	public void onCreate() {
		super.onCreate();
		SharedPreferences sp = getSharedPreferences("me.zwy.wirelessadb_preferences", MODE_PRIVATE);
		port_current = CommonUtils.str2Int(sp.getString("port", String.valueOf(Constant.PORT_DEFAULT)));
		isRoot = ShellUtils.checkRootPermission();
	}

}
