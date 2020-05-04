package com.example.football_daily_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.football_daily_2.community.community_activity;
import com.example.football_daily_2.home.home_activity;
import com.example.football_daily_2.news.news_activity;
import com.example.football_daily_2.setting.setting_activity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private back_press_handler_activity backPressHandlerActivity;
    private TextView mTextMessage;

    private com.example.football_daily_2.home.home_activity home_activity = new home_activity();
    private com.example.football_daily_2.news.news_activity news_activity = new news_activity();
    private com.example.football_daily_2.community.community_activity community_activity = new community_activity();
    private com.example.football_daily_2.setting.setting_activity setting_activity = new setting_activity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressHandlerActivity = new back_press_handler_activity(this);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, home_activity).commitAllowingStateLoss();

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        transaction.replace(R.id.frame_layout, home_activity);
                        transaction.commit();
                        return true;
                    case R.id.navigation_news:
                        transaction.replace(R.id.frame_layout, news_activity);
                        transaction.commit();
                        return true;
                    case R.id.navigation_community:
                        transaction.replace(R.id.frame_layout, community_activity);
                        transaction.commit();
                        return true;
                    case R.id.navigation_setting:
                        transaction.replace(R.id.frame_layout, setting_activity);
                        transaction.commit();
                        return true;
                }
                return false;
            }
        };
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onBackPressed() {
        backPressHandlerActivity.onBackPressed();
    }
}
