package com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.AudienceTargetingRenderer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class BlogRenderer extends AudienceTargetingRenderer {

	@Override
	protected View fillView(final View view, final Object object) throws JSONException {
		JSONObject jsonObject = (JSONObject) object;

		TextView title = (TextView) view.findViewById(R.id.audience_blog_title);
		title.setText(jsonObject.getString("title"));

		TextView content = (TextView) view.findViewById(R.id.audience_blog_content);
		content.setText(Html.fromHtml(jsonObject.getString("content")));

		return view;
	}

	@Override
	protected int getLayout() {
		return R.layout.audience_targeting_blog_default;
	}
}
