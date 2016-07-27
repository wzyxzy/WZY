package com.wzy.wzy;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.wzy.wzy.adapters.TVAdapter;
import com.wzy.wzy.model.TVModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class VideoTV extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {
    private VideoView tv_vv;
    private ProgressBar tv_probar;
    private TextView tv_download_rate;
    private TextView tv_load_rate;
    private ListView tv_lv2;
    private LinearLayout tv_drawer;
    private Button tv_p1;
    private Button tv_p2;
    private Button tv_p3;
    private Button tv_p4;
    private Button tv_p5;
    private LinearLayout tv_drawer_right;
    private String url;
    private DrawerLayout tv_dr_ly;
    private TVAdapter adapter;
    private List<TVModel.TvListEntity> tvListEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_tv);
        initView();
        initData();
        vplay();
    }

    private void vplay() {
        tv_vv.setVideoURI(Uri.parse(url));
        tv_vv.requestFocus();
        bufferVideo();
        tv_vv.start();
    }


    private void bufferVideo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tv_vv.setOnInfoListener(this);
        }
        tv_vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                tv_probar.setVisibility(View.GONE);
                tv_download_rate.setVisibility(View.GONE);
                tv_load_rate.setVisibility(View.GONE);
            }
        });
    }

    private void initData() {
        Gson gson = new Gson();
        TVModel tvModel = gson.fromJson(loadJSONFromAsset(), TVModel.class);
        tvListEntities = tvModel.getTv_list();
        adapter.updateRes(tvListEntities);
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("tv.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


    private void initView() {
        tv_vv = (VideoView) findViewById(R.id.tv_vv);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        tv_vv.setMediaController(new MediaController(this));
        tv_dr_ly = (DrawerLayout) findViewById(R.id.tv_dr_ly);
        tv_probar = (ProgressBar) findViewById(R.id.tv_probar);
        tv_download_rate = (TextView) findViewById(R.id.tv_download_rate);
        tv_load_rate = (TextView) findViewById(R.id.tv_load_rate);
        tv_lv2 = (ListView) findViewById(R.id.tv_lv2);
        tvListEntities = new ArrayList<>();
        adapter = new TVAdapter(null, this, R.layout.item_tv);
        tv_lv2.setAdapter(adapter);
        tv_lv2.setOnItemClickListener(this);
        tv_drawer = (LinearLayout) findViewById(R.id.tv_drawer);
        tv_p1 = (Button) findViewById(R.id.tv_p1);
        tv_p2 = (Button) findViewById(R.id.tv_p2);
        tv_p3 = (Button) findViewById(R.id.tv_p3);
        tv_p4 = (Button) findViewById(R.id.tv_p4);
        tv_p5 = (Button) findViewById(R.id.tv_p5);
        tv_drawer_right = (LinearLayout) findViewById(R.id.tv_drawer_right);

        tv_p1.setOnClickListener(this);
        tv_p2.setOnClickListener(this);
        tv_p3.setOnClickListener(this);
        tv_p4.setOnClickListener(this);
        tv_p5.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_p1:
                changeurl("1");
                break;
            case R.id.tv_p2:
                changeurl("2");
                break;
            case R.id.tv_p3:
                changeurl("3");
                break;
            case R.id.tv_p4:
                changeurl("4");
                break;
            case R.id.tv_p5:
                changeurl("5");
                break;
        }
    }

    private void changeurl(String s) {
        StringBuilder sb = new StringBuilder(url);
        sb.setCharAt(sb.length() - 6, s.charAt(0));
        url = sb.toString();
        vplay();
        tv_dr_ly.closeDrawer(tv_drawer_right);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        url = tvListEntities.get(position).getUrl();
        vplay();
        tv_dr_ly.closeDrawer(tv_drawer);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        tv_load_rate.setText(percent + "%");
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (tv_vv.isPlaying()) {
                    tv_vv.pause();
                }
                tv_probar.setVisibility(View.VISIBLE);
                tv_download_rate.setText("");
                tv_load_rate.setText("");
                tv_download_rate.setVisibility(View.VISIBLE);
                tv_load_rate.setVisibility(View.VISIBLE);
                tv_download_rate.setText("" + extra + "kb/s" + " ");
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                tv_vv.start();
                tv_probar.setVisibility(View.GONE);
                tv_download_rate.setVisibility(View.GONE);
                tv_load_rate.setVisibility(View.GONE);
                break;

        }

        return true;
    }
}
