package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.wikipage.WikiPageService;
import com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.AudienceTargetingJSONRequestedCallback;
import com.liferay.mobile.screens.context.SessionContext;

/**
 * @author Javier Gamarra
 */
public class WikiPageContentService extends AudienceTargetingContentService {
	@Override
	public void retrieveBySessionAndClassPK(final Integer classPK, String className, Integer screenletId) {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new AudienceTargetingJSONRequestedCallback(screenletId, className));
		WikiPageService wikiPageService = new WikiPageService(session);
		//TODO does not work
//		wikiPageService.getPage();
	}
}
