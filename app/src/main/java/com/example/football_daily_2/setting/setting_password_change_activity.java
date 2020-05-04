package com.example.football_daily_2.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.football_daily_2.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class setting_password_change_activity extends Fragment {
    private SharedPreferences appIDData;
    String CurrentID_data, CurrentPW_data;
    EditText currentPW, newPW, newPW_check;
    String currentPW_str, newPW_str, newPW_check_str;
    Button btn_change_complete, btn_change_cancel;
    setting_activity activity = new setting_activity();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting_changepw, container, false);

        appIDData = this.getActivity().getSharedPreferences("appData", Context.MODE_PRIVATE);

        CurrentID_data = appIDData.getString("ID", "");
        CurrentPW_data = appIDData.getString("PWD", "");

        currentPW = view.findViewById(R.id.current_pw);
        newPW = view.findViewById(R.id.new_pw);
        newPW_check = view.findViewById(R.id.new_pw_check);

        btn_change_cancel = view.findViewById(R.id.btn_change_pw_cancel);
        btn_change_complete = view.findViewById(R.id.btn_change_pw);

        btn_change_complete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                currentPW_str = currentPW.getText().toString();
                newPW_str = newPW.getText().toString();
                newPW_check_str = newPW_check.getText().toString();
                if(currentPW_str.equals(CurrentPW_data) && newPW_str.equals(newPW_check_str)){
                    changePWDB changePWDB = new changePWDB();
                    changePWDB.execute(CurrentID_data, newPW_str);
                }
                else if(!currentPW_str.equals(CurrentPW_data)) {
                    Toast.makeText(getContext(), "현재 비밀번호가 알맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else if(!newPW_str.equals(newPW_check_str)){
                    Toast.makeText(getContext(), "변경하려는 비밀번호를 다시 확인해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_change_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, activity);
                fragmentTransaction.commit();
                Toast.makeText(getContext(), "비밀번호 변경이 취소되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    public class changePWDB extends AsyncTask<String, Void, String> {
        String data = "";
        @Override
        protected String doInBackground(String... strings) {
            String param = "id=" + strings[0] + "&password=" + strings[1];
            try {
                /*서버연결*/
                URL url = new URL("http://15.164.129.2/passwordChange.php");
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
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, activity);
            fragmentTransaction.commit();
            Toast.makeText(getContext(), "비밀번호 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
