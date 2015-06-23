package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.blogsentry.BlogsEntryService;

/**
 * @author Javier Gamarra
 */
public class BlogsEntryContentService extends AudienceTargetingContentService {
	@Override
	public void retrieveBySessionAndClassPK(final Session session, final Integer classPK) throws Exception {
		BlogsEntryService blogsEntryService = new BlogsEntryService(session);
		blogsEntryService.getEntry(classPK);
	}
}
