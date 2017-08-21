package com.jcs.magazine.fragment;

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
import com.jcs.magazine.widget.Bitmaptest;
import com.jcs.magazine.widget.FastBlur;

/**
 * author：Jics
 * 2017/8/21 09:16
 */
public class MineFragement extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.main_fragment_mine, container, false);

		final ImageView imageView = (ImageView) view.findViewById(R.id.iv_img);
		final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.hmm);
		blur(bitmap, imageView);
		SuperTextView superTextView= (SuperTextView) view.findViewById(R.id.stv_notation);
		superTextView.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				Toast.makeText(getContext(), String.valueOf(b), Toast.LENGTH_SHORT).show();
			}
		});
		return view;
	}

	private void blur(Bitmap bkg, ImageView view) {
		float radius = 20;
		view.setImageBitmap(FastBlur.doBlur(Bitmaptest.fitBitmap(bkg, 200), (int) radius, true));
	}
}
