package com.livetv.android.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.databinding.library.baseAdapters.BR;
import com.livetv.android.LiveTvApplication;
import com.livetv.android.R;
import com.livetv.android.listeners.LiveTVCategorySelectedListener;
import com.livetv.android.model.LiveTVCategory;

import java.util.List;

public class LiveTVCategoryRecyclerAdapter extends RecyclerView.Adapter<LiveTVRecyclerViewHolder> implements View.OnFocusChangeListener{

    private final LiveTVCategorySelectedListener channelSelectedListener;
    private List<LiveTVCategory> categories;
    private Context mContext;

    public LiveTVCategoryRecyclerAdapter(Context context, List<LiveTVCategory> objects, LiveTVCategorySelectedListener listener) {//}, int rowPosition, MovieSelectedListener movieSelectedListener, boolean fromGrid) {
//    public MoviesRecyclerAdapter(Context context, List<Movie> objects, int rowPosition, MovieSelectedListener movieSelectedListener, MovieAcceptedListener movieAcceptedListener) {
        mContext = context;
        categories = objects;
        channelSelectedListener = listener;
    }

    @Override
    public LiveTVRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View convertView;
        convertView = inflater.inflate(R.layout.live_tv_category_item, parent, false);
        return new LiveTVRecyclerViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(LiveTVRecyclerViewHolder holder, int position) {
        LiveTVCategory channel = categories.get(position);

        holder.getViewBinding().setVariable(BR.liveTVCategoryItem, channel); // bind item with Movie model
        holder.getViewBinding().getRoot().setTag(position);
        holder.getViewBinding().getRoot().findViewById(R.id.channel_name).setTag(position);
        holder.getViewBinding().setVariable(BR.liveTVCategoryAdapter, this); //bind this adapter for click events

        holder.getViewBinding().getRoot().findViewById(R.id.channel_name).setOnFocusChangeListener(this);

        holder.getViewBinding().executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

//    @Override
    public void onClickItem(View view) {
        ;//Log.d("liveTV","click on category " + view.getTag());
        channelSelectedListener.onLiveTVCategorySelected(categories.get((Integer) view.getTag()));
//        liveProgramSelectedListener.onLiveProgramSelected(livePrograms.get((Integer) view.getTag()));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        ;//Log.d("liveTV","focus changedd" + v.getTag());
        if(hasFocus) {
//            v.findViewById(R.id.channel_name).setSelected(true);
            v.setSelected(true);
        }
        else {
//            v.findViewById(R.id.channel_name).setSelected(false);
            v.setSelected(false);
        }
    }
}
