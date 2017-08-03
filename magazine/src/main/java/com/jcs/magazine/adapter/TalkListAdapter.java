package com.jcs.magazine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcs.magazine.R;
import com.jcs.magazine.mock.MockConfig;
import com.jcs.magazine.util.CircleTransform;
import com.jcs.magazine.widget.CDView;
import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 * Created by liudong on 17/4/12.
 */

public class TalkListAdapter extends RecyclerView.Adapter {
    private Context context;

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
                    .load(MockConfig.URLS[new Random().nextInt(MockConfig.URLS.length)])
                    .transform(new CircleTransform())
                    .error(R.drawable.banner_default_circle)
                    .placeholder(R.drawable.banner_default_circle)
                    .noFade()
                    .into(cdView);


        }


    }

    class CDListHolder extends RecyclerView.ViewHolder implements CDView.OnStopListener, CDView.OnPlayListener {
        CDView cdView;

        TextView tv_like;
        private LinearLayout ll_like;
        private ImageView imv_like;
        private boolean isLike = false;

        public CDListHolder(View itemView) {
            super(itemView);
            tv_like = (TextView) itemView.findViewById(R.id.tv_praise);
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
