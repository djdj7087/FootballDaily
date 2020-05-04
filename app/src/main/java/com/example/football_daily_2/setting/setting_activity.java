package com.example.football_daily_2.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.football_daily_2.R;
import com.example.football_daily_2.community.community_write_activity;
import com.example.football_daily_2.login_activity;

public class setting_activity extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, container, false);

        Button btn_changepw = view.findViewById(R.id.setting_change_pw);
        Button btn_logout = view.findViewById(R.id.setting_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), login_activity.class);
                startActivity(intent);
            }
        });
        btn_changepw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setting_password_change_activity activity = new setting_password_change_activity();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, activity);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
