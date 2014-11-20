package com.kubeiwu.pull.pulltorefresh.listview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.kubeiwu.pull.pulltorefresh.PullFreshViewIF;

/**
 * @author cgpllx1@qq.com (www.kubeiwu.com)
 * @date 2014-7-29
 */
public class KListView extends ListView implements PullFreshViewIF {

	private PullFreshController mPullFreshController;

	/**
	 * @param context
	 */
	public KListView(Context context) {
		this(context, null, 0, null);
	}

	public KListView(Context context, KConfig config) {
		this(context, null, 0, config);
	}

	public KListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0, null);
	}

	public KListView(Context context, AttributeSet attrs, KConfig config) {
		this(context, attrs, 0, config);
	}

	public KListView(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs, defStyle, null);
	}

	public KListView(Context context, AttributeSet attrs, int defStyle, KConfig config) {
		super(context, attrs, defStyle);
		mPullFreshController = new PullFreshController(context, attrs, defStyle, config, this);

	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		mPullFreshController.setAdapter(adapter);
		super.setAdapter(adapter);
	}

	public void setPullRefreshEnable(boolean enable) {
		mPullFreshController.setPullRefreshEnable(enable);
	}

	/**
	 * enable or disable pull up load more feature.
	 * 
	 * @param enable
	 */
	public void setPullLoadEnable(boolean enable) {
		mPullFreshController.setPullLoadEnable(enable);
	}

	public void stopRefresh() {
		mPullFreshController.stopRefresh();
	}

	public void stopLoadMore() {
		mPullFreshController.stopLoadMore();
	}

	public void setRefreshTime(String time) {
		mPullFreshController.setRefreshTime(time);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mPullFreshController.onTouchEvent(ev);
		return super.onTouchEvent(ev);
	}

	@Override
	public void computeScroll() {
		mPullFreshController.computeScroll();
		super.computeScroll();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		mPullFreshController.onScrollStateChanged(view, scrollState);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		mPullFreshController.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
	}

	public void setKListViewListener(IKListViewListener l) {
		mPullFreshController.setKListViewListener(l);
	}

}
