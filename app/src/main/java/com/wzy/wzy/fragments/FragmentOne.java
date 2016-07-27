package com.wzy.wzy.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.wzy.wzy.ActivityInfo;
import com.wzy.wzy.R;
import com.wzy.wzy.adapters.BBCAdapter;
import com.wzy.wzy.model.BBCNews;
import com.wzy.wzy.teachlibrary.httpgson.GsonRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {

    private View layout;
    private ListView lv_bbc;
    private RequestQueue requestQueue;
    private static final String APIURL = "http://walter-producer-cdn.api.bbci.co.uk/content/cps/news/front_page";
    private BBCAdapter adapter;
    private List<BBCNews.RelationsEntity> relationsEntities;

    public FragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_fragment_one, container, false);
        initView(layout);
        initData();
        return layout;
    }

    private void initData() {
        requestQueue = Volley.newRequestQueue(getContext());
        GsonRequest<BBCNews> gsonRequest = new GsonRequest<BBCNews>(Request.Method.GET, APIURL, BBCNews.class, null, new Response.Listener<BBCNews>() {
            @Override
            public void onResponse(BBCNews response) {
                relationsEntities = response.getRelations();
                adapter.updateRes(relationsEntities);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(gsonRequest);
    }

    private void initView(View layout) {
        lv_bbc = (ListView) layout.findViewById(R.id.lv_bbc);
        relationsEntities = new ArrayList<>();
        adapter = new BBCAdapter(null, getContext(), R.layout.item_bbc);
        lv_bbc.setAdapter(adapter);
        //滑动监听,为我们的listView设置滑动监听,使用universal-image-loader中提供的默认PauseOnScrollListener
        //实例化的时候需要三个参数,1 ImageLoader实例,2当滑动的时候是否暂停,3.当快速滑动的时候是都暂停
//        lv_bbc.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(),false,true));
        lv_bbc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = relationsEntities.get(position).getContent().getShareUrl();
                if (url != null) {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), ActivityInfo.class);
                    intent.putExtra("infoUrl", url);
                    startActivity(intent);
                }
            }
        });
    }
}
