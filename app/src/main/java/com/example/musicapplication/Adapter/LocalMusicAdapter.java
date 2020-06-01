package com.example.musicapplication.Adapter;

import android.content.Context;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication.bean.LocalMusicBean;
import com.example.musicapplication.R;

import java.util.List;

public class LocalMusicAdapter extends RecyclerView.Adapter<LocalMusicAdapter.LocalMusicViewHolder> {
    private  Context context;
    private  List<LocalMusicBean> mDatas;
   private OnItemClickListener onItemClickListener= null;


    public LocalMusicAdapter(Context context, List<LocalMusicBean>mDatas) {
         this.context = context;
        this.mDatas = mDatas;
    }
    public interface OnItemClickListener{

        public void OnItemClick(View view,int position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public LocalMusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_local_music,parent,false);
        LocalMusicViewHolder holder = new LocalMusicViewHolder(view);
        return holder;
    }

    @Override

    public void onBindViewHolder(LocalMusicViewHolder holder, final int position) {

          LocalMusicBean musicBean  =  mDatas.get(position);
        Bitmap bitmap = musicBean.getbMap();
        if(bitmap !=null){
         holder.ablum_pic.setImageBitmap(bitmap);
        }
          holder.songTv.setText(musicBean.getSong());
          holder.singerTv.setText(musicBean.getSinger());
          holder.albumTv.setText(musicBean.getAlbum());
          holder.durationTv.setText(musicBean.getDuration());




          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
               onItemClickListener.OnItemClick(v,position);
              }
          });


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    class LocalMusicViewHolder extends RecyclerView.ViewHolder{
        private OnItemClickListener onItemClickListener;
         private  TextView songTv,singerTv,albumTv,durationTv;
         private ImageView ablum_pic;
        public LocalMusicViewHolder( View itemView) {
            super(itemView);
            ablum_pic = itemView.findViewById(R.id.item_local_music_pic);
            songTv = itemView.findViewById(R.id.item_local_music_song);
            singerTv = itemView.findViewById(R.id.item_local_music_singer);
            albumTv = itemView.findViewById(R.id.item_local_music_album);
            durationTv = itemView.findViewById(R.id.item_local_music_time);


        }
    }





}
