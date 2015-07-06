package com.liferay.mobile.screens.audiencetargeting;

import com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets.AudienceTargetingScreenletsLoadedEvent;
import com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.AudienceTargetingContentRequestedEvent;

/**
 * @author Javier Gamarra
 */
public interface AudienceTargetingListener {
	void onFailure(Exception exception);

	void onSuccess(AudienceTargetingScreenletsLoadedEvent event);

	void onSuccess(AudienceTargetingContentRequestedEvent event);
}
