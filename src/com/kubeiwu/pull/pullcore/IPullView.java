package com.kubeiwu.pull.pullcore;

import android.view.View;
import android.widget.AbsListView.OnScrollListener;

public interface IPullView {

	/**
	 * implements this interface to get refresh/load more event.
	 */
	public interface IKPullListener {
		public void onRefresh();

		public void onLoadMore();
	}

	public void addFooterView(View v);

	public void addHeaderView(View mHeaderView);

	public void setPullRefreshEnable(boolean enable);

	public void setPullLoadEnable(boolean enable);

	public void stopRefresh();

	public void stopLoadMore();

	public void setRefreshTime(String time);

	public void setKListViewListener(IKPullListener l);

	/**
	 * you can listen ListView.OnScrollListener or this one. it will invoke onXScrolling when header/footer scroll back.
	 */
	public interface OnXScrollListener extends OnScrollListener {
		public void onXScrolling(View view);
	}

	/**
	 * 参数
	 * 
	 * @author Administrator
	 * 
	 */
	public static class KConfig {
		private String header_hint_normal = "\u4e0b\u62c9\u5237\u65b0",// 下拉刷新
				header_hint_ready = "\u677e\u5f00\u5237\u65b0\u6570\u636e",// 松开刷新数据
				header_hint_loading = "\u6b63\u5728\u52a0\u8f7d\u002e\u002e\u002e", // 正在加载...
				footer_hint_ready = "\u677e\u5f00\u52a0\u8f7d\u6570\u636e", // 松开加载数据
				footer_hint_normal = "\u4e0a\u62c9\u52a0\u8f7d";// 上拉加载
		private int footer_heaght, header_heaght = 60, arrow_pic;

		private KConfig() {
		}

		public static KConfig getSimpleInstance() {
			return new KConfig();
		}

		public String getHeader_hint_normal() {
			return header_hint_normal;
		}

		public KConfig setHeader_hint_normal(String header_hint_normal) {
			this.header_hint_normal = header_hint_normal;
			return this;
		}

		public String getHeader_hint_ready() {
			return header_hint_ready;
		}

		public KConfig setHeader_hint_ready(String header_hint_ready) {
			this.header_hint_ready = header_hint_ready;
			return this;
		}

		public String getHeader_hint_loading() {
			return header_hint_loading;
		}

		public KConfig setHeader_hint_loading(String header_hint_loading) {
			this.header_hint_loading = header_hint_loading;
			return this;
		}

		public String getFooter_hint_ready() {
			return footer_hint_ready;
		}

		public KConfig setFooter_hint_ready(String footer_hint_ready) {
			this.footer_hint_ready = footer_hint_ready;
			return this;
		}

		public String getFooter_hint_normal() {
			return footer_hint_normal;
		}

		public KConfig setFooter_hint_normal(String footer_hint_normal) {
			this.footer_hint_normal = footer_hint_normal;
			return this;
		}

		public int getFooter_heaght() {
			return footer_heaght;
		}

		public KConfig setFooter_heaght(int footer_heaght) {
			this.footer_heaght = footer_heaght;
			return this;
		}

		public int getHeader_heaght() {
			return header_heaght;
		}

		public KConfig setHeader_heaght(int header_heaght) {
			this.header_heaght = header_heaght;
			return this;
		}

		public int getArrow_pic() {
			return arrow_pic;
		}

		public KConfig setArrow_pic(int arrow_pic) {
			this.arrow_pic = arrow_pic;
			return this;
		}
	}

	public void setOnScrollListener(OnScrollListener l);

}
