package com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.services;

import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingResult;

/**
 * @author Javier Gamarra
 */
public abstract class AudienceTargetingContentService {

	public abstract void retrieveBySessionAndClassPK(AudienceTargetingResult result, Integer screenletId)
		throws Exception;
}
