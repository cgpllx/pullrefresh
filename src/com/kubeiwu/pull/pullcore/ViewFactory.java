package com.kubeiwu.pull.pullcore;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author 耳东    www.kubeiwu.com
 *
 */
public class ViewFactory {  
	public static View getPullFooter(Context mContext, int headerHeaght) {
		LinearLayout mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		mLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mLinearLayout.setGravity(Gravity.BOTTOM);
		//----------------
		RelativeLayout pull_footer_content = new RelativeLayout(mContext);
		pull_footer_content.setId(IPullView.pull_footer_content);
		pull_footer_content.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, headerHeaght));
		pull_footer_content.setPadding(0, 0, 0, 0);
		//-----------------------
		ProgressBar pull_footer_progressbar = new ProgressBar(mContext);
		pull_footer_progressbar.setId(IPullView.pull_footer_progressbar);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		pull_footer_progressbar.setLayoutParams(layoutParams);
		//-------------------------
		TextView pull_footer_hint_textview = new TextView(mContext);
		pull_footer_hint_textview.setId(IPullView.pull_footer_hint_textview);
		RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams2.addRule(RelativeLayout.CENTER_IN_PARENT);
		pull_footer_hint_textview.setLayoutParams(layoutParams2);
		pull_footer_hint_textview.setGravity(Gravity.CENTER);
		//-----------------------
		mLinearLayout.addView(pull_footer_content);
		pull_footer_content.addView(pull_footer_progressbar);
		pull_footer_content.addView(pull_footer_hint_textview);
		return mLinearLayout;
	}

	public static View getPullHeader(Context mContext, int headerHeaght) {
		LinearLayout mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		mLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mLinearLayout.setGravity(Gravity.BOTTOM);
		//----------------
		RelativeLayout pull_header_content = new RelativeLayout(mContext);
		pull_header_content.setId(IPullView.pull_header_content);
		pull_header_content.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, headerHeaght));
		//-----------------
		LinearLayout pull_header_text = new LinearLayout(mContext);
		pull_header_text.setId(IPullView.pull_header_text);
		pull_header_text.setOrientation(LinearLayout.VERTICAL);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		pull_header_text.setLayoutParams(layoutParams);
		pull_header_text.setGravity(Gravity.CENTER);
		//---------------------------
		TextView pull_header_hint_textview = new TextView(mContext);
		pull_header_hint_textview.setId(IPullView.pull_header_hint_textview);
		pull_header_hint_textview.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		LinearLayout mLinearLayoutgone = new LinearLayout(mContext);
		LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams1.topMargin = 10;
		mLinearLayoutgone.setLayoutParams(layoutParams1);
		mLinearLayoutgone.setVisibility(View.GONE);
		//-------------------------
		TextView textView = new TextView(mContext);
		textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		textView.setTextSize(15);
		//-------------------------
		TextView pull_header_time = new TextView(mContext);
		pull_header_time.setId(IPullView.pull_header_time);
		pull_header_time.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		pull_header_time.setTextSize(12);
		//-----------------------
		ImageView pull_header_arrow = new ImageView(mContext);
		pull_header_arrow.setId(IPullView.pull_header_arrow);
		RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams2.addRule(RelativeLayout.ALIGN_LEFT, IPullView.pull_header_text);
		layoutParams2.addRule(RelativeLayout.CENTER_VERTICAL);
		layoutParams2.leftMargin = -50;
		pull_header_arrow.setLayoutParams(layoutParams2);
		//-----------------------
		ProgressBar pull_header_progressbar = new ProgressBar(mContext);
		pull_header_progressbar.setId(IPullView.pull_header_progressbar);
		RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(40, 40);
		layoutParams3.addRule(RelativeLayout.ALIGN_LEFT, IPullView.pull_header_text);
		layoutParams3.addRule(RelativeLayout.CENTER_VERTICAL);
		layoutParams3.leftMargin = -55;
		pull_header_progressbar.setLayoutParams(layoutParams3);
		//-----------------------
		mLinearLayout.addView(pull_header_content);
		pull_header_content.addView(pull_header_text);
		pull_header_text.addView(pull_header_hint_textview);
		pull_header_text.addView(mLinearLayoutgone);
		mLinearLayoutgone.addView(textView);
		mLinearLayoutgone.addView(pull_header_time);
		pull_header_content.addView(pull_header_arrow);
		pull_header_content.addView(pull_header_progressbar);
		//-------------		
		return mLinearLayout;
	}
}
