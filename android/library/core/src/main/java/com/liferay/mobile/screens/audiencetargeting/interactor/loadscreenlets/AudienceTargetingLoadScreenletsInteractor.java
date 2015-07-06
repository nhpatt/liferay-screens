package com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets;

import com.liferay.mobile.screens.audiencetargeting.AudienceTargetingListener;
import com.liferay.mobile.screens.audiencetargeting.UserContext;
import com.liferay.mobile.screens.audiencetargeting.interactor.AudienceTargetingInteractor;
import com.liferay.mobile.screens.base.interactor.Interactor;

/**
 * @author Javier Gamarra
 */
public interface AudienceTargetingLoadScreenletsInteractor extends AudienceTargetingInteractor {

	void getScreenlets(String screenletApp, Long groupId, UserContext userContext) throws Exception;

	void getScreenlets(String screenletApp, Long groupId, String placeholder, UserContext userContext)
		throws Exception;

}
