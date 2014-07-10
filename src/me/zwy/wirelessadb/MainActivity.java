package me.zwy.wirelessadb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import me.zwy.utils.ShellUtils;
import me.zwy.utils.ShellUtils.CommandResult;
import me.zwy.wirelessadb.R;

public class MainActivity extends Activity {
	
//	private TextView ip;
//	private ToggleButton toggle;
//	private boolean isCheck;
//	private boolean isRun;
	private TextView adb_info;
	private Switch adb_switch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		if(isConnectWIFI()){
				setContentView(R.layout.main);
				adb_info = (TextView) findViewById(R.id.adb_info);
				adb_switch = (Switch) findViewById(R.id.adb_switch);
				if(!ShellUtils.checkRootPermission()){
					adb_switch.setEnabled(false);
				}
//				ip = (TextView) findViewById(R.id.IP);
//				toggle = (ToggleButton) findViewById(R.id.toggle);
//				Runtime runtime = Runtime.getRuntime();
//				try {
//					Process pro = runtime.exec("getprop service.adb.tcp.port");
//					if(pro.waitFor() == 0){
//						BufferedReader in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
//						String msg = in.readLine();
//						in.close();
//						if(msg.contains("5555")){
//							isCheck = true;
//							isRun = true;
//						}else{
//							isCheck = false;
//							isRun = false;
//						}
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			if(savedInstanceState != null){
//				isCheck = savedInstanceState.getBoolean("toggle");
//			}
//			toggle.setChecked(isCheck);
//			if(isCheck){
//				ip.setText("��CMD����:\nadb connect " + getWIFIIP());
//			}else{
//				ip.setText("");
//			}
//			toggle.setOnCheckedChangeListener(this);
//		}else{
//			new AlertDialog.Builder(this).setTitle("û��WIFI���ӣ�").setMessage("������WIFI��").setCancelable(false).setPositiveButton("ȷ��", new OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					finish();
//				}
//			}).show();
//		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.ic_setting){
			Intent intent = new Intent(this, SettingActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public boolean start(){
		CommandResult result = ShellUtils.execCommand(Constant.COMMAD_START, true);
		return result.result == 0;
	}
	
	public boolean stop(){
		CommandResult result = ShellUtils.execCommand(Constant.COMMAD_STOP, true);
		return result.result == 0;
	}
	
	public boolean isConnectWIFI(){
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(info != null){
			return info.isConnected();
		}
		return false;
	}
	
	public String getIP(){
		WifiManager wifiManger = (WifiManager) getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManger.getConnectionInfo();
		int ip = wifiInfo.getIpAddress();
		return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + (ip >> 24 & 0xFF);
	}

//	@Override
//	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//		if(isChecked){
//			if(!isRun){
//				start();
//				isRun = true;
//				ip.setText("��CMD����:\nadb connect " + getWIFIIP());
//			}
//		}else{
//			if(isRun){
//				stop();
//				isRun = false;
//				ip.setText("");
//			}
//		}
//	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
//		outState.putBoolean("toggle", toggle.isChecked());
	}
	
}
