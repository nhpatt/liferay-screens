package com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONException;

/**
 * @author Javier Gamarra
 */
public abstract class AudienceTargetingRenderer {

	public View render(AudienceTargetingResult result, final Object jsonObject, Context context) {
		try {
			LayoutInflater layoutInflater = (LayoutInflater) LiferayScreensContext.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			return fillView(layoutInflater.inflate(getLayout(), null), result, jsonObject);
		}
		catch (Exception e) {
			LiferayLogger.e("Error inflating view", e);
		}
		return null;
	}

	protected abstract View fillView(final View view, AudienceTargetingResult result, final Object jsonObject) throws JSONException;

	protected abstract int getLayout();

}
