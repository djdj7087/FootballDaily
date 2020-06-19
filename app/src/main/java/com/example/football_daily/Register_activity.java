package com.example.football_daily;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class Register_activity extends Activity {
    private back_press_handler_activity backPressHandlerActivity;
    EditText reg_id, reg_pw, reg_pw_check, reg_name, reg_age;
    String id, pw, pw_check, name, age;
    boolean isthereID = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        backPressHandlerActivity = new back_press_handler_activity(this);

        reg_id = (EditText) findViewById(R.id.reg_ID);
        reg_pw = (EditText) findViewById(R.id.passwordText);
        reg_pw_check = (EditText) findViewById(R.id.passwordCheckText);
        reg_name = (EditText) findViewById(R.id.nameText);
        reg_age = (EditText) findViewById(R.id.ageText);
    }
    public void btn_register(View view){
        id = reg_id.getText().toString();
        pw = reg_pw.getText().toString();
        pw_check = reg_pw_check.getText().toString();
        name = reg_name.getText().toString();
        age = reg_age.getText().toString();

        if(pw.equals(pw_check) && !isthereID){
            registDB registDB = new registDB();
            registDB.execute(id, pw, name, age);
            Intent intent = new Intent(getApplicationContext(), login_activity.class);
            startActivity(intent);
            Toast.makeText(this,"회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        }
        else if(!pw.equals(pw_check)){
            Toast.makeText(this,"비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
        }
        else if(isthereID){
            Toast.makeText(this, "아이디 중복확인을 해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
    public void btn_cancel(View view){
        Intent intent = new Intent(getApplicationContext(), login_activity.class);
        startActivity(intent);
    }
    public void btn_IDTest(View view){
        id = reg_id.getText().toString();
        testID testID = new testID();
        testID.execute(id);
    }
    public class testID extends AsyncTask<String, Void, String>{
        String data = "";
        @Override
        protected String doInBackground(String... strings) {
            String params = "id=" + strings[0];
            try{
                /*서버연결*/
                URL url = new URL("http://15.164.129.2/testID.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.connect();

                /*안드로이드 > 서버*/
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(params.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                /*서버 > 안드로이드*/
                InputStream inputStream = null;
                BufferedReader bufferedReader = null;

                inputStream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream),8*1024);
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();

                while((line = bufferedReader.readLine())!=null)
                    stringBuilder.append(line+"\n");

                data = stringBuilder.toString().trim();
                Log.e("RECV DATA: ", data);

                return stringBuilder.toString();
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(data.equals("1")) {
                isthereID = true;
                Toast.makeText(getApplicationContext(), "아이디가 존재합니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                isthereID = false;
                Toast.makeText(getApplicationContext(), "아이디를 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class registDB extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            String params = "id=" + strings[0] +"&password=" + strings[1] +
                    "&name=" + strings[2] + "&age=" + strings[3];
            try{
                /*서버연결*/
                URL url = new URL("http://15.164.129.2/register.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.connect();

                /*안드로이드 > 서버*/
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(params.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                /*서버 > 안드로이드*/
                InputStream inputStream = null;
                BufferedReader bufferedReader = null;
                String data = "";

                inputStream = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream),8*1024);
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();

                while((line = bufferedReader.readLine())!=null)
                    stringBuilder.append(line+"\n");

                data = stringBuilder.toString().trim();
                Log.e("RECV DATA: ", data);

                return stringBuilder.toString();
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
    public void onBackPressed(){
        backPressHandlerActivity.onBackPressed();
    }
}