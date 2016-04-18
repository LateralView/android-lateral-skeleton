package co.lateralview.androidskeleton.ui.component;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Julian on 4/8/16.
 */
public class FixedViewPager extends ViewPager
{
	public FixedViewPager(Context context)
	{
		super(context);
	}

	public FixedViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		try
		{
			return super.onTouchEvent(ev);
		} catch (IllegalArgumentException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		try
		{
			return super.onInterceptTouchEvent(ev);
		} catch (IllegalArgumentException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
}
