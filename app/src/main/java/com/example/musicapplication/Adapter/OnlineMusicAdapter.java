package com.example.musicapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication.bean.OnlineBillBoardBean;
import com.example.musicapplication.bean.OnlineMusicBean;
import com.example.musicapplication.R;

import java.util.ArrayList;


public class OnlineMusicAdapter extends RecyclerView.Adapter<OnlineMusicAdapter.OnlineMusicViewHolder> {
    private Context context;
   private ArrayList<OnlineMusicBean> mDatas;
    private ArrayList<OnlineBillBoardBean> bDatas;
    private LocalMusicAdapter.OnItemClickListener onItemClickListener = null;

    public OnlineMusicAdapter(Context context, ArrayList<OnlineBillBoardBean> bDatas){
    this.context = context;
    this.bDatas = bDatas;

}
    public interface OnItemClickListener{

        public void OnItemClick(View view,int position);
    }


    public void setOnItemClickListener(LocalMusicAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public OnlineMusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_online_music,parent,false);
        OnlineMusicViewHolder holder = new OnlineMusicViewHolder(view);
             
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineMusicViewHolder holder,  int position) {
        OnlineBillBoardBean billBoardBean = bDatas.get(position);

        mDatas = billBoardBean.getMusic();

        String Song1 =" 1."+ mDatas.get(0).getSong()+"- "+mDatas.get(0).getSinger();
        if (Song1.length()>18){
            Song1=Song1.substring(0,18)+"...";
        }
        String Song2= " 2."+mDatas.get(1).getSong()+"- "+mDatas.get(1).getSinger();
        if (Song2.length()>18){
            Song2=Song2.substring(0,18)+"...";
        }
        String Song3 =" 3."+ mDatas.get(2).getSong()+"- "+mDatas.get(2).getSinger();
        if (Song3.length()>18){
            Song3=Song3.substring(0,18)+"...";
        }
        holder.album.setImageBitmap(billBoardBean.getPicCover());
        holder.song1Tv.setText(Song1);
        holder.song2Tv.setText(Song2);
        holder.song3Tv.setText(Song3);


    }

    @Override
    public int getItemCount() {
        return bDatas.size();
    }

    class OnlineMusicViewHolder extends RecyclerView.ViewHolder{
            private TextView song1Tv,song2Tv,song3Tv;
            private ImageView  album;
        public OnlineMusicViewHolder(View itemView) {
            super(itemView);
            song1Tv = itemView.findViewById(R.id.online_music_first);
            song2Tv= itemView.findViewById(R.id.online_music_second);
            song3Tv = itemView.findViewById(R.id.online_music_third);
            album = itemView.findViewById(R.id.online_music_pic);


        }
    }
}
