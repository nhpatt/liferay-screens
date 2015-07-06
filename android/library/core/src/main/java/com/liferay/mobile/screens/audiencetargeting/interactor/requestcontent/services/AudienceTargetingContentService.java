package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services;

import com.liferay.mobile.android.service.Session;

/**
 * @author Javier Gamarra
 */
public abstract class AudienceTargetingContentService {

	public abstract void retrieveBySessionAndClassPK(Integer classPK, String className, Integer screenletId) throws Exception;
}
