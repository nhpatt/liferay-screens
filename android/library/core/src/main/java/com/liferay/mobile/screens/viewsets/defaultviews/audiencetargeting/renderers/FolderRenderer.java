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
public class FolderRenderer extends AudienceTargetingRenderer {

	@Override
	protected View fillView(final View view, final Object object) throws JSONException {
		JSONObject jsonObject = (JSONObject) object;
		TextView folderName = (TextView) view.findViewById(R.id.audience_folder_name);
		folderName.setText(jsonObject.getString("name"));
		return view;
	}

	@Override
	protected int getLayout() {
		return R.layout.audience_targeting_folder_default;
	}
}
