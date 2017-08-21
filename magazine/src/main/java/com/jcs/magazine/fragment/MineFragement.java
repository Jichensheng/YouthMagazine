package com.jcs.magazine.fragment;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.jcs.magazine.R;
import com.jcs.magazine.base.BaseFragment;
import com.jcs.magazine.util.DialogHelper;
import com.jcs.magazine.util.LocalFileManager;
import com.jcs.magazine.widget.Bitmaptest;
import com.jcs.magazine.widget.CircleImageView;
import com.jcs.magazine.widget.FastBlur;

/**
 * author：Jics
 * 2017/8/21 09:16
 */
public class MineFragement extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.main_fragment_mine, container, false);

		initview(view);
		return view;
	}

	private void initview(View containor) {
		CircleImageView civ_avater= (CircleImageView) containor.findViewById(R.id.civ_avater);
		civ_avater.setImageResource(R.drawable.hmm);
		final ImageView imageView = (ImageView) containor.findViewById(R.id.iv_img);
		final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.hmm);
		blur(bitmap, imageView);

		//开关通知
		SuperTextView superTextView= (SuperTextView) containor.findViewById(R.id.stv_notation);
		superTextView.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				Toast.makeText(getContext(), String.valueOf(b), Toast.LENGTH_SHORT).show();
			}
		});
		//清除缓存
		final SuperTextView stv_catch=(SuperTextView) containor.findViewById(R.id.stv_cache);
		stv_catch.setRightString(LocalFileManager.getInstance().getCacheSize());
		stv_catch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				new DialogHelper(getContext()).show(new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						LocalFileManager.getInstance().cleanCache();

						stv_catch.setRightString(LocalFileManager.getInstance().getCacheSize());
					}
				},true,0,0,"清除缓存","确认清除缓存",true);
			}
		});
	}


	private void blur(Bitmap bkg, ImageView view) {
		float radius = 20;
		view.setImageBitmap(FastBlur.doBlur(Bitmaptest.fitBitmap(bkg, 200), (int) radius, true));
	}
}
