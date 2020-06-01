package com.example.musicapplication.Fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.musicapplication.Adapter.LocalMusicAdapter;
import com.example.musicapplication.bean.LocalMusicBean;
import com.example.musicapplication.R;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class LocalMusicFragment  extends Fragment implements View.OnClickListener{
     View view;//定义view用来设置fragment的layout
     TextView  music_songTv,music_singerTv;
     ImageView playIv_main,nextTv_main,picIv_music,playIv_play,nextIv_play;
     SeekBar seekBar;
    public RecyclerView recyclerView;//定义RecyclerView
    //定义实体类为对象的数据集合
    private ArrayList<LocalMusicBean> mDatas = new ArrayList<LocalMusicBean>();
    //自定义recyclerveiw的适配器
    private LocalMusicAdapter localMusicAdapter;
    int currentPlayPosition = -1;
    int currentPausePositionInSong =0;
    MediaPlayer mediaPlayer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取fragment的layout
        view = inflater.inflate(R.layout.localmusic_fragment ,container, false);
        picIv_music = getActivity().findViewById(R.id.music_bottom_pic);
        music_singerTv =getActivity().findViewById(R.id.music_bottom_singer);
        music_songTv = getActivity().findViewById(R.id.music_bottom_song);
        playIv_main = getActivity().findViewById(R.id.music_bottom_play);
        nextTv_main = getActivity().findViewById(R.id.music_bottom_next);
        seekBar = getActivity().findViewById(R.id.play_middle_seekBar);
        playIv_main.setOnClickListener(this);
        nextTv_main.setOnClickListener(this);

//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

        //对recycleview进行配置
        initRecyclerView();
        //模拟数据

        return view;
    }


    private void initRecyclerView() {
//    空白的一行
//        LocalMusicBean localMusicBean =  new LocalMusicBean();
//
//        mDatas.add(localMusicBean);


        //获取RecyclerView
        recyclerView= view.findViewById(R.id.local_rv);
        //创建adapter
        localMusicAdapter = new LocalMusicAdapter(getContext(),mDatas);
        //给RecyclerView设置adapter
        recyclerView.setAdapter(localMusicAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //加一条分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        loadLocalMusicData();
        mediaPlayer = new MediaPlayer();
        //设置每一项的点击时间
        setEventListener();

    }
 //设置每一项点击事件
    private void setEventListener() {

       localMusicAdapter.setOnItemClickListener(new LocalMusicAdapter.OnItemClickListener() {
           @Override
           public void OnItemClick(View view, int position) {
               currentPlayPosition = position;
               LocalMusicBean musicBean = mDatas.get(position);

               //设置主页面的歌手名称和歌曲名字
               picIv_music.setImageBitmap(musicBean.getbMap());
                music_singerTv.setText(musicBean.getSinger());
                music_songTv.setText(musicBean.getSong());

                stopMusic();
                //重置media
                mediaPlayer.reset();
                //设置新的播放路径
               try {
                   mediaPlayer.setDataSource(musicBean.getPath());
                   playMusic();
               } catch (IOException e) {
                   e.printStackTrace();
               }

           }



       });
    }

    private void loadLocalMusicData() {

        ContentResolver resolver = getActivity().getContentResolver();
        //对应sdcard下的多媒体文件s
        Uri uri =   MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //对应System下的多媒体文件
       // Uri uri =   MediaStore.Audio.Media. INTERNAL_CONTENT_URI;


        Cursor cursor = resolver.query( uri,null,null,null,null);

     while (cursor.moveToNext()){
         String song = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
         String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
         String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
         String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
         long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
         long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
         Long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
         SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
         String time = sdf.format(new Date(duration));
         Bitmap bMap = getArtAlbum(id);
      if(size>1024*800){
         LocalMusicBean bean = new LocalMusicBean(song,singer,album,time,path);
         bean.setbMap(bMap);
         mDatas.add(bean);
}
     }

        //提示适配器更新
        localMusicAdapter.notifyDataSetChanged();

    }

    public Bitmap getArtAlbum(long song_id){
        //获取的是每首歌曲的Id 返回值为专辑封面图片的Bitmap
        String str = "content://media/external/audio/media/" + song_id + "/albumart";
        Uri uri = Uri.parse(str);
        ParcelFileDescriptor pfd;
        try {

            pfd = this.getContext().getContentResolver().openFileDescriptor(uri, "r");
        } catch (FileNotFoundException e) {
            pfd =  null;
        }
        Bitmap bMap;
        if (pfd != null) {
            FileDescriptor fd = pfd.getFileDescriptor();
            bMap= BitmapFactory.decodeFileDescriptor(fd);

        }
        else {
            bMap =  BitmapFactory.decodeResource(getResources(),R.mipmap.default_cover);

        }
        return bMap;
    }






    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case  R.id.music_bottom_play:

//          if(currentPlayPosition ==-1){
//           //没有选中音乐
////              Toast.makeText(this,"请选择要播放的音乐",Toast.LENGTH_SHORT).show();
//               return;
//          }
          if(mediaPlayer.isPlaying()){

              //播放。需要暂停
              pauseMusic();

          }else{
              //没有播放。点击开始播放
              try {
                  playMusic();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }

             break;
         case R.id.music_bottom_next:


             next();
             break;
     }


    }

    private void playMusic() throws IOException {
        if (mediaPlayer!=null&&!mediaPlayer.isPlaying()) {


            if(currentPausePositionInSong == 0){
                mediaPlayer.prepare();
                mediaPlayer.start();

            }else {
                //暂停到播放
                mediaPlayer.seekTo(currentPausePositionInSong);
                mediaPlayer.start();


            }

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    //设置自动下一首
                    next();

                    //设置重复播放
//                    mediaPlayer.start();
//                    mediaPlayer.setLooping(true);


                }
            });
            playIv_main.setImageResource(R.mipmap.ic_play_bar_btn_pause);

        }

    }

    private void next() {
        if(currentPlayPosition  == mDatas.size()-1){
            currentPlayPosition = -1;
            return;
        }
        currentPlayPosition = currentPlayPosition + 1;
        LocalMusicBean nextBean = mDatas.get(currentPlayPosition);
        music_singerTv.setText(nextBean.getSinger());
        music_songTv.setText(nextBean.getSong());
        picIv_music.setImageBitmap(nextBean.getbMap());

        stopMusic();
        //重置media
        mediaPlayer.reset();
        //设置新的播放路径
        try {
            mediaPlayer.setDataSource(nextBean.getPath());
            playMusic();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //播放音乐的函数


    private void pauseMusic() {
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            currentPausePositionInSong = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            playIv_main.setImageResource(R.mipmap.ic_play_bar_btn_play);
        }
    }
    //停止音乐
    private void stopMusic() {
        if(mediaPlayer!=null){
            currentPausePositionInSong = 0;
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
            mediaPlayer.stop();
            playIv_main.setImageResource(R.mipmap.ic_play_bar_btn_play);

        }
    }
}
