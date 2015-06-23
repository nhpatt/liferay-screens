package com.liferay.mobile.screens.audiencetargeting;

import com.liferay.mobile.screens.audiencetargeting.interactor.loadscreenlets.AudienceTargetingLoadedEvent;
import com.liferay.mobile.screens.audiencetargeting.interactor.requestcontent.AudienceTargetingContentEvent;

/**
 * @author Javier Gamarra
 */
public interface AudienceTargetingListener {
	void onFailure(Exception exception);

	void onSuccess(AudienceTargetingLoadedEvent event);

	void onSuccess(AudienceTargetingContentEvent audienceTargetingContentEvent);
}
