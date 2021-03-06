package com.kubeiwu.pull.pullview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListAdapter;

import com.kubeiwu.pull.pullcore.HeaderFooterGridView;
import com.kubeiwu.pull.pullcore.IPullView;
import com.kubeiwu.pull.pullcore.PullController;

/**
 * @author cgpllx1@qq.com (www.kubeiwu.com)
 * @date 2014-7-29
 */
public class KGistView extends HeaderFooterGridView implements IPullView {

	private PullController mPullFreshController;

	public KGistView(Context context) {
		this(context, null, 0, null);
	}

	public KGistView(Context context, KConfig config) {
		this(context, null, 0, config);
	}

	public KGistView(Context context, AttributeSet attrs) {
		this(context, attrs, 0, null);
	}

	public KGistView(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs, defStyle, null);
	}

	public KGistView(Context context, AttributeSet attrs, int defStyle, KConfig config) {
		super(context, attrs, defStyle);
		mPullFreshController = new PullController(context, attrs, defStyle, config, this);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		mPullFreshController.setAdapter(adapter);
		super.setAdapter(adapter);
	}
   
	@Override
	public void setPullRefreshEnable(boolean enable) {
		mPullFreshController.setPullRefreshEnable(enable);
	}

	@Override
	public void setPullLoadEnable(boolean enable) {
		mPullFreshController.setPullLoadEnable(enable);
	}

	@Override
	public void stopRefresh() {
		mPullFreshController.stopRefresh();
	}

	@Override
	public void stopLoadMore() {
		mPullFreshController.stopLoadMore();
	}

	public void setPullLoadError() {
		mPullFreshController.setPullLoadError();
	}

	public void setPullRefreshError() {
		mPullFreshController.setPullRefreshError();
	}

	public void setOnAutoLoad(boolean isAutoLoad) {
		mPullFreshController.setOnAutoLoad(isAutoLoad);
	}

	@Override
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
	public void setKListViewListener(IKPullListener l) {
		mPullFreshController.setKListViewListener(l);
	}


}
