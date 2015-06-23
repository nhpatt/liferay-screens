package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services;

/**
 * @author Javier Gamarra
 */
public class AudienceTargetingServiceFactory {

	public static AudienceTargetingContentService getContentServiceByClassName(final String className) {
		switch (className) {
			case "com.liferay.portlet.wiki.model.WikiPage":
				return new WikiPageContentService();
			case "com.liferay.portlet.documentlibrary.model.DLFolder":
				return new DLFolderContentService();
			case "com.liferay.portlet.bookmarks.model.BookmarksFolder":
				return new BookmarksFolderContentService();
			case "com.liferay.portlet.blogs.model.BlogsEntry":
				return new BlogsEntryContentService();
			case "com.liferay.portlet.messageboards.model.MBMessage":
				return new MBMessageContentService();
			case "com.liferay.portlet.bookmarks.model.BookmarksEntry":
				return new BookmarksEntryContentService();
			case "com.liferay.portlet.journal.model.JournalArticle":
				return new JournalArticleContentService();
			case "com.liferay.portlet.journal.model.JournalFolder":
				return new JournalFolderContentService();
			case "com.liferay.portlet.documentlibrary.model.DLFileEntry":
				return new DLFileEntryContentService();
		}
		return null;
	}
}
