package com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activity.MomentActivity;
import com.githang.navigatordemo.R;
import com.widget.nine_grid.NineGridTestLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudong on 17/4/12.
 */

public class MomentListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> urls;
    private String[] mUrls = new String[]{
            "http://img4.imgtn.bdimg.com/it/u=3445377427,2645691367&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2644422079,4250545639&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1444023808,3753293381&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=882039601,2636712663&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119861953,350096499&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1717647885,4193212272&fm=21&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3251359643,4211266111&fm=21&gp=0.jpg",
            "http://preview.quanjing.com/ojo001/pe0060887.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2024625579,507531332&fm=21&gp=0.jpg"};
    public MomentListAdapter(Context context) {
        this.context = context;
        urls=new ArrayList<>();
        for (String mUrl : mUrls) {
            urls.add(mUrl);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_moments, parent, false);
        return new MomentListHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MomentListHolder) {
            ((MomentListHolder) holder).layout.setUrlList(urls);
        }
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    class MomentListHolder extends RecyclerView.ViewHolder {
        NineGridTestLayout layout;
        TextView tv_content;
        public MomentListHolder(View itemView) {
            super(itemView);
            layout= (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);
            tv_content= (TextView) itemView.findViewById(R.id.tv_content);
            tv_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,MomentActivity.class);
                    intent.putExtra("urls", (Serializable) urls);
                    intent.putExtra("nickname","Jcs");
                    context.startActivity(intent);
                }
            });
        }
    }
}
