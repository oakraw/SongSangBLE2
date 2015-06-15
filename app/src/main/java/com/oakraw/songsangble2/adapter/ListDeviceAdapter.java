package com.oakraw.songsangble2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.oakraw.songsangble2.R;

import java.util.ArrayList;


/**
 * Created by Rathanan on 3/27/2015.
 */
public class ListDeviceAdapter extends RecyclerView.Adapter<ListDeviceAdapter.ViewHolder> {
    private ArrayList<String> mDataset;
    private Context mContext;
    private OnItemClickListener mItemClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton mIcon;
        public TextView mTitle;

        public ViewHolder(View v) {
            super(v);
            mIcon = (ImageButton) v.findViewById(R.id.icon);
            mTitle = (TextView) v.findViewById(R.id.title);
        }
    }

    public ListDeviceAdapter(Context context, ArrayList<String> myDataset) {
        mDataset = myDataset;
        mContext = context;
    }

    @Override
    public ListDeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.door_grid, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTitle.setText(mDataset.get(position));
        holder.mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(v, position);
            }
        });

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
