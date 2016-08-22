package com.liferay.mobile.screens.testapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

/**
 * @author Javier Gamarra
 */
public abstract class CardActivity2 extends Activity implements View.OnClickListener {

	public static final int CARD_SIZE = 100;
	public static final int DURATION_MILLIS = 2000;
	public static final int MARGIN = 30;

	@Override
	protected void onStart() {
		super.onStart();

		calculateSize();
	}

	private void calculateSize() {
		if (maxWidth != 0 && maxHeight != 0) {
			onWindowDrawnOnScreens();
		} else {
			final View content = findViewById(android.R.id.content);
			content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
						removeObserver();
					} else {
						content.getViewTreeObserver().removeGlobalOnLayoutListener(this);
					}

					maxWidth = content.getWidth();
					maxHeight = content.getHeight();

					onWindowDrawnOnScreens();
				}

				@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
				private void removeObserver() {
					content.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
			});
		}
	}

	protected void onWindowDrawnOnScreens() {
		FrameLayout content = (FrameLayout) findViewById(R.id.cards);

		for (int i = 0; i < content.getChildCount(); i++) {
			View view = content.getChildAt(i);
			view.setY(maxHeight - (content.getChildCount() - i) * CARD_SIZE);
			view.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		FrameLayout content = (FrameLayout) findViewById(R.id.cards);

		int j;
		for (j = 0; j < content.getChildCount(); j++) {
			View view = content.getChildAt(j);
			if (view == v) {
				break;
			}
		}

		for (int i = 0; i < content.getChildCount(); i++) {
			View view = content.getChildAt(i);

			int value = j >= i ? MARGIN + i * MARGIN : maxHeight - (content.getChildCount() - i) * CARD_SIZE;
			view.animate().setDuration(DURATION_MILLIS).y(value);

			int margin = j > i ? MARGIN * (j - i) : 0;
			setFrameLayoutMargins(view, margin);
		}
	}

	private void setFrameLayoutMargins(final View view, final int margin) {
		final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
		Animation a = new Animation() {

			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				int value = getValue(interpolatedTime);
				layoutParams.leftMargin = value;
				layoutParams.rightMargin = value;
				view.setLayoutParams(layoutParams);
			}

			private int getValue(float interpolatedTime) {
				if (layoutParams.leftMargin <= margin) {
					return Math.max(layoutParams.leftMargin, (int) (margin * interpolatedTime));
				} else {
					return Math.min(layoutParams.leftMargin,
						(int) (layoutParams.leftMargin - (layoutParams.leftMargin - margin) * interpolatedTime));
				}
			}
		};
		a.setDuration(DURATION_MILLIS);
		view.startAnimation(a);
	}

	private int convertDpToPx(int dp) {
		Resources resources = getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
	}

	protected int maxWidth;
	protected int maxHeight;
}