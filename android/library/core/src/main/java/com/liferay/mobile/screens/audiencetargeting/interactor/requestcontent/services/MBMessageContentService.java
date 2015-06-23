package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.mbmessage.MBMessageService;

/**
 * @author Javier Gamarra
 */
public class MBMessageContentService extends AudienceTargetingContentService {
	@Override
	public void retrieveBySessionAndClassPK(final Session session, final Integer classPK) throws Exception {
		MBMessageService mbMessageService = new MBMessageService(session);
		mbMessageService.getMessage(classPK);
	}
}
