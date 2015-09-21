package com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.AudienceTargetingRenderer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class MBMessageRenderer extends AudienceTargetingRenderer {
	@Override
	protected View fillView(final View view, AudienceTargetingResult result, final Object object) throws JSONException {
		JSONObject jsonObject = (JSONObject) object;

		TextView body = (TextView) view.findViewById(R.id.audience_mb_body);
		body.setText(Html.fromHtml(jsonObject.getString("body")));

		TextView username = (TextView) view.findViewById(R.id.audience_mb_username);
		username.setText(Html.fromHtml(jsonObject.getString("userName")));

		return view;
	}

	@Override
	protected int getLayout() {
		return R.layout.audience_targeting_mbmessage_default;
	}
}
