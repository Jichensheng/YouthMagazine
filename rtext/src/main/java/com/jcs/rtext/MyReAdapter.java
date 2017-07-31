package com.jcs.rtext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ice on 2016/8/29.
 */
public class MyReAdapter extends RecyclerView.Adapter<ViewHolder> {


    List<ItemBean> arrayList;
    Context context;

    private OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(Context context, OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
        this.context = context;
    }


    public MyReAdapter(List<ItemBean> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler, parent, false);

        return new ViewHolder(v);
    }


    public Bitmap resizeBitmap(int position) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), arrayList.get(position).getDrawable(), options);

        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(context.getResources(), arrayList.get(position).getDrawable(), options);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.imageView.setImageBitmap(resizeBitmap(position));
        holder.titleText.setText(arrayList.get(position).getTitle());


//        ViewCompat.setTransitionName(holder.imageView, String.valueOf(position) + "_image");


        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.imageView, position); // 2
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}


class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView titleText;


    public ViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        titleText = (TextView) itemView.findViewById(R.id.title);

    }


}

interface OnItemClickListener {
    void onItemClick(View view, int position);
}

