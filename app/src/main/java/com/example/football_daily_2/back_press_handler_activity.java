package com.example.football_daily_2;

import android.app.Activity;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class back_press_handler_activity {
    private long backKeyPressedTime = 0;
    private Toast toast;
    private Activity activity;
    public back_press_handler_activity(Activity context) {
        this.activity = context;
    }
    public void onBackPressed() {
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            ActivityCompat.finishAffinity(activity);
        }
    }
    public void showGuide() {
        Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다." , Toast.LENGTH_SHORT).show();
    }
}