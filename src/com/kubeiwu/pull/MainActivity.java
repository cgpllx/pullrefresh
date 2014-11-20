package com.kubeiwu.pull;

import android.app.Activity;
import android.os.Bundle;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		KListView kListView = text1();

		setContentView(kListView);
	}

	private KListView text1() {
		KListView kListView = new KListView(this);
		kListView.setKListViewListener(this);
		kListView.setPullLoadEnable(true);
		kListView.setPullRefreshEnable(true);
		// kListView.setOnScrollListener(this);
		kListView.setOnItemClickListener(this);
		kListView.setAdapter(new BaseAdapter() {

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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Toast.makeText(this, position + "", 0).show();
		System.out.println("点击的位置" + position);
	}

}
