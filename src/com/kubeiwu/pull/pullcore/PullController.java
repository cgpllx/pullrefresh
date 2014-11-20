package com.kubeiwu.pull.pullcore;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.kubeiwu.pull.R;
import com.kubeiwu.pull.pullcore.PullFreshViewIF.IKPullListener;
import com.kubeiwu.pull.pullcore.PullFreshViewIF.KConfig;
import com.kubeiwu.pull.pullcore.PullFreshViewIF.OnXScrollListener;

public class PullController implements OnScrollListener {
	private PullFreshViewIF mAbsListView;
	private Context context;
	private static final int SCROLLBACK_HEADER = 0;

	private static final int SCROLLBACK_FOOTER = 1;

	private static final int SCROLL_DURATION = 400; // scroll back duration

	private static final int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
	// at bottom, trigger
	// load more.

	private final static float OFFSET_RADIO = 1.8f; // support iOS like pull

	public PullController(Context context, AttributeSet attrs, int defStyle, KConfig config, PullFreshViewIF mAbsListView) {
		this.mAbsListView = mAbsListView;
		this.context = context;
		if (config == null) {
			config = KConfig.getSimpleInstance();
		}
		initConfig(config, attrs);
		initWithContext(context, config);
//		((AbsListView)mAbsListView).setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//		});
	}

	public void setAdapter(ListAdapter adapter) {
		if (this.mIsFooterReady == false) {
			this.mIsFooterReady = true;
			mAbsListView.addFooterView(this.mFooterView);
		}
	}

	/**
	 * enable or disable pull up load more feature.
	 * 
	 * @param enable
	 */
	public void setPullLoadEnable(boolean enable) {
		this.mEnablePullLoad = enable;
		if (!this.mEnablePullLoad) {
			this.mFooterView.hide();
			this.mFooterView.setOnClickListener(null);
		} else {
			this.mPullLoading = false;
			this.mFooterView.show();
			this.mFooterView.setState(KPullFooter.STATE_NORMAL);
			// both "pull up" and "click" will invoke load more.
			this.mFooterView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});
		}
	}

	public void startLoadMore() {
		this.mPullLoading = true;
		this.mFooterView.setState(KPullFooter.STATE_LOADING);
		if (this.mListViewListener != null) {
			this.mListViewListener.onLoadMore();
		}
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mScrollListener != null) {
			mScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// send to user's listener
		mTotalItemCount = totalItemCount;
		if (mScrollListener != null) {
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	public boolean onTouchEvent(MotionEvent ev) {
		if (mLastY == -1) {
			mLastY = ev.getRawY();
		}

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float deltaY = ev.getRawY() - mLastY;
			mLastY = ev.getRawY();
			System.out.println("((AbsListView)mAbsListView).getLastVisiblePosition()=" + ((AbsListView) mAbsListView).getLastVisiblePosition());
			System.out.println("(mFooterView.getBottomMargin()=" + mFooterView.getBottomMargin());
			System.out.println("deltaY=" + deltaY);
			System.out.println("mTotalItemCount=" + mTotalItemCount);
			if (((AbsListView) mAbsListView).getFirstVisiblePosition() == 0 && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
				// the first item is showing, header has shown or pull down.
				System.out.println("onTouchEvent44444444");
				updateHeaderHeight(deltaY / OFFSET_RADIO);
				invokeOnScrolling();
			} else if (((AbsListView) mAbsListView).getLastVisiblePosition() == mTotalItemCount - 1 && (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
				System.out.println("onTouchEvent5555555");
				// last item, already pulled up or want to pull up.
				updateFooterHeight(-deltaY / OFFSET_RADIO);
			}
			System.out.println("onTouchEvent666");
			break;
		default:
			mLastY = -1; // reset
			if (((AbsListView) mAbsListView).getFirstVisiblePosition() == 0) {
				// invoke refresh
				if (mEnablePullRefresh && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
					mPullRefreshing = true;
					mHeaderView.setState(KPullHeader.STATE_REFRESHING);
					if (mListViewListener != null) {
						mListViewListener.onRefresh();
					}
				}
				resetHeaderHeight();
			} else if (((AbsListView) mAbsListView).getLastVisiblePosition() == mTotalItemCount - 1) {
				// invoke load more.
				if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
					startLoadMore();
				}
				resetFooterHeight();
			}

			break;
		}
		return false;
	}

	public void stopRefresh() {
		if (mPullRefreshing == true) {
			mPullRefreshing = false;
			resetHeaderHeight();
		}
	};

	/**
	 * stop load more, reset footer view.
	 */
	public void stopLoadMore() {
		if (mPullLoading == true) {
			mPullLoading = false;
			mFooterView.setState(KPullFooter.STATE_NORMAL);
		}
	}

	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			if (mScrollBack == SCROLLBACK_HEADER) {
				mHeaderView.setVisiableHeight(mScroller.getCurrY());
			} else {
				mFooterView.setBottomMargin(mScroller.getCurrY());
			}
			((View) mAbsListView).postInvalidate();
			invokeOnScrolling();
		}
	}

	/**
	 * reset header view's height.
	 */
	public void resetHeaderHeight() {
		int height = mHeaderView.getVisiableHeight();
		if (height == 0) // not visible.
			return;
		// refreshing and header isn't shown fully. do nothing.
		if (mPullRefreshing && height <= mHeaderViewHeight) {
			return;
		}
		int finalHeight = 0; // default: scroll back to dismiss header.
		// is refreshing, just scroll back to show all the header.
		if (mPullRefreshing && height > mHeaderViewHeight) {
			finalHeight = mHeaderViewHeight;
		}
		mScrollBack = SCROLLBACK_HEADER;
		mScroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);
		// trigger computeScroll
		((View) mAbsListView).invalidate();
	}

	private void invokeOnScrolling() {
		System.out.println("invokeOnScrolling" + "1111111111");
		if (mScrollListener instanceof OnXScrollListener) {
			System.out.println("invokeOnScrolling" + "222222");
			OnXScrollListener l = (OnXScrollListener) mScrollListener;
			l.onXScrolling((AbsListView) mAbsListView);
		}
		System.out.println("invokeOnScrolling" + "333333333");
	}

	private void updateHeaderHeight(float delta) {
		mHeaderView.setVisiableHeight((int) delta + mHeaderView.getVisiableHeight());
		if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
			if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
				mHeaderView.setState(KPullHeader.STATE_READY);
			} else {
				mHeaderView.setState(KPullHeader.STATE_NORMAL);
			}
		}
		((AbsListView) mAbsListView).setSelection(0); // scroll to top each time
	}

	private void updateFooterHeight(float delta) {
		int height = mFooterView.getBottomMargin() + (int) delta;
		if (mEnablePullLoad && !mPullLoading) {
			if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
													// more.
				mFooterView.setState(KPullFooter.STATE_READY);
			} else {
				mFooterView.setState(KPullFooter.STATE_NORMAL);
			}
		}
		System.out.println("updateFooterHeight555555555");
		mFooterView.setBottomMargin(height);

		// setSelection(mTotalItemCount - 1); // scroll to bottom
	}

	private void resetFooterHeight() {
		int bottomMargin = mFooterView.getBottomMargin();
		if (bottomMargin > 0) {
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);
			((View) mAbsListView).invalidate();
		}
	}

	/**
	 * enable or disable pull down refresh feature.
	 * 
	 * @param enable
	 */
	public void setPullRefreshEnable(boolean enable) {
		this.mEnablePullRefresh = enable;
		if (!this.mEnablePullRefresh) { // disable, hide the content
			this.mHeaderViewContent.setVisibility(View.INVISIBLE);
		} else {
			this.mHeaderViewContent.setVisibility(View.VISIBLE);
		}
	}

	public void setKListViewListener(IKPullListener l) {
		mListViewListener = l;
	}

	// private StringHoder stringHoder = null;
	public void setOnScrollListener(OnScrollListener l) {
		mScrollListener = l;
	}

	/**
	 * set last refresh time
	 * 
	 * @param time
	 */
	public void setRefreshTime(String time) {
		mHeaderTimeView.setText(time);
	}

	public void initWithContext(Context context, KConfig config) {
		this.mScroller = new Scroller(context, new DecelerateInterpolator());
		// XListView need the scroll event, and it will dispatch the event to
		// user's listener (as a proxy).
		mAbsListView.setOnScrollListener(this);
		// stringHoder = new StringHoder(header_hint_normal, header_hint_ready,
		// header_hint_loading, footer_hint_ready, footer_hint_normal,
		// footer_heaght, header_heaght, arrow_pic);
		// init header view
		this.mHeaderView = new KPullHeader(context, config);
		this.mHeaderViewContent = (RelativeLayout) this.mHeaderView.findViewById(R.id.klistview_header_content);
		this.mHeaderTimeView = (TextView) this.mHeaderView.findViewById(R.id.klistview_header_time);
		mAbsListView.addHeaderView(this.mHeaderView);

		// init footer view
		this.mFooterView = new KPullFooter(context, config);
		/* 2014 04 22 cgp */
		this.mFooterView.hide();
		/* 2014 04 22 cgp */

		// init header height
		this.mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				mHeaderViewHeight = mHeaderViewContent.getHeight();
				((View) mAbsListView).getViewTreeObserver().removeOnGlobalLayoutListener(this);
			}
		});
	}

	private float mLastY = -1; // save event y

	private Scroller mScroller; // used for scroll back

	private OnScrollListener mScrollListener; // user's scroll listener

	// the interface to trigger refresh and load more.
	private IKPullListener mListViewListener;

	// -- header view
	private KPullHeader mHeaderView;

	// header view content, use it to calculate the Header's height. And hide it
	// when disable pull refresh.
	private RelativeLayout mHeaderViewContent;

	private TextView mHeaderTimeView;

	private int mHeaderViewHeight; // header view's height

	private boolean mEnablePullRefresh = false;// ++++++++++++++++++++刷新

	private boolean mPullRefreshing = false; // is refreashing.

	// -- footer view
	private KPullFooter mFooterView;

	private boolean mEnablePullLoad = false;// +++++++++++++++++++++++++++加载

	private boolean mPullLoading;

	private boolean mIsFooterReady = false;

	// total list items, used to detect is at the bottom of listview.
	private int mTotalItemCount;

	// for mScroller, scroll back from header or footer.
	private int mScrollBack;

	public void initConfig(KConfig config, AttributeSet attrs) {
		if (attrs != null) {
			TypedArray a = null;
			try {
				a = context.obtainStyledAttributes(attrs, R.styleable.KListView);
				if (a.hasValue(R.styleable.KListView_header_hint_normal))
					config.setHeader_hint_normal(a.getString(R.styleable.KListView_header_hint_normal));
				if (a.hasValue(R.styleable.KListView_header_hint_ready))
					config.setHeader_hint_ready(a.getString(R.styleable.KListView_header_hint_ready));
				if (a.hasValue(R.styleable.KListView_header_hint_loading))
					config.setHeader_hint_loading(a.getString(R.styleable.KListView_header_hint_loading));
				if (a.hasValue(R.styleable.KListView_footer_hint_ready))
					config.setFooter_hint_ready(a.getString(R.styleable.KListView_footer_hint_ready));
				if (a.hasValue(R.styleable.KListView_footer_hint_normal))
					config.setFooter_hint_normal(a.getString(R.styleable.KListView_footer_hint_normal));
				if (a.hasValue(R.styleable.KListView_header_heaght))
					config.setHeader_heaght(a.getInt(R.styleable.KListView_header_heaght, 60));
				if (a.hasValue(R.styleable.KListView_footer_heaght))
					config.setFooter_heaght(a.getInt(R.styleable.KListView_footer_heaght, 60));
				if (a.hasValue(R.styleable.KListView_arrow_pic))
					config.setArrow_pic(a.getResourceId(R.styleable.KListView_arrow_pic, R.drawable.xlistview_arrow));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (a != null) {
					a.recycle();
				}
			}
		}
	}
}
