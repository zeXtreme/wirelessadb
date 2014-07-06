package me.zwy.wirelessadb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import me.zwy.wirelessadb.R;

public class MainActivity extends Activity implements OnCheckedChangeListener {
	
	private TextView ip;
	private ToggleButton toggle;
	private boolean isCheck;
	private boolean isRun;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(isConnectWIFI()){
				setContentView(R.layout.main);
				ip = (TextView) findViewById(R.id.IP);
				toggle = (ToggleButton) findViewById(R.id.toggle);
				Runtime runtime = Runtime.getRuntime();
				try {
					Process pro = runtime.exec("getprop service.adb.tcp.port");
					if(pro.waitFor() == 0){
						BufferedReader in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
						String msg = in.readLine();
						in.close();
						if(msg.contains("5555")){
							isCheck = true;
							isRun = true;
						}else{
							isCheck = false;
							isRun = false;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			if(savedInstanceState != null){
				isCheck = savedInstanceState.getBoolean("toggle");
			}
			toggle.setChecked(isCheck);
			if(isCheck){
				ip.setText("在CMD输入:\nadb connect " + getWIFIIP());
			}else{
				ip.setText("");
			}
			toggle.setOnCheckedChangeListener(this);
		}else{
			new AlertDialog.Builder(this).setTitle("没有WIFI连接！").setMessage("请连接WIFI！").setCancelable(false).setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			}).show();
		}
	}
	
	public boolean start(){
		Runtime runtime = Runtime.getRuntime();
		Process pro;
		try {
			pro = runtime.exec("su");
			DataOutputStream dos = new DataOutputStream(pro.getOutputStream());
			dos.writeBytes("setprop service.adb.tcp.port 5555\n");
			dos.writeBytes("stop adbd\n");
			dos.writeBytes("start adbd\n");
			dos.flush();
			dos.close();
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
	
	public boolean stop(){
		Runtime runtime = Runtime.getRuntime();
		Process pro;
		try {
			pro = runtime.exec("su");
			DataOutputStream dos = new DataOutputStream(pro.getOutputStream());
			dos.writeBytes("setprop service.adb.tcp.port -1\n");
			dos.writeBytes("stop adbd\n");
			dos.writeBytes("start adbd\n");
			dos.flush();
			dos.close();
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
	
	public boolean isConnectWIFI(){
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(info != null){
			return info.isConnected();
		}
		return false;
	}
	
	public String getWIFIIP(){
		WifiManager wifiManger = (WifiManager) getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManger.getConnectionInfo();
		int ip = wifiInfo.getIpAddress();
		return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + (ip >> 24 & 0xFF);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			if(!isRun){
				start();
				isRun = true;
				ip.setText("在CMD输入:\nadb connect " + getWIFIIP());
			}
		}else{
			if(isRun){
				stop();
				isRun = false;
				ip.setText("");
			}
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("toggle", toggle.isChecked());
	}
	
}
