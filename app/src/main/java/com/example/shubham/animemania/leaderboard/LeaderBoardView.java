package com.example.shubham.animemania.leaderboard;

import com.example.shubham.animemania.model.LeaderBoardModel;
import com.example.shubham.animemania.utility.CommonMethods;

import java.util.List;

/**
 * LeaderBoard Interface
 * Created by shubham on 27/1/17.
 */
public interface LeaderBoardView extends CommonMethods {
    void  setData(List<LeaderBoardModel> list);
}
