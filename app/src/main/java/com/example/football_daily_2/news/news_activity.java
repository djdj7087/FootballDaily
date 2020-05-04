package com.example.football_daily_2.news;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.football_daily_2.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class news_activity extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<news_listItem_activity> list = new ArrayList<>();

    private void refresh() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FetchNews fetchNews = new FetchNews();
        fetchNews.execute();

        refresh();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_news, container, false);

        recyclerView = view.findViewById(R.id.recycler_news_list);

        return view;
    }

    private class FetchNews extends AsyncTask<Void, Void, Void> {
        String URL_WorldFootball_Naver = "https://sports.news.naver.com/wfootball/index.nhn";
        String URL_KoreaFootball_Naver = "https://sports.news.naver.com/kfootball/index.nhn";

        @Override
        protected Void doInBackground(Void... voids) {
            news_listItem_activity listItem;
            list.clear();

            try {
                Document doc_world = Jsoup.connect(URL_WorldFootball_Naver).get();
                Document doc_domestic = Jsoup.connect(URL_KoreaFootball_Naver).get();
                Elements mElementData_domestic = doc_domestic.select("ul[class=home_news_list]").select("li[class=\"\"]");
                Elements mElementData_domestic_division = doc_domestic.select("ul[class=home_news_list division]").select("li[class=division]");
                Elements mElementData_world = doc_world.select("ul[class=home_news_list]").select("li[class=\"\"]");
                Elements mElementData_world_division = doc_world.select("ul[class=home_news_list division]").select("li[class=division]");

                for (Element elements : mElementData_domestic) {
                    String news_Title = elements.select("a")
                            .attr("title");
                    String news_url = elements.select("a").attr("href");

                    listItem = new news_listItem_activity(news_Title, news_url);
                    list.add(listItem);
                }
                for (Element elements : mElementData_domestic_division) {
                    String news_Title = elements.select("a").attr("title");
                    String news_url = elements.select("a").attr("href");

                    listItem = new news_listItem_activity(news_Title, news_url);
                    list.add(listItem);
                }
                for (Element elements : mElementData_world) {
                    String news_Title = elements.select("a").attr("title");
                    String news_url = elements.select("a").attr("href");

                    listItem = new news_listItem_activity(news_Title, news_url);
                    list.add(listItem);
                }
                for (Element elements : mElementData_world_division) {
                    String news_Title = elements.select("a").attr("title");
                    String news_url = elements.select("a").attr("href");

                    listItem = new news_listItem_activity(news_Title, news_url);
                    list.add(listItem);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            news_list_adapter adapter = new news_list_adapter(getActivity(), list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }
}
