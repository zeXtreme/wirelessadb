package me.zwy.wirelessadb;

import me.zwy.utils.CommonUtils;
import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SettingActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		getActionBar().setTitle(getResources().getString(R.string.setting));
	}
	
	public static class SettingFragment extends PreferenceFragment{
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.setting);
			PreferenceManager pm = getPreferenceManager();
			Preference port = pm.findPreference("port");
			Preference about = pm.findPreference("about");
			port.setSummary(String.format(getResources().getString(R.string.current_port), BaseApplication.port_current));
			port.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					Toast.makeText(getActivity(), newValue.toString(), Toast.LENGTH_SHORT).show();
					BaseApplication.port_current = CommonUtils.str2Int(newValue.toString());
					Toast.makeText(getActivity(), BaseApplication.port_current+"", Toast.LENGTH_SHORT).show();
					preference.setSummary(String.format(getResources().getString(R.string.current_port), BaseApplication.port_current));
					return true;
				}
			});
			about.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					return false;
				}
			});
		}
	}

}
