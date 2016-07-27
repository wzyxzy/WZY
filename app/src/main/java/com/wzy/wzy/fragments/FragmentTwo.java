package com.wzy.wzy.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.wzy.wzy.R;
import com.wzy.wzy.VideoTV;
import com.wzy.wzy.adapters.TVAdapter;
import com.wzy.wzy.model.TVModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment implements AdapterView.OnItemClickListener{

    private String url;
    private ListView tv_lv;
    private TVAdapter adapter;
    private List<TVModel.TvListEntity> tvListEntities;
    private View layout;

    public FragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_fragment_two, container, false);
        initView(layout);
        initData();
        return layout;
    }

    private void initData() {
        Gson gson=new Gson();
        TVModel tvModel=gson.fromJson(loadJSONFromAsset(),TVModel.class);
        tvListEntities=tvModel.getTv_list();
        adapter.updateRes(tvListEntities);
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getResources().getAssets().open("tv.json");
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

    private void initView(View layout) {
        tv_lv = (ListView)layout.findViewById(R.id.tv_lv);
        tvListEntities=new ArrayList<>();
        adapter=new TVAdapter(null,getContext(),R.layout.item_tv);
        tv_lv.setAdapter(adapter);
        tv_lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        url=tvListEntities.get(position).getUrl();
        intentgo();
    }

    private void intentgo() {
        Intent intent = new Intent();
        intent.setClass(this.getContext(), VideoTV.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
