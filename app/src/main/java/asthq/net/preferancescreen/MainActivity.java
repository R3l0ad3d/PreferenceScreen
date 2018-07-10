package asthq.net.preferancescreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView tvText;
    private ConstraintLayout layout;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvText=findViewById(R.id.tvText);
        layout=findViewById(R.id.layout);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.registerOnSharedPreferenceChangeListener(this);

        layout.setBackgroundColor(Color.parseColor(sp
                .getString(getResources().getString(R.string.pref_background_color),
                        getResources().getString(R.string.red_code))));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.menu_setting){
            Intent intent =new Intent(this,SettingsActivity.class);
            startActivity(intent);
            //finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals("key")){
            if(sharedPreferences.getBoolean(s,false)){
                tvText.setText("Change");
            }else {
                tvText.setText("no Change");
            }

        }else if(s.equals(getResources().getString(R.string.pref_background_color))){
            layout.setBackgroundColor(Color.parseColor(sharedPreferences
                    .getString(getResources().getString(R.string.pref_background_color),
                            getResources().getString(R.string.red_code))));
        }else if(s.equals(getResources().getString(R.string.pref_font_size))){
            tvText.setTextSize(Float.valueOf(sharedPreferences.getString(s,"0")));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sp.unregisterOnSharedPreferenceChangeListener(this);
    }
}
