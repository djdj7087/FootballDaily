package com.example.football_daily.community;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.football_daily.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class community_write_activity extends Activity {
    private TextView textView;
    private SharedPreferences appIDData;
    private String id, content_str, title_str, time;
    private EditText content, title;
    private community_activity communityActivity = new community_activity();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_write);

        content = findViewById(R.id.content);
        title = findViewById(R.id.title);

        Button btn_complete = findViewById(R.id.btn_complete);
        Button btn_cancel = findViewById(R.id.write_cancel);

        appIDData = this.getSharedPreferences("appData", Context.MODE_PRIVATE);
        id = appIDData.getString("ID", "");
        textView = findViewById(R.id.write_id);
        textView.setText(id);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        time = month + "." + date;

        btn_complete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                content_str = content.getText().toString();
                title_str = title.getText().toString();

                communityDB communityDB = new communityDB();
                communityDB.execute(id, title_str, content_str);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class communityDB extends AsyncTask<String, Void, String>{
        private String data = "";
        @Override
        protected String doInBackground(String... strings) {
            String param = "name=" + strings[0] + "&time=" + time + "&title=" + strings[1] + "&content=" + strings[2] + "&image=" + 1;
            try {
                /*서버연결*/
                URL url = new URL("http://15.164.129.2/community.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.connect();

                /*안드로이드 > 서버*/
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(param.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                /*서버 > 안드로이드*/
                InputStream inputStream = null;
                BufferedReader bufferedReader = null;

                inputStream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8 * 1024);
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null)
                    stringBuilder.append(line + "\n");

                data = stringBuilder.toString().trim();
                Log.e("RECV DATA", data);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            finish();
        }
    }
}
