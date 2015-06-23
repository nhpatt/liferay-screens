package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.journalarticle.JournalArticleService;

/**
 * @author Javier Gamarra
 */
public class JournalArticleContentService extends AudienceTargetingContentService {
	@Override
	public void retrieveBySessionAndClassPK(final Session session, final Integer classPK) throws Exception {
		JournalArticleService journalArticleService = new JournalArticleService(session);
		//FIXME
//		journalArticleService.getArticle()
	}
}
