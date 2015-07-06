package com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting;

import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers.BlogRenderer;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers.BookmarkRenderer;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers.FileRenderer;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers.FolderRenderer;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers.ImageRenderer;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers.JournalArticleRenderer;
import com.liferay.mobile.screens.viewsets.defaultviews.audiencetargeting.renderers.MBMessageRenderer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingRendererFactory {
	public AudienceTargetingRenderer getRenderer(String className, final Object object) throws JSONException {
		switch (className) {
			case "com.liferay.portlet.documentlibrary.model.DLFolder":
			case "com.liferay.portlet.bookmarks.model.BookmarksFolder":
			case "com.liferay.portlet.journal.model.JournalFolder":
				return new FolderRenderer();
			case "com.liferay.portlet.blogs.model.BlogsEntry":
				return new BlogRenderer();
			case "com.liferay.portlet.messageboards.model.MBMessage":
				return new MBMessageRenderer();
			case "com.liferay.portlet.bookmarks.model.BookmarksEntry":
				return new BookmarkRenderer();
			case "com.liferay.portlet.journal.model.JournalArticle":
				return new JournalArticleRenderer();
			case "com.liferay.portlet.documentlibrary.model.DLFileEntry":
				JSONObject jsonObject = (JSONObject) object;
				String mimeType = jsonObject.getString("mimeType");
				if (isImage(mimeType)) {
					return new ImageRenderer();
				}
				else {
					return new FileRenderer();
				}
			default:
				return null;
		}
	}

	private boolean isImage(String mimeType) {
		return "image/png".equals(mimeType) || "image/jpeg".equals(mimeType) || "image/jpg".equals(mimeType);
	}
}
