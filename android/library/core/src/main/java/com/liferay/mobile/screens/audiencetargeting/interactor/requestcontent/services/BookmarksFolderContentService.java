package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.bookmarksfolder.BookmarksFolderService;

/**
 * @author Javier Gamarra
 */
public class BookmarksFolderContentService extends AudienceTargetingContentService {
	@Override
	public void retrieveBySessionAndClassPK(final Session session, final Integer classPK) throws Exception {
		BookmarksFolderService bookmarksFolderService = new BookmarksFolderService(session);
		bookmarksFolderService.getFolder(classPK);
	}
}
