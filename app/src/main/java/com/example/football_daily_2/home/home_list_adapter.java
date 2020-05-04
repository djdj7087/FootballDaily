package com.example.football_daily_2.home;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.football_daily_2.R;

import java.util.ArrayList;
import java.util.HashMap;

public class home_list_adapter extends RecyclerView.Adapter<home_list_adapter.ViewHolder> {
    private Activity activity = null;
    private ArrayList<home_listitem_activity> list;

    public home_list_adapter(Activity activity, ArrayList<home_listitem_activity> list){
        this.activity = activity;
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView team1_img, team2_img;
        private TextView team1_name, team2_name, match_time;

        public ViewHolder(View itemView) {
            super(itemView);

            team1_img = itemView.findViewById(R.id.team1_image);
            team2_img = itemView.findViewById(R.id.team2_image);
            team1_name = itemView.findViewById(R.id.team1_name);
            team2_name = itemView.findViewById(R.id.team2_name);
            match_time = itemView.findViewById(R.id.match_time);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String team1_img_str = list.get(position).getTeam1_img();
        String team2_img_str = list.get(position).getTeam2_img();
        holder.team1_name.setText(list.get(position).getTeam1_str());
        holder.team2_name.setText(list.get(position).getTeam2_str());
        if(list.get(position).getMatch_time().equals("")){
            holder.match_time.setText("경기종료");
        }
        else{
            holder.match_time.setText(list.get(position).getMatch_time());
        }

        Glide.with(activity).load(team1_img_str).override(200, 200).into(holder.team1_img);
        Glide.with(activity).load(team2_img_str).override(200, 200).into(holder.team2_img);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
