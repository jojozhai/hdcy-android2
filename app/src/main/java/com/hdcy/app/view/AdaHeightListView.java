package com.hdcy.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class AdaHeightListView extends ListView
{

	public AdaHeightListView(Context context)
	{
		super(context);
	}

	public AdaHeightListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public AdaHeightListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}

}
