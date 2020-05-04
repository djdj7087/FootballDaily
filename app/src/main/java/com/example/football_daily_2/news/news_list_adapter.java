package com.example.football_daily_2.news;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.football_daily_2.R;
import com.example.football_daily_2.community.community_content_activity;
import com.example.football_daily_2.home.home_list_adapter;
import com.example.football_daily_2.home.home_listitem_activity;

import java.util.ArrayList;

public class news_list_adapter extends RecyclerView.Adapter<news_list_adapter.ViewHolder> {
    private Activity activity = null;
    private ArrayList<news_listItem_activity> list;

    public news_list_adapter(Activity activity, ArrayList<news_listItem_activity> list){
        this.activity = activity;
        this.list = list;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView news_title;

        private int itemPosition = -1;
        FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();

        public ViewHolder(View itemView) {
            super(itemView);

            news_title = itemView.findViewById(R.id.news_title);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    itemPosition = getAdapterPosition();
                    news_show_activity activity = new news_show_activity(list.get(itemPosition).getNews_url(), list.get(itemPosition).getNews_title());
                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, activity);
                    fragmentTransaction.commit();
                }
            });
        }
    }
    @NonNull
    @Override
    public news_list_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_news_list_item, parent, false);
        news_list_adapter.ViewHolder viewHolder = new news_list_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull news_list_adapter.ViewHolder holder, int position) {
        holder.news_title.setText(list.get(position).getNews_title());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
