package com.example.football_daily.community;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.football_daily.R;
import java.util.Calendar;

public class community_content_activity extends Activity {
    Button btn_back;
    TextView name, title, time, content, main_Title;
    String name_str, title_str, time_str, content_str;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_content);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        main_Title = findViewById(R.id.id_main_title);
        name = findViewById(R.id.id_write_name);
        title = findViewById(R.id.id_title_text);
        time = findViewById(R.id.id_time);
        content = findViewById(R.id.content_text);

        title_str = intent.getStringExtra("Title");
        name_str = intent.getStringExtra("Name");
        time_str = intent.getStringExtra("Time");
        content_str = intent.getStringExtra("Content");

        main_Title.setText(title_str);
        name.setText(name_str);
        title.setText(title_str);
        time.setText(Calendar.getInstance().get(Calendar.YEAR) + "." + time_str);
        content.setText(content_str);
    }
}