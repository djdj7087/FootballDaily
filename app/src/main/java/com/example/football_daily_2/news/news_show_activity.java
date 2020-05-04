package com.example.football_daily_2.news;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.football_daily_2.R;
import com.example.football_daily_2.community.community_activity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class news_show_activity extends Fragment {
    news_activity activity = new news_activity();
    TextView news_title, news_content;
    String News_URL, title;
    Button btn_back;
    String content = "";

    public news_show_activity(String URL, String title){
        this.News_URL = URL;
        this.title = title;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FetchNews_Content fetchNewsContent = new FetchNews_Content();
        fetchNewsContent.execute();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_news_show, container, false);

        news_title = view.findViewById(R.id.news_title_show);
        news_content = view.findViewById(R.id.news_content);

        news_title.setText(title);

        btn_back = view.findViewById(R.id.btn_news_back);
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, activity);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
    private class FetchNews_Content extends AsyncTask<Void, Void, Void> {
        String URL = "https://sports.news.naver.com" + News_URL;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect(URL).get();
                Elements mElementData = doc.select("div[class=news_end]");

                for (Element elements : mElementData) {
                    content = elements.text();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            news_content.setText(content);
        }
    }
}
