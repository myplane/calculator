package cz.pavelpilar.calculator;

import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import cz.pavelpilar.calculator.calculator.history.DatabaseHelper;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.toolbarDark));
        getFragmentManager().beginTransaction()
                            .replace(R.id.settings_content, new SettingsFragment())
                            .commit();
    }

    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            findPreference("preferences_clear_history").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    MainActivity.mContext.deleteDatabase(DatabaseHelper.DATABASE_NAME);
                    return false;
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(menuItem);
    }
}
