package com.jcs.magazine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcs.magazine.R;
import com.jcs.magazine.adapter.RvAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author：Jics
 * 2017/4/5 09:52
 */
public class ArticleFragment extends Fragment {
	private int position;
	private RecyclerView recyclerView;
	private List<String> list;

	public ArticleFragment() {

	}

	public ArticleFragment(int position) {
		this.position = position;
	}


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_article_list, container, false);
		initView(view);
		return view;
	}

	private void initView(View parent) {
		list = new ArrayList<>();
		for (int i = 0; i < 15; i++) {
			list.add("- "+position+" -\n" + i);
		}
		recyclerView = (RecyclerView) parent.findViewById(R.id.rv_content);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//		recyclerView.addItemDecoration(new SpaceItemDecoration((int) getResources().getDimension(R.dimen.space)));
		recyclerView.setAdapter(new RvAdapter(getContext(), list));


	}
}
