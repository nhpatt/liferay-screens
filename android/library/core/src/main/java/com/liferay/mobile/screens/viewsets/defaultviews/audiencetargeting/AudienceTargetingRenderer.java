package com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public abstract class AudienceTargetingRenderer {

	public View render(final JSONObject jsonObject) {
		try {
			LayoutInflater layoutInflater = (LayoutInflater) LiferayScreensContext.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			return fillView(layoutInflater.inflate(getLayout(), null), jsonObject);
		}
		catch (Exception e) {
			LiferayLogger.e("Error inflating view", e);
		}
		return null;
	}

	protected abstract View fillView(final View view, final JSONObject jsonObject) throws JSONException;

	protected abstract int getLayout();

}
