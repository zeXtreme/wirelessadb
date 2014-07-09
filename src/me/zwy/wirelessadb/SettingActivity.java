package me.zwy.wirelessadb;

import me.zwy.utils.CommonUtils;
import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
	}
	
	public static class SettingFragment extends PreferenceFragment{
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.setting);
			PreferenceManager pm = getPreferenceManager();
			Preference port = pm.findPreference("port");
			port.setSummary(String.format(getResources().getString(R.string.current_port), BaseApplication.port_current));
			port.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					BaseApplication.port_current = CommonUtils.str2Int(newValue.toString());
					preference.setSummary(String.format(getResources().getString(R.string.current_port), BaseApplication.port_current));
					return true;
				}
			});
		}
	}

}
