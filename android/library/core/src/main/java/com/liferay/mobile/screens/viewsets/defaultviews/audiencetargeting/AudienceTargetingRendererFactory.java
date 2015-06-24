package com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting;

import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers.BlogRenderer;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers.BookmarkRenderer;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers.FolderRenderer;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers.ImageRenderer;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers.MBMessageRenderer;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers.PlaceholderRenderer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingRendererFactory {
	public AudienceTargetingRenderer getRenderer(final JSONObject jsonObject) throws JSONException {
		//FIXME duck assigning is bad
		if (jsonObject.has("content")) {
			return new BlogRenderer();
		}
		else if (jsonObject.has("url")) {
			return new BookmarkRenderer();
		}
		else if (jsonObject.has("body")) {
			return new MBMessageRenderer();
		}
		else if (jsonObject.has("mimeType")) {
			return new ImageRenderer();
		}
		else if (jsonObject.has("treePath")) {
			return new FolderRenderer();
		}
		else {
			return new PlaceholderRenderer();
		}
	}
}
