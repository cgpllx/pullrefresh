package com.kubeiwu.pull.pullcore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kubeiwu.pull.R;
import com.kubeiwu.pull.pullcore.IPullView.KConfig;

public class PullFooter extends LinearLayout {
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;
	public final static int STATE_COMPLETE = 3;
	public final static int STATE_ERROR = 4;

	private Context mContext;

	private View mContentView;
	private View mProgressBar;
	private TextView mHintView;
	private KConfig config;

	public PullFooter(Context context, KConfig config) {
		super(context);
		this.config = config;
		initView(context);
	}

	public PullFooter(Context context, AttributeSet attrs, KConfig config) {
		super(context, attrs);
		this.config = config;
		initView(context);
	}

	public void setState(int state) {
		mHintView.setVisibility(View.INVISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
		if (state == STATE_READY) {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(config.getFooter_hint_ready());
		} else if (state == STATE_LOADING) {
			mProgressBar.setVisibility(View.VISIBLE);
		} else if (state == STATE_COMPLETE) {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText("加载完成");
		}else if (state == STATE_ERROR) {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText("加载失败,点击重试");
		}  else {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(config.getFooter_hint_normal());
		}
	}

	public void setBottomMargin(int height) {
		if (height < 0)
			return;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}

	public int getBottomMargin() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		return lp.bottomMargin;
	}

	/**
	 * normal status
	 */
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
	}

	/**
	 * loading status
	 */
	public void loading() {
		mHintView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}

	/**
	 * hide footer when disable pull load more
	 */
	public void hide() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}

	/**
	 * show footer
	 */
	public void show() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}

	private void initView(Context context) {
		mContext = context;
		LinearLayout moreView = (LinearLayout) ViewFactory.getPullFooter(mContext, config.getFooter_heaght());
		// (LinearLayout)LayoutInflater.from(mContext).inflate(R.layout.klistview_footer, null);

		addView(moreView);
		moreView.setLayoutParams(new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

		mContentView = moreView.findViewById(IPullView.pull_footer_content);
		mProgressBar = moreView.findViewById(IPullView.pull_footer_progressbar);
		mHintView = (TextView) moreView.findViewById(IPullView.pull_footer_hint_textview);
	}

}
