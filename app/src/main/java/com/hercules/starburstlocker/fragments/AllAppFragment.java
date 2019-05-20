package com.hercules.starburstlocker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hercules.starburstlocker.AppInfo;
import com.hercules.starburstlocker.ApplicationListAdapter;
import com.hercules.starburstlocker.GetListOfAppsAsyncTask;
import com.hercules.starburstlocker.R;

import java.util.ArrayList;
import java.util.List;


public class AllAppFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    static String requiredAppsType;

    public static AllAppFragment newInstance(String requiredApps) {
        requiredAppsType = requiredApps;
        AllAppFragment allAppFragment = new AllAppFragment();
        return (allAppFragment);
    }


    public AllAppFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    List<AppInfo> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_apps, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        // improves performance if the RecyclerView layout size does not change
        mRecyclerView.setHasFixedSize(true);

        list = new ArrayList<>();
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ApplicationListAdapter(list , getActivity(), requiredAppsType);
        mRecyclerView.setAdapter(mAdapter);
        //call async list class
        GetListOfAppsAsyncTask task = new GetListOfAppsAsyncTask(this);
        task.execute(requiredAppsType);

        return view;

    }

    public void updateData(List<AppInfo> list){
        this.list.addAll(list);
        mAdapter.notifyDataSetChanged();
    }
}
