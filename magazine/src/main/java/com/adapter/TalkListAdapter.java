package com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activity.MomentActivity;
import com.githang.navigatordemo.R;
import com.squareup.picasso.Picasso;
import com.util.CircleTransform;
import com.widget.CDView;
import com.widget.nine_grid.NineGridTestLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by liudong on 17/4/12.
 */

public class TalkListAdapter extends RecyclerView.Adapter {
    private Context context;
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

    public TalkListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CDListHolder(LayoutInflater.from(context).inflate(R.layout.holder_cd, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CDListHolder) {
            CDView cdView = ((CDListHolder) holder).cdView;
            Picasso.with(context)
                    .load(mUrls[new Random().nextInt(mUrls.length)])
                    .transform(new CircleTransform())
                    .error(R.drawable.banner_default_circle)
                    .placeholder(R.drawable.banner_default_circle)
                    .noFade()
                    .into(cdView);
        }


    }

    /**
     * 杂志封面holder
     */
    class CDListHolder extends RecyclerView.ViewHolder implements CDView.OnStopListener, CDView.OnPlayListener {
        CDView cdView;

        TextView tv_like;
        private LinearLayout ll_like;
        private ImageView imv_like;
        private boolean isLike = false;

        public CDListHolder(View itemView) {
            super(itemView);
            tv_like = (TextView) itemView.findViewById(R.id.tv_like);
            cdView = (CDView) itemView.findViewById(R.id.cd_music);
            ll_like = (LinearLayout) itemView.findViewById(R.id.ll_like);
            ll_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isLike) {
                        isLike = false;
                        imv_like.setImageResource(R.drawable.bubble_like);
                        tv_like.setText("" + (Integer.parseInt(tv_like.getText().toString()) - 1));
                    } else {
                        isLike = true;
                        imv_like.setImageResource(R.drawable.bubble_liked);
                        tv_like.setText("" + (Integer.parseInt(tv_like.getText().toString()) + 1));
                    }
                }
            });
        }

        @Override
        public void onPlay() {
        }

        @Override
        public void onStop() {

        }
    }

    @Override
    public int getItemCount() {
        return 30;
    }

}
