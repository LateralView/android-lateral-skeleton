package co.lateralview.androidskeleton.ui.component;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/*
 * - TAKE CARE WITH THIS, IF THE SIZES IS BIG ANDROID DON´T USE THE RECYCLER VIEW PATTERN
 * - THIS SHOULD BE USED WITH ITEMS WITH FIXED HEIGHT
 */
public class DynamicLinearLayoutManager extends LinearLayoutManager
{
	public DynamicLinearLayoutManager(Context context)
	{
		this(context, LinearLayoutManager.VERTICAL, false);
	}

	public DynamicLinearLayoutManager(Context context, int orientation, boolean reverseLayout)
	{
		super(context, orientation, reverseLayout);
	}

	private int[] mMeasuredDimension = new int[2];

	@Override
	public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec)
	{
		final int widthMode = View.MeasureSpec.getMode(widthSpec);
		final int heightMode = View.MeasureSpec.getMode(heightSpec);
		final int widthSize = View.MeasureSpec.getSize(widthSpec);
		final int heightSize = View.MeasureSpec.getSize(heightSpec);
		int width = 0;
		int height = 0;

		if (widthMode != View.MeasureSpec.EXACTLY || heightMode != View.MeasureSpec.EXACTLY)
		{
			for (int i = 0; i < getItemCount(); i++)
			{
				measureScrapChild(recycler, i,
						View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
						View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
						mMeasuredDimension);

				if (getOrientation() == HORIZONTAL)
				{
					width = width + mMeasuredDimension[0];
					if (i == 0)
					{
						height = mMeasuredDimension[1];
					}
				} else
				{
					height = height + mMeasuredDimension[1];
					if (i == 0)
					{
						width = mMeasuredDimension[0];
					}
				}
			}
		}

		if (widthMode == View.MeasureSpec.EXACTLY)
		{
			width = widthSize;
		}

		if (heightMode == View.MeasureSpec.EXACTLY)
		{
			height = heightSize;
		}

		setMeasuredDimension(width, height);
	}

	private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec, int heightSpec, int[] measuredDimension)
	{
		View view = recycler.getViewForPosition(position);
		if (view != null)
		{
			RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();

			int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, getPaddingLeft() + getPaddingRight(), p.width);
			int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, getPaddingTop() + getPaddingBottom(), p.height);

			view.measure(childWidthSpec, childHeightSpec);

			measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
			measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;

			recycler.recycleView(view);
		}
	}

	@Override
	public boolean canScrollVertically()
	{
		return false;
	}
}