package com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers;

import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.AudienceTargetingRenderer;
import com.liferay.mobile.screens.webcontentdisplay.WebContentDisplayScreenlet;

import org.json.JSONException;

/**
 * @author Javier Gamarra
 */
public class JournalArticleRenderer extends AudienceTargetingRenderer {

	@Override
	protected View fillView(final View view, final Object object) throws JSONException {
		String html = (String) object;

		WebContentDisplayScreenlet webContent = (WebContentDisplayScreenlet) view.findViewById(R.id.audience_webcontent);
		webContent.onWebContentReceived(webContent, html);

		return view;
	}

	@Override
	protected int getLayout() {
		return R.layout.audience_targeting_journal_article_default;
	}
}
