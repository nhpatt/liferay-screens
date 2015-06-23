package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.bookmarksentry.BookmarksEntryService;

/**
 * @author Javier Gamarra
 */
public class BookmarksEntryContentService extends AudienceTargetingContentService {
	@Override
	public void retrieveBySessionAndClassPK(final Session session, final Integer classPK) throws Exception {
		BookmarksEntryService bookmarksEntryService = new BookmarksEntryService(session);
		bookmarksEntryService.getEntry(classPK);
	}
}
