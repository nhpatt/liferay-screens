package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.AudienceTargetingStringRequestedCallback;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v62.ScreensjournalarticleService;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class JournalArticleContentService extends AudienceTargetingContentService {
	@Override
	public void retrieveBySessionAndClassPK(final Integer classPK, String className, Integer screenletId) throws Exception {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new AudienceTargetingStringRequestedCallback(screenletId, className));
		ScreensjournalarticleService journalArticleService = new ScreensjournalarticleService(session);

		Locale locale = LiferayScreensContext.getContext().getResources().getConfiguration().locale;
		journalArticleService.getJournalArticle((int) LiferayServerContext.getGroupId(), classPK, locale.toString());
	}
}
