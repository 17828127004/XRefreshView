package com.example.xrefreshviewdemo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshView.SimpleXRefreshListener;
import com.andview.refreshview.utils.LogUtils;
import com.example.xrefreshviewdemo.R;

public class ListViewActivity extends Activity {
	private ListView lv;
	private List<String> str_name = new ArrayList<String>();
	private XRefreshView refreshView;
	private ArrayAdapter<String> adapter;
	public static long lastRefreshTime;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		for (int i = 0; i < 30; i++) {
			str_name.add("数据" + i);
		}
		lv = (ListView) findViewById(R.id.lv);
		refreshView = (XRefreshView) findViewById(R.id.custom_view);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, str_name);
		lv.setAdapter(adapter);

		// 设置是否可以下拉刷新
		refreshView.setPullRefreshEnable(false);
		// 设置是否可以上拉加载
		refreshView.setPullLoadEnable(false);
		// 设置上次刷新的时间
		refreshView.restoreLastRefreshTime(lastRefreshTime);
		// 设置时候可以自动刷新
		refreshView.setAutoRefresh(false);
		refreshView.setXRefreshViewListener(new SimpleXRefreshListener() {

			@Override
			public void onRefresh() {

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						refreshView.stopRefresh();
						lastRefreshTime = refreshView.getLastRefreshTime();
					}
				}, 2000);
			}

			@Override
			public void onLoadMore() {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						refreshView.stopLoadMore();
					}
				}, 2000);
			}

			@Override
			public void onRelease(float direction) {
				super.onRelease(direction);
				if (direction > 0) {
					toast("下拉");
				} else {
					toast("上拉");
				}
			}
		});
		refreshView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				LogUtils.i("onScrollStateChanged");
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				LogUtils.i("onScroll");
			}
		});

	}

	public void toast(String msg) {
		Toast.makeText(getApplicationContext(), msg, 0).show();
	}

}