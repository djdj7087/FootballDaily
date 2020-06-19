package com.example.football_daily.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.football_daily.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class home_activity extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<home_listitem_activity> list = new ArrayList<>();

    private void refresh() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FetchMatch fetchMatch = new FetchMatch();
        fetchMatch.execute();

        refresh();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

        TextView textView = view.findViewById(R.id.today_date);
        recyclerView = view.findViewById(R.id.recycler_home_list);

        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String day_str = null;
        switch (day) {
            case 2:
                day_str = "(월)";
                break;
            case 3:
                day_str = "(화)";
                break;
            case 4:
                day_str = "(수)";
                break;
            case 5:
                day_str = "(목)";
                break;
            case 6:
                day_str = "(금)";
                break;
            case 7:
                day_str = "(토)";
                break;
            case 1:
                day_str = "(일)";
                break;
        }
        textView.setText("Today: " + year + " . " + month + " . " + date + " " + day_str);

        return view;
    }

    private class FetchMatch extends AsyncTask<Void, Void, Void> {
        String URL_WorldFootball_Naver = "https://sports.news.naver.com/wfootball/index.nhn";
        String URL_KoreaFootball_Naver = "https://sports.news.naver.com/kfootball/index.nhn";

        @Override
        protected Void doInBackground(Void... voids) {
            home_listitem_activity listItem;
            list.clear();
            try {
                Document doc_world = Jsoup.connect(URL_WorldFootball_Naver).get();
                Document doc_domestic = Jsoup.connect(URL_KoreaFootball_Naver).get();
                Elements mElementData_domestic = doc_domestic.select("ul").select("li[class=hmb_list_items ]");
                Elements mElementData_world = doc_world.select("ul").select("li[class=hmb_list_items ]");

                for (Element elements : mElementData_domestic) {
                    String team1_name = elements.select("div[class=vs_list vs_list1]").select("div[class=inner]").select("span[class=name]")
                            .text();
                    String team1_img = elements.select("div[class=vs_list vs_list1]").select("div[class=inner]").select("img")
                            .attr("src");
                    String team2_name = elements.select("div[class=vs_list vs_list2]").select("div[class=inner]").select("span[class=name]")
                            .text();
                    String team2_img = elements.select("div[class=vs_list vs_list2]").select("div[class=inner]").select("img")
                            .attr("src");
                    String match_time = elements.select("div[class=state]").select("div[class=inner]").select("em[class=time]")
                            .text();
                    listItem = new home_listitem_activity(team1_name, team1_img, match_time, team2_name, team2_img);
                    list.add(listItem);
                }
                for (Element elements : mElementData_world) {
                    String team1_name = elements.select("div[class=vs_list vs_list1]").select("div[class=inner]").select("span[class=name]")
                            .text();
                    String team1_img = elements.select("div[class=vs_list vs_list1]").select("div[class=inner]").select("img")
                            .attr("src");
                    String team2_name = elements.select("div[class=vs_list vs_list2]").select("div[class=inner]").select("span[class=name]")
                            .text();
                    String team2_img = elements.select("div[class=vs_list vs_list2]").select("div[class=inner]").select("img")
                            .attr("src");
                    String match_time = elements.select("div[class=state]").select("div[class=inner]").select("em[class=time]")
                            .text();
                    listItem = new home_listitem_activity(team1_name, team1_img, match_time, team2_name, team2_img);
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

            home_list_adapter adapter = new home_list_adapter(getActivity(), list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }
}