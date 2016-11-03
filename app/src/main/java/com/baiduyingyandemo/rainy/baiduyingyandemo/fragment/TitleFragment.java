package com.baiduyingyandemo.rainy.baiduyingyandemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baiduyingyandemo.rainy.baiduyingyandemo.R;

/**
 * Author: Rainy <br>
 * Description: BaiduYingyanDemo <br>
 * Since: 2016/11/2 0002 上午 10:20 <br>
 */

public class TitleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title,container,false);
        return view;
    }
}
