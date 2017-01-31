package com.example.shubham.animemania.leaderboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.shubham.animemania.R;
import com.example.shubham.animemania.model.LeaderBoardAdapter;
import com.example.shubham.animemania.model.LeaderBoardModel;
import com.example.shubham.animemania.presenter.TriviaPresenter;
import com.example.shubham.animemania.utility.AppContext;

import java.util.List;

/**
 * LeaderBoard Activity
 * For Showing LeaderBoard to user
 */

public class LeaderBoardActivity extends AppCompatActivity implements LeaderBoardView {

    private ListView mLeaderBoardList;
    private TriviaPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        initialiseWidgets();
        addListeners();
        configureMVP();
    }

    @Override
    public void initialiseWidgets() {
        mLeaderBoardList = (ListView) findViewById(R.id.leader_board_list);
    }

    @Override
    public void addListeners() {
        //No Listeners
    }

    @Override
    public void configureMVP() {
        mPresenter = TriviaPresenter.getInstance();
        mPresenter.setView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.leaderBoardPageDisplayed();
    }

    @Override
    public void setData(List<LeaderBoardModel> list) {
        //setting Data in List
        LeaderBoardAdapter leaderBoardAdapter = new LeaderBoardAdapter(AppContext.getContext(), list);
        mLeaderBoardList.setAdapter(leaderBoardAdapter);

    }

}