package cz.pavelpilar.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private static Context mContext;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer = (LinearLayout) findViewById(R.id.drawer);
        Calculator.initialize();

        if(getResources().getBoolean(R.bool.portrait_only))
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Fragment fragment = new cz.pavelpilar.calculator.calculator.MainFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
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

    public static Context getContext(){
        return mContext;
    }

    public void settings(View v) {
        new Handler().postDelayed(new Runnable() {  //Avoids lag
            @Override
            public void run() {
                mDrawerLayout.closeDrawer(mDrawer);
            }
        }, 300);
        startActivity(new Intent(getContext(), SettingsActivity.class));
    }
}
