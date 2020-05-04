package com.example.football_daily_2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class login_activity extends Activity {
    private back_press_handler_activity backPressHandlerActivity;
    private String ID, Password;
    private EditText IDView, PasswordView;
    private CheckBox checkBox;
    private SharedPreferences appData;
    private boolean isLoginInform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        backPressHandlerActivity = new back_press_handler_activity(this);
        IDView = (EditText) findViewById(R.id.email);
        PasswordView = (EditText) findViewById(R.id.password);
        checkBox = (CheckBox) findViewById(R.id.auto_login);

        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

        if (isLoginInform){
            IDView.setText(ID);
            PasswordView.setText(Password);
            checkBox.setChecked(isLoginInform);
        }
    }
    public void btn_Signup(View view){
        Intent intent = new Intent(getApplicationContext(), Register_activity.class);
        startActivity(intent);
    }
    public void btn_login(View view) {
        ID = IDView.getText().toString();
        Password = PasswordView.getText().toString();

        loginDB loginDB = new loginDB();
        loginDB.execute(ID, Password);
    }
    private void clear(){
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("SAVE_LOGIN_DATA", checkBox.isChecked());
        editor.putString("ID", "");
        editor.putString("PWD", "");
        editor.apply();
    }
    private void save(){
        SharedPreferences.Editor editor = appData.edit();
        editor.putBoolean("SAVE_LOGIN_DATA", checkBox.isChecked());
        editor.putString("ID", IDView.getText().toString().trim());
        editor.putString("PWD", PasswordView.getText().toString().trim());
        editor.apply();
    }
    private void load(){
        isLoginInform = appData.getBoolean("SAVE_LOGIN_DATA", false);
        ID = appData.getString("ID", "");
        Password = appData.getString("PWD", "");
    }
    public class loginDB extends AsyncTask<String, Void, String> {
        String data = "";
        boolean isLogin;
        @Override
        protected String doInBackground(String... strings) {
            String param = "id=" + strings[0] + "&password=" + strings[1];
            try {
                /*서버연결*/
                URL url = new URL("http://15.164.129.2/login.php");
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

                if(data.equals("1"))
                    isLogin = true;
                else
                    isLogin = false;
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
            if(isLogin && checkBox.isChecked() == true){
                save();
                Toast.makeText(getApplicationContext(), "환영합니다!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            else if(isLogin && checkBox.isChecked() == false){
                clear();
                Toast.makeText(getApplicationContext(), "환영합니다!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onBackPressed(){
        backPressHandlerActivity.onBackPressed();
    }
}

