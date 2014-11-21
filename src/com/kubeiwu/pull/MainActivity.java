package com.kubeiwu.pull;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kubeiwu.pull.pullcore.IPullView.IKPullListener;
import com.kubeiwu.pull.pullview.KListView;

public class MainActivity extends Activity implements IKPullListener, OnItemClickListener {
	KListView kListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	

		setContentView(R.layout.klistview);
		kListView = text1();
		kListView.setOnAutoLoad(true);
//		setContentView(kListView);
	}

	MyAdapter b;

	private KListView text1() {
//		KListView kListView = new KListView(this);
		KListView kListView = (KListView) findViewById(R.id.klistview);
		kListView.setKListViewListener(this);
		kListView.setPullLoadEnable(true);
		kListView.setPullRefreshEnable(true);
		kListView.setOnItemClickListener(this);
		b = new MyAdapter();
		kListView.setAdapter(b);
		return kListView;
	}

	class MyAdapter extends BaseAdapter {
		int count = 20;

		public void setCount1(int count) {
			this.count += count;
			notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(MainActivity.this);
			textView.setText("测试" + position);
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
			return count;
		}
	};

	int i = 0;

	@Override
	public void onRefresh() {
		System.out.println("刷新" + i++);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (kListView.getCount() > 10) {
					// kListView.stopLoadMore();
					kListView.setPullRefreshError();
					return;
				}
				kListView.stopRefresh();
			}
		}, 1000);
	}

	// int count=20;
	@Override
	public void onLoadMore() {
		System.out.println("加载");
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (kListView.getCount() > 50) {
					kListView.stopLoadMore();
					kListView.setPullLoadError();
					return;
				}
				b.setCount1(20);
				kListView.stopLoadMore();
			}
		}, 1000);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Toast.makeText(this, position + "", 0).show();
		System.out.println("点击的位置" + position);
	}

}
