package cz.pavelpilar.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import cz.pavelpilar.calculator.calculator.Calculator;

public class MainActivity extends AppCompatActivity {

    public static Context mContext;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawer;

    public static SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mContext = this;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer = (LinearLayout) findViewById(R.id.drawer);

        if(getResources().getBoolean(R.bool.portrait_only))
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Fragment fragment;
        switch (mPreferences.getString("lastFragment", "")){
            case "calculator": fragment = new cz.pavelpilar.calculator.calculator.MainFragment(); break;
            case "graphs": fragment = new cz.pavelpilar.calculator.graphs.MainFragment(); break;
            default:
                fragment = new FirstStartFragment();
                mPreferences.edit().clear().apply();   //Remove preferences from last version, will be removed in next release.
        }
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.content_frame, fragment)
                                   .commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (!mDrawerLayout.isDrawerOpen(mDrawer))
                mDrawerLayout.openDrawer(mDrawer);
            else
                mDrawerLayout.closeDrawer(mDrawer);
            return true;
        }
        return super.onKeyDown(keyCode, e);
    }

    public void settings(View v) {
        new Handler().postDelayed(new Runnable() {  //Avoids lag
            @Override
            public void run() {
                mDrawerLayout.closeDrawer(mDrawer);
            }
        }, 300);
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void drawerCalculator(View v) {
        if(!mPreferences.getString("lastFragment", "").equals("calculator")) {
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.content_frame, new cz.pavelpilar.calculator.calculator.MainFragment())
                                       .commit();
            mPreferences.edit()
                        .putString("lastFragment", "calculator")
                        .apply();
        }
        mDrawerLayout.closeDrawer(mDrawer);
    }

    public void drawerGraphs(View v) {
        if(!mPreferences.getString("lastFragment", "").equals("graphs")) {
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.content_frame, new cz.pavelpilar.calculator.graphs.MainFragment())
                                       .commit();
            mPreferences.edit()
                        .putString("lastFragment", "graphs")
                        .apply();
        }
        mDrawerLayout.closeDrawer(mDrawer);
    }
}
