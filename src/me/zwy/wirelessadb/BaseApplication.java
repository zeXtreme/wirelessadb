package me.zwy.wirelessadb;

import me.zwy.utils.CommonUtils;
import android.app.Application;
import android.content.SharedPreferences;

public class BaseApplication extends Application {
	
	public static int port_current;
	
	@Override
	public void onCreate() {
		super.onCreate();
		SharedPreferences sp = getSharedPreferences("me.zwy.wirelessadb_preferences", MODE_PRIVATE);
		port_current = CommonUtils.str2Int(sp.getString("port", Constant.PORT_DEFAULT));
	}

}
