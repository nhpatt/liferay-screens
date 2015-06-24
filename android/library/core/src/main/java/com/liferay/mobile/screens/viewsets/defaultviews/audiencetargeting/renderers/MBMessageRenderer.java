package com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers;

import android.view.View;
import android.widget.TextView;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.AudienceTargetingRenderer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class MBMessageRenderer extends AudienceTargetingRenderer {
	@Override
	protected View fillView(final View view, final JSONObject jsonObject) throws JSONException {

		TextView title = (TextView) view.findViewById(R.id.audience_mb_body);
		title.setText(jsonObject.getString("body"));

		TextView content = (TextView) view.findViewById(R.id.audience_mb_username);
		content.setText(jsonObject.getString("userName"));


		return view;
	}

	@Override
	protected int getLayout() {
		return R.layout.audience_targeting_mbmessage_default;
	}
}
