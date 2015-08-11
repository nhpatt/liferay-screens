package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services;

/**
 * @author Javier Gamarra
 */
public abstract class AudienceTargetingContentService {

	public abstract void retrieveBySessionAndClassPK(Integer classPK, String className, Integer screenletId) throws Exception;
}
