package com.kubeiwu.pull.pullcore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kubeiwu.pull.R;
import com.kubeiwu.pull.pullcore.IPullView.KConfig;

/**
 * @author cgpllx1@qq.com (www.kubeiwu.com)
 * @date 2014-7-29
 */
public class PullHeader extends LinearLayout {

	private LinearLayout mContainer;
	private ImageView mArrowImageView;
	private ProgressBar mProgressBar;
	private TextView mHintTextView;
	private int mState = STATE_NORMAL;

	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;

	private final int ROTATE_ANIM_DURATION = 180;

	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;
	public final static int STATE_COMPLETE = 3;
	private KConfig config;
	public final static int STATE_ERROR = 4;

	public PullHeader(Context context, KConfig config) {
		super(context);
		this.config = config;
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public PullHeader(Context context, AttributeSet attrs, KConfig config) {
		super(context, attrs);
		this.config = config;
		initView(context);
	}

	private void initView(Context context) {
		// 初始情况，设置下拉刷新view高度为0
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 0);
		mContainer = (LinearLayout) ViewFactory.getPullHeader(context, config.getHeader_heaght());
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);

		mArrowImageView = (ImageView) findViewById(IPullView.pull_header_arrow);
		// mArrowImageView.setImageResource(R.drawable.xlistview_arrow);
		mArrowImageView.setImageResource(config.getArrow_pic());
		mHintTextView = (TextView) findViewById(IPullView.pull_header_hint_textview);
		mHintTextView.setText(config.getHeader_hint_normal());
		mProgressBar = (ProgressBar) findViewById(IPullView.pull_header_progressbar);
		mProgressBar.setVisibility(View.GONE);
		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}

	public void setState(int state) {
		if (state == mState)
			return;
		if (state == STATE_REFRESHING) { // 显示进度
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.INVISIBLE);
			mProgressBar.setVisibility(View.VISIBLE);
		} else { // 显示箭头图片
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.INVISIBLE);
		}
		switch (state) {
		case STATE_NORMAL:
			if (mState == STATE_READY) {
				mArrowImageView.startAnimation(mRotateDownAnim);
			}
			if (mState == STATE_REFRESHING) {
				mArrowImageView.clearAnimation();
			}
			mHintTextView.setText(config.getHeader_hint_normal());
			break;
		case STATE_READY:
			if (mState != STATE_READY) {
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(mRotateUpAnim);
				mHintTextView.setText(config.getHeader_hint_ready());
			}
			break;
		case STATE_REFRESHING:
			mHintTextView.setText(config.getHeader_hint_loading());
			break;
		case STATE_COMPLETE:
			mArrowImageView.setVisibility(View.INVISIBLE);
			mHintTextView.setText("加载完成");
			break;
		case STATE_ERROR:
			mArrowImageView.setVisibility(View.INVISIBLE);
			mHintTextView.setText("刷新失败,请重试");
			break;
		default:
		}
		mState = state;
	}

	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
		return mContainer.getHeight();
	}

}
