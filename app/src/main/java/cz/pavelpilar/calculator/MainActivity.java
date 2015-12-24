package cz.pavelpilar.calculator;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cz.pavelpilar.calculator.calculator.MainFragment;

public class MainActivity extends AppCompatActivity {

    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        Fragment fragment = new MainFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
               .replace(R.id.content_frame, fragment)
               .commit();
    }

    public static Context getContext(){
        return mContext;
    }
}
