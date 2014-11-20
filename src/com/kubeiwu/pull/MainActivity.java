package com.kubeiwu.pull;

import com.kubeiwu.pull.pulltorefresh.PullFreshViewIF;
import com.kubeiwu.pull.pulltorefresh.PullFreshViewIF.IKPullListener;
import com.kubeiwu.pull.pulltorefresh.PullFreshViewIF.OnXScrollListener;
import com.kubeiwu.pull.pulltorefresh.listview.KListView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements IKPullListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		KListView kListView = text1();
		
		setContentView(kListView);
	}
	private KListView text1() {
		KListView kListView=new KListView(this);
		kListView.setKListViewListener(this);
		kListView.setPullLoadEnable(true);
		kListView.setPullRefreshEnable(true);
//		kListView.setOnScrollListener(this);
		kListView.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView textView=new TextView(MainActivity.this);
				textView.setText("测试");
				return textView;
			}
			
			@Override
			public long getItemId(int position) {
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				return null;
			}
			
			@Override
			public int getCount() {
				return 200;
			}
		});
		return kListView;
	}

	@Override
	public void onRefresh() {
		
	}

	@Override
	public void onLoadMore() {
		
	}
 

}
