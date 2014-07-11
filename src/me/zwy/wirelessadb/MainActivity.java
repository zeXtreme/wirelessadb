package me.zwy.wirelessadb;

import android.app.Activity;
import android.content.Intent;
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
import me.zwy.utils.ShellUtils;
import me.zwy.utils.ShellUtils.CommandResult;
import me.zwy.wirelessadb.R;

public class MainActivity extends Activity implements OnCheckedChangeListener {

	private TextView adb_info;
	private Switch adb_switch;
	private boolean isRun;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isConnectWIFI()) {
			setContentView(R.layout.main);
			adb_info = (TextView) findViewById(R.id.adb_info);
			adb_switch = (Switch) findViewById(R.id.adb_switch);
			isRun = checkIsRun();
			if (!ShellUtils.checkRootPermission()) {
				adb_switch.setEnabled(false);
				adb_info.setText("手机没有ROOT权限");
			}else{
				adb_switch.setChecked(isRun);
				adb_switch.setOnCheckedChangeListener(this);
			}
		} else {
			Toast.makeText(this, "没有可用的WIFI网络", Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.ic_setting) {
			Intent intent = new Intent(this, SettingActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean start() {
		CommandResult result = ShellUtils.execCommand(Constant.COMMAD_START,
				true);
		return result.result == 0;
	}

	public boolean stop() {
		CommandResult result = ShellUtils.execCommand(Constant.COMMAD_STOP,
				true);
		return result.result == 0;
	}
	
	public boolean checkIsRun(){
		CommandResult result = ShellUtils.execCommand(Constant.COMMAD_STATUS, false, true);
		return result.successMsg.equals(String.valueOf(BaseApplication.port_current));
	}

	public boolean isConnectWIFI() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (info != null) {
			return info.isConnected();
		}
		return false;
	}

	public String getIP() {
		WifiManager wifiManger = (WifiManager) getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManger.getConnectionInfo();
		int ip = wifiInfo.getIpAddress();
		return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "."
				+ ((ip >> 16) & 0xFF) + "." + (ip >> 24 & 0xFF);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// outState.putBoolean("toggle", toggle.isChecked());
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked && !isRun){
			start();
			isRun = true;
			adb_info.setText(getIP());
		}else{
			stop();
			isRun = false;
			adb_info.setText("");
		}
	}

}
