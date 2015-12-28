package cz.pavelpilar.calculator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;

import cz.pavelpilar.calculator.calculator.MainFragment;

public class MainActivity extends AppCompatActivity {

    private static Context mContext;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // Action bar is needed to get menu overflow button
        getSupportActionBar().setTitle("");

        mContext = this;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer = (LinearLayout) findViewById(R.id.drawer);
        Calculator.initialize();

        Fragment fragment = new cz.pavelpilar.calculator.calculator.MainFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
               .replace(R.id.content_frame, fragment)
               .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calculator_menu, menu);
        return true;
    }

    public static Context getContext(){
        return mContext;
    }

    public void settings(View v) {
        new Handler().postDelayed(new Runnable() {  //Avoids lag
            @Override
            public void run() {
                startActivity(new Intent(getContext(), SettingsActivity.class));
            }
        }, 150);
        mDrawerLayout.closeDrawer(mDrawer);
    }
}
