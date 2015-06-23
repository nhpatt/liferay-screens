package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services;

import com.liferay.mobile.android.service.Session;

/**
 * @author Javier Gamarra
 */
public abstract class AudienceTargetingContentService {

	public abstract void retrieveBySessionAndClassPK(final Session session, Integer classPK) throws Exception;
}
