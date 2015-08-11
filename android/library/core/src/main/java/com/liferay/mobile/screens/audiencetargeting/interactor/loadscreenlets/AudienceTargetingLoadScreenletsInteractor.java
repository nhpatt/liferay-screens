package com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets;

import com.liferay.mobile.screens.audiencetargeting.UserContext;
import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingInteractor;

/**
 * @author Javier Gamarra
 */
public interface AudienceTargetingLoadScreenletsInteractor extends AudienceTargetingInteractor {

	void getScreenlets(String screenletApp, Long groupId, UserContext userContext) throws Exception;

	void getScreenlets(String screenletApp, Long groupId, String placeholder, UserContext userContext)
		throws Exception;

}
