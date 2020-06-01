package com.example.musicapplication.Fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapplication.Adapter.LocalMusicAdapter;
import com.example.musicapplication.Adapter.OnlineMusicAdapter;
import com.example.musicapplication.bean.OnlineBillBoardBean;
import com.example.musicapplication.bean.OnlineMusicBean;
import com.example.musicapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



public class OnlineMusicFragment extends Fragment {
    private  View view;
    private RecyclerView recyclerView;
    private ArrayList<OnlineMusicBean> mDatas = new ArrayList<>();
    private ArrayList<OnlineBillBoardBean> bDatas= new ArrayList<>();
    private OnlineMusicAdapter onlineMusicAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.onlinemusic_fragment,container,false);
        new MyTask().execute();
//        setEventListener();
        return view;
    }

//    private void setEventListener() {
//        onlineMusicAdapter.setOnItemClickListener(new LocalMusicAdapter.OnItemClickListener() {
//            @Override
//            public void OnItemClick(View view, int position) {
//
//            }
//        });
//    }


    class MyTask extends AsyncTask<Void, String, JSONObject> {


        @Override
        protected JSONObject doInBackground(Void... voids) {
            System.out.println("你好");


            JSONObject jsonObject1 = null;
            try {
                for (int i = 1; i < 26; i++) {
                    if (i == 3) {
                        i = 11;
                    }
                    if (i == 12) {
                        i = 21;
                    }

                    URL url = new URL("http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.billboard.billList&format=json&type=" + i + "&offset=0&size=10");

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    Log.i("HttpURLConnection.GET", "开始连接");
                    connection.connect();
                    if (connection.getResponseCode() == 200) {
                        Log.i("HttpURLConnection.GET", "连接成功");
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder builder = new StringBuilder();
                        String tmp = "";
                        while ((tmp = reader.readLine()) != null) {
                            builder.append(tmp);
                        }
                        String jsonResponse = "";
                        jsonResponse = builder.toString();

                        jsonObject1 = new JSONObject(jsonResponse);

                        //song_list
                        JSONArray jsonArray = jsonObject1.getJSONArray("song_list");
                        mDatas= new ArrayList<OnlineMusicBean>(); //不定义，界面只显示前三首歌
                        for (int j = 0; j < jsonArray.length(); j++) {

                            JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                            String song = jsonObject.getString("title");
                            System.out.println(song);
                            String singer = jsonObject.getString("author");
                            long songId = jsonObject.getLong("song_id");
                            String album = jsonObject.getString("album_title");
                            long albumId = jsonObject.getLong("album_id");

                            OnlineMusicBean bean = new OnlineMusicBean(song, singer, songId, album, albumId);
                            mDatas.add(bean);

                        }
                        //billboard
                        JSONObject data_billboard = jsonObject1.getJSONObject("billboard");
                        String name = data_billboard.getString("name");
                        System.out.println(name);
                        String comment = data_billboard.getString("comment");
                        String update = data_billboard.getString("update_date");
                        int songNum = data_billboard.getInt("billboard_songnum");
                        Bitmap picCover = getBitmap (data_billboard.getString("pic_s192"));
                        OnlineBillBoardBean billBoardBean = new OnlineBillBoardBean(name, comment, update, songNum, picCover);
                        billBoardBean.setMusic(mDatas);
                        bDatas.add(billBoardBean);


                    } else {
                        Log.e("HttpURLConnection.GET", "连接失败");
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return jsonObject1;


        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            recyclerView= view.findViewById(R.id.online_rv);
            //创建adapter
            onlineMusicAdapter = new OnlineMusicAdapter(getContext(),bDatas);
            //给RecyclerView设置adapter
            recyclerView.setAdapter(onlineMusicAdapter);
            //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
            //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            super.onPostExecute(jsonObject);
        }

        private Bitmap getBitmap(String pic_s192) {
            try {
                URL url = new URL(pic_s192);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                Bitmap bitmap= BitmapFactory.decodeStream(connection.getInputStream());
                return bitmap;
            }
            catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }
        }





}
