package com.example.football_daily_2.community;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.football_daily_2.R;
import com.example.football_daily_2.home.home_list_adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class community_activity extends Fragment {
    private RecyclerView mRecyclerView;
    private community_list_adapter mAdapter;
    private String data = "";
    private static Context context;

    ArrayList<community_list_item> listData = new ArrayList<>();

    public void refresh(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();

        fetchContentDB fetchContentDB = new fetchContentDB();
        fetchContentDB.execute();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

        fetchContentDB fetchContentDB = new fetchContentDB();
        fetchContentDB.execute();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_community, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_community_list);

        Button btn_write = view.findViewById(R.id.btn_write);
        Button btn_refresh = view.findViewById(R.id.btn_refresh_community);
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), community_write_activity.class);
                startActivity(intent);
            }
        });
        btn_refresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        return view;
    }

    public class fetchContentDB extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                /*서버연결*/
                URL url = new URL("http://15.164.129.2/cummunity_db.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.connect();

                /*안드로이드 > 서버*/
                OutputStream outputStream = connection.getOutputStream();
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
                Log.e("DATA: " , data);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JsonData(data);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mAdapter = new community_list_adapter(getActivity(), listData);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
    private void JsonData(String data){
        listData.clear();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0 ; i<jsonArray.length(); i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String number = jsonObject.getString("number");
                String name = jsonObject.getString("name");
                String title = jsonObject.getString("title");
                String content = jsonObject.getString("content");
                String time = jsonObject.getString("time");

                community_list_item listItem = new community_list_item();

                listItem.setNumber(number);
                listItem.setName(name);
                listItem.setTitle(title);
                listItem.setContent(content);
                listItem.setTime(time);

                listData.add(listItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
