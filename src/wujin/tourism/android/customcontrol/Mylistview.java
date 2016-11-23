package wujin.tourism.android.customcontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class Mylistview extends ListView {
	public Mylistview(Context context) {
		super(context);
	}

	public Mylistview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// GridView(Context context, AttributeSet attrs)
	// GridView(Context context, AttributeSet attrs, int defStyleAttr)
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
