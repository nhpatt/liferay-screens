package com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers;

import android.content.Context;
import android.view.View;

import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.AudienceTargetingRenderer;

import org.json.JSONException;

/**
 * Created by nhpatt on 30/06/2015.
 */
public class FileRenderer extends AudienceTargetingRenderer {

	@Override
	public View render(AudienceTargetingResult result, final Object object, Context context) {

		new Thread(new DownloadTask(context, object)).start();

		return null;
	}

	@Override
	protected View fillView(View view, AudienceTargetingResult result, Object jsonObject) throws JSONException {
		return null;
	}

	@Override
	protected int getLayout() {
		return 0;
	}
}
