package cz.pavelpilar.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import cz.pavelpilar.calculator.calculator.history.HistoryActivity;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    public static Context mContext;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawer;

    private Button mButtonCalculator;

    public static boolean isTablet;

    public static SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!BuildConfig.DEBUG) Fabric.with(this, new Crashlytics());
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mContext = this;

        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.toolbarDark));

        mButtonCalculator = (Button) findViewById(R.id.drawer_calculator);
        isTablet = getResources().getBoolean(R.bool.tablet);
        if(!isTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawer = (LinearLayout) findViewById(R.id.drawer);
        }

        Fragment fragment;
        switch (mPreferences.getString("lastFragment", "")){
            case "calculator":
                fragment = new cz.pavelpilar.calculator.calculator.MainFragment();
                if(!isTablet) {
                    mButtonCalculator.setText(getString(R.string.menu_history));
                    mButtonCalculator.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_history, 0, 0, 0);
                }
                break;
            case "graphs": fragment = new cz.pavelpilar.calculator.graphs.MainFragment(); break;
            case "conversions": fragment = new cz.pavelpilar.calculator.conversions.MainFragment(); break;
            default:
                if(isTablet) {
                    fragment = new cz.pavelpilar.calculator.calculator.MainFragment();
                } else
                    fragment = new FirstStartFragment();
        }
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.content_frame, fragment)
                                   .commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e) {
        if (keyCode == KeyEvent.KEYCODE_MENU && !isTablet) {
            if (!mDrawerLayout.isDrawerOpen(mDrawer))
                mDrawerLayout.openDrawer(mDrawer);
            else
                mDrawerLayout.closeDrawer(mDrawer);
            return true;
        }
        return super.onKeyDown(keyCode, e);
    }

    public void settings(View v) {
        if(!isTablet)
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

            if(!isTablet) {
                mButtonCalculator.setText(getString(R.string.menu_history));
                mButtonCalculator.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_history, 0, 0, 0);
            }
        } else if(!isTablet) {
            new Handler().postDelayed(new Runnable() {  //Avoids lag
                 @Override
                 public void run() {
                    mDrawerLayout.closeDrawer(mDrawer);
                 }
            }, 300);
            startActivity(new Intent(this, HistoryActivity.class));
        }
        if(!isTablet) mDrawerLayout.closeDrawer(mDrawer);
    }

    public void drawerGraphs(View v) {
        if(!mPreferences.getString("lastFragment", "").equals("graphs")) {
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.content_frame, new cz.pavelpilar.calculator.graphs.MainFragment())
                                       .commit();
            mPreferences.edit()
                        .putString("lastFragment", "graphs")
                        .apply();

            if(!isTablet) {
                mButtonCalculator.setText(getString(R.string.menu_calculator));
                mButtonCalculator.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_calculator, 0, 0, 0);
            }
        }
        if(!isTablet) mDrawerLayout.closeDrawer(mDrawer);
    }

    public void drawerConversions(View v) {
        if(!mPreferences.getString("lastFragment", "").equals("conversions")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new cz.pavelpilar.calculator.conversions.MainFragment())
                    .commit();
            mPreferences.edit()
                    .putString("lastFragment", "conversions")
                    .apply();

            if(!isTablet) {
                mButtonCalculator.setText(getString(R.string.menu_calculator));
                mButtonCalculator.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_calculator, 0, 0, 0);
            }
        }
        if(!isTablet) mDrawerLayout.closeDrawer(mDrawer);
    }
}
