package com.jcs.magazine.talk.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcs.magazine.R;
import com.jcs.magazine.adapter.TalkListAdapter;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.TalkBean;
import com.jcs.magazine.fragment.TalkFragment;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.talk.Constant;
import com.jcs.magazine.talk.MediaPlayerService;
import com.jcs.magazine.talk.interfaces.LoveInterface;
import com.jcs.magazine.util.DialogHelper;
import com.jcs.magazine.util.UiUtil;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * author：Jics
 * 2017/9/5 14:38
 */
public class ChildTalkFragment extends Fragment implements LoveInterface, TalkListAdapter.OnClickTalkListener {
	private static final String TAG = TalkFragment.class.getName();
	public Messenger mServiceMessenger;//来自服务端的Messenger
	private Messenger mClientMessenger;
	private MyHandler myHandler;

	public int currentTime;//实时当前进度
	public int mPosition;//传递过来的的歌曲的位置

	private List<TalkBean> talkList;
	private TalkListAdapter adapter;
	private XRecyclerView recyclerView;

	private String tabName;
	@Override
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	@Override
	public String getTabName() {
		return tabName;
	}

	@Override
	public Fragment getFragment() {
		return this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_fragment_moment, container, false);
		recyclerView = (XRecyclerView) view.findViewById(R.id.rv_main_talk);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		talkList = new ArrayList<>();
		AlertDialog loading = new DialogHelper(getContext()).show(R.layout.loading);
		initData(loading);
		adapter = new TalkListAdapter(getContext(), talkList);
		adapter.setOnClickTalkListener(this);
		recyclerView.setAdapter(adapter);
//		recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));
		recyclerView.setLoadingListener(initListener());
		return view;
	}

	private void initServer() {
		myHandler = new MyHandler(this);
		mClientMessenger = new Messenger(myHandler);
		getActivity().bindService(new Intent(getContext(), MediaPlayerService.class), mServiceConnection, BIND_AUTO_CREATE);
	}


	ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
			Log.e(TAG, "onServiceConnected");
			//获取服务端信使 用于向服务端传递数据
			mServiceMessenger = new Messenger(iBinder);
			//用于在服务端初始化来自客户端的Messenger对象,连接成功的时候，就进行初始化
			if (null != mServiceMessenger) {
				Message msgToService = Message.obtain();
				//实现数据双向传递关键步骤
				msgToService.replyTo = mClientMessenger;//将自己的“信使”通过replyto放到msg里，让服务端获取后传递数据
				msgToService.what = Constant.MusicAcitcity_ACTIVITY;//msg的标志
				if (0 != currentTime) {//当前进度不是0，就更新MediaPlayerService的当前进度
					msgToService.arg1 = currentTime;
				}
				try {
					mServiceMessenger.send(msgToService);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			//连接成功的时候，
//            mPosition = getIntent().getIntExtra("position", 0);
			Log.e(TAG, "传递过来的positon:" + mPosition + " flag:");
			if (null != mServiceMessenger) {
				Message msgToService = Message.obtain();
				msgToService.arg1 = mPosition;
//				mList.clear();
				//Todo 歌曲集合
//				List<MusicBean> musicBeanList=new ArrayList<>();
//				mList.addAll(musicBeanList);
				if (null != talkList) {
					//传递歌曲集合数据
					Bundle songsData = new Bundle();
					songsData.putSerializable(Constant.PLAYING_ACTIVITY_DATA_KEY, (Serializable) talkList);
					msgToService.setData(songsData);
					msgToService.what = Constant.PLAYING_ACTIVITY_INIT;
					try {
						mServiceMessenger.send(msgToService);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}

			}
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			Log.e(TAG, "onServiceDisconnected");
		}
	};

	/**
	 * 上拉下拉监听器
	 *
	 * @return
	 */
	private XRecyclerView.LoadingListener initListener() {
		return new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				recyclerView.setPullRefreshEnabled(false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									recyclerView.refreshComplete();
									recyclerView.setPullRefreshEnabled(true);

									reInitPlayState();
								}
							});
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}

			@Override
			public void onLoadMore() {
				recyclerView.setPullRefreshEnabled(false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									recyclerView.setPullRefreshEnabled(true);
									recyclerView.loadMoreComplete();
									recyclerView.setNoMore(true);

									reInitPlayState();
								}
							});
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		};
	}

	@Override
	public void onResume() {
		super.onResume();
		reInitPlayState();

	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onDestroy() {
		Log.e(TAG, "onDestroy");
		getActivity().unbindService(mServiceConnection);
		if (null != myHandler) {
			myHandler.removeCallbacksAndMessages(null);//移除消息队列中所有的消息和所有的Runnable
			myHandler = null;
		}
		System.gc();
		super.onDestroy();
	}

	private void initData(final AlertDialog loading) {

		YzuClient.getInstance()
				.getTalkLists(1, 10)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<BaseListTemplet<TalkBean>>() {
					@Override
					public void accept(BaseListTemplet<TalkBean> talkBeanBaseListTemplet) throws Exception {
						talkList.clear();
						talkList.addAll(talkBeanBaseListTemplet.getResults().getBody());
						adapter.notifyDataSetChanged();
						initServer();
						loading.dismiss();
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {

					}
				});
	}

	/**
	 * 重新请求状态
	 */
	private void reInitPlayState() {
		//请求当前播放的position
		Message msg = new Message();
		msg.what = Constant.PLAYING_ACTIVITY_PLAY_NOW;
		if (mServiceMessenger != null) {
			try {
				mServiceMessenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	class MyHandler extends Handler {
		private WeakReference<ChildTalkFragment> weakActivity;

		public MyHandler(ChildTalkFragment fragment) {
			weakActivity = new WeakReference<>(fragment);
		}

		@Override
		public void handleMessage(Message msgFromService) {
			ChildTalkFragment fragment = weakActivity.get();
			if (null == fragment) return;
			switch (msgFromService.what) {
				case Constant.MEDIA_PLAYER_SERVICE_PROGRESS://更新进度条


					break;
				//从服务端获取到歌曲列表
				case Constant.MEDIA_PLAYER_SERVICE_SONG_PLAYING:


					break;
				case Constant.MEDIA_PLAYER_SERVICE_IS_PLAYING:
					if (1 == msgFromService.arg1) {//正在播放
						adapter.setmActivePosition(msgFromService.arg2);
						adapter.notifyDataSetChanged();
					} else {
						adapter.setmActivePosition(-1);
						adapter.notifyDataSetChanged();

					}
					break;
				case Constant.PLAYING_ACTIVITY_PLAY_MODE://显示播放器的播放模式

					break;
				case Constant.MEDIA_PLAYER_SERVICE_UPDATE_SONG://播放完成自动播放下一首时，更新正在播放UI
					int positionPlaying = msgFromService.arg1;
					adapter.setmActivePosition(positionPlaying);
					adapter.notifyDataSetChanged();
					break;
				case Constant.MEDIA_PLAYER_SERVICE_DOWNLOAD_PROGRESS://缓冲进度
					int downloadProgress = msgFromService.arg1;

					break;

			}
			super.handleMessage(msgFromService);
		}
	}


	@Override
	public void onPlaySound(int position) {
		UiUtil.toast(position + "");
		if (null != mServiceMessenger) {
			Message msgToServicePlay = Message.obtain();
			msgToServicePlay.arg1 = 0x40001;//表示这个暂停是由点击按钮造成的，
			msgToServicePlay.arg2 = position;
			msgToServicePlay.what = Constant.PLAYING_ACTIVITY_PLAY;
			try {
				mServiceMessenger.send(msgToServicePlay);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClickItem(int position) {
		UiUtil.toast(position + "");
	}


}