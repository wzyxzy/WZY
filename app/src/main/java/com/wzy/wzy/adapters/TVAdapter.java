package com.wzy.wzy.adapters;

import android.content.Context;
import android.widget.TextView;

import com.wzy.wzy.R;
import com.wzy.wzy.model.TVModel;

import java.util.List;

/**
 * Created by zy on 2016/4/9.
 */
public class TVAdapter extends TeachBaseAdapter<TVModel.TvListEntity> {
    public TVAdapter(List<TVModel.TvListEntity> data, Context context, int layoutRes) {
        super(data, context, layoutRes);
    }

    @Override
    public void bindData(ViewHolder holder, TVModel.TvListEntity tvListEntity) {
        TextView title = (TextView)holder.getView(R.id.tv_tv);
        title.setText(tvListEntity.getTitle());
    }
}
