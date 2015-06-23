package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.wikipage.WikiPageService;

/**
 * @author Javier Gamarra
 */
public class WikiPageContentService extends AudienceTargetingContentService {
	@Override
	public void retrieveBySessionAndClassPK(final Session session, final Integer classPK) {
		WikiPageService wikiPageService = new WikiPageService(session);
		//FIXME does not work
//		wikiPageService.getPage();
	}
}
