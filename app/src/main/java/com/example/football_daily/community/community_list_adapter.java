package com.example.football_daily.community;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.football_daily.R;

import java.util.ArrayList;

public class community_list_adapter extends RecyclerView.Adapter<community_list_adapter.ViewHolder> {
    private Activity activity;
    private ArrayList<community_list_item> list;

    public community_list_adapter(Activity activity, ArrayList<community_list_item> list){
        this.activity = activity;
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView number;
        TextView name;
        TextView title;
        TextView time;
        TextView content;

        private int itemPosition = -1;

        FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();

        public ViewHolder(final View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.content_name);
            number = itemView.findViewById(R.id.content_number);
            title = itemView.findViewById(R.id.content_title);
            time = itemView.findViewById(R.id.content_time);
            content = itemView.findViewById(R.id.content_text);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    itemPosition = getAdapterPosition();
                    String getName = list.get(itemPosition).getName();
                    String getTitle = list.get(itemPosition).getTitle();
                    String getTime = list.get(itemPosition).getTime();
                    String getContent = list.get(itemPosition).getContent();
                    Intent intent = new Intent(v.getContext(), community_content_activity.class);
                    intent.putExtra("Name", getName);
                    intent.putExtra("Title", getTitle);
                    intent.putExtra("Time", getTime);
                    intent.putExtra("Content", getContent);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_community_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        community_list_item data = list.get(position);
        holder.name.setText(data.getName());
        holder.number.setText(data.getNumber());
        holder.title.setText(data.getTitle());
        holder.time.setText(data.getTime());
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
