package com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers;


import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.AudienceTargetingRenderer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class BookmarkRenderer extends AudienceTargetingRenderer {
	@Override
	protected View fillView(final View view, AudienceTargetingResult result, final Object object) throws JSONException {
		JSONObject jsonObject = (JSONObject) object;
		//TODO implement
		return view;
	}

	@Override
	protected int getLayout() {
		return R.layout.audience_targeting_bookmark_default;
	}
}
