package com.oakraw.songsangble2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.oakraw.songsangble2.R;
import com.oakraw.songsangble2.adapter.ListDeviceAdapter;

import java.util.ArrayList;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by Rawipol on 6/13/15 AD.
 */
public class CallFriendFragment extends Fragment {
    private GridLayoutManager gridManager;
    private ListDeviceAdapter adapter;
    private ArrayList<String> roomList;
    private boolean isScan = false;
    private FloatingActionButton scanBtn;
    private SmoothProgressBar progrssBar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_call_friend, container, false);

        roomList = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler);
        scanBtn = (FloatingActionButton) rootView.findViewById(R.id.fab);
        progrssBar = (SmoothProgressBar) rootView.findViewById(R.id.progressBar);

        gridManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ListDeviceAdapter(getActivity(), roomList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ListDeviceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "item " + position, Toast.LENGTH_SHORT).show();

            }
        });


        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isScan){
                    stopScanSongSang();
                }else{
                    startScanSongSang();
                }

                isScan = !isScan;
            }
        });

        startScanSongSang();


        return rootView;
    }

    Handler handler = new Handler();
    Runnable simulateScanning = new Runnable() {
        @Override
        public void run() {
            stopScanSongSang();
            adapter.notifyDataSetChanged();
        }
    };

    //TODO : scan for SongSang and update to list
    private void startScanSongSang(){
        scanBtn.setImageResource(R.mipmap.ic_close_black_24dp);
        progrssBar.setVisibility(View.VISIBLE);
        roomList.clear();

        roomList.add("ห้อง 001");
        roomList.add("ห้อง 002");
        roomList.add("ห้อง 003");
        roomList.add("ห้อง 004");
        roomList.add("ห้อง 005");

        //simulate scanning for device
        handler.postDelayed(simulateScanning, 3000);

    }

    private void stopScanSongSang(){
        scanBtn.setImageResource(R.mipmap.ic_search_black_24dp);
        progrssBar.setVisibility(View.GONE);
        handler.removeCallbacks(simulateScanning);
    }
}
