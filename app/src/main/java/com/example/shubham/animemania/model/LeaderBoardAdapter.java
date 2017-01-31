package com.example.shubham.animemania.model;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shubham.animemania.R;

import java.util.List;

/**
 * Adapter for LeaderBoardActivity
 * <p>
 * Created by shubham on 27/1/17.
 */
public class LeaderBoardAdapter extends BaseAdapter {

    private List<LeaderBoardModel> mList;
    private LayoutInflater mInflater;

    public LeaderBoardAdapter(Context context, List<LeaderBoardModel> list) {
        mInflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_item, viewGroup, false);
        }

        TextView userName = (TextView) convertView.findViewById(R.id.leader_board_user_name_textView);
        TextView userTotalScore = (TextView) convertView.findViewById(R.id.leader_board_total_score_textView);

        String usrName = mList.get(i).getUserName();
        int userScore = mList.get(i).getTotalScore();
        if (!TextUtils.isEmpty(usrName))
            userName.setText(usrName);
        userTotalScore.setText(String.valueOf(userScore));


        return convertView;
    }
}
