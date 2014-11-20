package com.kubeiwu.pull.pullview;

import com.kubeiwu.pull.pullcore.HeaderFooterGridView;
import com.kubeiwu.pull.pullcore.PullController;
import com.kubeiwu.pull.pullcore.PullFreshViewIF;
import com.kubeiwu.pull.pullcore.PullFreshViewIF.IKPullListener;
import com.kubeiwu.pull.pullcore.PullFreshViewIF.KConfig;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;


/**
 * @author cgpllx1@qq.com (www.kubeiwu.com)
 * @date 2014-7-29
 */
public class KGistView extends HeaderFooterGridView implements PullFreshViewIF {

	private PullController mPullFreshController;

	/**
	 * @param context
	 */
	public KGistView(Context context) {
		this(context, null, 0, null);
	}

	public KGistView(Context context, KConfig config) {
		this(context, null, 0, config);
	}

	public KGistView(Context context, AttributeSet attrs) {
		this(context, attrs, 0, null);
	}

	public KGistView(Context context, AttributeSet attrs, KConfig config) {
		this(context, attrs, 0, config);
	}

	public KGistView(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs, defStyle, null);
	}

	public KGistView(Context context, AttributeSet attrs, int defStyle, KConfig config) {
		super(context, attrs, defStyle);
		if (config == null) {
			config = KConfig.getSimpleInstance();
		}
		mPullFreshController = new PullController(context, attrs, defStyle, config, this);
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


	public void setKListViewListener(IKPullListener l) {
		mPullFreshController.setKListViewListener(l);
	}

}
