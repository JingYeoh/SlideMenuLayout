package com.jkb.slidemenulayout.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.slidemenulayout.R;
import com.jkb.slidemenulayout.adapter.FriendAdapter;
import com.jkb.slidemenulayout.adapter.TabTwoAdapter;

/**
 * TabTwo
 * Created by yangjing on 17-6-7.
 */

public class TabTwoFragment extends Fragment {

    public static TabTwoFragment newInstance() {
        Bundle args = new Bundle();
        TabTwoFragment fragment = new TabTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //ui
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frg_tab_two, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.ff_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        TabTwoAdapter tabTwoAdapter = new TabTwoAdapter(mContext);
        recyclerView.setAdapter(tabTwoAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
