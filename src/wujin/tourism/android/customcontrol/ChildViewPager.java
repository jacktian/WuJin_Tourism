package wujin.tourism.android.customcontrol;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

public class ChildViewPager extends ViewPager {
	public ChildViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ChildViewPager(Context context) {
		super(context);
	}

	private float x;
	private float y;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		ViewParent mViewParent = this.getParent();
		final int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			x = ev.getX();
			y = ev.getY();
			mViewParent.requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_MOVE:
			if (Math.abs(ev.getX() - x) > 10)
				mViewParent.requestDisallowInterceptTouchEvent(true);
			else if (Math.abs(ev.getY() - y) > 10)
				mViewParent.requestDisallowInterceptTouchEvent(false);
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			mViewParent.requestDisallowInterceptTouchEvent(false);
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

}
