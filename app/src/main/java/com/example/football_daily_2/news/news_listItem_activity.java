package com.example.football_daily_2.news;

public class news_listItem_activity {
        private String news_title, news_url;

        public news_listItem_activity(String news_title, String news_url){
            this.news_title = news_title;
            this.news_url = news_url;
        }

    public String getNews_title() {
        return news_title;
    }

    public String getNews_url() {
        return news_url;
    }
}
