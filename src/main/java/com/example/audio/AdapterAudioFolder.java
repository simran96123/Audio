package com.example.audio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterAudioFolder extends RecyclerView.Adapter<AdapterAudioFolder.ViewHolder>  implements View.OnClickListener {

    ArrayList<Model_Audio> al_audio;
    private long mLastClickTime = System.currentTimeMillis();
    private static final long CLICK_TIME_INTERVAL = 300;
    //
    Context context;

    private audiolistener audiolistener;


    public AdapterAudioFolder(Context context , ArrayList<Model_Audio> al_audio,AdapterAudioFolder.audiolistener audiolistener )
    {
        this.al_audio = al_audio;
        this.context = context;
        this.audiolistener = audiolistener;

    }


    @Override
    public void onClick(View view) {
        long now = System.currentTimeMillis();
        if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
            return;
        }
        mLastClickTime = now;


    }

    public static  class  ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView iv_audio;



        public ViewHolder(View v) {

            super(v);

            iv_audio = (ImageView) v.findViewById(R.id.iv_audio);



        }
    }

    @NonNull
    @Override
    public AdapterAudioFolder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audioitem, parent, false);

        ViewHolder viewHolder1 = new ViewHolder(view);

        return viewHolder1;

    }

    @Override
    public void onBindViewHolder( final ViewHolder Vholder,final int position) {

        Glide.with(context).load("file://" + al_audio.get(position)
                .getStr_thumb())
                .skipMemoryCache(false)
                .into(Vholder.iv_audio);

    }

    @Override
    public int getItemCount() {
        return al_audio.size();
    }
    public interface  audiolistener
    {
        void onaudioclick(String path);
    }

}
