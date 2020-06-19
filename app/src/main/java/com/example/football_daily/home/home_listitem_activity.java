package com.example.football_daily.home;

public class home_listitem_activity {
    private String team1_str = "";
    private String team1_img = "";
    private String match_time = "";
    private String team2_str = "";
    private String team2_img = "";

    public home_listitem_activity(String team1_str, String team1_img, String match_time,
                                        String team2_str, String team2_img){
        this.team1_str = team1_str;
        this.team1_img = team1_img;
        this.match_time = match_time;
        this.team2_str = team2_str;
        this.team2_img = team2_img;
    }

    public String getTeam1_str() {
        return team1_str;
    }

    public String getTeam1_img() {
        return team1_img;
    }

    public String getMatch_time() {
        return match_time;
    }

    public String getTeam2_str() {
        return team2_str;
    }

    public String getTeam2_img() {
        return team2_img;
    }
}
