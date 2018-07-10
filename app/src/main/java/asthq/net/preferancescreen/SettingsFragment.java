package asthq.net.preferancescreen;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{




    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_preference);

        SharedPreferences sp=getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen=getPreferenceScreen();

        int count=prefScreen.getPreferenceCount();

        for(int i=0;i<count;i++){
            Preference preference=prefScreen.getPreference(i);

            if(preference instanceof ListPreference){
                String value=sp.getString(preference.getKey(),"");
                setPrefSummery(preference,value);
            }else if(preference instanceof EditTextPreference){
                String value=sp.getString(preference.getKey(),"");
                setPrefSummery(preference,value);
            }
        }

    }

    protected void setPrefSummery(Preference preference,String value){
        if(preference instanceof ListPreference){
            ListPreference listPreference= (ListPreference) preference;
            int prefIndx= listPreference.findIndexOfValue(value);
            if(prefIndx>=0){
                listPreference.setSummary(listPreference.getEntries()[prefIndx]);
            }
        }else if(preference instanceof EditTextPreference){
            EditTextPreference editTextPreference = (EditTextPreference) preference;
            editTextPreference.setSummary(value);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Preference preference=findPreference(s);
        if(null!=preference){
            if(preference instanceof ListPreference){
                String value=sharedPreferences.getString(preference.getKey(),"");
                setPrefSummery(preference,value);
            }else if(preference instanceof EditTextPreference){
                String value=sharedPreferences.getString(preference.getKey(),"");
                setPrefSummery(preference,String.valueOf(value));
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
